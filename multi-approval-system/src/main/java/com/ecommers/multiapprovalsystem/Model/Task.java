package com.ecommers.multiapprovalsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity(name = "Task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private String userId;
    private TaskStatus status;
    private Integer approvalCount;
}
