package com.jst.jstdao.repository;

import com.jst.jstdao.model.TaskInfo;
import javafx.concurrent.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskInfoRepository extends JpaRepository<TaskInfo, String> {


    @Modifying
    @Query("update TaskInfo set status = ?2, startTime = ?3, modifyTime= ?4 where taskName = ?1 and status= ?5 and startTime = ?6 and modifyTime= ?7")
    int lockForUpdate(String taskName, int newStatus, Timestamp newStartTime, Timestamp newModifyTime, int status, Timestamp startTime, Timestamp modifyTime);

    List<TaskInfo> findAllByEnbaleAndStatus(int enable, int status);

    @Query(value = "select t from TaskInfo t where t.taskName like %?1%")
    List<TaskInfo> findByTaskNameLike(String taskName);






}
