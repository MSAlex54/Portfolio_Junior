package ru.my_prtfolio.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.my_prtfolio.spring.persist.entity.User;
import ru.my_prtfolio.spring.service.UserService;

import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path="/all", produces = "application/json")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping(path = "/{id}/id")
    public User findById(@PathVariable ("id") long id){
        return userService.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public void createUser (@RequestBody User user){
        if (user.getId()!=null){
            throw new IllegalArgumentException("Id field in create request");
        }
        userService.save(user);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser (@RequestBody User user){
        userService.save(user);
    }

    @DeleteMapping(path = "/{id}/id")
    public void deleteUser (@PathVariable ("id") long id){
        userService.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<illegalArgumentErrorResponse> illegalArgumentExceptionHandler (IllegalArgumentException exc) {
        illegalArgumentErrorResponse response = new illegalArgumentErrorResponse(HttpStatus.BAD_REQUEST.value(),exc.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<NotFoundErrorResponse> notFoundExceptionHandler (NotFoundException exc) {
        NotFoundErrorResponse response = new NotFoundErrorResponse(HttpStatus.NOT_FOUND.value(), User.class.getSimpleName() + " object not found",System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
