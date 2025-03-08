package com.ecommers.multiapprovalsystem.Repository;

import com.ecommers.multiapprovalsystem.Model.TaskToCommentMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskToCommentMapRepository extends JpaRepository<TaskToCommentMap,String> {
    TaskToCommentMap findByTaskId(String taskId);

    List<TaskToCommentMap>  findAllByTaskId(String taskId);
}
