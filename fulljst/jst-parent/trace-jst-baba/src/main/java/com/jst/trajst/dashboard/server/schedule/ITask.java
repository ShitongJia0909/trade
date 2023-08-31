package com.jst.trajst.dashboard.server.schedule;

import com.jst.trajst.dashboard.enums.EnumBooleanResult;

public interface ITask {

    EnumBooleanResult doPerform() throws Exception;

}
