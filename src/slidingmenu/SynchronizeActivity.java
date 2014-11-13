package slidingmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import slidingmenu.services.LoadContentTask;


public class SynchronizeActivity extends Activity {

    /**
     * Database instance
     */
    private DatabaseHandler db = new DatabaseHandler(this);

    /**
     * Initialize the database, make sure it is synchronised
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeDatabase(new Runnable() {
            @Override
            public void run() {
                Log.i("SynchronizeActivity", "Done synchronizing the database!");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Initialise the database
     */
    protected void initializeDatabase(Runnable onComplete) {
        //TODO the application should always query the webservice
    	//store the updated date using SharedPreferences
    	//so query the database every time here
    	//need to make it just run load content task everytime here
    	if(db.checkDataBase()) {
            if (onComplete != null) {
                onComplete.run();
            }
        }
        else {
            LoadContentTask task = new LoadContentTask(this);
            task.setOnComplete(onComplete);
            task.execute();
        }
    }
}
