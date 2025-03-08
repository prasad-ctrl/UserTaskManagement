package com.ecommers.multiapprovalsystem.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity(name = "Login")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {
    @Id
    @UuidGenerator
    private String id;
    private String userId;
    private String loginId;
}
