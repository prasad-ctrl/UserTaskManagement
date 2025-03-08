package com.ecommers.multiapprovalsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<com.ecommers.multiapprovalsystem.Model.User,String> {
    public com.ecommers.multiapprovalsystem.Model.User findByEmail(String email);

}
