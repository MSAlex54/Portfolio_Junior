package ru.my_prtfolio.spring.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.my_prtfolio.spring.persist.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
