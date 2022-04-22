package A2.Exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE_getRandomNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int number_boundary = 500;
        Intent intent = new Intent("A2.Exercise1.RandomActivity");
        intent.putExtra("number-boundary", number_boundary);
        startActivityForResult(intent, REQUEST_CODE_getRandomNumber);

        TextView textView = findViewById(R.id.number_boundary_text);
        textView.setText("Number boundary: " + number_boundary);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_getRandomNumber && resultCode == RESULT_OK) {
            TextView textView = findViewById(R.id.default_textView);
            String text = "Random number from other activity: " + data.getIntExtra("random_number", -1);
            textView.setText(text);
        }
    }
}
