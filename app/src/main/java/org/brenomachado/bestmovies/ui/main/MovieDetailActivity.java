package org.brenomachado.bestmovies.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.brenomachado.bestmovies.R;
import org.brenomachado.bestmovies.entity.Movie;

import java.text.SimpleDateFormat;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(Intent.EXTRA_TEXT);

        TextView textTitle = (TextView) findViewById(R.id.text_movie_title);
        textTitle.setText(movie.getTitle());

        TextView textSynopsis = (TextView) findViewById(R.id.text_movie_plot_synopsis);
        textSynopsis.setText("Sinopse: " + movie.getPlotSynopsis());

        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        TextView textReleaseDate = (TextView) findViewById(R.id.text_movie_release_date);
        textReleaseDate.setText("Data de estréia: " + dt1.format(movie.getReleaseDate()));

        TextView textAverage = (TextView) findViewById(R.id.text_movie_vote_average);
        textAverage.setText(String.format("Média de votos: %.2f", movie.getVoteAverage()));

        ImageView imagePoster = (ImageView) findViewById(R.id.image_movie_poster);
        imagePoster.setImageBitmap(movie.getPosterImage());
    }

}
