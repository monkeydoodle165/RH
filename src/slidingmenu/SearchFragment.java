package slidingmenu;




import java.util.ArrayList;
import java.util.List;

import slidingmenu.DatabaseHandler;
//import slidingmenu.MainActivity.SlideMenuClickListener;
import slidingmenu.adaptor.NavDrawerListAdapter;
import slidingmenu.model.Info;
import slidingmenu.model.NavDrawerItem;

import com.example.rh.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.provider.DocumentsContract.Root;
import android.text.Spannable;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
 
@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment {
	
	private static DatabaseHandler db;
	protected EditText input;
	protected ImageButton searchButton;
	private static Activity activity;
	List<Info> infoList = new ArrayList<Info>();
	View rootView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(R.layout.search, container, false);
    	input = (EditText) rootView.findViewById(R.id.searchText);
    	searchButton = (ImageButton) rootView.findViewById(R.id.searchButton);
    	searchButton.setOnClickListener(new View.OnClickListener() 
		{
    		
		    public void onClick(View v) {
		   Log.i("myApp", "Button Clicked");
    	 String inputString = input.getText().toString();
    	 Log.i("myApp", "Button Clicked2");
    		infoList = db.getItemByNameSearch(inputString);
    		
    		displayList(infoList);
    		for (Info in : infoList){
    		   String log = "Id: "+in.getTitle();
    		   Log.d("Info: ", log);
    		   
    		   
    		}
		    }});
        return rootView;
    }
    
    private void displayList(List<Info> input)
    {
    	ListView mDrawerList = (ListView) rootView.findViewById(R.id.list);

        

    	ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();

        for (Info info : infoList) {

        	navDrawerItems.add(new NavDrawerItem(info.getTitle()));

        }

        // Recycle the typed array

        mDrawerList.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        }

        // setting the Navigation drawer list adapter
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(activity.getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

    
    

 
	public SearchFragment(){
		
	}
    
    public SearchFragment (Activity act) {
		this.db = new DatabaseHandler(act);
		this.activity = act;
	}

    
}

