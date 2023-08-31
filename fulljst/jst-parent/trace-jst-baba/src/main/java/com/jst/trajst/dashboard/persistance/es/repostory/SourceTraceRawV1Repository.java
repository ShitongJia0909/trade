package com.jst.trajst.dashboard.persistance.es.repostory;

import com.jst.common.dto.GlobalResult;
import com.jst.trajst.dashboard.persistance.es.model.TraceRawData;

import java.util.List;

public interface SourceTraceRawV1Repository {
    GlobalResult<List<TraceRawData>> findRawLogData(TraceRawData traceRawData);


}
