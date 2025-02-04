package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.model.Issues;
import com.cdacProject.taskhive.model.User;
import com.cdacProject.taskhive.request.IssueRequest;

import java.util.List;

public interface IssueService {


    Issues getIssueById(Long issueId) throws Exception;

    List<Issues> getIssueByProjectId(Long projectId) throws Exception;

    Issues createIssue(IssueRequest issueRequest,  User user) throws Exception;


    void deleteIssue(Long issueId, Long userId) throws Exception;

    Issues addUserToIssue(Long issueId, Long userId) throws Exception;

    Issues updateStatus(Long issueId, String status) throws Exception;




}
