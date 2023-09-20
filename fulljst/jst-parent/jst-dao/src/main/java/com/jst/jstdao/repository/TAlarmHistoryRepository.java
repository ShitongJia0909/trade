package com.jst.jstdao.repository;

import com.jst.jstdao.model.TAlarmHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TAlarmHistoryRepository extends JpaRepository<TAlarmHistory, String> {



}
