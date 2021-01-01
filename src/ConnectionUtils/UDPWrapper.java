package ConnectionUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPWrapper implements Runnable{
    private static final int BUFFER_SIZE = 16000;
    UDPDataReceivedListener mListener;
    int mUDPPort;
    String mIPAddress;
    private InetSocketAddress socketAddress;

    public UDPWrapper(String ipAddress, int port, UDPDataReceivedListener listener) {
        mIPAddress = ipAddress;
        mUDPPort = port;
        mListener = listener;
    }

    boolean isRunning;
    DatagramSocket socket;
    @Override
    public void run() {
        isRunning = true;
        try {
            socket = new DatagramSocket(12234);

            while (isRunning) {
                byte buffer[] = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, BUFFER_SIZE);
                socket.receive(packet);
                mListener.onDataReceived(packet.getData());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
