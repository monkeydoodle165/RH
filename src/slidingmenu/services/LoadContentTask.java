package slidingmenu.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import slidingmenu.DatabaseHandler;
import slidingmenu.model.Category;
import slidingmenu.model.Info;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Synchronize operation
 */
public class LoadContentTask extends AsyncTask<String, Void, Void> {

    // Required initialization

    //  private final HttpClient Client = new DefaultHttpClient();
    private Context context;
    private String content;
    private ProgressDialog dialog;
    private String data ="";
    private DatabaseHandler db;
    private Runnable onComplete;
    private Date lastUpdated = null;
    
    //set the URLs to the webservices here
    private final String categoryURL = "http://www.jjk.co.nz/categories.php";
    private final String itemURL = "http://www.jjk.co.nz/JSON.php";

    // TextView uiUpdate = (TextView) findViewById(R.id.output);
    // TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
    // int sizeData = 0;
    // EditText serverText = (EditText) findViewById(R.id.serverText);

    public LoadContentTask(Context context) {
        this.context = context;
        this.dialog = new ProgressDialog(this.context);
        this.db = new DatabaseHandler(this.context);
    }


    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        //Start Progress Dialog (Message)

        dialog.setMessage("Please wait ...");
        dialog.show();

    }

    // Call after onPreExecute method
    protected Void doInBackground(String... urls) {

        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
        	//here you need to add the last updated date to the URL before sending it in
            URL url = new URL(categoryURL);


            //String serverURL = "http://jjk.co.nz/JSON.php";

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            //might be useless
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }

            // Append Server Response To Content String
            content = sb.toString();
            parseCategories(content);

            // Defined URL  where to send data
          //here you need to add the last updated date to the URL before sending it in
            url = new URL(itemURL);


            // Send POST data request

            conn = url.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            line = null;

            // Read Server Response
            //might be useless
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }

            // Append Server Response To Content String
            content = sb.toString();
            parseItems(content);
        }


        catch(Exception ex)
        {
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        /*****************************************************/
        return null;
    }

    protected void onPostExecute(Void unused) {
        // NOTE: You can call UI Element here.

        // Close progress dialog
        dialog.dismiss();

        if (this.onComplete != null) {
            this.onComplete.run();
        }
    }

    private void parseItems(String content)
    {
        String OutputData = "";
        JSONObject jsonResponse;
        try {

            /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
            jsonResponse = new JSONObject(content);

            /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
            /*******  Returns null otherwise.  *******/

            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");

            /*********** Process each JSON Node ************/

            int lengthJsonArr = jsonMainNode.length();
            Log.d("Insert: ", "Inserting Items");
            for(int i=0; i < lengthJsonArr; i++)
            {
            	//Reset all strings and prepare them to be populated
            	String phNum = "";
                String email = "";
                String address = "";
                String postal = "";
                String fax = "";
                String web = "";
                String regions = "";
            	
                /****** Get Object for each JSON node.***********/
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                // String extra = jsonChildNode.optString("extra_fields").toString();
                /******* Fetch node values **********/
                String catid   = jsonChildNode.optString("catid").toString();
                String title = jsonChildNode.optString("title").toString();
                String alias = jsonChildNode.optString("alias").toString();
                String extra = jsonChildNode.optString("extra_fields").toString();
                
                //START TEST
                
                
        		//this should be replaced using the table hpxft_extra_fields in order to make it fully variable
        		
                JSONObject extraFields;

                try {
        		/****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                extraFields = new JSONObject("{ 'extra_fields': " + extra +" }");

                /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                /*******  Returns null otherwise.  *******/

                JSONArray jsonMainNodeExtra = extraFields.getJSONArray("extra_fields");

                /*********** Process each JSON Node ************/

                int lengthJsonArrExtra = jsonMainNodeExtra.length();
                for(int j=0; j < lengthJsonArrExtra; j++)
                {
                    /****** Get Object for each JSON node.***********/
                    JSONObject jsonChildNodeExtra = jsonMainNodeExtra.getJSONObject(j);
                    // String extra = jsonChildNode.optString("extra_fields").toString();
                    /******* Fetch node values **********/
                    String id   = jsonChildNodeExtra.optString("id").toString();
                    String value = jsonChildNodeExtra.optString("value").toString();
                    
                    //This is used to check which type of contact detail is stored in this object
                    //This should be extended later to be able to detect what type of contact it is
                    //based on the hpxft_k2_extra_fields table(this needs to be downloaded and stored on the phone)
                    if (id.contains("1"))
                    {
                    	phNum = value;
                    }
                    if (id.contains("2"))
                    {
                    	email = value;
                    }
                    if (id.contains("3"))
                    {
                    	address = value;
                    }
                    if (id.contains("4"))
                    {
                    	postal = value;
                    }
                    if (id.contains("5"))
                    {
                    	fax = value;
                    }
                    
                    if (id.contains("6"))
                    {
	                    String[] tempSplit = value.split(",");
	                    web = Clean(tempSplit[0]);
	                    //Html.fromHtml("<a href=\'" + Clean(tempSplit[0])+"'>" + Clean(tempSplit[0]) + "</a>")
                    }
                    
                    //This will need to be sourced from the db or a config file in the future
                    if(id.contains("7"))
                    {
                    	if(value.contains("1"))
                    	{
                    		regions = regions + "North Shore   ";
                    	}
                    	if(value.contains("2"))
                    	{
                    		regions = regions + "Rodney   ";
                    	}
                    	if(value.contains("3"))
                    	{
                    		regions = regions + "Waitakere";
                    	}
                    }
                    
                    



                    //Log.i("JSON parse", song_name);
                }
                /****************** End Parse Response JSON Data *************/
        		
                } catch (JSONException e) {

                    e.printStackTrace();
                }

                //END TEST
                
                
                String intro = jsonChildNode.optString("introtext").toString();
                intro=intro.replaceAll("<li>", "\u2022");

                intro = intro.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
                intro = intro.replaceAll("<(.*?)\\\n"," ");//Must be underneath
                intro = intro.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
                intro = intro.replaceAll("&nbsp;"," ");
                intro = intro.replaceAll("&amp;"," ");
                intro = intro.trim();
                //nevermind, to change it I have to redownload :P
                //text size fine//?  Ie so all the  think so, we can ask raeburn house if they like it
                //ok tomorrow I will cdo colour andla icons etc then just updating and searching to finish perfect, thank you
                //hope to be dones paby friday then Iyout is done now, goona leave it there can take a break on saturday :D hahaha yeah you need it ok I'm goining to bed now so tired hahaha :
                //hahatrailin g fixedthe  
                //have a good night  you too 

                Log.d("Insert: ", OutputData);
                db.addInfo(new Info(Integer.parseInt(catid), title, phNum, email, address, postal, fax, web,  regions, intro));



                //Log.i("JSON parse", song_name);
            }
            /****************** End Parse Response JSON Data *************/

            //Show Parsed Output on screen (activity)
            // jsonParsed.setText( OutputData );



        } catch (JSONException e) {

            e.printStackTrace();
        }


    }
    
    private String Clean(String input)
	{	
		String output = input.replaceAll("\"", "").replaceAll("\\\\", "").replaceAll("\\]", "").replaceAll("\\[", "");
		return output;
	}

    private void parseCategories(String content) {
        String OutputData = "";
        JSONObject jsonResponse;

        try {

            /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
            jsonResponse = new JSONObject(content);

            /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
            /*******  Returns null otherwise.  *******/
            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");

            /*********** Process each JSON Node ************/

            int lengthJsonArr = jsonMainNode.length();

            Log.d("Insert: ", "Inserting Categories");
            for(int i=0; i < lengthJsonArr; i++)
            {
                /****** Get Object for each JSON node.***********/
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                // String extra = jsonChildNode.optString("extra_fields").toString();
                /******* Fetch node values **********/
                Integer catid   = jsonChildNode.optInt("id");
                String name = jsonChildNode.optString("name").toString();
                String parent = jsonChildNode.optString("parent").toString();

                OutputData = "ID: "+ catid
                        + " Name: "+ name
                        + " Parent: "+ parent;

                Log.d("Insert: ", OutputData);


                db.addCat(new Category(catid, name, parent));


            }
            /****************** End Parse Response JSON Data *************/

            //Show Parsed Output on screen (activity)
            // jsonParsed.setText( OutputData );

        } catch (JSONException e) {

            e.printStackTrace();
        }


    }

    public void setOnComplete(Runnable onComplete) {
        this.onComplete = onComplete;
    }
}

