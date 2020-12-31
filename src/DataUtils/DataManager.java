package DataUtils;

import DataUtils.Listeners.BufferFilledListener;
import DataUtils.Listeners.ConsumedListener;
import DataUtils.Listeners.OnDataReceivedListener;
import PointerUtils.Mouse;

import java.util.ArrayList;

public class DataManager implements OnDataReceivedListener {
    Buffer mBuffer;
    Mouse mMouse;

    public DataManager(int size, Mouse mouse) {
        mBuffer = new Buffer(size);
        mMouse = mouse;
    }

    @Override
    public void onDataReceived(QueueItem item) {
        Producer producer = new Producer(mBuffer, new BufferFilledListener() {
            @Override
            public void onBufferFilled() {
                Consumer consumer = new Consumer(mBuffer, new ConsumedListener() {
                    @Override
                    public void onConsumed(ArrayList<QueueItem> items) {
                        mMouse.moveMouse(items);
                    }
                });
                Thread consumerThread = new Thread(consumer);
                consumerThread.start();
            }
        });
        producer.setItem(item);
        Thread producerThread = new Thread(producer);
        producerThread.start();
    }
}
