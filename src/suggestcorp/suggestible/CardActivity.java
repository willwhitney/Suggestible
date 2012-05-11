package suggestcorp.suggestible;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import suggestcorp.suggestible.GetServerResponse.Suggestion;
import uk.co.jasonfry.android.tools.ui.SwipeView.OnPageChangedListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import android.widget.LinearLayout.LayoutParams;
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
	final int MAX_PAGES = 100;
	int remainingPages = MAX_PAGES;
	ImageButton[] filterButtons;
	LinearLayout filters;
	List<Suggestion> movies;
	List<Suggestion> books;
	List<Suggestion> restaurants;
	List<Suggestion> outings;
	List<Drawable> imagesToLoad;
	List<Integer> cardsNeedingImages;
	int fetchedCount = 0;
	
	public enum CardType {
		movie, book, restaurant, outing
	}
		
	
	private class MovieFetcher extends AsyncTask<Void, Void, List<Suggestion>> {
		@Override
		protected List<Suggestion> doInBackground(Void... params) {
			return GetServerResponse.getMovies();
		}
		@Override
		protected void onPostExecute(List<Suggestion> suggestions) {
			movies = suggestions;
			fetchedCount++;
			if (fetchedCount >3) {
				cardmaker.onPageChanged(-1, 0);
			}
		}
	}
	
	private class BookFetcher extends AsyncTask<Void, Void, List<Suggestion>> {
		@Override
		protected List<Suggestion> doInBackground(Void... params) {
			return GetServerResponse.getBooks();
		}
		@Override
		protected void onPostExecute(List<Suggestion> suggestions) {
			books = suggestions;
			fetchedCount++;
			if (fetchedCount >3) {
				cardmaker.onPageChanged(-1, 0);
			}
			//Log.d("Suggestible", "first cardType: " + cardType.values()[1]);
		}
	}
	
	private class RestaurantFetcher extends AsyncTask<Double, Void, List<Suggestion>> {
		@Override
		protected List<Suggestion> doInBackground(Double... location) {
			return GetServerResponse.getRestaurants(location[0], location[1]);
		}
		@Override
		protected void onPostExecute(List<Suggestion> suggestions) {
			restaurants = suggestions;
			fetchedCount++;
			if (fetchedCount >3) {
				cardmaker.onPageChanged(-1, 0);
			}
		}
	}
	
	private class OutingFetcher extends AsyncTask<Double, Void, List<Suggestion>> {
		@Override
		protected List<Suggestion> doInBackground(Double... location) {
			return GetServerResponse.getOutings(location[0], location[1]);
		}
		@Override
		protected void onPostExecute(List<Suggestion> suggestions) {
			outings = suggestions;
			fetchedCount++;
			if (fetchedCount >3) {
				cardmaker.onPageChanged(-1, 0);
			}
			//Log.d("Suggestible", outings.get(0).title);
		}
	}
	
	private class DrawableFetcher extends AsyncTask<String, Void, Drawable> {
		
		int cardnum;
		@Override
		protected Drawable doInBackground(String... params) {
			
			cardnum = Integer.parseInt(params[1]);
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
			imagesToLoad.add(imgDrawable);
			getImageViewByPageNum(cardnum).setImageDrawable(imgDrawable);
		}
	}
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_layout);
        Log.d("Suggestible", "I'm running!");
        
        imagesToLoad = new ArrayList<Drawable>();
        cardsNeedingImages = new ArrayList<Integer>();

        
        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        
        new MovieFetcher().execute();
        new BookFetcher().execute();
        new RestaurantFetcher().execute(location.getLatitude(), location.getLongitude());
        new OutingFetcher().execute(location.getLatitude(), location.getLongitude());
        
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
        
        fixFonts();
		
		swiper.setOnPageChangedListener(cardmaker);

    }
    
    public class OnDemandCardmaker implements OnPageChangedListener {

		public void onPageChanged(final int oldpage, final int newpage) {
			
			if (newpage > oldpage) {
								
				if (newpage == 0) {
					Suggestion movie = movies.remove(0);
					
					getCardByPageNum(newpage).setTag("movie");
					String textToSet = movie.title;      //getResources().getIdentifier("card" + newpage, "string", "suggestcorp.suggestible");
					getTextViewByPageNum(newpage).setText(textToSet);

					
					Log.d("Suggestible", "Trying to load image " + movie.imageurl);
					
					cardsNeedingImages.add(newpage);
					new DrawableFetcher().execute(movie.imageurl, Integer.toString(newpage));
					getImageViewByPageNum(newpage).setScaleType(ImageView.ScaleType.FIT_XY);
					getImageViewByPageNum(newpage).setLayoutParams(new LayoutParams(250, 400));
					//Drawable imageToSet = LoadImageFromWeb(movies.get(newpage).imageurl);           //getResources().getIdentifier("card" + newpage, "drawable", "suggestcorp.suggestible");
					//getImageViewByPageNum(newpage).setImageDrawable(imageToSet);
					
					
					
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
					addCard();
				}
				
				addCard();
				fixFonts();
				
				// THIS IS HOW YOU ASSIGN STRINGS AS IDs
				// http://stackoverflow.com/questions/3937010/array-of-imagebuttons-assign-r-view-id-from-a-variable
//				int resID = getResources().getIdentifier(btnID, "drawable", "com.your.package");
//				(ImageButton) findViewById(resID);

				
			}
			
		}
		
		private int pickCardType() {
			Random rand = new Random();
			int i = rand.nextInt(4);
			while(filterButtons[i].getTag() != "on") {
				i = rand.nextInt(4);
			}
			return i;
		}
		
		public void addCard() {
			if (swiper.getPageCount() < remainingPages) {
				
				int cardNum = swiper.getPageCount();
				View newCard = new View(CardActivity.this);
				newCard.inflate(CardActivity.this, R.layout.card, (ViewGroup) swiper);
				
				Suggestion card = movies.get(0);
				String tag = "";
				int currentCardType = pickCardType();
				switch(currentCardType) {
				case 0:
					card = outings.remove(0);
					tag = "outing";
					break;
				case 1:
					card = books.remove(0);
					tag = "book";
					getImageViewByPageNum(cardNum).setScaleType(ImageView.ScaleType.FIT_XY);
					break;
				case 2:
					card = movies.remove(0);
					tag = "movie";
					getImageViewByPageNum(cardNum).setScaleType(ImageView.ScaleType.FIT_XY);
					getImageViewByPageNum(cardNum).setLayoutParams(new LayoutParams(250, 400));
					break;
				case 3:
					card = restaurants.remove(0);
					tag = "restaurant";
					break;
				}
				
				getCardByPageNum(cardNum).setTag(tag);
				String textToSet = card.title;      //getResources().getIdentifier("card" + newpage, "string", "suggestcorp.suggestible");
				getTextViewByPageNum(cardNum).setText(textToSet);

				
				Log.d("Suggestible", "Trying to load image " + card.imageurl);
				
				cardsNeedingImages.add(cardNum);
				new DrawableFetcher().execute(card.imageurl, Integer.toString(cardNum));
				
				
//				String textToSet = movies.get(cardNum).title;
//				getTextViewByPageNum(cardNum).setText(textToSet);
//				
//				
//				Log.d("Suggestible", "Trying to load image " + movies.get(cardNum).imageurl);
//				
//				cardsNeedingImages.add(cardNum);
//				new DrawableFetcher().execute(movies.get(cardNum).imageurl, Integer.toString(cardNum));

				
				
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
			
//			addCard();
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
    
    public int updateFilters() {
    	int active = 0;
    	for (int i = 0; i < 4; i++) {
    		if (filterButtons[i].getTag().equals("on")) {
    			active ++;
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
    	return active;
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