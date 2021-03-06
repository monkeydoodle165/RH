package slidingmenu;

import java.util.ArrayList;
import java.util.List;
import slidingmenu.adaptor.NavDrawerListAdapter;
import slidingmenu.model.NavDrawerItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rh.R;

import slidingmenu.services.MenuGeneratorService;


/**
 * Main activity
 */
public class MainActivity extends Activity
{
	
	private DatabaseHandler db = new DatabaseHandler(this);
    private MenuGeneratorService mGenerator = new MenuGeneratorService(db);

	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
 
    // Navigation drawer title
    private CharSequence mDrawerTitle;
 
    // used to store application title
    private CharSequence mTitle;
    
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
 
    private List<String> navMenuTitles;

    private List<Integer> parentStack = new ArrayList<Integer>();
    private boolean isMain = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        	
        
        setContentView(R.layout.activity_main);
            LayoutInflater inflater = (LayoutInflater) getActionBar()
                    .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);

            ActionBar actionBar = getActionBar();
            actionBar.setDisplayOptions(
                    ActionBar.DISPLAY_SHOW_CUSTOM,
                    ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                            | ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setCustomView(customActionBarView,
                    new ActionBar.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            
            final Button buttonDirectory = (Button) findViewById(R.id.action_directory);
            buttonDirectory.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showDoSS();
                }
            });
            
            final Button buttonSearch = (Button) findViewById(R.id.action_search);
            buttonSearch.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    displaySearch();
                }
            });
            
            final Button buttonAbout = (Button) findViewById(R.id.action_about);
            buttonAbout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showAbout();
                }
            });

            
        
        
        initializeViewElements(savedInstanceState);
    }
    protected void initializeViewElements(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        // Navigation drawer icons from resources
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        pushStack(0);

        initializeMenuItems();

        // Recycle the typed array
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the Navigation drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar application icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = getActionBarDrawerToggleInstance();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
        }
    }
    
    public void showDoSS(View v)
    {
    	mDrawerLayout.openDrawer(mDrawerList);
    }
    
    public void showDoSS()
    {
    	mDrawerLayout.openDrawer(mDrawerList);
    }

    protected ActionBarDrawerToggle getActionBarDrawerToggleInstance() {
        return new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
    }

    protected void initializeMenuItems() {
        initializeMenuItems(MenuGeneratorService.ROOT_MENU_PARENT);
    }

    /**
     * Transform database categories into menu items
     */
    protected void initializeMenuItems(int categoryId) {


        navMenuTitles = mGenerator.getMainMenuItemLabels();
        navDrawerItems = new ArrayList<NavDrawerItem>();

        for (String label : navMenuTitles) {

        	navDrawerItems.add(new NavDrawerItem(label));
        }
    }
    
    public void showAbout()
    {
    Uri uriUrl = Uri.parse("http://www.raeburnhouse.org.nz/about-us");  
    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);  

    startActivity(launchBrowser); 
    }
    
    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            String selectedLabel = navMenuTitles.get(position);
            
            if(selectedLabel.equals("Search"))
            {
            	displaySearch();
            }
            else if(selectedLabel.equals("About"))
            {
            	showAbout();
            }
            else
            {
            int clickedId = mGenerator.getIdForLabel(selectedLabel);

            Log.i("INFO", "Selected: " + selectedLabel + ", " + clickedId);

            if (mGenerator.hasChildren(clickedId)) {
                pushStack(clickedId);
                generateSubMenuFor(clickedId);
            }
            else {
                displayViewByClass(clickedId);
                Log.i("sub", "Hit a leaf node");
            }
            }
        }
        
        

        public void generateSubMenuFor(int categoryId) {
            List<NavDrawerItem> subNavDrawerItems = createSubMenuDrawer(categoryId);
            
            // setting the Navigation drawer list adapter
            final NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(), subNavDrawerItems);
            mDrawerList.setAdapter(adapter);
            mDrawerList.setOnItemClickListener(onSubmenuClick());
        }

        private OnItemClickListener onSubmenuClick() {
			return new ListView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int subPosition, long id) {

                    // back button hit?
					if (isBackButton(subPosition)) 
					{
                        goBackUp();
					}
					else 
					{
                        List<String> currentLabels = mGenerator.getSubMenuItemLabels(getLatestOnStack());
                        String selectedLabel = currentLabels.get(subPosition - 1);
                        int categoryId = mGenerator.getIdForLabel(selectedLabel);

                        if (mGenerator.hasChildren(categoryId)) 
                        {
                            pushStack(categoryId);
                            generateSubMenuFor(categoryId);
                        }
                        else 
                        {
                            displayViewByClass(categoryId);

                            Log.i("INFO", "Reached leaf: " + selectedLabel);
                        }


					}
				}

                private boolean isBackButton(int subPosition) 
                {
                    return subPosition == 0;
                }

                private void goBackUp() {

                    popStack();
                    int parentId = getLatestOnStack();

                    Log.i("Submenu", "Back was pressed, going back to parent with id: " + parentId);

                    // Going back to main menu
                    if (parentId == 0) {
                        Log.i("Submenu", "Going back up to main menu");
                        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
                        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
                        mDrawerList.setAdapter(adapter);
                    }
                    else {
                        generateSubMenuFor(parentId);
                        Log.i("Submenu", "going up to another submenu with id: " + parentId);
                    }
                }

            };
		}

		private List<NavDrawerItem> createSubMenuDrawer(int categoryId) {
			List<NavDrawerItem> subNavDrawerItems = new ArrayList<NavDrawerItem>();
			
			subNavDrawerItems.add(new NavDrawerItem("Back"));

            for (String label : mGenerator.getSubMenuItemLabels(categoryId)) {
                subNavDrawerItems.add(new NavDrawerItem(label));
            }

			return subNavDrawerItems;
		}
    }

    private void pushStack(int categoryId) {
        this.parentStack.add(categoryId);
    }

    private void popStack() {
        this.parentStack.remove(this.parentStack.size() - 1);
    }

    private Integer getLatestOnStack() {
        return this.parentStack.get(this.parentStack.size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         //toggle Navigation drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
    	System.out.println(item.getItemId());
        
    	if (item.getItemId() == R.id.action_directory) {
    	      Toast.makeText(this, "Got your tap", Toast.LENGTH_LONG).show();
    	      return true;
    	    }
    	
    	
    	switch (item.getItemId()) {
        
        case R.id.action_directory:
        	System.out.println("directory");
        	showDoSS();
            return true;
        case R.id.action_search:
        	System.out.println("directory");
        	showDoSS();
            return true;
        case R.id.action_about:
        	System.out.println("directory");
        	showDoSS();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    
    private void displaySearch()
    {
    	Fragment fragment = new SearchFragment(this, getApplicationContext());

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).addToBackStack(null).commit();

       // setTitle(navMenuTitles.get(itemId));
        isMain = false;
        mDrawerLayout.closeDrawer(mDrawerList);
        
    }
    
   private void displayViewByClass(int itemId) {
       Log.i("Leaf", "Requested view of " + itemId);

       Fragment fragment = new DatabaseDetailFragment(itemId,this);
       
           FragmentManager fragmentManager = getFragmentManager();
           fragmentManager.beginTransaction()
                   .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
          
           
           // update selected item and title, then close the drawer
           mDrawerList.setItemChecked(itemId, true);
           mDrawerList.setSelection(itemId);
          // setTitle(navMenuTitles.get(itemId));
           isMain=false;
           mDrawerLayout.closeDrawer(mDrawerList);
           

    }
    
    /**
    * Displaying fragment view for selected nav drawer list item
    * */
   
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
        
    } 
    
    @Override
    public void onBackPressed()
    {
    	if(isMain == true)
    	{
    		Intent intent = new Intent(Intent.ACTION_MAIN);
        	intent.addCategory(Intent.CATEGORY_HOME);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(intent);
    	}
    	else
    	{
    		super.onBackPressed();
    		isMain = true;
    	}
    	
    }
}
    
