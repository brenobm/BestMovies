package org.brenomachado.bestmovies.entity;

import android.graphics.Bitmap;

import org.brenomachado.bestmovies.infrastructure.SerializableBitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by breno on 29/09/2016.
 */

public class Movie implements Serializable {
    private String title;

    private Date releaseDate;

    private String posterPath;

    private double voteAverage;

    private String plotSynopsis;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

}
