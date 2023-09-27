package com.jst.jstdao.repository;

import com.jst.jstdao.model.ZbHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZbHostRepository extends JpaRepository<ZbHost, String> {

    @Query(value = "select h from ZbHost h where h.ip = ?1 and h.dc = ?2")
    List<ZbHost> findAllByIpAndDc(String ip, String dc);

    ZbHost findByHostidAndDc(String hostid, String idc);

    @Modifying
    @Query("delete from ZbHost h where h.dc = ?1")
    void deleteByDc(String dc);



}
