package DataUtils;

import ConnectionUtils.NetworkManager;
import ConnectionUtils.UDPWrapper;
import PointerUtils.DataInfo;
import PointerUtils.Keyboard;
import PointerUtils.LogUtil;
import PointerUtils.Mouse;

import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class DataManager implements OnDataReceivedListener {
    Mouse mMouse;
    Keyboard mKeyboard;
    NetworkManager networkManager;
    ConcurrentLinkedQueue<QueueItem> queue;
    Thread consumer;

    public DataManager(Mouse mouse, Keyboard keyboard) {
        mMouse = mouse;
        mKeyboard = keyboard;
        queue = new ConcurrentLinkedQueue<>();
        consumer = new Thread() {
          public void run() {
              while(true) {
                  QueueItem item = queue.poll();
                  if (item == null)
                      continue;
                  System.out.println("Received data");
                  if (item.mType == DataInfo.MOUSE_MOVE)
                      mMouse.moveMouse(item);
                  else if (item.mType == DataInfo.MOUSE_CLICK)
                      mMouse.clickMouse();
                  else if (item.mType == DataInfo.MOUSE_LEFT_PRESS)
                      mMouse.leftPressMouse();
                  else if (item.mType == DataInfo.MOUSE_LEFT_RELEASE)
                      mMouse.leftReleaseMouse();
                  else if (item.mType == DataInfo.MOUSE_SCROLL)
                      mMouse.scrollMouse(item);
                  else if (item.mType == DataInfo.MOUSE_RIGHT_CLICK)
                      mMouse.rightClickMouse();
                  else if (item.mType == DataInfo.MOUSE_RIGHT_PRESS)
                      mMouse.rightPressMouse();
                  else if (item.mType == DataInfo.MOUSE_RIGHT_RELEASE)
                      mMouse.rightReleaseMouse();
                  else if (item.mType == DataInfo.KEY_PRESSED)
                      mKeyboard.onKeyPressed(item);
                  else if (item.mType == DataInfo.KEY_RELEASED)
                      mKeyboard.onKeyReleased(item);
                  else if (item.mType == DataInfo.TEXT_INPUT)
                      mKeyboard.onTextInput(item);
                  else if (item.mType == DataInfo.KEY_ACTION)
                      mKeyboard.onKeyAction(item);
                  else if (item.mType == DataInfo.BROADCAST_MESSAGE) {
                      networkManager.sendConnectionPacket();
                      System.out.println("Received broadcast message");
                  } else if (item.mType == DataInfo.LOG)
                      LogUtil.print(item);
                  else if(item.mType == DataInfo.PHYSICAL_KEY_ACTION)
                      keyboard.onPhysicalKeyAction(item);
              }
          }
        };
        consumer.start();
    }

    public void setNetworkManager(NetworkManager nManager) {
        if(networkManager == null)
            networkManager = nManager;
    }

    @Override
    public void onDataReceived(QueueItem item) {
        queue.add(item);
    }
}
