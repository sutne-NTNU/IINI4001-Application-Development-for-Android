package A4.Fragments_ActionBar_And_Menus;

import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SelectionFragment.SelectionListener {
    @StyleableRes
    private int selected = 0;
    private TypedArray movieCovers;
    private String[] movieTitles;
    private String[] movieDescriptions;
    private ImageFragment imageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources resources = getResources();
        movieCovers = resources.obtainTypedArray(R.array.movie_covers);
        movieTitles = resources.getStringArray(R.array.move_titles);
        movieDescriptions = resources.getStringArray(R.array.movie_descriptions);
        imageFragment = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.image_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_previous:
                previousImage();
                return true;
            case R.id.menu_next:
                nextImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void previousImage() {
        if (selected == 0) {
            toast("This is the first movie");
        } else {
            selected--;
            OnSelectionListener(selected, movieTitles[selected], movieDescriptions[selected]);
        }
    }

    public void nextImage() {
        if (selected == movieTitles.length - 1) {
            toast("You have reached the end of the movies!");
        } else {
            selected++;
            OnSelectionListener(selected, movieTitles[selected], movieDescriptions[selected]);
        }
    }

    @Override
    public void OnSelectionListener(int resourceId, String title, String movieDescription) {
        selected = resourceId;
        int resourceID = movieCovers.peekValue(resourceId).resourceId;
        TextView movieTitle = findViewById(R.id.movie_title);
        movieTitle.setText(title);
        imageFragment.changeImage(resourceID, movieDescription);
    }


    private void toast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
