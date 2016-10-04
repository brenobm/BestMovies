package org.brenomachado.bestmovies.infrastructure.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.brenomachado.bestmovies.exception.KnowException;
import org.brenomachado.bestmovies.infrastructure.MovieDataParse;
import org.brenomachado.bestmovies.R;
import org.brenomachado.bestmovies.entity.MovieList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;

/**
 * Created by breno on 29/09/2016.
 */

public class GetMoviesTask extends AsyncTask<String, Void, MovieList> {

    private TaskListener<MovieList> listener;

    private Context context;

    public GetMoviesTask(TaskListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected void onPostExecute(MovieList lists) {
        super.onPostExecute(lists);

        listener.update(lists);
    }

    @Override
    protected MovieList doInBackground(String... params) {

        try {
            Uri uriService = buildUri(1);
            String jsonResult = retriveJsonFromService(uriService);

            MovieList movieList = MovieDataParse.getMoviesDataFromJson(jsonResult);

            return movieList;

        } catch (KnowException ke) {
            return new MovieList();
        }

    }

    private String retriveJsonFromService(Uri uri) throws KnowException {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResponse = null;

        try {
            URL url = new URL(uri.toString());

            Log.v("Teste", uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            jsonResponse = buffer.toString();


        } catch (MalformedURLException mue) {
            throw new KnowException(context.getString(R.string.exception_malformed_url));
        } catch (IOException ioe) {
            throw new KnowException(context.getString(R.string.exception_network_error));
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    throw new KnowException(context.getString(R.string.exception_stream_error));
                }
            }
        }

        return jsonResponse;
    }

    private Uri buildUri(int page) {
        validPageRange(page);

        String baseUrl = context.getString(R.string.base_url);
        String apiKey = context.getString(R.string.tmdb_key);
        String apiKeyParam = context.getString(R.string.tmdb_key_param);
        String pageParam = context.getString(R.string.tmdb_page_param);
        String serviceDirectory = context.getString(R.string.service_movie_directory);
        String service = context.getString(R.string.service_movie_popular);

        Uri uri = Uri.parse(baseUrl);

        Uri.Builder uriBuilder = uri.buildUpon();
        uriBuilder.appendPath(serviceDirectory);
        uriBuilder.appendPath(service);
        uriBuilder.appendQueryParameter(apiKeyParam, apiKey);
        uriBuilder.appendQueryParameter(pageParam, Integer.toString(page));

        uri = uriBuilder.build();

        return uri;
    }

    private void validPageRange(int page) {
        if (page < 1) {
            throw new InvalidParameterException();
        }
    }
}
