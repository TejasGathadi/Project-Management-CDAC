package com.cdacProject.taskhive.repository;

import com.cdacProject.taskhive.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
