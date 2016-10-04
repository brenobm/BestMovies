package org.brenomachado.bestmovies.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;

import org.brenomachado.bestmovies.entity.Movie;
import org.brenomachado.bestmovies.infrastructure.tasks.GetMoviesTask;
import org.brenomachado.bestmovies.infrastructure.adapter.MovieListAdapter;
import org.brenomachado.bestmovies.R;
import org.brenomachado.bestmovies.infrastructure.tasks.TaskListener;
import org.brenomachado.bestmovies.entity.MovieList;

public class MainActivity extends AppCompatActivity
                        implements TaskListener<MovieList> {

    private MovieListAdapter movieAdapter;
    private MovieList movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            this.movieList = (MovieList) savedInstanceState.getSerializable("MovieList");
        } else {
            this.movieList = new MovieList();
        }

        movieAdapter = new MovieListAdapter(getApplicationContext(),
                R.layout.movie_banner,
                R.id.image_movie,
                this.movieList
        );

        GridView gridMovies = (GridView) findViewById(R.id.grid_view_movies);

        gridMovies.setAdapter(movieAdapter);

        gridMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Context context = getApplicationContext();
                //CharSequence text = mForecastAdapter.getItem(i);
                //int duration = Toast.LENGTH_SHORT;

                //Toast toast = Toast.makeText(context, text, duration);
                //toast.show();
                Movie movie = (Movie) movieAdapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movie);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        updateMovies(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("MovieList", this.movieList);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        this.movieList = (MovieList) savedInstanceState.getSerializable("MovieList");

        update(this.movieList);
    }

    @Override
    public void update(MovieList data) {
        this.movieList = data;

        this.movieAdapter.add(data);
    }

    private void updateMovies(int page) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sort = prefs.getString(getString(R.string.pref_order_key),
                getString(R.string.pref_default_sorted_movie_title));

        GetMoviesTask task = new GetMoviesTask(this, getApplicationContext());

        task.execute(sort, Integer.toString(page));
    }
}
