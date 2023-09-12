package com.jst.jstdao.repository;

import com.jst.jstdao.model.TDatasource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TDataSourceRepository extends JpaRepository<TDatasource, String> {

    List<TDatasource> findAllByKafkaTopicEquals(String topic);

    TDatasource findDistinctByCodeAndGroupCode(String code, String groupCode);

    TDatasource findDistinctByCode(String code);

    List<TDatasource> findAllByGroupCode(String groupCode);

    @Query(value = "select t from TDatasource t where t.name like %?1% and t.code like %?2% and t.groupCode like %?3%")
    List<TDatasource> findByFilterLike(String name, String code, String groupCode);

}
