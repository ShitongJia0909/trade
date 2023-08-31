package com.jst.jstdao.repository;

import com.jst.jstdao.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, String> {

}
