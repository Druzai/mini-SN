package ru.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
