package alarm.access.processor;

import alarm.common.exception.ExecuteAuthException;

public interface IExecuteAuthority<T> {
    void gainAccess() throws ExecuteAuthException;
}
