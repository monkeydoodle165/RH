package slidingmenu;

import java.util.ArrayList;
import java.util.List;
import slidingmenu.DatabaseHandler;
import slidingmenu.adaptor.NavDrawerListAdapter;
import slidingmenu.model.Info;
import slidingmenu.model.NavDrawerItem;
import com.example.rh.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
 
@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment {
	
	private static DatabaseHandler db;
	private static List<Info> currentList;
	protected EditText input;
	protected ImageButton searchButton;
	private static Activity activity;
	
	View rootView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(R.layout.search, container, false);
    	input = (EditText) rootView.findViewById(R.id.searchText);
    	searchButton = (ImageButton) rootView.findViewById(R.id.searchButton);
    	searchButton.setOnClickListener(new View.OnClickListener() 
		{
		    public void onClick(View v) 
		    {
		    	String inputString = input.getText().toString();
		    	currentList = db.getItemByNameSearch(inputString);
    		
		    	displayList(currentList);
		    	for (Info in : currentList)
		    	{
		    		String log = "Id: "+in.getTitle();
		    		Log.d("Info: ", log);   
    			}
		    }
		 });
        return rootView;
    }
    
    private void displayList(List<Info> input)
    {
    	ListView mDrawerList = (ListView) rootView.findViewById(R.id.list);
    	ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();

        for (Info info : currentList) {

        	navDrawerItems.add(new NavDrawerItem(info.getTitle()));
        }
        // setting the Navigation drawer list adapter
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(activity.getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(clicking());
    }
    
    private OnItemClickListener clicking() {
		return new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int subPosition, long id) {
				int itemId = currentList.get((int) id).getID();
                displayFragment(itemId);
			}
        };
	}
    
    private void displayFragment(int itemId) {
        Log.i("Leaf", "Requested view of " + itemId);

        Fragment fragment = new DatabaseDetailFragment(itemId,this.activity);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.search, fragment).addToBackStack(null).commit();
    }
 
	public SearchFragment(){
		
	}
    
    public SearchFragment (Activity act) {
		this.db = new DatabaseHandler(act);
		this.activity = act;
	}

    
}