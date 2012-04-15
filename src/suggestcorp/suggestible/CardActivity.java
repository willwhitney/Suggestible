package suggestcorp.suggestible;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class CardActivity extends Activity {

	LocationManager locManager;
	Location location;
	TextView txt;
	LinearLayout linear;
	GestureDetector gdetect;
	int screen = 0;
	uk.co.jasonfry.android.tools.ui.SwipeView swiper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_layout);

        txt = (TextView) findViewById(R.id.locText);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        txt.setTypeface(font);

        swiper = (uk.co.jasonfry.android.tools.ui.SwipeView) findViewById(R.id.flipper);
        

        linear = (LinearLayout) findViewById(R.id.card);


        Log.d("Suggestible", "I'm running!");

        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        txt.setText("Sushi sounds great right now.");


    }


}