package ConnectionUtils;

import java.io.IOException;
import java.net.*;

public class UDPWrapper implements Runnable{
    private static final int BUFFER_SIZE = 16000;
    UDPDataReceivedListener mListener;
    private static final int PORT = 12234;
    private static final int BROADCAST_RECEIVER_PORT = 12236;
    int mUDPPort;
    InetAddress mIPAddress;
    private InetSocketAddress socketAddress;

    public UDPWrapper(UDPDataReceivedListener listener) {
        mListener = listener;
    }

    boolean isRunning;
    boolean isBroadcasting;
    DatagramSocket socket;
    @Override
    public void run() {
        isRunning = true;
        try {
            socket = new DatagramSocket(PORT);

            while (isRunning) {
                byte buffer[] = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, BUFFER_SIZE);
                socket.receive(packet);
                if(mIPAddress == null) {
                    isBroadcasting = false;
                    mIPAddress = packet.getAddress();
                    sendConnectionPacket();
                }
                mListener.onDataReceived(packet.getData());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendConnectionPacket() {
        isBroadcasting = true;
        try {
            DatagramSocket broadcastSocket = new DatagramSocket(BROADCAST_RECEIVER_PORT);
            String data = "hello";
            byte[] buffer = data.getBytes();
            Thread broadcastThread = new Thread() {
                public void run() {
                    while(isBroadcasting) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, mIPAddress, BROADCAST_RECEIVER_PORT);
                        try {
                            broadcastSocket.send(packet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            broadcastThread.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
