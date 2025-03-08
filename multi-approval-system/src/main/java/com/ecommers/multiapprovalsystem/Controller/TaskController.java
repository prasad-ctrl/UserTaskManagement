package com.ecommers.multiapprovalsystem.Controller;

import com.ecommers.multiapprovalsystem.Component.JwtUtil;
import com.ecommers.multiapprovalsystem.DTO.CommentRequest;
import com.ecommers.multiapprovalsystem.DTO.TaskRequest;
import com.ecommers.multiapprovalsystem.DTO.TaskResponse;
import com.ecommers.multiapprovalsystem.Service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private JwtUtil jwtUtil;

    private TaskService service;

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request,
                                                   @RequestHeader("X-Session-Token") String token) throws Exception {
        return new ResponseEntity<>(service.createTask(token,request), HttpStatus.OK);
    }
    @PostMapping("/allTask")
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        return new ResponseEntity<>(service.getAllTask(),HttpStatus.OK);
    }
    @PostMapping("/pendingTask")
    public ResponseEntity<List<TaskResponse>> getPendingTasks(){
        return new ResponseEntity<>(service.getAllTask(),HttpStatus.OK);
    }
    @PostMapping("/approve")
    public ResponseEntity<TaskResponse> approveTask(@RequestParam String taskId,
                                                    @RequestHeader("X-Session-Token") String token){
        return new ResponseEntity<>(service.approveTask(token, taskId),HttpStatus.OK);
    }
    @PostMapping("/comment")
    public ResponseEntity<TaskResponse> commentTask(@RequestBody CommentRequest commentRequest,
                                                    @RequestHeader("X-Session-Token") String token){
        return new ResponseEntity<>(service.commentTask(token, commentRequest),HttpStatus.OK);
    }

}
