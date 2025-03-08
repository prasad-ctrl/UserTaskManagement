package com.ecommers.multiapprovalsystem.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity(name = "Login")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    @Id
    @UuidGenerator
    private String id;
    private String userId;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String loginId;
}
