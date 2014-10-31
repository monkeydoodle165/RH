package slidingmenu.model;

import java.util.logging.Logger;

import android.app.Fragment;

public class ItemFragmentPair {
	
	private String title;
	private Fragment fragmentInstance;
	private static final Logger LOG = Logger.getLogger("fragmentPair");
	
	/**
	 * Initialize data-members
	 * 
	 * @param title
	 * @param fragmentInstance
	 */
	public ItemFragmentPair(String title, Fragment fragmentInstance) {
		this.title = title;
		this.fragmentInstance = fragmentInstance;
	}
	
	
	public static ItemFragmentPair createPair(String title, Fragment fragmentInstance) {
		return new ItemFragmentPair(title, fragmentInstance);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Fragment getFragmentInstance() {
		return fragmentInstance;
	}

	public void setFragmentClass(Fragment fragmentInstance) {
		this.fragmentInstance = fragmentInstance;
	}
	
	
	
}
