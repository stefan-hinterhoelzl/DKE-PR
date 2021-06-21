package at.dkepr.exceptions;

public class RedisException extends RuntimeException {

    long id;

    public RedisException(String msg, long id) {
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
    

