package com.ecommers.multiapprovalsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity(name = "UserToCommentMap")
public class UserToCommentMap {
    @Id
    @UuidGenerator
    private String id;
    private String comment;
    private String userId;
    private String taskId;
}
