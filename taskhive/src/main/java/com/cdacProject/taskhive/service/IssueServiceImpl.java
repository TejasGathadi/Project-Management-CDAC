package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.model.Issues;
import com.cdacProject.taskhive.model.Project;
import com.cdacProject.taskhive.model.User;
import com.cdacProject.taskhive.repository.IssueRepository;
import com.cdacProject.taskhive.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements  IssueService{

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;



    @Override
    public Issues getIssueById(Long issueId) throws Exception {
       Optional<Issues> issues = issueRepository.findById(issueId);

       if(issues.isPresent()){
           return issues.get();
       }

        throw new Exception("No Issues Found with IssueId " + issueId);
    }

    @Override
    public List<Issues> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issues createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectId());
        Issues issues = new Issues();
        issues.setTitle(issueRequest.getTitle());
        issues.setDescription(issueRequest.getDescription());
        issues.setStatus(issueRequest.getStatus());
        issues.setProjectID(issueRequest.getProjectId());
        issues.setPriority(issueRequest.getPriority());
        issues.setDueDate(issueRequest.getDueDate());
        issues.setProject(project);

        return issueRepository.save(issues);

    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssueById(issueId);
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issues addUserToIssue(Long issueId, Long userId) throws Exception {

        User user = userService.findUserById(userId);
        Issues issue = getIssueById(issueId);

        issue.setAssignee(user);
        return issueRepository.save(issue);

    }

    @Override
    public Issues updateStatus(Long issueId, String status) throws Exception {
        Issues issues = getIssueById(issueId);
        issues.setStatus(status);
        return issueRepository.save(issues);
    }
}
