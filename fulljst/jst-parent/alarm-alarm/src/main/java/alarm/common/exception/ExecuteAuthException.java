package alarm.common.exception;

public class ExecuteAuthException extends NormalTerminateException{
    public ExecuteAuthException(String task, String id) {
        super("cannot get execute authorization: "+ id + ":" + task);
    }
}
