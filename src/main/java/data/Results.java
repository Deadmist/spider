package data;

/**
 * Created by Deadmist on 30/08/15.
 */
public class Results {

    private String url, status, body;

    public Results(String url, String status, String body) {
        this.status = status;
        this.body = body;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
