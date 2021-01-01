import ConnectionUtils.UDPDataReceivedListener;
import ConnectionUtils.UDPWrapper;
import DataUtils.DataManager;
import PointerUtils.DataInfo;
import PointerUtils.Mouse;
import PointerUtils.UtilItem;

public class Main {
    public static void main(String[] args) {
        Mouse mouse = new Mouse();
        DataManager manager = new DataManager(mouse);

        UDPWrapper udpWrapper = new UDPWrapper("25.224.16.74", 1234, new UDPDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] bytes) {
                UtilItem data = new UtilItem(new String(bytes), DataInfo.MOUSE_MOVE_TYPE);
                manager.onDataReceived(data);
            }
        });
        Thread udpWrapperThread = new Thread(udpWrapper);
        udpWrapperThread.start();
        System.out.println("End");
    }
}
