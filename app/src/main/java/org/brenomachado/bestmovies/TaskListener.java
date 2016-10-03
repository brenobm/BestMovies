package org.brenomachado.bestmovies;

/**
 * Created by breno on 30/09/2016.
 */

public interface TaskListener <TType> {
    void update (TType data);
}
