package ru.sfedu.brms.models;

import ru.sfedu.brms.models.enums.Result;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class HistoryContent implements Serializable {

    private UUID id;
    private String className;
    private String time;
    private Object object;
    private String methodName;
    private String author;
    private Result result;

    public HistoryContent() {
    }

    public HistoryContent(UUID uuid, String className, long timeMilliseconds, Object object, String methodName, String author, Result result) {
        this.id = uuid;
        this.className = className;
        this.time = formatTime(timeMilliseconds);
        this.object = object;
        this.methodName = methodName;
        this.author = author;
        this.result = result;
    }

    public String getTime() {
        return time;
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

    public void setTime(String time) {
        this.time = time;
    }

    private String formatTime(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
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
