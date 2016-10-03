package org.brenomachado.bestmovies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by breno on 30/09/2016.
 */

public class MovieList {
    private int page;
    private long totalResults;
    private long totalPages;

    private List<Movie> movies = new ArrayList<Movie>();

    public List<Movie> getMovies() {
        return movies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
