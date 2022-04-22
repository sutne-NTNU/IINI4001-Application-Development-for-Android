package A2.Exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class RandomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

//        int random_number = (int) (Math.random() * 100);
//        Toast toast = Toast.makeText(this, "Toast with random number:  " + random_number, Toast.LENGTH_SHORT);
//        toast.show();

        int number_boundary = getIntent().getIntExtra("number-boundary", 100);
        int random_number = (int) (Math.random() * number_boundary);

        Intent intent = new Intent();
        intent.putExtra("random_number", random_number);
        setResult(RESULT_OK, intent);
        finish();
    }
}
