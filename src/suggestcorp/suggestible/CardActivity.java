package suggestcorp.suggestible;

import java.util.List;

import suggestcorp.suggestible.GetServerResponse.Suggestion;
import uk.co.jasonfry.android.tools.ui.SwipeView.OnPageChangedListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CardActivity extends Activity {

	LocationManager locManager;
	Location location;
	TextView txt;
	RelativeLayout card1;
	uk.co.jasonfry.android.tools.ui.SwipeView swiper;
	OnDemandCardmaker cardmaker;
	final int MAX_PAGES = 8;
	int remainingPages = MAX_PAGES;
	ImageButton[] filterButtons;
	LinearLayout filters;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_layout);
        Log.d("Suggestible", "I'm running!");
        
        
        List<Suggestion> suggestions = GetServerResponse.getMovies();
        Toast.makeText(this, "first suggestion name: " + suggestions.get(0).title, Toast.LENGTH_SHORT).show();
        
        cardmaker = new OnDemandCardmaker();
        swiper = (uk.co.jasonfry.android.tools.ui.SwipeView) findViewById(R.id.flipper);
        filterButtons = new ImageButton[4];
        filters = (LinearLayout) findViewById(R.id.filters);
                        
        for (int i = 0; i < 4; i++) {
        	filterButtons[i] = (ImageButton) filters.getChildAt(i);
        	filterButtons[i].setTag("on");
        	filterButtons[i].setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if (v.getTag().equals("on")) {
						v.setTag("off");
					} else {
						v.setTag("on");
					}
					
					updateFilters();
					
				}
        		
        	});
        }
        
        updateFilters();
        
        
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
    
    public class OnDemandCardmaker implements OnPageChangedListener {

		public void onPageChanged(final int oldpage, final int newpage) {
			
			if (newpage > oldpage) {
								
				if (newpage == 0) {
					int textToSet = getResources().getIdentifier("card" + newpage, "string", "suggestcorp.suggestible");
					getTextViewByPageNum(newpage).setText(textToSet);
					
					int imageToSet = getResources().getIdentifier("card" + newpage, "drawable", "suggestcorp.suggestible");
					getImageViewByPageNum(newpage).setImageResource(imageToSet);
					
					
					
					if (newpage  + (MAX_PAGES - remainingPages) == 0) {
						getCardButtonsByPageNum(newpage)[0].setOnClickListener(new OnClickListener() {
							
							public void onClick(View v) {
								Intent detailsIntent = new Intent(CardActivity.this, PlaceInfoActivity.class);
								CardActivity.this.startActivity(detailsIntent);
							}
							
						});
					} else if (newpage  + (MAX_PAGES - remainingPages) == 1) {
						getCardButtonsByPageNum(newpage)[0].setOnClickListener(new OnClickListener() {
							
							public void onClick(View v) {
								Intent detailsIntent = new Intent(CardActivity.this, BookInfoActivity.class);
								CardActivity.this.startActivity(detailsIntent);
							}
							
						});
					} else if (newpage  + (MAX_PAGES - remainingPages) == 2) {
						getCardButtonsByPageNum(newpage)[0].setOnClickListener(new OnClickListener() {
							
							public void onClick(View v) {
								Intent detailsIntent = new Intent(CardActivity.this, MovieInfoActivity.class);
								CardActivity.this.startActivity(detailsIntent);
							}
							
						});
					} else if (newpage  + (MAX_PAGES - remainingPages) == 3) {
						getCardButtonsByPageNum(newpage)[0].setOnClickListener(new OnClickListener() {
							
							public void onClick(View v) {
								Intent detailsIntent = new Intent(CardActivity.this, RestaurantInfoActivity.class);
								CardActivity.this.startActivity(detailsIntent);
							}
							
						});
					} else {
						getCardButtonsByPageNum(newpage)[0].setOnClickListener(new OnClickListener() {
							
							public void onClick(View v) {

								Toast.makeText(CardActivity.this, "not implemented", Toast.LENGTH_SHORT).show();
								
							}
						});
					}
					
					
					
					
					getCardButtonsByPageNum(newpage)[1].setOnClickListener(new OnClickListener() {
	
						public void onClick(View v) {
							remainingPages--;
							((ViewGroup) swiper.getChildAt(0)).removeView((View) v.getParent().getParent().getParent());
							addCard();
							
						}
						
					});
				
				}
				
				addCard();

//				if (swiper.getPageCount() < MAX_PAGES) {
//					View newCard = new View(CardActivity.this);
//					newCard.inflate(CardActivity.this, R.layout.card, (ViewGroup) swiper);
//					
//					int textToSet2 = getResources().getIdentifier("card" + (swiper.getPageCount() - 1), "string", "suggestcorp.suggestible");
//					getTextViewByPageNum(swiper.getPageCount() - 1).setText(textToSet2);
//					
//					int imageToSet2 = getResources().getIdentifier("card" + (swiper.getPageCount() - 1), "drawable", "suggestcorp.suggestible");
//					getImageViewByPageNum(swiper.getPageCount() - 1).setImageResource(imageToSet2);
//
//				}
				
				
				

				fixFonts();
				
				

				
				
				
				// THIS IS HOW YOU ASSIGN STRINGS AS IDs
				// http://stackoverflow.com/questions/3937010/array-of-imagebuttons-assign-r-view-id-from-a-variable
//				int resID = getResources().getIdentifier(btnID, "drawable", "com.your.package");
//				(ImageButton) findViewById(resID);

				
			}
			
		}
		
		public void addCard() {
			if (swiper.getPageCount() < remainingPages) {
				View newCard = new View(CardActivity.this);
				newCard.inflate(CardActivity.this, R.layout.card, (ViewGroup) swiper);
				
				
				int textToSet = getResources().getIdentifier("card" + (swiper.getPageCount() - 1 + (MAX_PAGES - remainingPages)), "string", "suggestcorp.suggestible");
				getTextViewByPageNum(swiper.getPageCount() - 1).setText(textToSet);
				
				int imageToSet = getResources().getIdentifier("card" + (swiper.getPageCount() - 1 + (MAX_PAGES - remainingPages)), "drawable", "suggestcorp.suggestible");
				getImageViewByPageNum(swiper.getPageCount() - 1).setImageResource(imageToSet);
				
				if (swiper.getPageCount() - 1  + (MAX_PAGES - remainingPages) % 4 == 0) {
					getCardButtonsByPageNum(swiper.getPageCount() - 1)[0].setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							Intent detailsIntent = new Intent(CardActivity.this, PlaceInfoActivity.class);
							CardActivity.this.startActivity(detailsIntent);
						}
						
					});
				} else if (swiper.getPageCount() - 1  + (MAX_PAGES - remainingPages) % 4 == 1) {
					getCardButtonsByPageNum(swiper.getPageCount() - 1)[0].setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							Intent detailsIntent = new Intent(CardActivity.this, BookInfoActivity.class);
							/*
							 * Pass JSON variables to the new info display activity (id not included for books)
							detailsIntent.putExtra("title", title);
							detailsIntent.putExtra("imagesrc", imagesrc);
							detailsIntent.putExtra("rating", rating);
							detailsIntent.putExtra("description", description);
							detailsIntent.putExtra("maplocation", maplocation);
							*/
							CardActivity.this.startActivity(detailsIntent);
						}
						
					});
				} else if (swiper.getPageCount() - 1  + (MAX_PAGES - remainingPages) % 4 == 2) {
					getCardButtonsByPageNum(swiper.getPageCount() - 1)[0].setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							Intent detailsIntent = new Intent(CardActivity.this, MovieInfoActivity.class);
							/*
							 * Pass JSON variables to the new info display activity
							detailsIntent.putExtra("title", title);
                            detailsIntent.putExtra("imagesrc", imagesrc);
                            detailsIntent.putExtra("rating", rating);
                            detailsIntent.putExtra("description", description);
                            detailsIntent.putExtra("maplocation", maplocation);
                            detailsIntent.putExtra("id", id);
                            */
							CardActivity.this.startActivity(detailsIntent);
						}
						
					});
				} else if (swiper.getPageCount() - 1  + (MAX_PAGES - remainingPages) % 4 == 3) {
					getCardButtonsByPageNum(swiper.getPageCount() - 1)[0].setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							Intent detailsIntent = new Intent(CardActivity.this, RestaurantInfoActivity.class);
							CardActivity.this.startActivity(detailsIntent);
						}
						
					});
				} else {
					getCardButtonsByPageNum(swiper.getPageCount() - 1)[0].setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {

							Toast.makeText(CardActivity.this, "not implemented", Toast.LENGTH_SHORT).show();
							
						}
					});
				}
				
				
				getCardButtonsByPageNum(swiper.getPageCount() - 1)[1].setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						remainingPages--;
						((ViewGroup) swiper.getChildAt(0)).removeView((View) v.getParent().getParent().getParent());
						addCard();
						
					}
					
				});

			}
		}
    	
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
        	Intent detailsIntent = new Intent(CardActivity.this, UndeleteActivity.class);
			CardActivity.this.startActivity(detailsIntent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    public void updateFilters() {
    	for (int i = 0; i < 4; i++) {
    		if (filterButtons[i].getTag().equals("on")) {
    			filterButtons[i].setBackgroundColor(Color.parseColor("#F3F377"));
//    			switch(i) {
//    				case 0:
//    					filterButtons[i].setImageResource(R.drawable.placesglow);
//    					break;
//    				case 1:
//    					filterButtons[i].setImageResource(R.drawable.bookglow);
//    					break;
//    				case 2:
//    					filterButtons[i].setImageResource(R.drawable.movieglow);
//    					break;
//    				case 3:
//    					filterButtons[i].setImageResource(R.drawable.foodglow);
//    					break;
//    			}
    		} else {
    			filterButtons[i].setBackgroundColor(Color.parseColor("#AAAAAA"));
//    			switch(i) {
//					case 0:
//						filterButtons[i].setImageResource(R.drawable.places);
//						break;
//					case 1:
//						filterButtons[i].setImageResource(R.drawable.book);
//						break;
//					case 2:
//						filterButtons[i].setImageResource(R.drawable.movie);
//						break;
//					case 3:
//						filterButtons[i].setImageResource(R.drawable.food);
//						break;
//    			}
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
    	buttons[1] = (Button) getCardByPageNum(page).findViewWithTag("second");
    	
    	return buttons;
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}