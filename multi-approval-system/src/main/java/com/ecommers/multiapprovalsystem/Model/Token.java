package com.ecommers.multiapprovalsystem.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity(name = "Login")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @UuidGenerator
    private String id;
    private String userId;
    @Column(unique = true, nullable = false)
    private String loginId;
    @Column(nullable = false)
    private Date expiryDate;
}
