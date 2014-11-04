package slidingmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import slidingmenu.adaptor.NavDrawerListAdapter;
import slidingmenu.model.NavDrawerItem;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.rh.R;

import slidingmenu.services.MenuGeneratorService;


/**
 * Main activity
 */
public class MainActivity extends Activity {
	
	private DatabaseHandler db = new DatabaseHandler(this);
    private MenuGeneratorService mGenerator = new MenuGeneratorService(db);

	private Logger LOG = Logger.getLogger(MainActivity.class.getName());
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

    private List<Integer> parentStack = new ArrayList<Integer>();;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViewElements(savedInstanceState);
    }
    
    private Context getContext()
    {
    	return this;
    }


    protected void initializeViewElements(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        // Navigation drawer icons from resources
        //navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
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
            // on first time display view for first nav item
   //         displayView(0);
        }
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

        	navDrawerItems.add(
        			new NavDrawerItem(
                            label
                            //,navMenuIcons.getResourceId(0, -1)
                    )
                );

        }
    }



    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            String selectedLabel = navMenuTitles.get(position);
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

        private void generateSubMenuFor(int categoryId) {
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
					if (isBackButton(subPosition)) {
                        goBackUp();
					}
					else {

                        List<String> currentLabels = mGenerator.getSubMenuItemLabels(getLatestOnStack());
                        String selectedLabel = currentLabels.get(subPosition - 1);
                        int categoryId = mGenerator.getIdForLabel(selectedLabel);

                        if (mGenerator.hasChildren(categoryId)) {
                            pushStack(categoryId);
                            generateSubMenuFor(categoryId);
                        }
                        else {
                            displayViewByClass(categoryId);

                            Log.i("INFO", "Reached leaf: " + selectedLabel);
                        }


					}
				}

                private boolean isBackButton(int subPosition) {
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
        // toggle Navigation drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_settings:
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
        // if Navigation drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
   private void displayViewByClass(int itemId) {
       Log.i("Leaf", "Requested view of " + itemId);

       Fragment fragment = new DatabaseDetailFragment(itemId,this);
         //Menu_dossFragment mdf = new Menu_dossFragment(itemId, this);
       
       android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
       ft.show(fragment);
       
       
           FragmentManager fragmentManager = getFragmentManager();
           fragmentManager.beginTransaction()
                   .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
          
           
           // update selected item and title, then close the drawer
           mDrawerList.setItemChecked(itemId, true);
           mDrawerList.setSelection(itemId);
          // setTitle(navMenuTitles.get(itemId));
           mDrawerLayout.closeDrawer(mDrawerList);
       

    }
    
    /**
    * Displaying fragment view for selected nav drawer list item
    * */
   private void displayView(int position) {
       // update the main content by replacing fragments
	   //pass position or similar to databasedetailfragment then populate and show page dependent on that id
       Fragment fragment = null;

       if (fragment != null) {
           FragmentManager fragmentManager = getFragmentManager();
           fragmentManager.beginTransaction()
                   .replace(R.id.frame_container, fragment).commit();

           // update selected item and title, then close the drawer
           mDrawerList.setItemChecked(position, true);
           mDrawerList.setSelection(position);
           setTitle(navMenuTitles.get(position));
           mDrawerLayout.closeDrawer(mDrawerList);
       }
       else {
           // error in creating fragment
           Log.e("MainActivity", "Error in creating fragment");
       }
   }
 
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
    
    
    

}
    
