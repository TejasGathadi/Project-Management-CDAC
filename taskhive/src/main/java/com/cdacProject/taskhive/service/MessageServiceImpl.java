package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.model.Chat;
import com.cdacProject.taskhive.model.Message;
import com.cdacProject.taskhive.model.User;
import com.cdacProject.taskhive.repository.MessageRepository;
import com.cdacProject.taskhive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{


    @Autowired
    private ProjectService projectService;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private MessageRepository messageRepository;




    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepository.findById(senderId)
                .orElseThrow(()-> new Exception("User Not Found with Id " + senderId));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);
        Message savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);
        return savedMessage;

    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {

        Chat chat = projectService.getChatByProjectId((projectId));
        List<Message> findByChatIdOrderByCreatedAtAsc = messageRepository.findByChatIdOrderByCreatedAtAsc(projectId);
        return findByChatIdOrderByCreatedAtAsc;

    }
}
