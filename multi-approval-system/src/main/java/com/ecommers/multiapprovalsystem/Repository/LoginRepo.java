package com.ecommers.multiapprovalsystem.Repository;

import com.ecommers.multiapprovalsystem.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends JpaRepository<Login,String> {
    public Login findByEmail(String email);

    public Login findByUserId(String userId);


}
