package com.ecommers.multiapprovalsystem.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Builder
public class User {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
}
