package com.ecommers.multiapprovalsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity(name = "UserToTaskMap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserToTaskMap {
    @Id
    @UuidGenerator
    private String id;
    private String userId;
    private String taskId;
}
