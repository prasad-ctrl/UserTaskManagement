package com.ecommers.multiapprovalsystem.DTO;

import com.ecommers.multiapprovalsystem.Model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private String taskId;
    private String taskName;
    private String taskDescription;
    private TaskStatus taskStatus;
    private int approvalCount;
    private List<String> comments;

}
