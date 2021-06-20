package com.monk.monkchat.Models;

public class MessageModel {
    String id;
    String message;
    Long timestamp;

    public MessageModel(String id, String message, Long timestamp) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public MessageModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
