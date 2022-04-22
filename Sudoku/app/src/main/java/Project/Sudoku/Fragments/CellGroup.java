package Project.Sudoku.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import Project.Sudoku.R;


public class CellGroup extends Fragment
{
    private OnFragmentInteractionListener listener;
    private int groupID;
    private View view;
    private final int[] cellIDs;

    public CellGroup()
    {
        cellIDs = new int[]{
                R.id.cell_1,
                R.id.cell_2,
                R.id.cell_3,
                R.id.cell_4,
                R.id.cell_5,
                R.id.cell_6,
                R.id.cell_7,
                R.id.cell_8,
                R.id.cell_9
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.cellgroup_fragment, container, false);
        for (int id : cellIDs)
        {
            TextView textView = view.findViewById(id);
            textView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    listener.onFragmentInteraction(groupID, Integer.parseInt(view.getTag().toString()), view);
                }
            });
        }
        return view;
    }

    public void setGroupID(int groupID)
    {
        this.groupID = groupID;
    }

    public TextView getCell(int position)
    {
        return view.findViewById(cellIDs[position]);
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
            listener = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(int groupId, int cellId, View view);
    }
}
