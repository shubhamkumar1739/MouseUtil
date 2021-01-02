package DataUtils;

import PointerUtils.DataInfo;
import PointerUtils.Mouse;

import java.util.ArrayList;

public class DataManager implements OnDataReceivedListener {
    Mouse mMouse;

    public DataManager(Mouse mouse) {
        mMouse = mouse;
    }

    @Override
    public void onDataReceived(QueueItem item) {
        if(item.mType == DataInfo.MOUSE_MOVE)
            mMouse.moveMouse(item);
        else if(item.mType == DataInfo.MOUSE_CLICK)
            mMouse.clickMouse();
    }
}
