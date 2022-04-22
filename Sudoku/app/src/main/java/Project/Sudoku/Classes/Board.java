package Project.Sudoku.Classes;

import java.util.ArrayList;

public class Board
{
    private final boolean[][] startingValues = new boolean[9][9];
    private final boolean[][] unsure = new boolean[9][9];
    private final int[][] values = new int[9][9];

    public Board()
    {

    }

    // initializes the board from a string from storage
    public Board(String fromStorage)
    {
        int columnNR;
        int rowNR = 0;
        for (String row : fromStorage.split("-", 9))
        {
            columnNR = 0;
            for (String column : row.split(" ", 9))
            {
                String identifier = column.split("")[0];
                int value = Integer.parseInt(column.split("")[1]);
                if (identifier.equals("s"))
                    setStartingValue(rowNR, columnNR, value);
                else if (identifier.equals("u"))
                {
                    setValue(rowNR, columnNR, value);
                    setUnsure(rowNR, columnNR, true);
                }
                else
                    setValue(rowNR, columnNR, value);
                columnNR++;
            }
            rowNR++;
        }
    }

    public void setStartingValue(int row, int column, int value)
    {
        startingValues[row][column] = true;
        values[row][column] = value;
    }

    public boolean isStartingValue(int row, int column)
    {
        return startingValues[row][column];
    }

    public void setUnsure(int row, int column, boolean unsure)
    {
        this.unsure[row][column] = unsure;
    }

    public boolean isUnsure(int row, int column)
    {
        return unsure[row][column];
    }

    public void setValue(int row, int column, int value)
    {
        values[row][column] = value;
    }

    public int getValue(int row, int column)
    {
        return values[row][column];
    }

    /*

    Conversion between group/group-positions and row/column

     */
    public int getGroupNr(int row, int column)
    {
        return 3 * (int) (row / 3) + (int) (column / 3);
    }

    public int getCellNrInGroup(int row, int column)
    {
        return 3 * (row % 3) + column % 3;
    }

    public int getRowFromGroup(int groupNr, int groupPosition)
    {
        return (groupNr / 3) * 3 + (groupPosition / 3);
    }

    public int getColumnFromGroup(int groupNr, int groupPosition)
    {
        return (groupNr % 3) * 3 + ((groupPosition) % 3);
    }

    /*

    Verification of results

     */

    public boolean isFilled()
    {
        for (int i = 0; i < values.length; i++)
        {
            for (int j = 0; j < values[i].length; j++)
            {
                if (values[i][j] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /*
    Finds any values are placed incorrectly, and return their placements in a formatted ArrayList
     */
    public ArrayList<String> getWrongValuePositions()
    {
        ArrayList<String> wrongValuePositions = new ArrayList<>();
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                int value = values[row][column];
                // check column for matching value
                for (int check = 0; check < 9; check++)
                {
                    if (value == values[check][column] && row != check)
                    {
                        wrongValuePositions.add(row + "," + column);
                    }
                }

                // check row for matching value
                for (int check = 0; check < 9; check++)
                {
                    if (value == values[row][check] && column != check)
                    {
                        wrongValuePositions.add(row + "," + column);
                    }
                }

                // check within current group for matching value
                int group = getGroupNr(row, column);
                int startRow = getRowFromGroup(group, 0);
                int startColumn = getColumnFromGroup(group, 0);
                for (int groupRow = startRow; groupRow < startRow + 2; groupRow++)
                {
                    for (int groupColumn = startColumn; groupColumn < startColumn + 3; groupColumn++)
                    {
                        if (value == values[groupRow][groupColumn] && (row != groupRow && column != groupColumn))
                        {
                            wrongValuePositions.add(row + "," + column);
                        }
                    }
                }
            }
        }
        return wrongValuePositions;
    }


    // Formats board to string for storage
    public String toStorage()
    {
        StringBuilder board = new StringBuilder();
        for (int row = 0; row < values.length; row++)
        {
            for (int column = 0; column < values[row].length; column++)
            {
                int value = values[row][column];
                if (startingValues[row][column])
                    board.append("s" + value);
                else if (unsure[row][column])
                    board.append("u" + value);
                else
                    board.append("v" + value);
                board.append(" ");
            }
            board.append("-");
        }
        return board.toString() + "\n";
    }

    @Override
    public String toString()
    {
        StringBuilder board = new StringBuilder();
        for (int row = 0; row < values.length; row++)
        {
            for (int column = 0; column < values[row].length; column++)
            {
                if (column == 0 && row != 0)
                    // newline at start of each row except first one
                    board.append("\n");
                int currentNumber = values[row][column];
                if (currentNumber == 0)
                    board.append("  ");
                else
                    board.append(currentNumber);
                if (column != (values[row].length - 1))
                    board.append(" ");
            }
        }
        board.append("\n");
        return board.toString();
    }
}
