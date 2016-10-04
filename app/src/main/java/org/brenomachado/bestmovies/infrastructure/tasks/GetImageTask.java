package org.brenomachado.bestmovies.infrastructure.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.brenomachado.bestmovies.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by breno on 03/10/2016.
 */

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

