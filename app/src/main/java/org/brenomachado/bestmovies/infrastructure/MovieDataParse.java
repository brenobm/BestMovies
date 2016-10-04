package org.brenomachado.bestmovies.infrastructure;

import android.util.Log;

import org.brenomachado.bestmovies.entity.Movie;
import org.brenomachado.bestmovies.entity.MovieList;
import org.brenomachado.bestmovies.exception.KnowException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by breno on 30/09/2016.
 */

public class MovieDataParse {

    private final static String TOKEN_PAGE = "page";
    private final static String TOKEN_MOVIE_LIST = "results";
    private final static String TOKEN_TOTAL_PAGES = "total_pages";
    private final static String TOKEN_TOTAL_RESULTS = "total_results";

    private final static String TOKEN_MOVIE_TITLE = "title";
    private final static String TOKEN_MOVIE_RELEASE_DATE = "release_date";
    private final static String TOKEN_MOVIE_POSTER_PATH = "poster_path";
    private final static String TOKEN_MOVIE_VOTE_AVERAGE = "vote_average";
    private final static String TOKEN_MOVIE_PLOT_SYNOPSIS = "overview";

    public static MovieList getMoviesDataFromJson(String jsonString) throws KnowException {
        MovieList movieList = new MovieList();

        Log.v("Teste", jsonString);

        try {
            JSONObject parsedObject = new JSONObject(jsonString);

            movieList.setPage(parsedObject.getInt(MovieDataParse.TOKEN_PAGE));
            movieList.setTotalPages(parsedObject.getInt(MovieDataParse.TOKEN_TOTAL_PAGES));
            movieList.setTotalResults(parsedObject.getInt(MovieDataParse.TOKEN_TOTAL_RESULTS));

            JSONArray list = parsedObject.getJSONArray(MovieDataParse.TOKEN_MOVIE_LIST);

            for (int i = 0; i < list.length(); i++) {
                JSONObject parsedMovie = list.getJSONObject(i);

                Movie movie = new Movie();

                movie.setTitle(parsedMovie.getString(MovieDataParse.TOKEN_MOVIE_TITLE));
                movie.setPosterPath(parsedMovie.getString(MovieDataParse.TOKEN_MOVIE_POSTER_PATH));
                movie.setPlotSynopsis(parsedMovie.getString(MovieDataParse.TOKEN_MOVIE_PLOT_SYNOPSIS));
                movie.setVoteAverage(parsedMovie.getDouble(MovieDataParse.TOKEN_MOVIE_VOTE_AVERAGE));

                String dateToParse = parsedMovie.getString(MovieDataParse.TOKEN_MOVIE_RELEASE_DATE);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = sdf.parse(dateToParse);

                movie.setReleaseDate(parsedDate);

                movieList.getMovies().add(movie);
            }

        } catch (JSONException je) {
            throw new KnowException("Erro ao parsear a resposta json do serviço.");
        } catch (ParseException e) {
            throw new KnowException("Erro ao parsear a resposta json do serviço.");
        }

        return movieList;
    }
}
