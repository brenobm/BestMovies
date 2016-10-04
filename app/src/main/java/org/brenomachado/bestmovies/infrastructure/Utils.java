package org.brenomachado.bestmovies.infrastructure;

import android.content.Context;
import android.net.Uri;

import org.brenomachado.bestmovies.R;

/**
 * Created by breno on 04/10/2016.
 */

public class Utils {
    public static String getUrl(String posterPath, Context context){
        String baseUrl = context.getString(R.string.base_url_image);
        String imageSize = context.getString(R.string.file_size);

        Uri uri = Uri.parse(baseUrl);

        Uri.Builder uriBuilder = uri.buildUpon();
        uriBuilder.appendPath(imageSize);
        uriBuilder.appendPath(posterPath.replace("/", ""));

        uri = uriBuilder.build();

        return uri.toString();
    }
}
