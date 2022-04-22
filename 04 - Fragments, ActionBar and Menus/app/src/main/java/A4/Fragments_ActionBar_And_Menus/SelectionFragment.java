package A4.Fragments_ActionBar_And_Menus;

import android.os.Bundle;
import android.view.View;

import android.app.Activity;
import android.app.ListFragment;
import android.content.res.Resources;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SelectionFragment extends ListFragment {
    private String[] movieTitles;
    private String[] movieDescriptions;
    private SelectionListener movieListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = getResources();
        movieTitles = resources.getStringArray(R.array.move_titles);
        movieDescriptions = resources.getStringArray(R.array.movie_descriptions);
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, movieTitles));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            movieListener = (SelectionListener) activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentMovieInteractionListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        movieListener.OnSelectionListener(position, movieTitles[position], movieDescriptions[position]);
    }

    public interface SelectionListener {
        void OnSelectionListener(int resourceId, String title, String movieDescription);
    }
}