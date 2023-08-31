package com.jst.trajst.dashboard.tasks;

import com.jst.trajst.dashboard.enums.EnumBooleanResult;
import com.jst.trajst.dashboard.server.schedule.ITask;
import org.springframework.stereotype.Component;

@Component(value = "failedTask")
public class FailedTask implements ITask {


    @Override
    public EnumBooleanResult doPerform() throws Exception {
        return EnumBooleanResult.FALSE;
    }


}
