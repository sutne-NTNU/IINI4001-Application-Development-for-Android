package A2.Exercise2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int ADDITION = 1;
    final int MULTIPLICATION = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void checkCalculation(int calculationType) {
        // Get values from views
        int number1 = -1;
        int number2 = -1;
        int answer = -1;
        int number_boundary = 10;
        TextView number1TextView = findViewById(R.id.number1);
        TextView number2TextView = findViewById(R.id.number2);
        EditText answerEditText = findViewById(R.id.answer);
        EditText edit_number_boundary = findViewById(R.id.edit_number_boundary);
        try {
            number1 = Integer.parseInt(number1TextView.getText().toString());
            number2 = Integer.parseInt(number2TextView.getText().toString());
            answer = Integer.parseInt(answerEditText.getText().toString());
            number_boundary = Integer.parseInt(edit_number_boundary.getText().toString());
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Error: invalid input", Toast.LENGTH_LONG);
            toast.show();
        }
        // Calculate correct answer
        int correctAnswer;
        switch (calculationType) {
            case ADDITION:
                correctAnswer = number1 + number2;
                break;
            case MULTIPLICATION:
                correctAnswer = number1 * number2;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + calculationType);
        }
        // Check if user-answer is correct
        String toast_message;
        if (answer == correctAnswer) {
            toast_message = this.getString(R.string.correct);
        } else {
            toast_message = this.getString(R.string.incorrect) + " " + correctAnswer;
        }
        // Show Toast with result
        Toast toast = Toast.makeText(this, toast_message, Toast.LENGTH_SHORT);
        toast.show();

        // Ask RandomActivity for new random numbers (they are changed in onActivityResult)
        Intent intent = new Intent("A2.Exercise2.RandomActivity");
        intent.putExtra("number-boundary", number_boundary);
        startActivityForResult(intent, 1);
        startActivityForResult(intent, 2);
    }

    public void onAdditionClicked(View view) {
        checkCalculation(ADDITION);
    }

    public void onMultiplicationClicked(View view) {
        checkCalculation(MULTIPLICATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        String random_number = Integer.toString(data.getIntExtra("random-number", -1));
        if (requestCode == 1) {
            TextView textView = findViewById(R.id.number1);
            textView.setText(random_number);
        }
        if (requestCode == 2) {
            TextView textView = findViewById(R.id.number2);
            textView.setText(random_number);
        }
    }
}