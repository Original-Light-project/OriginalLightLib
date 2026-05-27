package ol.originallightlib.common;

public class Result {

    private final boolean success;
    private final String message;

    protected Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static Result success() {
        return new Result(true, "");
    }

    public static Result success(String message) {
        return new Result(true, message);
    }

    public static Result failure(String message) {
        return new Result(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }

    public String getMessage() {
        return message;
    }
}
