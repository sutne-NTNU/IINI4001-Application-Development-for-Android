package A4.Fragments_ActionBar_And_Menus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageFragment extends Fragment {
    private TextView textViewMovieDescription;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        textViewMovieDescription = (TextView) view.findViewById(R.id.fragment_image_description);
        imageView = (ImageView) view.findViewById(R.id.fragment_image_view);
        return view;
    }

    public void changeImage(int resourceId, String movieDescription) {
        imageView.setImageResource(resourceId);
        textViewMovieDescription.setText(movieDescription);
    }
}
