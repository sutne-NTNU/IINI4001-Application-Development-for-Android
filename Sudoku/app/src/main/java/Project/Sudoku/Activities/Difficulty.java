package Project.Sudoku.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import Project.Sudoku.R;

public class Difficulty extends AppCompatActivity
{
    private int selected_Difficulty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty);
    }


    public void onClick_RadioButton(View view)
    {

        Button button_StartGame = findViewById(R.id.button_StartGame);
        if (!((RadioButton) view).isChecked())
        {
            button_StartGame.setVisibility(View.INVISIBLE);
            return;
        }
        switch (view.getId())
        {
            case R.id.radio_Easy:
                selected_Difficulty = 0;
                break;
            case R.id.radio_Normal:
                selected_Difficulty = 1;
                break;
            case R.id.radio_Hard:
                selected_Difficulty = 2;
                break;
        }
        button_StartGame.setVisibility(View.VISIBLE);
    }

    public void onClick_StartGame(View view)
    {
        Intent intent = new Intent("Project.Sudoku.Activities.Game");
        intent.putExtra("board-type", selected_Difficulty);
        finish();
        startActivity(intent);
    }

    public void onClick_Return(View view)
    {
        finish();
    }
}
