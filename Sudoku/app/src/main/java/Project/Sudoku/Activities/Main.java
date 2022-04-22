package Project.Sudoku.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import Project.Sudoku.Classes.BoardHandler;
import Project.Sudoku.Classes.LanguageHandler;
import Project.Sudoku.R;

public class Main extends AppCompatActivity
{
    private boolean needsRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LanguageHandler.loadCurrentLanguage(this);
        setContentView(R.layout.main);
        toggleContinueButton();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (needsRefresh)
        {
            // refresh because language might have changed
            needsRefresh = false;
            Intent intent = new Intent(Main.this, Main.class);
            startActivity(intent);
            finish();
        }
        toggleContinueButton();
    }


    private void toggleContinueButton()
    {
        Button button_Continue = findViewById(R.id.button_Continue);
        if (BoardHandler.fileExists(this, "boards-saved"))
            button_Continue.setVisibility(View.VISIBLE);
        else
            button_Continue.setVisibility(View.INVISIBLE);
    }

    public void onClick_ContinueGame(View view)
    {
        int SAVED = 3;
        Intent intent = new Intent("Project.Sudoku.Activities.Game");
        intent.putExtra("board-type", SAVED);
        startActivity(intent);
    }


    public void onClick_NewGame(View view)
    {
        startActivity(new Intent("Project.Sudoku.Activities.Difficulty"));
    }

    public void onClick_AddBoard(View view)
    {
        startActivity(new Intent("Project.Sudoku.Activities.AddBoard"));
    }

    public void onClick_Instructions(View view)
    {
        startActivity(new Intent("Project.Sudoku.Activities.HowToPlay"));
    }

    public void onClick_Settings(View view)
    {
        needsRefresh = true;
        startActivity(new Intent("Project.Sudoku.Activities.Settings"));
    }
}