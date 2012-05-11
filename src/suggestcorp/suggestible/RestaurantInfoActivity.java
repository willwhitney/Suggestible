package suggestcorp.suggestible;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class RestaurantInfoActivity extends Activity {
    Double latitude = 42.359013;
    Double longitude = -71.09354;
    String location = "32 Vassar St, Cambridge, MA, 02139";

    public void onCreate(Bundle savedInstanceState) {
        /** hello */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantlayout);

        String urlLocation = URLEncoder.encode(location);
        String url = "http://maps.google.com/maps/api/staticmap?size=250x200&maptype=roadmap&sensor=false&markers=color:green%7Clabel:A%7C"
                + latitude
                + ","
                + longitude
                + "&markers=color:green%7Clabel:B%7C" + urlLocation;
        new MapFetcher().execute(url);

        Button googleButton = (Button) findViewById(R.id.googlebutton);
        googleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlLocation = URLEncoder.encode(location);
                String directions = "http://maps.google.com/maps?saddr=" + latitude
                        + "," + longitude + "&daddr=" + urlLocation;
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(directions));
                RestaurantInfoActivity.this.startActivity(intent);
            }
        });
        
        Button yelpButton = (Button) findViewById(R.id.yelpbutton);
        yelpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String yelpURL = "";
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(yelpURL));
                RestaurantInfoActivity.this.startActivity(intent);
            }
        });

    }

    private class MapFetcher extends AsyncTask<String, Void, Drawable> {

        @Override
        protected Drawable doInBackground(String... params) {
            try {
                InputStream is = (InputStream) new URL(params[0]).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e) {
                Log.e("Suggestible", "Could not load image at " + params[0]);
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Drawable imgDrawable) {
            ImageView image = (ImageView) findViewById(R.id.mapview);
            image.setImageDrawable(imgDrawable);
        }
    }
}
