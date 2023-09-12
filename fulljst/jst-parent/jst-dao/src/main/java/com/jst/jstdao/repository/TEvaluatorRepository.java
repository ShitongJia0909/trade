package com.jst.jstdao.repository;

import com.jst.jstdao.model.TEvaluator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TEvaluatorRepository extends JpaRepository<TEvaluator, String> {

    TEvaluator findDistinctByName(String name);

    List<TEvaluator> findAllByGroupCodeAndDsCode(String groupCode, String dsCode);

    TEvaluator findDistinctByGroupCodeAndDsCodeAndName(String groupCode, String dsCode, String name);

    @Query(value = "select t from TEvaluator t where t.name like %?1% and t.groupCode like %?2% and t.dsCode like %?3% and t.owner like %?4% ")
    List<TEvaluator> findByFilterLike(String name, String groupCode, String dsCode, String owner);

}
