package Project.Sudoku.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import Project.Sudoku.Classes.Board;
import Project.Sudoku.Classes.BoardHandler;
import Project.Sudoku.Fragments.CellGroup;
import Project.Sudoku.R;


public class AddBoard extends AppCompatActivity implements CellGroup.OnFragmentInteractionListener
{
    private final int EASY = 0;
    private final int NORMAL = 1;
    private final int HARD = 2;

    // New Board values
    private Board board;
    //Components
    private TableLayout layout_SelectNumber;
    private Spinner spinner;
    private int selected_Number;
    // Selected cell information
    private int selected_GroupID;
    private int selected_CellID;
    private TextView selected_Cell;
    // Values
    private int selected_Difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addboard);
        layout_SelectNumber = findViewById(R.id.layout_SelectNumber);
        initializeSpinner();
        initializeBoard();
    }

    private void initializeBoard()
    {
        board = new Board();
        int[] cellGroups = new int[]{
                R.id.group0,
                R.id.group1,
                R.id.group2,
                R.id.group3,
                R.id.group4,
                R.id.group5,
                R.id.group6,
                R.id.group7,
                R.id.group8
        };
        for (int i = 0; i < 9; i++)
        {
            CellGroup cellGroup = (CellGroup) getSupportFragmentManager().findFragmentById(cellGroups[i]);
            cellGroup.setGroupID(i);
        }
    }

    private void initializeSpinner()
    {
        spinner = findViewById(R.id.spinner);
        final Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numbers);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selected_Number = numbers[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });
    }


    @Override
    public void onFragmentInteraction(int groupID, int cellID, View view)
    {
        if (selected_Cell != null)
        {
            selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell));
        }
        selected_GroupID = groupID;
        selected_CellID = cellID;
        selected_Cell = (TextView) view;
        if (!selected_Cell.getText().toString().equals(""))
        {
            setSpinnerValue(selected_Cell.getText().toString());
        }
        layout_SelectNumber.setVisibility(View.VISIBLE);
        selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell_selected));
    }

    private void setSpinnerValue(String number)
    {
        for (int i = 0; i < spinner.getCount(); i++)
        {
            if (spinner.getItemAtPosition(i).toString().equals(number))
            {
                spinner.setSelection(i);
                return;
            }
        }
        spinner.setSelection(0);
    }

    public void onClick_Clear(View view)
    {
        int row = board.getRowFromGroup(selected_GroupID, selected_CellID);
        int column = board.getColumnFromGroup(selected_GroupID, selected_CellID);
        board.setValue(row, column, 0);
        selected_Cell.setText("");
        selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell));
        layout_SelectNumber.setVisibility(View.INVISIBLE);
    }

    public void onClick_Confirm(View view)
    {
        int row = board.getRowFromGroup(selected_GroupID, selected_CellID);
        int column = board.getColumnFromGroup(selected_GroupID, selected_CellID);
        selected_Cell.setText("" + selected_Number);
        board.setStartingValue(row, column, selected_Number);
        selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell_starting_value));
        selected_Cell = null;
        layout_SelectNumber.setVisibility(View.INVISIBLE);
    }

    public void onClick_RadioButton(View view)
    {
        Button button_Save = findViewById(R.id.button_Save);
        if (!((RadioButton) view).isChecked())
        {
            button_Save.setVisibility(View.INVISIBLE);
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
        button_Save.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick_Save(View view)
    {
        switch (selected_Difficulty)
        {
            case EASY:
                BoardHandler.writeBoardToFile(this, "boards-easy", board);
                break;
            case NORMAL:
                BoardHandler.writeBoardToFile(this, "boards-normal", board);
                break;
            case HARD:
                BoardHandler.writeBoardToFile(this, "boards-hard", board);
                break;
        }
        Toast.makeText(this, getString(R.string.feedback_NewBoardSavedSuccessfully), Toast.LENGTH_SHORT).show();
        finish();
    }


    public void onClick_Return(View view)
    {
        finish();
    }
}
