package AO5.IntroToHTTP;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private HTTPConnection HTTPConnection;

    final private String RESPONSE_WON = "du har vunnet";
    final private String RESPONSE_LOST = "du må starte på nytt";
    final private String RESPONSE_INSTRUCTIONS = "Oppgi et tall";
    final private String ServerURL = "http://tomcat.stud.iie.ntnu.no/studtomas/tallspill.jsp";

    private EditText input_Name;
    private EditText input_CardNumber;
    private EditText input_Guess;
    private TextView text_Response;
    private Button button_Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HTTPConnection = new HTTPConnection(this, ServerURL);

        input_Name = findViewById(R.id.input_Name);
        input_CardNumber = findViewById(R.id.input_CardNumber);
        input_Guess = findViewById(R.id.input_Guess);
        text_Response = findViewById(R.id.text_Response);
        button_Submit = findViewById(R.id.button_Submit);
    }

    public void setResponse(String response)
    {
        text_Response.setText(response);
        if (response.contains(RESPONSE_INSTRUCTIONS))
        {
            //Show
            text_Response.setVisibility(View.VISIBLE);
            input_Guess.setVisibility(View.VISIBLE);
            //Hide
            input_Name.setVisibility(View.INVISIBLE);
            input_CardNumber.setVisibility(View.INVISIBLE);
        }
        else if (response.contains(RESPONSE_WON)) // User won
        {
            // Hide
            input_Guess.setVisibility(View.INVISIBLE);
            button_Submit.setVisibility(View.INVISIBLE);
        }
        else if (response.contains(RESPONSE_LOST)) // All three attempts have been used
        {
            // hide
            input_Guess.setVisibility(View.INVISIBLE);
            button_Submit.setVisibility(View.INVISIBLE);
        }

    }

    public void onClick_Restart(View view)
    {
        // Hide
        text_Response.setVisibility(View.INVISIBLE);
        input_Guess.setVisibility(View.INVISIBLE);
        // Show
        input_Name.setVisibility(View.VISIBLE);
        input_CardNumber.setVisibility(View.VISIBLE);
        button_Submit.setVisibility(View.VISIBLE);
    }

    public void onClick_Submit(View view)
    {
        if (input_Name.getVisibility() == View.VISIBLE)
        {
            if (input_Name.getText().toString().trim().equals("") || input_CardNumber.getText().toString().trim().equals(""))
            {
                toast("Fields cannot be empty");
                return;
            }
            Map<String, String> parameters = new HashMap<>();
            parameters.put("navn", input_Name.getText().toString());
            parameters.put("kortnummer", input_CardNumber.getText().toString());
            HTTPConnection.startThread(parameters);
        }
        else
        {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("tall", input_Guess.getText().toString());
            HTTPConnection.startThread(parameters);
        }
    }

    private void toast(String message)
    {
        Toast toast = new Toast(this);
        toast.setText(message);
        toast.show();
    }
}
