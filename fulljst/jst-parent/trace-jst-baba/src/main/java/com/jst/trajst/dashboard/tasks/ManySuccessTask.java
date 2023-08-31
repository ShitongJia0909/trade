package com.jst.trajst.dashboard.tasks;

import com.jst.common.utils.SecureRandomUtil;
import com.jst.trajst.dashboard.enums.EnumBooleanResult;
import com.jst.trajst.dashboard.server.schedule.ITask;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component(value = "manySuccessTask")
public class ManySuccessTask implements ITask {
    @Override
    public EnumBooleanResult doPerform() throws Exception {

        if(SecureRandomUtil.getRandomInstance().nextInt(10) > 2){
            return EnumBooleanResult.TRUE;
        }
        return EnumBooleanResult.FALSE;
    }
}
