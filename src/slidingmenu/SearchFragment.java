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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
 
@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment {
	
	private static DatabaseHandler db;
	private static List<Info> currentList;
	//also need you to go through all the classes and make sure all of this is private
	//and the functions that dont need to be public and unecessary coments and so on
	//I#ll do all the real commenting later wait 
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
    		
		    public void onClick(View v) {
		   Log.i("myApp", "Button Clicked");
    	 String inputString = input.getText().toString();
    	 Log.i("myApp", "Button Clicked2");
    		currentList = db.getItemByNameSearch(inputString);
    		
    		displayList(currentList);
    		for (Info in : currentList){
    		   String log = "Id: "+in.getTitle();
    		   
    		   //im gona do that
    		   //all you need is an int that can feed it here and then use it down there
    		   //ok dont kill me :D 
    		   //haha
    		   Log.d("Info: ", log);
    		   
    		   
    		}
		    }});
        return rootView;
    }
    
    private void displayList(List<Info> input)
    {
    	ListView mDrawerList = (ListView) rootView.findViewById(R.id.list);

        

    	ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();

        for (Info info : currentList) {

        	navDrawerItems.add(new NavDrawerItem(info.getTitle()));

        }
    

    // Recycle the typed array

        
        

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


// can you comment out the part that is giving color to details
//anything else? mmm so what do you think? if we put a black bacground and change the text t
//white
//its an organisation that is supposed to help you, they dont use black anywhere its not associated with help
//I know it looks nice but its better for technology companies and games etc
//ok,if they are agrre with us to make it on colour
//otherwise make it look like www.raeburnhouse.org.nz/about-us/directory 
//about links, other than phon, I didn't put colour, andriod did it
//So u don't want to change it?
//play with it :) ok, can you try the map in ur phone? yeah. and how is mac going
//bad, shit why?
//lots of stuff to change to be able to build sub menus, I keep getting lost
//do think I can help u?
//its a mess at the moment, need to get it back up to working first
//ok, don't worry we have time. what do we need to do with android? other than
//search
//downloads yep. do you have a spot for that?
//make it run loadcontent task everytime in synchroniseactivity
//add the last updated date to the end of the URL in loadactivity
//this is done by storing a date using sharedpreference
//read about passing variables in a url
//you got it? so what is the accual format  yep, 
//url?date=YYYY-MM-DD%20hh:mm:ss
//so I need to change the url with this and then reload the loadcontent in sync clas
//each time the application runs loadcontent it will store todays date and time
//next time it runs loadcontent it will give that date and time so that the webservice can check if anything is new
//so the first time the date will be null and it will receive all data ok got it
//can you help me to finish the search and then Ill do update and colour stuff
//what is wrong with search?the thing that we couldn't make it remember
//I dont know how to fix it, go read xP
//I read alot but our code is totally different I know u can :)

