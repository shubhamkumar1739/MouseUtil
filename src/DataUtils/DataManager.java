package DataUtils;

import ConnectionUtils.NetworkManager;
import ConnectionUtils.UDPWrapper;
import PointerUtils.DataInfo;
import PointerUtils.Keyboard;
import PointerUtils.LogUtil;
import PointerUtils.Mouse;

public class DataManager implements OnDataReceivedListener {
    Mouse mMouse;
    long recentTimestamp;
    Keyboard mKeyboard;
    NetworkManager networkManager;

    public DataManager(Mouse mouse, Keyboard keyboard) {
        mMouse = mouse;
        mKeyboard = keyboard;
        recentTimestamp = 0;
    }

    public void setNetworkManager(NetworkManager nManager) {
        if(networkManager == null)
            networkManager = nManager;
    }

    @Override
    public void onDataReceived(QueueItem item) {
        System.out.println("Received data");
        if(item.timestamp < recentTimestamp) {
            System.out.println("Timestamp issue");
            return;
        }
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
        else if(item.mType == DataInfo.BROADCAST_MESSAGE) {
            networkManager.sendConnectionPacket();
            System.out.println("Received broadcast message");
        }
        else if(item.mType == DataInfo.LOG)
            LogUtil.print(item);

        if(item.mType != DataInfo.BROADCAST_MESSAGE) {
            networkManager.setBroadcasting(false);
            networkManager.setReceiving(false);
        }
    }
}
