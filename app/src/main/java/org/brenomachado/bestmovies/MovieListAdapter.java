package org.brenomachado.bestmovies;

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

        GetImageTask task = new GetImageTask(context,
                this,
                imageView);
        task.execute(movie.getPosterPath());

        return view;
    }

    @Override
    public int getCount() {
        return movieList.getMovies().size();
    }

    @Override
    public void update(Bitmap data, ImageView view) {
        view.setImageBitmap(data);
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

    public class GetImageTask extends AsyncTask<String, Void, Bitmap>
    {
        private Context context;
        private TaskViewListener listener;
        private ImageView view;

        public GetImageTask(Context context,
                            TaskViewListener listener,
                            ImageView view) {
            super();
            this.context = context;
            this.listener = listener;
            this.view = view;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            listener.update(bitmap, view);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bmp;
            String baseUrl = context.getString(R.string.base_url_image);
            String imageSize = context.getString(R.string.file_size);

            try {
                Uri uri = Uri.parse(baseUrl);

                Uri.Builder uriBuilder = uri.buildUpon();
                uriBuilder.appendPath(imageSize);
                uriBuilder.appendPath(params[0].replace("/", ""));

                uri = uriBuilder.build();

                URL url = new URL(uri.toString());
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                return bmp;
            } catch (MalformedURLException mue) {
                throw new RuntimeException(context.getString(R.string.exception_malformed_url));
            } catch (IOException ie) {
                Log.e("Teste", ie.getMessage());
                throw new RuntimeException(context.getString(R.string.exception_network_error));
            }
        }
    }
}
