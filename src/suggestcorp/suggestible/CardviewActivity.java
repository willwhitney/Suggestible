package suggestcorp.suggestible;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class CardviewActivity extends Activity{

public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_card_layout);
        ImageView arrowleft = (ImageView) findViewById(R.id.back);
        ImageView arrowright = (ImageView) findViewById(R.id.next);
        arrowleft.setAlpha(100);
        arrowright.setAlpha(100);
    }
    
}