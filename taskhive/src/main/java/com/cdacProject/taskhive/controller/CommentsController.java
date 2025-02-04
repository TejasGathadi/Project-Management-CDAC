package com.cdacProject.taskhive.controller;


import com.cdacProject.taskhive.model.Comments;
import com.cdacProject.taskhive.model.User;
import com.cdacProject.taskhive.request.CreateCommentRequest;
import com.cdacProject.taskhive.response.MessageResponse;
import com.cdacProject.taskhive.service.CommentsService;
import com.cdacProject.taskhive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;


    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Comments> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws  Exception
    {
        User user = userService.findUserProfileByJwt(jwt);
        Comments createdComment = commentsService.createComments(req.getIssueId(), user.getId(), req.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);

    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId,
                                                         @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        commentsService.deleteComment(commentId, user.getId());
        MessageResponse res = new MessageResponse();
        res.setMessage("Comment Deleted Successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comments>> getCommentByIssueId(
            @PathVariable Long issueId
    )
    {
        List<Comments> comments = commentsService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }





}
