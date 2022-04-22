package A06.Sockets;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler extends Thread
{
    private final static String TAG = "ClientHandler";
    private Server.ServerInfo serverLog;
    private Socket socket;

    public ClientHandler(Socket socket, Server.ServerInfo serverLog)
    {
        this.socket = socket;
        this.serverLog = serverLog;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run()
    {
        try (InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true))
        {
            String line = bufferedReader.readLine();
            while (line != null)
            {

                if (line.contains(":"))
                {
                    int number1 = Integer.parseInt(line.split(":")[0]);
                    int number2 = Integer.parseInt(line.split(":")[1]);
                    serverLog.addToServerLog("Finding sum of " + number1 + " and " + number2);
                    int sum = number1 + number2;
                    printWriter.println(sum);
                }
                line = bufferedReader.readLine();
            }
        }
        catch (IOException ioe)
        {
            Log.e(TAG, ioe.getMessage());
        }
    }
}
