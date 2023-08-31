package com.jst.trajst.dashboard.view.controller.global.local.app;


import com.alibaba.fastjson.JSON;
import com.jst.common.Constances;
import com.jst.common.dto.GlobalResult;
import com.jst.common.exceptions.BaseRuntimeException;
import com.jst.trajst.dashboard.internal.globalCalling.GlobalEsResultAddMerge;
import com.jst.trajst.dashboard.persistance.es.model.TraceRawData;
import com.jst.trajst.dashboard.persistance.es.repostory.SourceTraceRawV1Repository;
import com.jst.trajst.dashboard.view.controller.global.BaseGlobalController;
import com.jst.trajst.dashboard.view.query.TraceDetailQuery;
import com.jst.trajst.dashboard.vo.BaseResponse;
import com.jst.trajst.dashboard.vo.dto.SpanChainDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static com.jst.trajst.dashboard.enums.ErrorCode.PARA_ILLEGAL_ERROR;

@RestController
@RequestMapping("/globalRaw")
public class GlobalController {

    private static Logger logger = LoggerFactory.getLogger(GlobalController.class);

    @Resource
    BaseGlobalController baseGlobalController;

    @Resource
    private SourceTraceRawV1Repository sourceTraceRawV1Repository;

    @PostMapping("/getTreeGlobalRawData")
    public BaseResponse<GlobalResult<SpanChainDTO>> getTreeGlobalRawData(@RequestBody TraceDetailQuery traceDetailQuery) throws Exception {
        long startTime = System.currentTimeMillis();

        //改造为多城市查询
        if(StringUtils.isEmpty(traceDetailQuery.getTraceID()) && (StringUtils.isEmpty(traceDetailQuery.getTmsgSn()) || Constances.UNDEFINED_VALUE.equals(traceDetailQuery.getTmsgSn()))){
            throw new BaseRuntimeException(PARA_ILLEGAL_ERROR, "traceId和tmsgSn不能都为空");
        }

        String url = "/raw/sourceTrace/findRawLogByKey";
        logger.info("executing getTreeGlobalRawData method...");

        Map<Object, Object> param = JSON.parseObject(JSON.toJSONString(traceDetailQuery), Map.class);
        GlobalResult<List<TraceRawData>> globalResult = baseGlobalController.globalRequestV1(url, param, new GlobalEsResultAddMerge<TraceRawData>(TraceRawData.class));

        List<TraceRawData> list = globalResult.getResultObj();

        //SpanChainUtil
        return null;

    }


}
