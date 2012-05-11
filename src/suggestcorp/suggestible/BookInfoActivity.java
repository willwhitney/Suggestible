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
		
	}
	
	public void showMore(View view){
		TextView descriptionView = (TextView) findViewById(R.id.bookdescriptionSmall);
		//LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		//descriptionView.setLayoutParams(params);
		descriptionView.setHeight(LayoutParams.WRAP_CONTENT);
	}
}
