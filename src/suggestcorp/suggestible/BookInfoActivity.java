package suggestcorp.suggestible;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class BookInfoActivity extends Activity {
    String author = "Herman Melville";
    String title = "Moby Dick";
    
    public void showMore(View view){        
        ((Button)(findViewById(R.id.bookshowMoreButton))).setVisibility(8);//make it gone
        ((TextView)(findViewById(R.id.bookdescriptionSmall))).setVisibility(8);//make it gone
        ((TextView)(findViewById(R.id.bookdescriptionLarge))).setVisibility(2);//make it visible
        
    }
    
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booklayout);
		
		Button libraryButton = (Button) findViewById(R.id.librarybutton);
        libraryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.yelp.com/biz/cambridge-public-library-cambridge-2";
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                BookInfoActivity.this.startActivity(intent);
            }
        });
        
        Button bookstoreButton = (Button) findViewById(R.id.bookstorebutton);
        bookstoreButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.yelp.com/biz/mit-coop-cambridge";
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                BookInfoActivity.this.startActivity(intent);
            }
        });
        
        Button kindleButton = (Button) findViewById(R.id.kindlebutton);
        kindleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = title.replace(" ", "+") + "+" + author.replace(" ", "+");
                String url = "http://www.amazon.com/s/ref=nb_sb_noss?rh=n%3A133140011%2Ck%3A"+keyword+"&keywords="+keyword+"&ie=UTF8";
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                BookInfoActivity.this.startActivity(intent);
            }
        });
		
	}
	
}
