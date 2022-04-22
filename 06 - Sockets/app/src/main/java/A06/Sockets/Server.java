package A06.Sockets;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread
{
    private final static String TAG = "Server";
    private final static int PORT_NUMBER = 4848;

    private Server.ServerInfo server;


    public Server(Activity activity)
    {
        server = (ServerInfo) activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER))
        {
            int connectedClients = 0;

            server.addToServerLog("Waiting for clients...");
            while (connectedClients < 10)
            {
                server.setConnectedClients("Connected clients: " + connectedClients);
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket, server);
                client.start();
                connectedClients++;
                server.addToServerLog(socket.getInetAddress().getHostName() + " connected");
                Log.e(TAG, "NEW CLIENT CONNECTED");
            }
        }
        catch (IOException ioe)
        {
            Log.e(TAG, ioe.getMessage());
        }
    }

    public interface ServerInfo
    {
        void addToServerLog(String line);

        void setConnectedClients(String clients);
    }
}
