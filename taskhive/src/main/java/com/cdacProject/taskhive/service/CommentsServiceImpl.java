package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.model.Comments;
import com.cdacProject.taskhive.model.Issues;
import com.cdacProject.taskhive.model.User;
import com.cdacProject.taskhive.repository.CommentsRepository;
import com.cdacProject.taskhive.repository.IssueRepository;
import com.cdacProject.taskhive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements  CommentsService{


    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private IssueRepository issueRepository;


    @Autowired
    private UserRepository userRepository;



    @Override
    public Comments createComments(Long issueId, Long userId, String contents) throws Exception {
        Optional<Issues> issuesOptional = issueRepository.findById(issueId);

        Optional<User> optionalUser = userRepository.findById(userId);


        if(issuesOptional.isEmpty()){
            throw new Exception("Issue Not Found with Id : " + issueId);
        }
        if(optionalUser.isEmpty()){
            throw new Exception("User Not Found with id : " + userId);

        }



        Issues issues = issuesOptional.get();
        User user = optionalUser.get();

        Comments comments = new Comments();

        comments.setIssues(issues);
        comments.setUser(user);
        comments.setCreatedDateTime(LocalDateTime.now());
        comments.setContents(contents);

        Comments savedComment = commentsRepository.save(comments);

        issues.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {

        Optional<Comments> commentsOptional = commentsRepository.findById(commentId);

        Optional<User> userOptional = userRepository.findById(userId);

        if(commentsOptional.isEmpty()){
            throw new Exception("Comment Not Found : " +  commentId);
        }
        if(userOptional.isEmpty()){
            throw new Exception("User Not Found with id : " + userId);
        }

        Comments comments = commentsOptional.get();
        User user = userOptional.get();

        if(comments.getUser().equals(user)){
            commentsRepository.delete(comments);
        }else{
            throw new Exception("User Does not have Permission to delete this Comment");
        }

    }

    @Override
    public List<Comments> findCommentByIssueId(Long issueID) {
        return commentsRepository.findByIssuesId(issueID);
    }
}
