package com.jst.jstdao.repository;

import com.jst.jstdao.model.ZbGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZbGroupRepostory extends JpaRepository<ZbGroup, String> {

    @Query(value = "select t from ZbGroup t where t.groupName like %?1% order by t.modifyTime desc")
    List<ZbGroup> findByGroupNameLike(String groupName);

    List<ZbGroup> findByGroupId(String groupId);

    List<ZbGroup> findAllByGroupName(String groupName);


}
