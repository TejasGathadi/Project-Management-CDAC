package com.cdacProject.taskhive.repository;

import com.cdacProject.taskhive.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findByChatIdOrderByCreatedAtAsc(Long chatIds);


}
