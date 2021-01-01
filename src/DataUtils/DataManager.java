package DataUtils;

import PointerUtils.Mouse;

import java.util.ArrayList;

public class DataManager implements OnDataReceivedListener {
    Mouse mMouse;

    public DataManager(Mouse mouse) {
        mMouse = mouse;
    }

    @Override
    public void onDataReceived(QueueItem item) {
        mMouse.moveMouse(item);
    }
}
