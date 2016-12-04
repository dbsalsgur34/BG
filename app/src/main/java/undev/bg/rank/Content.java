package undev.bg.rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Content {
    public static final List<Rank> ITEMS = new ArrayList<Rank>();
    public static final Map<String, Rank> ITEM_MAP = new HashMap<String, Rank>();

    private static final int COUNT = 1;

    public Content(){

    }

    public void addItem(Rank item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.userName, item);
    }

}
