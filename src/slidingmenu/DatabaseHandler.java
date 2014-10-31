package slidingmenu;

import java.util.ArrayList;
import java.util.List;

import slidingmenu.model.Category;
import slidingmenu.model.Info;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "infoManager";

    // Contacts table name
    private static final String TABLE_INFO = "info";
    private static final String TABLE_CAT = "cat";

    // Contacts Table Columns names
    private static final String ITEM_ID = "itemId";
    private static final String ITEM_CATID = "catId";
    private static final String ITEM_TITLE = "title";
    private static final String ITEM_PHNUM = "phNum";
    private static final String ITEM_EMAIL = "email";
    private static final String ITEM_ADDRESS = "address";
    private static final String ITEM_POSTAL = "postal";
    private static final String ITEM_FAX = "fax";
    private static final String ITEM_WEBSITE = "website";
    private static final String ITEM_REGIONS = "regions";
    private static final String ITEM_INTROTEXT = "introText";


    //CAT TABLE COLUMNS
    private static final String CAT_ID = "id";
    private static final String CAT_CATNAME = "name";
    private static final String CAT_PARENT = "parent";

    public String dbPath;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //File outFile =context.getDatabasePath(DATABASE_NAME);
        //String outFileName =outFile.getPath() ;
        //dbPath= outFileName;
    }
    
    //DatabaseHandler mOpenHelper = new DatabaseHandler(getContext());
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INFO_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INFO + "("
                + ITEM_ID + " INTEGER PRIMARY KEY," + ITEM_CATID + " TEXT," + ITEM_TITLE + " TEXT," + ITEM_PHNUM + " TEXT," + ITEM_EMAIL + " TEXT," 
        		+ ITEM_ADDRESS + " TEXT," + ITEM_POSTAL + " TEXT," + ITEM_FAX + " TEXT," + ITEM_WEBSITE + " TEXT," + ITEM_REGIONS + " TEXT," + ITEM_INTROTEXT + " TEXT"
                + ")";
        db.execSQL(CREATE_INFO_TABLE);
        
        String CREATE_CAT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CAT + "("
                + CAT_ID + " INTEGER PRIMARY KEY," + CAT_CATNAME + " TEXT," + CAT_PARENT + " TEXT"
                + ")";
        db.execSQL(CREATE_CAT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addInfo(Info info) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_CATID, info.getCatid());
        values.put(ITEM_TITLE, info.getTitle());
        values.put(ITEM_PHNUM, info.getPhNum());
        values.put(ITEM_EMAIL, info.getEmail());
        values.put(ITEM_ADDRESS, info.getAddress());
        values.put(ITEM_POSTAL, info.getPostal());
        values.put(ITEM_FAX, info.getFax());
        values.put(ITEM_WEBSITE, info.getWeb());
        values.put(ITEM_REGIONS, info.getRegions());
        values.put(ITEM_INTROTEXT, info.getIntrotext());

        // Inserting Row
        db.insert(TABLE_INFO, null, values);
        db.close(); // Closing database connection
    }

    // Getting single info
    Info getInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INFO, new String[] { ITEM_ID, ITEM_CATID, ITEM_TITLE, ITEM_PHNUM, ITEM_EMAIL, 
        		ITEM_ADDRESS, ITEM_POSTAL, ITEM_FAX, ITEM_WEBSITE, ITEM_REGIONS, ITEM_INTROTEXT}, ITEM_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Info info = new Info(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3),
        		cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10));
        return info;
    }

    // Getting All Info
    public List<Info> getAllInfo() {
        List<Info> infoList = new ArrayList<Info>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Info info = new Info();
                info.setID(Integer.parseInt(cursor.getString(0)));
                info.setCatid(Integer.parseInt(cursor.getString(1)));
                info.setTitle(cursor.getString(2));
                info.setPhNum(cursor.getString(3));
                info.setEmail(cursor.getString(4));
                info.setAddress(cursor.getString(5));
                info.setPostal(cursor.getString(6));
                info.setFax(cursor.getString(7));
                info.setWeb(cursor.getString(8));
                info.setRegions(cursor.getString(9));
                info.setIntrotext(cursor.getString(10));

                // Adding contact to list
                infoList.add(info);
            } while (cursor.moveToNext());
        }

        // return contact list
        return infoList;
    }
    
    
 // Getting info by category
    public List<Info> getItemByCat(int catId) {
        List<Info> infoList = new ArrayList<Info>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INFO + " WHERE catid = " + Integer.toString(catId) ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Info info = new Info();
                info.setID(Integer.parseInt(cursor.getString(0)));
                info.setCatid(Integer.parseInt(cursor.getString(1)));
                info.setTitle(cursor.getString(2));
                info.setPhNum(cursor.getString(3));
                info.setEmail(cursor.getString(4));
                info.setAddress(cursor.getString(5));
                info.setPostal(cursor.getString(6));
                info.setFax(cursor.getString(7));
                info.setWeb(cursor.getString(8));
                info.setRegions(cursor.getString(9));
                info.setIntrotext(cursor.getString(10));

                // Adding contact to list
                infoList.add(info);
            } while (cursor.moveToNext());
        }

        // return contact list
        return infoList;
    }
    
    


    public int updateInfo(Info info) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_CATID, info.getCatid());
        values.put(ITEM_TITLE, info.getTitle());
        values.put(ITEM_PHNUM, info.getPhNum());
        values.put(ITEM_EMAIL, info.getEmail());
        values.put(ITEM_ADDRESS, info.getAddress());
        values.put(ITEM_POSTAL, info.getPostal());
        values.put(ITEM_FAX, info.getFax());
        values.put(ITEM_WEBSITE, info.getWeb());
        values.put(ITEM_REGIONS, info.getRegions());
        values.put(ITEM_INTROTEXT, info.getIntrotext());

        // updating row
        return db.update(TABLE_INFO, values, ITEM_ID + " = ?",
                new String[] { String.valueOf(info.getID()) });
    }

 
    public void deleteContact(Info info) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFO, ITEM_ID + " = ?",
                new String[] { String.valueOf(info.getID()) });
        db.close();
    }


    // Getting info Count
    public int getInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    /**
     * Check if the database exist
     *
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase() {
        return getInfoCount()> 0 ? true: false;
    }





    // Adding new contact
    public void addCat(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAT_ID, category.getId());
        values.put(CAT_CATNAME, category.getName()); // title
        values.put(CAT_PARENT, category.getParent()); // Alias


        // Inserting Row
        db.insert(TABLE_CAT, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Category
    public Category getCat(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CAT, new String[] { CAT_ID,
                CAT_CATNAME,CAT_PARENT}, CAT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return Category
        return category;
    }
    
    //TEST
    
    // Getting info by category
    public List<Info> getCategory(int catId) {
        List<Info> infoList = new ArrayList<Info>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INFO + " WHERE catid = " + Integer.toString(catId) ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Info info = new Info();
                info.setID(Integer.parseInt(cursor.getString(0)));
                info.setCatid(Integer.parseInt(cursor.getString(1)));
                info.setTitle(cursor.getString(2));
                info.setPhNum(cursor.getString(3));
                info.setEmail(cursor.getString(4));
                info.setAddress(cursor.getString(5));
                info.setPostal(cursor.getString(6));
                info.setFax(cursor.getString(7));
                info.setWeb(cursor.getString(8));
                info.setRegions(cursor.getString(9));
                info.setIntrotext(cursor.getString(10));

                // Adding contact to list
                infoList.add(info);
            } while (cursor.moveToNext());
        }

        // return contact list
        return infoList;
    }
    
    
    
    //END TEST
    
    
    

    // Getting All Category
    public List<Category> getAllCat() {
        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CAT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
                // Adding contact to list
                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        // return category list
        return categoryList;
    }

    // Updating single contact
    public int updateCat(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAT_ID, category.getId());
        values.put(CAT_CATNAME, category.getName());
        values.put(CAT_PARENT, category.getParent());


        // updating row
        return db.update(TABLE_CAT, values, CAT_ID + " = ?",
                new String[] { String.valueOf(category.getId()) });
    }

    // Deleting single contact
    public void deleteCat(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAT, ITEM_ID + " = ?",
                new String[] { String.valueOf(category.getId()) });
        db.close();
    }


}
