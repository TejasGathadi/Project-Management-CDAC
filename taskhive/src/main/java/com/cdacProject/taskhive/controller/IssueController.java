package com.cdacProject.taskhive.controller;


import com.cdacProject.taskhive.dto.IssueDTO;
import com.cdacProject.taskhive.model.Issues;
import com.cdacProject.taskhive.model.User;
import com.cdacProject.taskhive.request.IssueRequest;
import com.cdacProject.taskhive.response.MessageResponse;
import com.cdacProject.taskhive.service.IssueService;
import com.cdacProject.taskhive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;


    @GetMapping("/{issueId}")
    public ResponseEntity<Long> getIssueById(@PathVariable Long issueId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueById(issueId).getId());
    }


    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issues>> getIssueByProjectId(@PathVariable Long projectId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }


    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issueRequest, @RequestHeader("Authorization") String token) throws Exception{
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());



            Issues createdIssue = issueService.createIssue(issueRequest, tokenUser);

            IssueDTO issueDTO = new IssueDTO();

            issueDTO.setDescription(createdIssue.getDescription());
            issueDTO.setDueDate(createdIssue.getDueDate());
            issueDTO.setId(createdIssue.getId());
            issueDTO.setPriority(createdIssue.getPriority());
            issueDTO.setProject(createdIssue.getProject());
            issueDTO.setProjectID(createdIssue.getProjectID());
            issueDTO.setStatus(createdIssue.getStatus());
            issueDTO.setTitle(createdIssue.getTitle());
            issueDTO.setTags(createdIssue.getTags());
            issueDTO.setAssignee(createdIssue.getAssignee());

            return ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId, @RequestHeader("Authorization") String token) throws Exception{
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Issue Deleted");

        return ResponseEntity.ok(res);
    }


    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issues> addUserToIssue(@PathVariable Long issueId, @PathVariable Long userId) throws Exception{

        Issues issues = issueService.addUserToIssue(issueId, userId);

        return ResponseEntity.ok(issues);

    }



    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issues> updateIssueStatus(@PathVariable String status, @PathVariable Long issueId) throws Exception{
        Issues issues = issueService.updateStatus(issueId,status);
        return ResponseEntity.ok(issues);

    }



}
