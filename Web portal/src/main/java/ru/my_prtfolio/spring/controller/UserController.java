package ru.my_prtfolio.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.my_prtfolio.spring.persist.entity.User;
import ru.my_prtfolio.spring.persist.repo.RoleRepository;
import ru.my_prtfolio.spring.rest.NotFoundException;
import ru.my_prtfolio.spring.service.UserService;

import javax.validation.Valid;
import java.util.regex.Pattern;


@RequestMapping("/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/list")
    public String userList(Model model,
                                @RequestParam(name = "minAge", required = false) Integer minAge,
                                @RequestParam(name = "maxAge", required = false) Integer maxAge,
                                @RequestParam (name = "page", defaultValue = "1", required = false) Integer page,
                                @RequestParam (name = "size", defaultValue = "5", required = false) Integer size
                            ) {
        logger.info("User list. With minAge = {} and maxAge = {}", minAge, maxAge);

        Page<User> userPage = userService.filterByAge(minAge, maxAge,
                PageRequest.of(page- 1, size)
        );
        model.addAttribute("usersPage", userPage);
        model.addAttribute("prevPageNumber", userPage.hasPrevious() ? userPage.previousPageable().getPageNumber() + 1 : -1);
        model.addAttribute("nextPageNumber", userPage.hasNext() ? userPage.nextPageable().getPageNumber() + 1 : -1);
        return "users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String createUser(Model model) {
        logger.info("Create user form");
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path="/update/{id}")
    public String updateById(Model model, @PathVariable(value = "id") Long userId) {
        model.addAttribute("user",  userService.findById(userId)
                .orElseThrow(() -> new NotFoundException()));
        model.addAttribute("roles", roleRepository.findAll());
        return "user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path="/remove/{id}")
    public String removeById(@PathVariable(value = "id") Long userId) {
        userService.delete(userId);
        return "redirect:/user/list";
    }

    @PostMapping
    public String saveUser(@Valid User user, BindingResult bindingResult) {
        logger.info("Save user method");

        if (bindingResult.hasErrors()) {
            return "user";
        }
        logger.info("password {} repeat {}", user.getPassword(), user.getRepeatPassword());
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            bindingResult.rejectValue("repeatPassword", "", "пароли не совпадают");
            return "user";
        }

        //Password validation
        Pattern kirillPat = Pattern.compile("^.*[А-Яа-я].*$");
        Pattern mainPat = Pattern.compile("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$");//Честно стырено с Хабра, но всё понятно
        if (!(mainPat.matcher(user.getPassword()).matches()& !kirillPat.matcher(user.getPassword()).matches())) {
            bindingResult.rejectValue("password", "", "Пароль слишком простой");
            return "user";
        }

        userService.save(user);
        return "redirect:/user/list";
    }
}