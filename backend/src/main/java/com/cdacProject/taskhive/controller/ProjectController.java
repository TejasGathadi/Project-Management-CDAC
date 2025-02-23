package com.cdacProject.taskhive.controller;


import com.cdacProject.taskhive.model.*;
import com.cdacProject.taskhive.request.InviteRequest;
import com.cdacProject.taskhive.response.MessageResponse;
import com.cdacProject.taskhive.service.InvitationService;
import com.cdacProject.taskhive.service.ProjectService;
import com.cdacProject.taskhive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;


    @Autowired
    private InvitationService invitationService;


    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category,tag);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }





    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectsById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        if(user!= null){
        Project projects = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projects, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }





    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project

    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Project createdProjects = projectService.createProject(project,  user);



        return new ResponseEntity<>(createdProjects, HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project

    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        if(user != null){
        Project updatedProject = projectService.updateProject(project, projectId);

        return new ResponseEntity<>(updatedProject, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId() );

        MessageResponse messageResponse = new MessageResponse("Project Deleted Successfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }




    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProject(keyword, user);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }



    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        if(user != null){
        Chat chat = projectService.getChatByProjectId(projectId);

        return new ResponseEntity<>(chat, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }



    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestBody InviteRequest inviteRequest,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);


        invitationService.sendInvitation(inviteRequest.getEmail(), inviteRequest.getProjectId());
        MessageResponse res = new MessageResponse("User Invited to Project Successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }



    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInviteProject(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Invitation invitation = invitationService.accepectInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());

        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }















}
