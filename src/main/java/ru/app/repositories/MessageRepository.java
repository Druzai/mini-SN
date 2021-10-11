package ru.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
