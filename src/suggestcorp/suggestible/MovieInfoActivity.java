package suggestcorp.suggestible;


import org.w3c.dom.Document;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MovieInfoActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movielayout);
		fillStars(65);
		
	}
	
	public void showMore(View view){		
    	((Button)(findViewById(R.id.showMoreButton))).setVisibility(8);//make it gone
    	((TextView)(findViewById(R.id.descriptionSmall))).setVisibility(8);//make it gone
    	((TextView)(findViewById(R.id.descriptionLarge))).setVisibility(2);//make it visible
    	
    }
	
	public void fillStars(int rating){
		double stars = rating/20.00;
		if (stars >= 1){
			((View)(findViewById(R.id.star1))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=.5){
			((View)(findViewById(R.id.star1))).setBackgroundResource(R.drawable.half_star);
		}

		if (stars >= 2){
			((View)(findViewById(R.id.star2))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=1.5){
			((View)(findViewById(R.id.star2))).setBackgroundResource(R.drawable.half_star);
		}
		if (stars >= 3){
			((View)(findViewById(R.id.star3))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=2.5){
			((View)(findViewById(R.id.star3))).setBackgroundResource(R.drawable.half_star);
		}
		if (stars >= 4){
			((View)(findViewById(R.id.star4))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=3.5){
			((View)(findViewById(R.id.star4))).setBackgroundResource(R.drawable.full_star);
		}
		if (stars >= 5){
			((View)(findViewById(R.id.star4))).setBackgroundResource(R.drawable.full_star);
		}
		else if(stars >=4.5){
			((View)(findViewById(R.id.star4))).setBackgroundResource(R.drawable.full_star);
		}
		
	}
}
