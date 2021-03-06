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
import android.widget.TextView;

public class RestaurantInfoActivity extends Activity {
    Double latitude = 42.359013;
    Double longitude = -71.09354;
    String location = "32 Vassar St, Cambridge, MA, 02139";
    String yelpURL = "yelp.com";


    public void onCreate(Bundle savedInstanceState) {
        /** hello */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantlayout);
        
        
        String title = getIntent().getStringExtra("title");
        if (title != null)
        	((TextView) findViewById(R.id.name)).setText(title);
        
        Double rating = getIntent().getDoubleExtra("rating",0.0);
        fillStars(rating);
        
 
        String description = getIntent().getStringExtra("description");
        if (description != null){
        	((TextView) findViewById(R.id.restaurantdescriptionLarge)).setText(description);
        	((TextView) findViewById(R.id.restaurantdescriptionSmall)).setText(description);
        }
        
        if (getIntent().getStringExtra("location") != null)
        	location = getIntent().getStringExtra("location");
        latitude = getIntent().getDoubleExtra("latitude",latitude) * 1000;
        longitude = getIntent().getDoubleExtra("longitude",longitude) * 1000;
        yelpURL = getIntent().getStringExtra("url");
        Log.d("Suggestible", "yelpURL: " + yelpURL);

        

        String urlLocation = URLEncoder.encode(location);
        String url = "http://maps.google.com/maps/api/staticmap?size=600x450&maptype=roadmap&sensor=false&markers=color:green%7Clabel:A%7C"
                + latitude
                + ","
                + longitude
                + "&markers=color:green%7Clabel:B%7C" + urlLocation;
        new MapFetcher().execute(url);

        Button googleButton = (Button) findViewById(R.id.googlemapsbutton);
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
    
    public void showMore(View view){        
        ((Button)(findViewById(R.id.restaurantshowMoreButton))).setVisibility(8);//make it gone
        ((TextView)(findViewById(R.id.restaurantdescriptionSmall))).setVisibility(8);//make it gone
        ((TextView)(findViewById(R.id.restaurantdescriptionLarge))).setVisibility(2);//make it visible
        
    }
    
    public void fillStars(double rating){
		double stars = rating/20.00;
		if (stars >= 1){
			((View)(findViewById(R.id.star1))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=.5){
			((View)(findViewById(R.id.star1))).setBackgroundResource(R.drawable.half_star);
		}

		if (stars >= 2){
			((View)(findViewById(R.id.star2))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=1.5){
			((View)(findViewById(R.id.star2))).setBackgroundResource(R.drawable.half_star);
		}
		if (stars >= 3){
			((View)(findViewById(R.id.star3))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=2.5){
			((View)(findViewById(R.id.star3))).setBackgroundResource(R.drawable.half_star);
		}
		if (stars >= 4){
			((View)(findViewById(R.id.star4))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=3.5){
			((View)(findViewById(R.id.star4))).setBackgroundResource(R.drawable.full_star);
		}
		if (stars >= 5){
			((View)(findViewById(R.id.star5))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=4.5){
			((View)(findViewById(R.id.star5))).setBackgroundResource(R.drawable.full_star);
		}
		
	}
}
