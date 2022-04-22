package A2.Exercise2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class RandomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        int number_boundary = getIntent().getIntExtra("number-boundary", -1);
        int random_number = (int) (Math.random() * number_boundary);

        Intent intent = new Intent();
        intent.putExtra("random-number", random_number);
        setResult(RESULT_OK, intent);
        finish();
    }
}
