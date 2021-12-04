package brms.models;

import brms.Result;

import java.util.UUID;

public class HistoryContent {

    private UUID id;
    private String className;
    private long time;
    private Object object;
    private String methodName;
    private Result result;

    public HistoryContent() {
    }

    public HistoryContent(UUID uuid, String className, long time, Object object, String methodName, Result result) {
        this.id = uuid;
        this.className = className;
        this.time = time;
        this.object = object;
        this.methodName = methodName;
        this.result = result;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
