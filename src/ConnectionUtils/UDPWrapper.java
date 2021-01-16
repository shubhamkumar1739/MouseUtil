package ConnectionUtils;

import PointerUtils.DataInfo;

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
    DatagramSocket broadcastSocket;
    Thread broadCasterThread;

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
                    mIPAddress = InetAddress.getByName(packet.getAddress().getHostAddress());
                }
                mListener.onDataReceived(packet.getData());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendConnectionPacket() {
        if(broadCasterThread != null)
            return;
        isBroadcasting = true;
        try {
            if(broadcastSocket == null)
                broadcastSocket = new DatagramSocket(BROADCAST_RECEIVER_PORT);
            String data = System.currentTimeMillis() + "," + DataInfo.BROADCAST_MESSAGE + "," + InetAddress.getLocalHost().getHostName() + "," +System.getProperty("os.name") + "," +System.getProperty("os.version");
            byte[] buffer = data.getBytes();
            broadCasterThread = new Thread() {
                public void run() {
                    while(isBroadcasting) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, mIPAddress, BROADCAST_RECEIVER_PORT);
                        try {
                            broadcastSocket.send(packet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    broadCasterThread = null;
                }
            };
            broadCasterThread.start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
