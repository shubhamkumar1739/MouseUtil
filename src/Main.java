import ConnectionUtils.UDPDataReceivedListener;
import ConnectionUtils.UDPWrapper;
import DataUtils.DataManager;
import PointerUtils.DataInfo;
import PointerUtils.Keyboard;
import PointerUtils.Mouse;
import PointerUtils.UtilItem;

import java.awt.*;

public class Main {
    public static UDPWrapper mUDPWrapper;
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            Mouse mouse = new Mouse(robot);
            Keyboard keyboard = new Keyboard(robot);
            DataManager manager = new DataManager(mouse, keyboard);
            mUDPWrapper = new UDPWrapper(new UDPDataReceivedListener() {
                @Override
                public void onDataReceived(byte[] bytes) {
                    UtilItem data = new UtilItem(new String(bytes));
                    manager.onDataReceived(data);
                }
            });
            manager.setUDPWrapper(mUDPWrapper);

            Thread udpWrapperThread = new Thread(mUDPWrapper);
            udpWrapperThread.start();
            System.out.println("End");
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public static UDPWrapper getmUDPWrapper() {
        return mUDPWrapper;
    }
}
