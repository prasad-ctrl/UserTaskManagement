package com.ecommers.multiapprovalsystem.Repository;

import com.ecommers.multiapprovalsystem.Model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token,String> {
    public Token findByUserId(String userId);
    public Token findByLoginId(String loginId);

    public void deleteByLoginId(String loginId);

}
