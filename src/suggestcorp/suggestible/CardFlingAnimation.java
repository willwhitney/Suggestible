package suggestcorp.suggestible;

import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

public class CardFlingAnimation extends Animation implements Animation.AnimationListener {

	private View view;
	private int currentPosition;
	private int direction;
	
	// The steps to skip between colors
	private static int STEP_SIZE= 30;
	private static int ANIMATION_DURATION = 50;
	
	public CardFlingAnimation(View view) {
		this.view = view;
		setDuration(ANIMATION_DURATION);
		setInterpolator(new AccelerateInterpolator());
	
		setAnimationListener(this);
	}
	
	public CardFlingAnimation(View view, int direction) {
		this.view = view;
		setDuration(ANIMATION_DURATION);
		setInterpolator(new AccelerateInterpolator());
	
		setAnimationListener(this);
	}
	
	public void onAnimationEnd(Animation animation) {
	
	}
	
	public void onAnimationRepeat(Animation animation) {
		
	}
	
	public void onAnimationStart(Animation animation) {

	}

}
