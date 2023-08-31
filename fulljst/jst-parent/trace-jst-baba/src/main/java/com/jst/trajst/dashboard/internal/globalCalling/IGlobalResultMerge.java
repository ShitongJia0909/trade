package com.jst.trajst.dashboard.internal.globalCalling;

import java.util.List;

public interface IGlobalResultMerge<Output> {

    Output mergeResult(List<MultiThreadResult> input) throws Exception ;


}
