package DataUtils.Listeners;

import DataUtils.QueueItem;

import java.util.ArrayList;

public interface ConsumedListener {
    public void onConsumed(ArrayList<QueueItem> items);
}
