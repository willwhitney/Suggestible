package suggestcorp.suggestible;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MovieInfoActivity extends Activity {
    String title = "Ironman";

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movielayout);

        Button theaterButton = (Button) findViewById(R.id.theaterbutton);
        theaterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Button imdbButton = (Button) findViewById(R.id.imdbbutton);
        imdbButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("imdb", "CLICK");
                String url = "http://www.imdbapi.com/?t=" + title;   
            }
        });

    }

}
