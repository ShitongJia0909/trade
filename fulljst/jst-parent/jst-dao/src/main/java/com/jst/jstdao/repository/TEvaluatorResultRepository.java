package com.jst.jstdao.repository;

import com.jst.jstdao.model.TEvaluatorResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TEvaluatorResultRepository extends JpaRepository<TEvaluatorResult, String> {

    TEvaluatorResult findDistinctByFieldsExpAndEvalNameAndLogTime(String fieldsExp, String EvalName, Date logTime);



}
