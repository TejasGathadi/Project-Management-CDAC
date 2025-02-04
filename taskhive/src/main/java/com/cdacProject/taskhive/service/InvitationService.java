package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

     void sendInvitation(String email, Long projectId ) throws MessagingException;

     Invitation accepectInvitation(String token, Long userId) throws Exception;

     String getTokenByUserMail(String userEmail);

     void deleteToken(String token);

}
