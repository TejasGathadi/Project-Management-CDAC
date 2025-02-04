package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MessageService {

    Message sendMessage(Long senderId, Long projectId, String content) throws Exception;

    List<Message> getMessageByProjectId(Long projectId) throws Exception;

}
