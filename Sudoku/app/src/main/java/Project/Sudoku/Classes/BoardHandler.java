package Project.Sudoku.Classes;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BoardHandler
{
    /*

    Read from app/src/main/res/raw folder

     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static ArrayList<Board> readFromRaw(Context context, int fileID)
    {
        ArrayList<Board> boards = new ArrayList<>();
        InputStream inputStream = context.getResources().openRawResource(fileID);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                Board board = new Board();
                for (int row = 0; row < 9; row++)
                {
                    String[] row_values = line.split(" ", 9);
                    for (int column = 0; column < 9; column++)
                    {
                        if (row_values[column].equals("_"))
                            board.setValue(row, column, 0);
                        else
                            board.setStartingValue(row, column, Integer.parseInt(row_values[column]));
                    }
                    line = reader.readLine();
                }
                boards.add(board);
            }
        }
        catch (NumberFormatException nfe)
        {
            nfe.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return boards;
    }

    /*

    Internal Storage

     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void writeBoardToFile(Context context, String filename, Board board)
    {
        try (FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_APPEND))
        {
            fileOutputStream.write(board.toStorage().getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static ArrayList<Board> readBoardsFromFile(Context context, String filename)
    {
        ArrayList<Board> boards = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                boards.add(new Board(line));
            }
        }
        catch (FileNotFoundException ignored)
        {
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return boards;
    }

    public static boolean fileExists(Context context, String filename)
    {
        File file = new File(context.getFilesDir(), filename);
        return file.exists();
    }

    public static void deleteFile(Context context, String filename)
    {
        context.deleteFile(filename);
    }
}
