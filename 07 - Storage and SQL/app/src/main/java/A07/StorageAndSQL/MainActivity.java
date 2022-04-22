package A07.StorageAndSQL;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity
{
    private final String TAG = "MainActivity";

    private DatabaseManager databaseManager;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems;
    private ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Read from file and insert into database
        ArrayList<String[]> infoFromFile = readFile(R.raw.bookinforomation);
        databaseManager = new DatabaseManager(this);
        insertIntoDatabase(infoFromFile);

        listView = findViewById(R.id.dbListView);
        listItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        // Set background of list
        String backgroundColor = getDefaultSharedPreferences(this).getString("backgroundColorPref", "#ffffff");
        listView.setBackgroundColor(Color.parseColor(backgroundColor));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getItemId() == R.id.menu_color_settings)
        {
            Intent intent = new Intent("A07.StorageAndSQL.SettingsActivity");
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            listView = findViewById(R.id.dbListView);
            String backgroundColor = getDefaultSharedPreferences(this).getString("backgroundColorPref", "#ffffff");
            listView.setBackgroundColor(Color.parseColor(backgroundColor));
        }
    }

    public void onClick_RadioButton(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();
        if (!checked) return;

        switch (view.getId())
        {
            case R.id.radioButtonAuthor:
                ArrayList<String> authors = databaseManager.getAllAuthors();
                listItems.clear();
                listItems.addAll(authors);
                adapter.notifyDataSetChanged();
                break;

            case R.id.radioButtonTitle:
                ArrayList<String> books = databaseManager.getAllBooks();
                listItems.clear();
                listItems.addAll(books);
                adapter.notifyDataSetChanged();
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private ArrayList<String[]> readFile(int id)
    {
        ArrayList<String[]> dataFromFile = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(id))))
        {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                dataFromFile.add(line.split("-"));
            }
        }
        catch (IOException e)
        {
            Log.e(TAG, e.getMessage());
        }
        return dataFromFile;
    }

    private void insertIntoDatabase(ArrayList<String[]> info)
    {
        try
        {
            databaseManager.clean();
            for (int i = 0; i < info.size(); i++)
            {
                databaseManager.insert(info.get(i)[0], info.get(i)[1]);
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }
    }


}
