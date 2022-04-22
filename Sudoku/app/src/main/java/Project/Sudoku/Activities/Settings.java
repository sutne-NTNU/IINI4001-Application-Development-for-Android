package Project.Sudoku.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Project.Sudoku.Classes.Board;
import Project.Sudoku.Classes.BoardHandler;
import Project.Sudoku.Classes.LanguageHandler;
import Project.Sudoku.R;


public class Settings extends AppCompatActivity
{
    ArrayList<String> savedBoards;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LanguageHandler.loadCurrentLanguage(this);
        setContentView(R.layout.settings);

        ListView listView = findViewById(R.id.list_MyBoards);
        savedBoards = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedBoards);
        listView.setAdapter(adapter);
    }

    private void restartActivity()
    {
        // Restarting activity will load new language
        finish();
        startActivity(getIntent());
    }


    public void onClick_NorwegianFlag(View view)
    {
        if (LanguageHandler.getCurrentLanguage(this).equals("en"))
        {
            LanguageHandler.setCurrentLanguage(this, "no");
            restartActivity();
        }
    }

    public void onClick_BritishFlag(View view)
    {
        if (LanguageHandler.getCurrentLanguage(this).equals("no"))
        {
            LanguageHandler.setCurrentLanguage(this, "en");
            restartActivity();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick_ViewBoards(View view)
    {
        savedBoards.clear();
        // Get all currently saved boards
        ArrayList<Board> easy = BoardHandler.readBoardsFromFile(this, "boards-easy");
        ArrayList<Board> normal = BoardHandler.readBoardsFromFile(this, "boards-normal");
        ArrayList<Board> hard = BoardHandler.readBoardsFromFile(this, "boards-hard");
        for (Board b : easy)
            savedBoards.add(b.toString());
        for (Board b : normal)
            savedBoards.add(b.toString());
        for (Board b : hard)
            savedBoards.add(b.toString());

        Button button_DeleteBoards = findViewById(R.id.button_DeleteBoards);
        if (savedBoards.size() > 0)
            button_DeleteBoards.setVisibility(View.VISIBLE);
        else
        {
            button_DeleteBoards.setVisibility(View.INVISIBLE);
            Toast.makeText(this, getString(R.string.feedback_FoundNoSavedBoards), Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    public void onClick_Delete(View view)
    {
        savedBoards.clear();
        adapter.notifyDataSetChanged();
        BoardHandler.deleteFile(this, "boards-easy");
        BoardHandler.deleteFile(this, "boards-normal");
        BoardHandler.deleteFile(this, "boards-hard");
        Button button_Delete = findViewById(R.id.button_DeleteBoards);
        button_Delete.setVisibility(View.INVISIBLE);
        Toast.makeText(this, getString(R.string.feedback_BoardsWereDeleted), Toast.LENGTH_SHORT).show();
    }


    public void onClick_Return(View view)
    {
        finish();
    }
}
