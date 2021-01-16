package DataUtils;

import ConnectionUtils.UDPWrapper;
import PointerUtils.DataInfo;
import PointerUtils.Keyboard;
import PointerUtils.Mouse;

public class DataManager implements OnDataReceivedListener {
    Mouse mMouse;
    long recentTimestamp;
    Keyboard mKeyboard;
    UDPWrapper mUDPWrapper;

    public DataManager(Mouse mouse, Keyboard keyboard) {
        mMouse = mouse;
        mKeyboard = keyboard;
        recentTimestamp = System.currentTimeMillis();
    }

    public void setUDPWrapper(UDPWrapper wrapper) {
        if(mUDPWrapper == null)
            mUDPWrapper = wrapper;
    }

    @Override
    public void onDataReceived(QueueItem item) {
        if(item.timestamp < recentTimestamp)
            return;
        else
            recentTimestamp = item.timestamp;
        if(item.mType == DataInfo.MOUSE_MOVE)
            mMouse.moveMouse(item);
        else if(item.mType == DataInfo.MOUSE_CLICK)
            mMouse.clickMouse();
        else if(item.mType == DataInfo.MOUSE_LEFT_PRESS)
            mMouse.leftPressMouse();
        else if(item.mType == DataInfo.MOUSE_LEFT_RELEASE)
            mMouse.leftReleaseMouse();
        else if(item.mType == DataInfo.MOUSE_SCROLL)
            mMouse.scrollMouse(item);
        else if(item.mType == DataInfo.MOUSE_RIGHT_CLICK)
            mMouse.rightClickMouse();
        else if(item.mType == DataInfo.MOUSE_RIGHT_PRESS)
            mMouse.rightPressMouse();
        else if(item.mType == DataInfo.MOUSE_RIGHT_RELEASE)
            mMouse.rightReleaseMouse();
        else if(item.mType == DataInfo.KEY_PRESSED)
            mKeyboard.onKeyPressed(item);
        else if(item.mType == DataInfo.KEY_RELEASED)
            mKeyboard.onKeyReleased(item);
        else if(item.mType == DataInfo.TEXT_INPUT)
            mKeyboard.onTextInput(item);
        else if(item.mType == DataInfo.KEY_ACTION)
            mKeyboard.onKeyAction(item);
        else if(item.mType == DataInfo.BROADCAST_MESSAGE)
            mUDPWrapper.sendConnectionPacket();
    }
}
