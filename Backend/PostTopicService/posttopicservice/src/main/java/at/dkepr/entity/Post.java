package at.dkepr.entity;


public class Post {
   
    private String content;

    private String mood;

    private String authorid;

    private String authorname;

    private Long createdAt;


    //Required
    public Post() {
    }

    public Post(String id, String content, String mood, String authorid, String authorname, Long createdAt) {
        this.content = content;
        this.mood = mood;
        this.authorid = authorid;
        this.authorname = authorname;
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
    
}
