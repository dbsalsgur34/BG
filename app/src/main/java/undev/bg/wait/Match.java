package undev.bg.wait;

/**
 * Created by mhy on 2016-11-13.
 */

public class Match {
    public String title;
    public String matchID;
    public Match(String title, String matchID) {
        this.title = title;
        this.matchID = matchID;
    }

    @Override
    public String toString() {
        return title;
    }

}
