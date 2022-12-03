package com.userinterface.ssuroom.model;

public class Comment {
    private String name,comment;
    private long createdAt;

    public Comment(String name, String comment, long createdAt) {
        if(comment==null)
            name=" ";
        this.name = name;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
