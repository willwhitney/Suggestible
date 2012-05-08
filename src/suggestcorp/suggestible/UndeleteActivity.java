package suggestcorp.suggestible;

import java.util.ArrayList;
import java.util.Arrays;

import suggestcorp.suggestible.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class UndeleteActivity extends Activity {
    private ListView mainListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> suggestions;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.undeletelist);

        // Find the ListView resource.
        mainListView = (ListView) findViewById(R.id.UndeleteListView);

        //THIS IS JUST SAMPLE DATA, WE WOULD NEED THIS TO BE AN ARRAY OF SUGGESTIONS
        String[] genres = new String[] {
            "Action", "Adventure", "Animation", "Children", "Comedy", "Documentary", "Drama",
            "Foreign", "History", "Independent Films From 1990 Onwards", "Romance", "Sci-Fi", "Television", "Thriller"
        };
        
        suggestions = new ArrayList<String>();
        suggestions.addAll( Arrays.asList(genres) );

        
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, suggestions);
        mainListView.setAdapter(adapter);

        mainListView.setItemsCanFocus(false);
        mainListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final Button button = (Button) findViewById(R.id.UndeleteButton);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SparseBooleanArray checked = mainListView.getCheckedItemPositions();
                ArrayList<String> checkedItems = new ArrayList<String>();
                for (int i = 0; i < checked.size(); i++) {
                    if (checked.valueAt(i) == true) {
                        checkedItems.add((String)mainListView.getItemAtPosition(checked.keyAt(i)));
                        mainListView.setItemChecked(checked.keyAt(i), false);
                    }
                }
                suggestions.removeAll(checkedItems);
                adapter.notifyDataSetChanged();
            }
        });
    }
    /*
     * This menu should be put in the card activity. Has an about option and a manage deleted suggestions option
     * 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                Intent aboutIntent = new Intent(UndeleteActivity.this, About.class);
                UndeleteActivity.this.startActivity(aboutIntent);
                return true;
            case R.id.deleted:
                Intent detailsIntent = new Intent(UndeleteActivity.this, UndeleteActivity.class);
                UndeleteActivity.this.startActivity(detailsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
}
