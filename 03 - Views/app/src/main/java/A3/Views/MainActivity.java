package A3.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

import A3.Views.models.Friend;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Friend> friends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        friends.add(new Friend("Sivert Utne", "25. des. 1997"));
        friends.add(new Friend("Ola Nordmann", "14. feb. 1998"));
        startSpinner();
    }

    void startSpinner() {
        ArrayAdapter<Friend> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, friends);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView friendName_view = findViewById(R.id.friendName);
                friendName_view.setText(friends.get(position).getName());
                TextView textViewBirthday = findViewById(R.id.friendBirthday);
                textViewBirthday.setText(friends.get(position).getBirthday());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do Nothing
            }
        });
    }


    public void onClickAddFriend(View view) {
        Intent intent = new Intent("A3.Views.AddFriendActivity");
        startActivityForResult(intent, 1);
    }

    public void onClickEdit(View view) {
        // Get fields with information
        Spinner spinner = findViewById(R.id.spinner);
        EditText editTextName = findViewById(R.id.edit_friendName);
        EditText editTextBirthday = findViewById(R.id.edit_friendBirthday);
        // Set field to match the selected friend
        Friend selectedFriend = (Friend) spinner.getSelectedItem();
        editTextName.setText(selectedFriend.getName());
        editTextBirthday.setText(selectedFriend.getBirthday());
        // Show edit-fields and buttons
        setEditVisibility(true);
    }

    public void onClickSaveEdit(View view) {
        // Get Fields
        Spinner spinner = findViewById(R.id.spinner);
        EditText editNameField = findViewById(R.id.edit_friendName);
        EditText editBirthdayField = findViewById(R.id.edit_friendBirthday);
        TextView textViewName = findViewById(R.id.friendName);
        TextView textViewBirthday = findViewById(R.id.friendBirthday);

        // Get new values
        String new_name = editNameField.getText().toString();
        String new_birthday = editBirthdayField.getText().toString();

        // Update Friend object
        Friend friend = (Friend) spinner.getSelectedItem();
        friend.setName(new_name);
        friend.setBirthday(new_birthday);
        textViewName.setText(new_name);
        textViewBirthday.setText(new_birthday);

        setEditVisibility(false);
        toast("The changes were saved");
    }

    public void onClickCancelEdit(View view) {
        setEditVisibility(false);
    }


    // Toggles if the edit-layout and buttons should be visible or not
    private void setEditVisibility(boolean visible) {
        GridLayout editLayout = findViewById(R.id.layout_editFriend);
        LinearLayout editButtonLayout = findViewById(R.id.editFriendButtons);
        Button editFriendButton = findViewById(R.id.button_editFriend);
        Button addFriendButton = findViewById(R.id.button_addFriend);

        if (visible) {
            editLayout.setVisibility(View.VISIBLE);
            editButtonLayout.setVisibility(View.VISIBLE);
            editFriendButton.setVisibility(View.INVISIBLE);
            addFriendButton.setVisibility(View.INVISIBLE);
        } else {
            editLayout.setVisibility(View.INVISIBLE);
            editButtonLayout.setVisibility(View.INVISIBLE);
            editFriendButton.setVisibility(View.VISIBLE);
            addFriendButton.setVisibility(View.VISIBLE);
        }
    }

    // Called after adding a new friend
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra("new-name");
            String birthday = data.getStringExtra("new-birthday");
            friends.add(new Friend(name, birthday));
        }
    }


    private void toast(String toastMessage) {
        Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        toast.show();
    }
}