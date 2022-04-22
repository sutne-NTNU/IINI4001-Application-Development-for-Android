package A3.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddFriendActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
    }

    public void onClickSaveAddFriend(View view) {
        // Get fields
        EditText nameField = findViewById(R.id.edit_newFriendName);
        EditText birthdayField = findViewById(R.id.edit_newFriendBirthday);

        // Get input values
        String name = nameField.getText().toString();
        String birthday = birthdayField.getText().toString();

        // Validate input
        if (name.equals("") || birthday.equals("")) {
            toast("Please fill both fields to add a new friend");
        } else {
            // Send data as result back to main activity
            Intent intent = new Intent();
            intent.putExtra("new-name", name);
            intent.putExtra("new-birthday", birthday);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void onClickCancelAddFriend(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void toast(String toastMessage) {
        Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        toast.show();
    }
}
