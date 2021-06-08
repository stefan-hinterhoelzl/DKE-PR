package at.dkepr.entity;

import java.util.Date;

public class Notification {

    private String id;
    private String text;
    private Date createdAt;
    private boolean read;

    public Notification() {
    }

    public Notification(String id, String text, Date createdAt, boolean read) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.read = read;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getText(Date createdAt) {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

}
