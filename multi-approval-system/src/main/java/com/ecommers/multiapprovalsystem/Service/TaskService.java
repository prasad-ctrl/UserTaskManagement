package com.ecommers.multiapprovalsystem.Service;

import com.ecommers.multiapprovalsystem.DTO.CommentRequest;
import com.ecommers.multiapprovalsystem.DTO.TaskRequest;
import com.ecommers.multiapprovalsystem.DTO.TaskResponse;

import java.util.List;

public interface TaskService {
    public TaskResponse createTask(String token,TaskRequest request);
    public List<TaskResponse> getAllTask();
    public List<TaskResponse> getAllPendingTask();
    public TaskResponse approveTask(String token, String taskId);
    public TaskResponse commentTask(String token, CommentRequest commentRequest);
    public TaskResponse getTask(String taskId);
}
