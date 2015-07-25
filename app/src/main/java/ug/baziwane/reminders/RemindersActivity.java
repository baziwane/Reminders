package ug.baziwane.reminders;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ArrayAdapter;


public class RemindersActivity extends ActionBarActivity {

    private ListView mListView;
    private RemindersDbAdapter mDbAdapter;
    private RemindersSimpleCursorAdapter mCursorAdapter;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        mListView = (ListView) findViewById(R.id.reminders_list_view);
//The arrayAdatper is the controller in our model-view-controller relationship. (controller)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//context
                this,
//layout (view)
                R.layout.reminders_row,
//row (view)
                R.id.row_text,
//data (model) with bogus data to test our listview
                new String[]{"first record", "second record", "third record"});
        mListView.setAdapter(arrayAdapter);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        mListView = (ListView) findViewById(R.id.reminders_list_view);
        mListView.setDivider(null);
        mDbAdapter = new RemindersDbAdapter(this);
        mDbAdapter.open();
        Cursor cursor = mDbAdapter.fetchAllReminders();
//from columns defined in the db
        String[] from = new String[]{ RemindersDbAdapter.COL_CONTENT   };
//to the ids of views in the layout
        int[] to = new int[]{ R.id.row_text };
        mCursorAdapter = new RemindersSimpleCursorAdapter(
//context
                RemindersActivity.this,
//the layout of the row
                R.layout.reminders_row,
//cursor
                cursor,
//from columns defined in the db
                from,
//to the ids of views in the layout
                to,
//flag - not used
                0);
// the cursorAdapter (controller) is now updating the listView (view)
//with data from the db (model)
        mListView.setAdapter(mCursorAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_new:
                //create new Reminder
                Log.d(getLocalClassName(), "Add Reminder");
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return false;
        }
    }
}
