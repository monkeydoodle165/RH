package slidingmenu.services;

import slidingmenu.*;
import slidingmenu.model.Category;
import slidingmenu.model.Info;
import slidingmenu.model.MenuItem;
import java.util.*;

public class MenuGeneratorService {

    public static final int ROOT_MENU_PARENT = 0;

    /**
     * database handler
     */
    private DatabaseHandler dbHandler;

    private Map<String, MenuItem> labelIdMap;

    /**
     * container for lazy-loaded menu map
     */
    private Map<Integer, List<String>> menuMap;

    private boolean initialized = false;

    /**
     * Initialize data-members
     *
     * @param dbHandler is the database handler to query
     */
    public MenuGeneratorService(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }


    protected void reset() {
        this.labelIdMap = new HashMap<String, MenuItem>();
        this.menuMap = new LinkedHashMap<Integer, List<String>>();
    }

    /**
     * Build the menu item list and menu children map
     */
    protected void initialize() {
        reset();

        List<Category> allCategories = this.dbHandler.getAllCat();

        // iterate to find all sub items
        for (Category cat : allCategories) {
            int parent = Integer.parseInt(cat.getParent());
            
            // menu map doesn't have a list for this parent yet?
            if (!this.menuMap.containsKey(parent)) {
                this.menuMap.put(parent, new ArrayList<String>());
            }
            else
            {
            	this.menuMap.put(cat.getId(), new ArrayList<String>());
            }


            // add element to map
            this.menuMap.get(parent).add(cat.getName());
            
            List<Info> infoByCat = this.dbHandler.getItemByCat(cat.getId());
            
            if (!this.menuMap.containsKey(cat.getId())) {
        		this.menuMap.put(cat.getId(), new ArrayList<String>());
            }
            
            MenuItem menuItem = new MenuItem(cat.getId(),"cat");
            labelIdMap.put(cat.getName(), menuItem);
            
            for (Info item : infoByCat) {
            	this.menuMap.get(cat.getId()).add(item.getTitle());
            	
            	menuItem = new MenuItem(item.getID(),"item");
                labelIdMap.put(item.getTitle(), menuItem);
            }

        }
        initialized = true;
    }

    public Map<Integer, List<String>> getMenuMap() {
        if (!initialized) {
            initialize();
        }
        return this.menuMap;
    }

    /**
     * @return the root items
     */
    public List<String> getMainMenuItemLabels() {
        if (!initialized) {
            initialize();
        }
        
        List<String> menuList = getSubMenuItemLabels(ROOT_MENU_PARENT);
        menuList.add("Search");
        menuList.add("About");
        return menuList;
    }

    /**
     * @return a list of sub item names for the menu item with label `labelText`
     */
    public List<String> getSubMenuItemLabels(String labelText) {
        int idForLabel = getIdForLabel(labelText);
        return getSubMenuItemLabels(idForLabel);
    }

    public Integer getIdForLabel(String labelText) {
        return labelIdMap.get(labelText).getId();
    }

    /**
     * @return a list of sub item labels for items with parent `parent`
     */
    public List<String> getSubMenuItemLabels(int parent) {
        List<String> catList = this.menuMap.get(parent);

        List<String> labels = new ArrayList<String>();
        if (catList == null) {
            return labels;
        }

        for (String cat : catList) {
            labels.add(cat);
        }
        return labels;
    }

    public boolean hasChildren(int categoryId) {
        return getSubMenuItemLabels(categoryId).size() > 0;
    }
}
