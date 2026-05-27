package ol.originallightlib.integration.mythic;

import ol.originallightlib.common.Result;

public class MythicResult extends Result {

    private MythicResult(boolean success, String message) {
        super(success, message);
    }

    public static MythicResult success() {
        return new MythicResult(true, "");
    }

    public static MythicResult success(String message) {
        return new MythicResult(true, message);
    }

    public static MythicResult failure(String message) {
        return new MythicResult(false, message);
    }
}
