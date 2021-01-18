package ConnectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPWrapper extends NetworkManager{
    private static final int PORT = 12237;
    boolean isRunning;
    ServerSocket serverSocket;

    public TCPWrapper(DataReceivedListener listener) {
        mListener = listener;
    }

    public void startListening() {
        if(listenerThread == null)
            return;
        listenerThread = new Thread() {
            public void run() {
                isRunning = true;
                if(serverSocket == null) {
                    try {
                        serverSocket = new ServerSocket(PORT);
                        while(isRunning) {
                            Socket socket = serverSocket.accept();

                            InputStream inputStream = socket.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            mListener.onDataReceived(bufferedReader.readLine().getBytes());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
