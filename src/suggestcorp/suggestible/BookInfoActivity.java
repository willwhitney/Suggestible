package suggestcorp.suggestible;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class BookInfoActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booklayout);
		
		
	}
	
	public void showMore(View view){
		TextView descriptionView = (TextView) findViewById(R.id.description2);
		//LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		//descriptionView.setLayoutParams(params);
		descriptionView.setHeight(LayoutParams.WRAP_CONTENT);
	}
}
