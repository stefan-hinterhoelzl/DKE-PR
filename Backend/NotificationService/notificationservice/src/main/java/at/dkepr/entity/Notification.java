package at.dkepr.entity;


public class Notification {

    
    private String id;
    private String text;
    private long createdAt;
    private boolean read;


    
    public Notification(String id, String text, long createdAt, boolean read) {
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



    public long getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }



    public boolean isRead() {
        return read;
    }



    public void setRead(boolean read) {
        this.read = read;
    }

    


}
