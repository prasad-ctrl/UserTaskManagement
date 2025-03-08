package com.ecommers.multiapprovalsystem.Repository;

import com.ecommers.multiapprovalsystem.Model.Task;
import com.ecommers.multiapprovalsystem.Model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,String> {
    List<Task> findByStatusIn(List<TaskStatus> statuses);

    public Optional<Task> findById(String id);

}
