package A06.Sockets;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client extends Thread
{
    private final static String SERVER_IP_ADDRESS = "10.0.2.2";
    private final static int PORT_NUMBER = 4848;

    private CalculateNumbers mainActivitySum;
    private BufferedReader reader;
    private PrintWriter writer;


    public Client(Activity activity)
    {
        mainActivitySum = (CalculateNumbers) activity;
    }

    // Listens from responses from the Server
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run()
    {
        try
        {
            Socket socket = new Socket(SERVER_IP_ADDRESS, PORT_NUMBER);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStreamReader);
            writer = new PrintWriter(socket.getOutputStream(), true);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendToServer(int number1, int number2)
    {
        new SendToServer(number1, number2).start();
    }

    private class SendToServer extends Thread
    {
        int number1;
        int number2;

        public SendToServer(int number1, int number2)
        {
            this.number1 = number1;
            this.number2 = number2;
        }

        @Override
        public void run()
        {
            String response = null;
            try
            {
                writer.println(number1 + ":" + number2);
                response = reader.readLine();
                if (response != null)
                    mainActivitySum.setSum(Integer.parseInt(response));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    public interface CalculateNumbers
    {
        void setSum(int result);
    }
}

