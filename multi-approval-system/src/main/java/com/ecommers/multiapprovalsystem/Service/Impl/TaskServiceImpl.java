package com.ecommers.multiapprovalsystem.Service.Impl;

import com.ecommers.multiapprovalsystem.Component.JwtUtil;
import com.ecommers.multiapprovalsystem.DTO.CommentRequest;
import com.ecommers.multiapprovalsystem.DTO.TaskRequest;
import com.ecommers.multiapprovalsystem.DTO.TaskResponse;
import com.ecommers.multiapprovalsystem.Model.*;
import com.ecommers.multiapprovalsystem.Repository.TaskRepository;
import com.ecommers.multiapprovalsystem.Repository.TaskToCommentMapRepository;
import com.ecommers.multiapprovalsystem.Repository.UserRepo;
import com.ecommers.multiapprovalsystem.Repository.UserToTaskMapRepository;
import com.ecommers.multiapprovalsystem.Service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserToTaskMapRepository userToTaskMapRepository;

    @Autowired
    private TaskToCommentMapRepository taskToCommentMapRepository;

    @Autowired
    private UserRepo userRepo;
    @Override
    public TaskResponse createTask(String token,TaskRequest request) {
        try{
            if(!jwtUtil.verifyToken(token)){
                log.error("Invalid session");
                throw new Exception("Invalid Session");
            }
            validateRequest(request);
            String userId = jwtUtil.getUsername(token);
            Task task = Task.builder()
                    .name(request.getTaskName())
                    .userId(userId)
                    .description(request.getTaskDescription())
                    .approvalCount(0)
                    .status(TaskStatus.NEW)
                    .build();
            Task savedTask = taskRepository.save(task);
            UserToTaskMap userToTaskMap = UserToTaskMap.builder()
                    .taskId(savedTask.getId())
                    .userId(savedTask.getUserId())
                    .build();
            userToTaskMapRepository.save(userToTaskMap);
            TaskResponse response = TaskResponse.builder()
                    .taskName(task.getName())
                    .taskDescription(task.getDescription())
                    .approvalCount(task.getApprovalCount())
                    .taskStatus(task.getStatus())
                    .build();
            return response;
        }catch (Exception e){
            log.error("Error while creating task Error: {}",e.getMessage());
        }
        return null;
    }

    @Override
    public List<TaskResponse> getAllTask() {
       return taskRepository.findAll().stream().map(task -> getTask(task.getId())).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getAllPendingTask() {
        List<Task> taskResponses = taskRepository.findByStatusIn(List.of(TaskStatus.PENDING,TaskStatus.NEW));
        return taskResponses.stream().map(task -> getTask(task.getId())).collect(Collectors.toList());
    }

    @Override
    public TaskResponse approveTask(String token, String taskId) {
        try{
            if(!jwtUtil.verifyToken(token)){
                log.error("Invalid session");
                throw new Exception("Invalid Session");
            }
            Task task = taskRepository.findById(taskId).orElse(null);
            if(task == null) return  null;
            task.setApprovalCount(task.getApprovalCount() + 1);
            if(task.getApprovalCount()>=3) task.setStatus(TaskStatus.APPROVED);
            taskRepository.save(task);
            List<TaskToCommentMap> taskToCommentMap = taskToCommentMapRepository.findAllByTaskId(taskId);
            return TaskResponse.builder()
                    .taskId(taskId)
                    .approvalCount(task.getApprovalCount())
                    .taskStatus(task.getStatus())
                    .taskDescription(task.getDescription())
                    .taskName(task.getName())
                    .comments(taskToCommentMap.stream().map(TaskToCommentMap::getComment).collect(Collectors.toList()))
                    .build();
        }catch (Exception e){
            log.error("Error while approving task taskId {}",taskId);
        }
        return null;
    }

    @Override
    public TaskResponse commentTask(String token, CommentRequest commentRequest) {
        try{
            if(!jwtUtil.verifyToken(token)){
                log.error("Invalid session");
                throw new Exception("Invalid Session");
            }
            String username = jwtUtil.getUsername(token);
            User user = userRepo.findByEmail(username);
            Task task = taskRepository.findById(commentRequest.getTaskId()).orElse(null);
            if(user == null || task == null) return null;
            validateCommentRequest(commentRequest);
            TaskToCommentMap taskToComment = TaskToCommentMap.builder()
                    .userId(user.getId())
                    .comment(commentRequest.getComment())
                    .taskId(commentRequest.getTaskId())
                    .build();
            taskToCommentMapRepository.save(taskToComment);
            List<TaskToCommentMap> taskToCommentMap = taskToCommentMapRepository.findAllByTaskId(commentRequest.getTaskId());
            return TaskResponse.builder()
                    .taskId(task.getId())
                    .approvalCount(task.getApprovalCount())
                    .taskStatus(task.getStatus())
                    .taskDescription(task.getDescription())
                    .taskName(task.getName())
                    .comments(taskToCommentMap.stream().map(TaskToCommentMap::getComment).collect(Collectors.toList()))
                    .build();
        }catch (Exception e){
            log.error("error while commenting task {} Error {}",commentRequest.getTaskId(),e.getMessage());
        }
        return null;
    }

    @Override
    public TaskResponse getTask(String taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        List<TaskToCommentMap> taskToCommentMap = taskToCommentMapRepository.findAllByTaskId(taskId);
        if(task == null) return null;
        TaskResponse response = TaskResponse.builder()
                .taskId(task.getId())
                .approvalCount(task.getApprovalCount())
                .taskStatus(task.getStatus())
                .taskDescription(task.getDescription())
                .taskName(task.getName())
                .comments(taskToCommentMap.stream().map(TaskToCommentMap::getComment).collect(Collectors.toList()))
                .build();
        return response;
    }

    private void validateCommentRequest(CommentRequest commentRequest) throws Exception {
        if(commentRequest.getComment().isBlank() || commentRequest.getTaskId().isBlank())
            throw new Exception("TaskId or comment is empty");
    }

    private void validateRequest(TaskRequest request) throws Exception {
        if(request.getTaskName().isBlank() || request.getTaskDescription().isBlank())
            throw new Exception("Task name or description is empty");
    }
}
