package at.dkepr.exceptions;

public class Neo4JException extends RuntimeException {

    long id;

    public Neo4JException(String msg, long id) {
        super(msg);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
}
    

