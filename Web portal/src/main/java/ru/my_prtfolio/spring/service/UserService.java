package ru.my_prtfolio.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.my_prtfolio.spring.persist.entity.User;
import ru.my_prtfolio.spring.persist.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    public Page<User> filterByAge(Integer minAge, Integer maxAge, Pageable pageable) {
        if (minAge == null && maxAge == null) {
            return repository.findAll(pageable);
        }
        if (minAge != null && maxAge == null) {
            return repository.findByAgeGreaterThanEqual(minAge, pageable);
        }
        if (minAge == null) {
            return repository.findByAgeLessThanEqual(maxAge, pageable);
        }
        return repository.findByAgeBetween(minAge, maxAge, pageable);
    }

    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
