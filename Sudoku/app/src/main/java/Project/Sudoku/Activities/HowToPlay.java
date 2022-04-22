package Project.Sudoku.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import Project.Sudoku.R;


public class HowToPlay extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howtoplay);
    }


    public void onClick_Return(View view)
    {
        finish();
    }
}

