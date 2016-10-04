package org.brenomachado.bestmovies.infrastructure.tasks;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by breno on 03/10/2016.
 */

public interface TaskViewListener {
    void update(Bitmap data, ImageView view);
}
