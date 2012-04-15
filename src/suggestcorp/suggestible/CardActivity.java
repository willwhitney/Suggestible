package suggestcorp.suggestible;

import uk.co.jasonfry.android.tools.ui.SwipeView.OnPageChangedListener;
import android.app.Activity;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CardActivity extends Activity {

	LocationManager locManager;
	Location location;
	TextView txt;
	RelativeLayout card1;
	uk.co.jasonfry.android.tools.ui.SwipeView swiper;
	OnDemandCardmaker cardmaker;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_layout);
        Log.d("Suggestible", "I'm running!");
        
        cardmaker = new OnDemandCardmaker();
        txt = (TextView) findViewById(R.id.txt);
        swiper = (uk.co.jasonfry.android.tools.ui.SwipeView) findViewById(R.id.flipper);
        card1 = (RelativeLayout) findViewById(R.id.card1);
        
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
//        txt.setTypeface(font);
		final ViewGroup mContainer = (ViewGroup) findViewById(android.R.id.content).getRootView();
		CardActivity.setAppFont(mContainer, font);
		swiper.setOnPageChangedListener(cardmaker);
        

    }
    
    private class OnDemandCardmaker implements OnPageChangedListener {

		public void onPageChanged(int oldpage, int newpage) {

			txt = (TextView) findViewById(R.id.txt);
			txt.setText("fuck off");
			if (newpage > oldpage) {
//				Log.d("Suggestible", card1.getLayoutParams().toString());
//				LayoutParams params = (android.widget.RelativeLayout.LayoutParams) card1.getLayoutParams();
				View newCard = new View(CardActivity.this);
				newCard.inflate(CardActivity.this, R.layout.card, (ViewGroup) swiper);

				final ViewGroup mContainer = (ViewGroup) findViewById(android.R.id.content).getRootView();
				final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
				CardActivity.setAppFont(mContainer, font);
				
				// THIS IS HOW YOU ASSIGN STRINGS AS IDs
				// http://stackoverflow.com/questions/3937010/array-of-imagebuttons-assign-r-view-id-from-a-variable
//				int resID = getResources().getIdentifier(btnID, "drawable", "com.your.package");
//				(ImageButton) findViewById(resID);

				
			}
			
		}
    	
    }
    
    
    
    
    
    /**
     * Recursively sets a typeface to all TextViews in a ViewGroup.
     */
    public static final void setAppFont(ViewGroup mContainer, Typeface mFont)
    {
        if (mContainer == null || mFont == null) return;

        final int mCount = mContainer.getChildCount();

        // Loop through all of the children.
        for (int i = 0; i < mCount; ++i)
        {
            final View mChild = mContainer.getChildAt(i);
            if (mChild instanceof TextView)
            {
                // Set the font if it is a TextView.
                ((TextView) mChild).setTypeface(mFont);
            }
            else if (mChild instanceof ViewGroup)
            {
                // Recursively attempt another ViewGroup.
                setAppFont((ViewGroup) mChild, mFont);
            }
        }
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}