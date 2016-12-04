package undev.bg.rank;

/**
 * Created by mhy on 2016-11-13.
 */

public class Rank {
    public String userName;
    public String score;

    public Rank(String userName,  String score) {
        this.userName = userName;
        this.score = score;
    }

    @Override
    public String toString() {
        return userName;
    }

}
