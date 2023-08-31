package com.jst.common.dto;

public interface EsDcResultMerge<T> {

    GlobalResult<T> mergeResultList(EsDcResult<T> esDcResult);

}
