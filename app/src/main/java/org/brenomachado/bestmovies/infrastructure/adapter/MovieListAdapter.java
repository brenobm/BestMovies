package org.brenomachado.bestmovies.infrastructure.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.brenomachado.bestmovies.R;
import org.brenomachado.bestmovies.infrastructure.tasks.GetImageTask;
import org.brenomachado.bestmovies.infrastructure.tasks.TaskViewListener;
import org.brenomachado.bestmovies.entity.Movie;
import org.brenomachado.bestmovies.entity.MovieList;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by breno on 03/10/2016.
 */

public class MovieListAdapter extends BaseAdapter
        implements TaskViewListener {
    private Context context;
    private MovieList movieList;
    private int parentLayout;
    private int imageViewPoster;
    private LayoutInflater inflater;
    private ViewGroup parent;

    private final Object mLock = new Object();


    public MovieListAdapter(@NonNull Context context,
                            @LayoutRes int resource,
                            @IdRes int imageViewResource,
                            @NonNull MovieList movieList) {
        this.context = context;
        this.parentLayout = resource;
        this.imageViewPoster = imageViewResource;
        this.movieList = movieList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public Movie getItem(int position) {
        return movieList.getMovies().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        final View view;

        this.parent = parent;

        if (convertView == null) {
            view = inflater.inflate(this.parentLayout, parent, false);
        } else {
            view = convertView;
        }

        final Movie movie = this.getItem(position);

        imageView = (ImageView) view.findViewById(this.imageViewPoster);

        if (imageView == null) {
            throw new RuntimeException(String.format(context.getString(R.string.exception_view_id),
                    Integer.toString(this.imageViewPoster)));
        }

        imageView.setTag(movie);

        if (movie.hasBitmap()) {
            imageView.setImageBitmap(movie.getPosterImage());
        } else {
            GetImageTask task = new GetImageTask(context,
                    this,
                    imageView);
            task.execute(movie.getPosterPath());
        }

        return view;
    }

    @Override
    public int getCount() {
        return movieList.getMovies().size();
    }

    @Override
    public void update(Bitmap data, ImageView view) {
        view.setImageBitmap(data);
        ((Movie)view.getTag()).setPosterImage(data);
    }

    public void add(@Nullable MovieList object) {
        synchronized (mLock) {
            movieList = object;
        }

        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
