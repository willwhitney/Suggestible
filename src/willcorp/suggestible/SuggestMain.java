package willcorp.suggestible;

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
import android.widget.LinearLayout;
import android.widget.TextView;

public class SuggestMain extends Activity implements OnGestureListener {

	LocationManager locManager;
	Location location;
	TextView txt;
	LinearLayout linear;
	GestureDetector gdetect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        txt = (TextView) findViewById(R.id.locText);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        txt.setTypeface(font);

        linear = (LinearLayout) findViewById(R.id.linear);


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

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float xvel,
			float yvel) {
		if(Math.abs(xvel) > Math.abs(yvel)) {
			if(xvel > 0) {
				txt.setText("Previous!");
			} else {
				txt.setText("Next!");
			}
		}

		return true;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}



}