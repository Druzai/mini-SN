package ru.app.repositories;

import ru.app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с таблицей "role".
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
