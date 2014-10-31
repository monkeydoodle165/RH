package slidingmenu;



import java.util.ArrayList;

import slidingmenu.model.Info;

import com.example.rh.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
@SuppressLint("ValidFragment")
public class DatabaseDetailFragment extends Fragment {
	
	private static DatabaseHandler db;
	private static Info info;
	private static int id;
	private static int catid;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
    		this.info = this.db.getInfo(id);
    	
    		
    		String catName = this.db.getCat(this.info.getCatid()).getName();
    		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    		View mainView = rootView.findViewById(R.id.mainLayout);
    		mainView.setBackgroundResource(Utility.findBackColour(catName));
    		TextView txtLabel=(TextView)rootView.findViewById(R.id.txtLabel);
			txtLabel.setText(info.getTitle());
			txtLabel.setBackgroundResource(Utility.findColour(catName));
			  
			TextView txt2=(TextView)rootView.findViewById(R.id.txt2);
			txt2.setText(info.getRegions());
			  
			TextView txt3=(TextView)rootView.findViewById(R.id.txt3);
			String phNum = info.getPhNum();
			String email = info.getEmail();
			String address = info.getAddress();
			String postal = info.getPostal();
			String fax = info.getFax();
			String web = info.getWeb();
			String contacts = "";
			
				if (phNum != null && phNum != "" && !phNum.equals("Phone Number: "))
				{
					contacts = contacts + phNum + System.getProperty("line.separator");
				}
				if (email != null && email != "" && !email.equals("Email: "))
				{
					contacts = contacts + email + System.getProperty("line.separator");
				}
				if (address != null && address != "" && !address.equals("Address: "))
				{
					contacts = contacts + address + System.getProperty("line.separator");
				}
				if (postal != null && postal != "" && !postal.equals("Postal Address: "))
				{
					contacts = contacts + postal + System.getProperty("line.separator");
				}
				if (fax != null && fax != "" && !fax.equals("Fax: "))
				{
					contacts = contacts + fax + System.getProperty("line.separator");
				}
				if (web != null && web != "" && !web.equals("Website: "))
				{
					contacts = contacts + web;
				}
			txt3.setText(contacts);
			
			TextView txt4=(TextView)rootView.findViewById(R.id.txt4);
			txt4.setText(info.getIntrotext());
			  
//			TextView txt5=(TextView)rootView.findViewById(R.id.txt5);
//			txt5.setText(detailInfo.getBulletPoints()[0] + detailInfo.getBulletPoints()[1] + detailInfo.getBulletPoints()[2]); 
        return rootView;
    }
	
	public DatabaseDetailFragment(){
		
	}
    
    public DatabaseDetailFragment(int id, Activity act) {
		this.db = new DatabaseHandler(act);
		this.id = id;
		this.info = this.db.getInfo(id);
	}

}

