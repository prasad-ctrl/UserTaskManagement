package com.ecommers.multiapprovalsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TaskToCommentMap")
@Builder
public class TaskToCommentMap {
    @Id
    @UuidGenerator
    private String id;
    private String comment;
    private String userId;
    private String taskId;
}
