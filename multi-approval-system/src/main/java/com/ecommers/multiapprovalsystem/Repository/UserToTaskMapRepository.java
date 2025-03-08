package com.ecommers.multiapprovalsystem.Repository;

import com.ecommers.multiapprovalsystem.Model.UserToTaskMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToTaskMapRepository extends JpaRepository<UserToTaskMap,String> {

}
