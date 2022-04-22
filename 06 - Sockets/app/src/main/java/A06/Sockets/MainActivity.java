package A06.Sockets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Client.CalculateNumbers, Server.ServerInfo
{
    private Client client;

    TextView text_Info;
    RadioGroup radioGroup;
    Button button_ConnectToServer;
    Button button_StartServer;
    LinearLayout layout_SumNumbers;
    EditText input_Number1;
    EditText input_Number2;
    TextView text_Sum;
    TextView text_ServerLog;
    TextView text_ConnectedClients;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_Info = findViewById(R.id.text_Info);
        radioGroup = findViewById(R.id.radioGroup);
        button_ConnectToServer = findViewById(R.id.button_ConnectToServer);
        button_StartServer = findViewById(R.id.button_StartServer);
        layout_SumNumbers = findViewById(R.id.layout_SumNumbers);
        input_Number1 = findViewById(R.id.input_Number1);
        input_Number2 = findViewById(R.id.input_Number2);
        text_Sum = findViewById(R.id.text_Sum);
        text_ServerLog = findViewById(R.id.text_ServerLog);
        text_ConnectedClients = findViewById(R.id.text_ConnectedClients);
    }

    public void onClick_RadioButton(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();
        if (!checked) return;

        switch (view.getId())
        {
            case R.id.radioOption_Client:
                button_ConnectToServer.setVisibility(View.VISIBLE);
                button_StartServer.setVisibility(View.INVISIBLE);
                break;

            case R.id.radioOption_Server:
                button_ConnectToServer.setVisibility(View.INVISIBLE);
                button_StartServer.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void onClick_StartServer(View view)
    {
        Server server = new Server(this);
        server.start();
        text_Info.setText(R.string.info_StateServer);
        button_StartServer.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
        text_ServerLog.setVisibility(View.VISIBLE);
        text_ConnectedClients.setVisibility(View.VISIBLE);
    }

    public void onClick_ConnectToServer(View view) throws Exception
    {
        client = new Client(this);
        client.start();
        text_Info.setText(R.string.info_StateClient);
        layout_SumNumbers.setVisibility(View.VISIBLE);
        button_ConnectToServer.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
    }

    public void onClick_CalculateSum(View view)
    {
        if (input_Number1.getText().toString().equals("") || input_Number2.getText().toString().equals(""))
            return;
        int number1 = Integer.parseInt(input_Number1.getText().toString());
        int number2 = Integer.parseInt(input_Number2.getText().toString());
        // send numbers to the server
        client.sendToServer(number1, number2);
    }

    // The ServerLog is a String with info about the connected clients, and the last requests the server has received
    @Override
    public void addToServerLog(final String newLogEntry)
    {
        final int LENGTH_OF_LOG = 7;
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (text_ServerLog.getText().equals(""))
                {
                    text_ServerLog.setText(newLogEntry);
                    return;
                }
                // Get current log
                String[] currentLog = ((String) text_ServerLog.getText()).split("\n");
                int lengthOfLog = Math.min(currentLog.length + 1, LENGTH_OF_LOG);
                String[] newLog = new String[lengthOfLog];
                newLog[0] = newLogEntry + "\n";
                for (int i = 1; i < lengthOfLog; i++) newLog[i] = currentLog[i - 1] + "\n";
                // Merge to string, and update
                StringBuilder log = new StringBuilder();
                for (String line : newLog) log.append(line);
                text_ServerLog.setText(log.toString());
            }
        });
    }

    @Override
    public void setConnectedClients(final String str)
    {
        text_ConnectedClients.setText(str);
    }

    @Override
    public void setSum(final int result)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                String resultString = "The sum of " + input_Number1.getText() +
                        " and " + input_Number2.getText() +
                        " is " + Integer.toString(result);
                text_Sum.setText(resultString);
                text_Sum.setVisibility(View.VISIBLE);
            }
        });
    }
}
