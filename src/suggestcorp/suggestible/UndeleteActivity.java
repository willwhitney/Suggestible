package suggestcorp.suggestible;

import java.util.ArrayList;
import java.util.Arrays;

import willcorp.suggestible.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
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

}
