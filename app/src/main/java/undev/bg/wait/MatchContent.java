package undev.bg.wait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import undev.bg.rank.Rank;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MatchContent {
    public static final List<Match> ITEMS = new ArrayList<>();
    public static final Map<String, Match> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 1;

    public MatchContent(){

    }

    public void addItem(Match item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
    }

}
