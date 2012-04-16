package suggestcorp.suggestible;

import java.lang.reflect.Field;

import uk.co.jasonfry.android.tools.ui.SwipeView.OnPageChangedListener;
import android.app.Activity;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CardActivity extends Activity {

	LocationManager locManager;
	Location location;
	TextView txt;
	RelativeLayout card1;
	uk.co.jasonfry.android.tools.ui.SwipeView swiper;
	OnDemandCardmaker cardmaker;
	final int MAX_PAGES = 4;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_layout);
        Log.d("Suggestible", "I'm running!");
        
        cardmaker = new OnDemandCardmaker();
        swiper = (uk.co.jasonfry.android.tools.ui.SwipeView) findViewById(R.id.flipper);
        
        
//        int textToSet = getResources().getIdentifier("card" + 0, "string", "suggestcorp.suggestible");
//		getTextViewByPageNum(0).setText(textToSet);
//		
//		int imageToSet = getResources().getIdentifier("card" + 0, "drawable", "suggestcorp.suggestible");
//		getImageViewByPageNum(0).setImageResource(imageToSet);

        
//        card1 = (RelativeLayout) findViewById(R.id.card1);
//        RelativeLayout card2 = (RelativeLayout) findViewById(R.id.card2);
//        Toast.makeText(this, "index 1: " + ((ViewGroup) ((ViewGroup) ((ViewGroup) swiper.getChildAt(0)).getChildAt(0)).getChildAt(0)).getChildAt(0).getClass(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "card2 index: " + swiper.indexOfChild(card2), Toast.LENGTH_SHORT).show();
        
        
        fixFonts();
		
		swiper.setOnPageChangedListener(cardmaker);
        
		cardmaker.onPageChanged(-1, 0);

    }
    
    private class OnDemandCardmaker implements OnPageChangedListener {

		public void onPageChanged(int oldpage, int newpage) {
			
			if (newpage > oldpage) {
								
				int textToSet = getResources().getIdentifier("card" + newpage, "string", "suggestcorp.suggestible");
				getTextViewByPageNum(newpage).setText(textToSet);
				
				int imageToSet = getResources().getIdentifier("card" + newpage, "drawable", "suggestcorp.suggestible");
				getImageViewByPageNum(newpage).setImageResource(imageToSet);

				if (swiper.getPageCount() < MAX_PAGES) {
					View newCard = new View(CardActivity.this);
					newCard.inflate(CardActivity.this, R.layout.card, (ViewGroup) swiper);
				}
				

				fixFonts();
				
				

				
				
				
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
    
    
    
    public final void fixFonts() {
    	
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
		final ViewGroup mContainer = (ViewGroup) findViewById(android.R.id.content).getRootView();
		CardActivity.setAppFont(mContainer, font);

    }
    
    
    
    public TextView getTextViewByPageNum(int page) {
    	
    	RelativeLayout card = (RelativeLayout) ((ViewGroup) swiper.getChildAt(0)).getChildAt(page);
    	
    	return getTextViewChild(card);
    }
    
    public ImageView getImageViewByPageNum(int page) {
    	
    	RelativeLayout card = (RelativeLayout) ((ViewGroup) swiper.getChildAt(0)).getChildAt(page);
    	
    	return getImageViewChild(card);
    }
    
    public RelativeLayout getCardByPageNum(int page) {
    	return (RelativeLayout) ((ViewGroup) swiper.getChildAt(0)).getChildAt(page);
    }
    
    public TextView getTextViewChild(ViewGroup group) {
    	int childCount = group.getChildCount();
    	
    	for (int i = 0; i < childCount; i++) {
    		View child = group.getChildAt(i);
    		if (child instanceof TextView) {
    			return (TextView) child;
    		} else if (child instanceof ViewGroup) {
    			return getTextViewChild((ViewGroup) child);
    		}
    	}
    	
    	return null;
    }
    
    public ImageView getImageViewChild(ViewGroup group) {
    	int childCount = group.getChildCount();
    	
    	for (int i = 0; i < childCount; i++) {
    		View child = group.getChildAt(i);
    		if (child instanceof ImageView) {
    			return (ImageView) child;
    		} else if (child instanceof ViewGroup) {
    			return getImageViewChild((ViewGroup) child);
    		}
    	}
    	
    	return null;
    }
    
    public Button[] getCardButtonsByPageNum(int page) {
    	Button[] buttons = new Button[2];
    	
    	buttons[0] = (Button) getCardByPageNum(page).findViewWithTag("first");
    	buttons[0] = (Button) getCardByPageNum(page).findViewWithTag("second");
    	
    	return buttons;
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}