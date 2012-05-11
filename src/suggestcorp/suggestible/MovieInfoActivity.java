package suggestcorp.suggestible;


import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class MovieInfoActivity extends Activity {
    String title = "Hunger Games";

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movielayout);

        Button theaterButton = (Button) findViewById(R.id.theaterbutton);
        theaterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlePlus = title.replace(" ", "+");
                String url = "http://www.google.com/movies?q="+titlePlus+"&btnG=Search+Movies&near=02139";
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                MovieInfoActivity.this.startActivity(intent);
            }
        });

        Button imdbButton = (Button) findViewById(R.id.imdbbutton);
        imdbButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlePlus = title.replace(" ", "+");
                String url = "http://www.imdbapi.com/?t=" + titlePlus; 
                String json = GetServerResponse.getURLContents(url); 
                Gson gson = new Gson();
                String id = gson.fromJson("imdbID", String.class);
                url = "http://www.imdb.com/"+ id;
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                MovieInfoActivity.this.startActivity(intent);
            }
        });
        /* not functional at moment
         * 
        Button moreButton = (Button) findViewById(R.id.showMoreButton);
        moreButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView description = (TextView) findViewById(R.id.descriptionSmall);
                description.setHeight(LayoutParams.WRAP_CONTENT);
            }
        });
*/
    }

}
