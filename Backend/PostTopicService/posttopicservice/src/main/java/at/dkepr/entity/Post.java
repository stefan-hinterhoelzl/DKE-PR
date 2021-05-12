package at.dkepr.entity;


public class Post {
   
    private String content;

    private String mood;

    private String author;

    private Long createdAt;


    //Required
    public Post() {
    }

    public Post(String id, String content, String mood, String author, Long createdAt) {
        this.content = content;
        this.mood = mood;
        this.author = author;
        this.createdAt = createdAt;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuther(String author) {
        this.author = author;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
    
}
