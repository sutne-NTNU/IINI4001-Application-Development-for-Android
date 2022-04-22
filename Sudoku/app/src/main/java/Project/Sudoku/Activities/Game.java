package Project.Sudoku.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Project.Sudoku.Classes.Board;
import Project.Sudoku.Classes.BoardHandler;
import Project.Sudoku.Fragments.CellGroup;
import Project.Sudoku.R;


public class Game extends AppCompatActivity implements CellGroup.OnFragmentInteractionListener
{
    private final int EASY = 0;
    private final int NORMAL = 1;
    private final int HARD = 2;
    private final int SAVED = 3;

    // Create CellGroup fragments
    int[] cgf_IDs;
    // Current board
    private Board board;
    // Current cell
    private int selected_GroupID;
    private TextView selected_Cell;
    private int selected_CellID;
    private int selected_Value;
    // currentEdit
    private boolean selected_IsUnsure;
    // Components
    private TableLayout layout_SelectNumber;
    private Spinner spinner;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        layout_SelectNumber = findViewById(R.id.layout_SelectNumber);

        int board_type = getIntent().getIntExtra("board-type", 0);
        cgf_IDs = new int[]{
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
        initializeBoard(board_type);
        initializeSpinner();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected Board getBoard(int type)
    {
        switch (type)
        {
            case EASY:
                ArrayList<Board> easyBoards = BoardHandler.readFromRaw(this, R.raw.easy);
                easyBoards.addAll(BoardHandler.readBoardsFromFile(this, "boards-easy"));
                return easyBoards.get((int) (Math.random() * easyBoards.size()));

            case NORMAL:
                ArrayList<Board> normalBoards = BoardHandler.readFromRaw(this, R.raw.normal);
                normalBoards.addAll(BoardHandler.readBoardsFromFile(this, "boards-normal"));
                return normalBoards.get((int) (Math.random() * normalBoards.size()));

            case HARD:
                ArrayList<Board> hardBoards = BoardHandler.readFromRaw(this, R.raw.hard);
                hardBoards.addAll(BoardHandler.readBoardsFromFile(this, "boards-hard"));
                return hardBoards.get((int) (Math.random() * hardBoards.size()));

            case SAVED:
                ArrayList<Board> savedBoard = BoardHandler.readBoardsFromFile(this, "boards-saved");
                if (savedBoard.size() > 1)
                    Toast.makeText(this, getString(R.string.feedback_MoreThanOneBoardSaved), Toast.LENGTH_LONG).show();
                return savedBoard.get(0);
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void initializeBoard(int board_type)
    {
        board = getBoard(board_type);
        for (int i = 0; i < 9; i++)
        {
            CellGroup fragment = (CellGroup) getSupportFragmentManager().findFragmentById(cgf_IDs[i]);
            fragment.setGroupID(i);
        }
        CellGroup fragment;
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                int value = board.getValue(row, column);
                if (value == 0) continue;
                int[] fragmentPosition = fragmentPosition(row, column);
                fragment = (CellGroup) getSupportFragmentManager().findFragmentById(cgf_IDs[fragmentPosition[0]]);
                TextView cell = fragment.getCell(fragmentPosition[1]);
                cell.setText(Integer.toString(value));
                if (board.isStartingValue(row, column))
                {
                    cell.setTextColor(Color.BLACK);
                    cell.setBackground(getResources().getDrawable(R.drawable.cell_starting_value));
                }
                else if (board.isUnsure(row, column))
                {
                    cell.setBackground(getResources().getDrawable(R.drawable.cell_unsure));
                }
            }
        }
    }

    private int[] fragmentPosition(int row, int column)
    {
        int fragmentNumber = (row / 3 * 3) + column / 3;
        int cellPosition = ((row % 3) * 3) + (column % 3);
        int[] frPos = new int[2];
        frPos[0] = fragmentNumber;
        frPos[1] = cellPosition;
        return frPos;
    }


    private void initializeSpinner()
    {
        spinner = findViewById(R.id.spinner);
        final Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selected_Value = values[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
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

    @Override
    public void onFragmentInteraction(int groupID, int cellID, View view)
    {
        // Un-selecting previous cell
        if (selected_Cell != null)
        {
            if (selected_IsUnsure)
                selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell_unsure));
            else
                selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell));
        }
        int row = board.getRowFromGroup(groupID, cellID);
        int column = board.getColumnFromGroup(groupID, cellID);
        // making sure this cell isn't a starting value
        if (board.isStartingValue(row, column))
        {
            layout_SelectNumber.setVisibility(View.INVISIBLE);
            return;
        }
        //Checking if new selected cell was already marked as unsure
        CheckBox unsureCheckBox = findViewById(R.id.checkBox);
        if (board.isUnsure(row, column))
        {
            unsureCheckBox.setChecked(true);
            selected_IsUnsure = true;
        }
        else
        {
            unsureCheckBox.setChecked(false);
            selected_IsUnsure = false;
        }
        // set new values for new selected cell
        layout_SelectNumber.setVisibility(View.VISIBLE);
        selected_Cell = (TextView) view;
        if (!selected_Cell.getText().toString().equals(""))
            setSpinnerValue(selected_Cell.getText().toString());
        selected_GroupID = groupID;
        selected_CellID = cellID;
        selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell_selected));
    }


    public void onClick_Unsure(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        selected_IsUnsure = view.getId() == R.id.checkBox && checked;
    }

    public void onClick_Clear(View view)
    {
        int row = board.getRowFromGroup(selected_GroupID, selected_CellID);
        int column = board.getColumnFromGroup(selected_GroupID, selected_CellID);
        layout_SelectNumber.setVisibility(View.INVISIBLE);
        board.setValue(row, column, 0);
        selected_Cell.setText("");
        board.setUnsure(row, column, false);
        selected_IsUnsure = false;
        selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell));
        // if the board was previously filled we have to now hide the check board button
        Button buttonCheckBoard = findViewById(R.id.buttonCheckBoard);
        buttonCheckBoard.setVisibility(View.INVISIBLE);
    }

    public void onClick_Confirm(View view)
    {
        int row = board.getRowFromGroup(selected_GroupID, selected_CellID);
        int column = board.getColumnFromGroup(selected_GroupID, selected_CellID);
        selected_Cell.setText("" + selected_Value);
        board.setValue(row, column, selected_Value);
        layout_SelectNumber.setVisibility(View.INVISIBLE);
        if (selected_IsUnsure)
        {
            selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell_unsure));
            board.setUnsure(row, column, true);
        }
        else
        {
            selected_Cell.setBackground(getResources().getDrawable(R.drawable.cell));
            board.setUnsure(row, column, false);
        }
        selected_Cell = null;
        if (board.isFilled())
        {
            Button buttonCheckBoard = findViewById(R.id.buttonCheckBoard);
            buttonCheckBoard.setVisibility(View.VISIBLE);
        }
    }


    public void onClick_CheckBoard(View view)
    {
        CellGroup currentFragment;
        TextView currentCell;
        // Assume all cells were correct
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                currentFragment = (CellGroup) getSupportFragmentManager().findFragmentById(cgf_IDs[board.getGroupNr(row, column)]);
                currentCell = currentFragment.getCell(board.getCellNrInGroup(row, column));
                if (!board.isStartingValue(row, column))
                {
                    currentCell.setBackground(getResources().getDrawable(R.drawable.cell_correct));
                }
            }
        }
        ArrayList<String> wrongValuePosition = board.getWrongValuePositions();
        if (wrongValuePosition.size() == 0)
        {
            Toast.makeText(this, getString(R.string.feedback_BoardIsCorrect), Toast.LENGTH_SHORT).show();
            return;
        }

        // Change cells that are wrong to be red:
        for (String s : wrongValuePosition)
        {
            String[] positionInGrid = s.split(",");
            int[] fragmentPosition = fragmentPosition(Integer.parseInt(positionInGrid[0]), Integer.parseInt(positionInGrid[1]));
            int groupNr = fragmentPosition[0];
            int cellNrInGroup = fragmentPosition[1];
            if (!board.isStartingValue(board.getRowFromGroup(groupNr, cellNrInGroup), board.getColumnFromGroup(groupNr, cellNrInGroup)))
            {
                CellGroup fragment = (CellGroup) getSupportFragmentManager().findFragmentById(cgf_IDs[groupNr]);
                TextView cell = fragment.getCell(cellNrInGroup);
                cell.setBackground(getResources().getDrawable(R.drawable.cell_wrong));
            }
        }
        Toast.makeText(this, getString(R.string.feedback_BoardIsNotCorrect), Toast.LENGTH_SHORT).show();
    }

    public void onClick_Instructions(View view)
    {
        startActivity(new Intent("Project.Sudoku.Activities.HowToPlay"));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick_Return(View view)
    {
        // delete previous saved game if there is one
        if (BoardHandler.fileExists(this, "boards-saved"))
            BoardHandler.deleteFile(this, "boards-saved");
        BoardHandler.writeBoardToFile(this, "boards-saved", board);
        Toast.makeText(this, getString(R.string.feedback_BoardWasSaved), Toast.LENGTH_LONG).show();
        finish();
    }
}
