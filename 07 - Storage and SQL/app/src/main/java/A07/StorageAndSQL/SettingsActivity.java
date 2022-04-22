package A07.StorageAndSQL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onGoBackButton(View view)
    {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
