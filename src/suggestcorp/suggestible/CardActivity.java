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

public class CardActivity extends Activity implements OnGestureListener {

	LocationManager locManager;
	Location location;
	TextView txt;
	LinearLayout linear;
	GestureDetector gdetect;
	int screen = 0;
	ViewFlipper flipper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        txt = (TextView) findViewById(R.id.locText);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        txt.setTypeface(font);

        flipper = (ViewFlipper) findViewById(R.id.flipper);
        

        linear = (LinearLayout) findViewById(R.id.card);


        Log.d("Suggestible", "I'm running!");

        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        txt.setText("Sushi sounds great right now.");

        gdetect = new GestureDetector(this, this);



//        txt.setText( String.format("latitude: %f, longitude: %f", location.getLatitude(), location.getLongitude()));



    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
    	this.gdetect.onTouchEvent(event);
    	return super.onTouchEvent(event);
    }

	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float xvel,
			float yvel) {
				
		if(Math.abs(xvel) > Math.abs(yvel)) {
			
			RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) linear.getLayoutParams();


			if(xvel > 0) {
				
				// This isn't correct, but want to set the animations based on which direction we're going
				flipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
				
				if(screen > -1) {
					if (screen == 0)
						txt.setText("Previous!");
					if (screen == 1)
						txt.setText("Sushi sounds great right now.");
					screen--;
				}
			} else {
				if(screen < 1) {
					if (screen == 0)
						txt.setText("Next!");
					if (screen == -1)
						txt.setText("Sushi sounds great right now.");
					screen++;
				}
			}
			
			
			params.leftMargin = 15;
			params.rightMargin = 15;
			linear.setLayoutParams(params);
			return true;
		}

		return false;
	}

	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float x,
			float y) {
		// need to add handling for when it's released
		// if it's more than halfway, animate to the next one, otherwise animate back
		// may not be able to do that in onScroll
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) linear.getLayoutParams();
		params.leftMargin -= (int) (x);
		params.rightMargin += (int) (x);
		linear.setLayoutParams(params);
		return true;
	}

	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}



}