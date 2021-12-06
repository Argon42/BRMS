package ru.sfedu.brms.models;

import ru.sfedu.brms.models.enums.Result;

import java.util.UUID;

public class HistoryContent {

    private UUID id;
    private String className;
    private long time;
    private Object object;
    private String methodName;
    private String author;
    private Result result;

    public HistoryContent() {
    }

    public HistoryContent(UUID uuid, String className, long time, Object object, String methodName, String author, Result result) {
        this.id = uuid;
        this.className = className;
        this.time = time;
        this.object = object;
        this.methodName = methodName;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
