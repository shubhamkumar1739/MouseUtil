package ConnectionUtils;

import PointerUtils.DataInfo;

import java.io.IOException;
import java.net.*;

public class UDPWrapper implements Runnable{
    private static final int BUFFER_SIZE = 16000;
    UDPDataReceivedListener mListener;
    private static final int PORT = 12234;
    private static final int BROADCAST_RECEIVER_PORT = 12236;
    InetAddress mIPAddress;
    DatagramSocket broadcastSocket;
    Thread broadCasterThread;

    public UDPWrapper(UDPDataReceivedListener listener) {
        mListener = listener;
    }

    boolean isRunning;

    public boolean isBroadcasting() {
        return isBroadcasting;
    }

    public void setBroadcasting(boolean broadcasting) {
        isBroadcasting = broadcasting;
    }

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
                mIPAddress = InetAddress.getByName(packet.getAddress().getHostAddress());
                System.out.println("Packet Received from : " + packet.getAddress().getHostAddress());
                mListener.onDataReceived(packet.getData());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendConnectionPacket() {
        System.out.println("Trying to send packet to " + mIPAddress.getHostAddress());
        if(broadCasterThread != null) {
            return;
        }
        isBroadcasting = true;
        try {
            if(broadcastSocket == null) {
                broadcastSocket = new DatagramSocket(BROADCAST_RECEIVER_PORT);
            }
            String data = System.currentTimeMillis() + "," + DataInfo.BROADCAST_MESSAGE + "," + InetAddress.getLocalHost().getHostName() + "," +System.getProperty("os.name") + "," +System.getProperty("os.version");
            byte[] buffer = data.getBytes();
            broadCasterThread = new Thread() {
                public void run() {
                    while(isBroadcasting) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, mIPAddress, BROADCAST_RECEIVER_PORT);
                        try {
                            broadcastSocket.send(packet);
                            System.out.println("Sent Packet to " + mIPAddress.getHostAddress() + " " +mIPAddress.getHostName());
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Error sending packet!");
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
