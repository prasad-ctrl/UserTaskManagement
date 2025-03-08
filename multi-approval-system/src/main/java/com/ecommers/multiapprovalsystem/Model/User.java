package com.ecommers.multiapprovalsystem.Model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
public class User {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private String email;
    private String password;
}
