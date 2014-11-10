package slidingmenu;




import java.util.ArrayList;
import java.util.regex.Pattern;

import slidingmenu.model.Info;

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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
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
	String tempString = "";
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
    		this.info = this.db.getInfo(id);
    	
    		//get the cat name from the database
    		String catName = this.db.getCat(this.info.getCatid()).getName();
    		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    		View mainView = rootView.findViewById(R.id.mainLayout);
    		//set background of the view
    		//mainView.setBackgroundResource(Utility.findBackColour(catName));
    		//get the txtlabel
    		TextView txtLabel=(TextView)rootView.findViewById(R.id.txtLabel);
    		//set the text of it to title
			txtLabel.setText(info.getTitle());
			//set the background colour of the heading
			//txtLabel.setBackgroundResource(Utility.findColour(catName));
			  
			TextView txt2=(TextView)rootView.findViewById(R.id.txt2);
			txt2.setText(info.getRegions());
			final TextView txtPhone =(TextView) rootView.findViewById(R.id.txtPhone);
			final TextView txtEmail =(TextView) rootView.findViewById(R.id.txtEmail);
			final TextView txtAddress =(TextView) rootView.findViewById(R.id.txtAddress);
			final TextView txtPostal =(TextView) rootView.findViewById(R.id.txtPostal);
			final TextView txtFax =(TextView) rootView.findViewById(R.id.txtFax);
			final TextView txtWeb =(TextView) rootView.findViewById(R.id.txtWeb);
			String phNum = info.getPhNum();
			String email = info.getEmail();
			String address = info.getAddress();
			String postal = info.getPostal();
			String fax = info.getFax();
			final String web = info.getWeb();
			String Phone = "";
			String Email = "";
			String Address = "";
			String Postal = "";
			String Fax = "";
			String Web = "";

				if (phNum != null && phNum != "" && !phNum.equals(""))
				{
					phNum=phNum.replaceAll("(09)", "09").replaceAll("09", "+649");
					Phone = Phone + phNum + System.getProperty("line.separator");
					txtPhone.setText(Phone);
					Linkify.addLinks(txtPhone,Linkify.PHONE_NUMBERS);
				}
				else
				{
					TextView phoneLabel =(TextView) rootView.findViewById(R.id.phoneLabel);
					//((ViewGroup) phoneLabel.getParent()).removeView(phoneLabel);
					phoneLabel.setText("");
					phoneLabel.setHeight(0);
				
				}
				
				if (email != null && email != "" && !email.equals(""))
				{
					Email = Email + email + System.getProperty("line.separator");
					txtEmail.setText(Email);
				}else
				{
					TextView emailLabel =(TextView) rootView.findViewById(R.id.emailLabel);
					//((ViewGroup) emailLabel.getParent()).removeView(emailLabel);
					emailLabel.setText("");
					emailLabel.setHeight(0);
				}
				if (address != null && address != "" && !address.equals(""))
				{
					Address = Address + address + System.getProperty("line.separator");
					SpannableString spanStr = new SpannableString(Address.toString());
					spanStr.setSpan(new UnderlineSpan(), 0, spanStr.length(), 0);
					txtAddress.setText(spanStr);
					txtAddress.setOnClickListener(new View.OnClickListener() 
					{
				    	 
					    public void onClick(View v) {
					Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="
                            +txtAddress.getText().toString()));
					startActivity(geoIntent);
					    }
					});
				}else
				{
					TextView addressLabel =(TextView) rootView.findViewById(R.id.addressLabel);
					//((ViewGroup) addressLabel.getParent()).removeView(addressLabel);
					addressLabel.setText("");
					addressLabel.setHeight(0);
				}
				if (postal != null && postal != "" && !postal.equals(""))
				{
					Postal = Postal + postal + System.getProperty("line.separator");
					txtPostal.setText(Postal);
				}else
				{
					TextView postalLabel =(TextView) rootView.findViewById(R.id.postalLabel);
					//((ViewGroup) postalLabel.getParent()).removeView(postalLabel);
					postalLabel.setText("");
					postalLabel.setHeight(0);
				}
				if (fax != null && fax != "" && !fax.equals(""))
				{
					Fax = Fax + fax + System.getProperty("line.separator");
					txtFax.setText(Fax);
				}else
				{
					TextView faxLabel =(TextView) rootView.findViewById(R.id.faxLabel);
					//((ViewGroup) faxLabel.getParent()).removeView(faxLabel);
					faxLabel.setText("");
					faxLabel.setHeight(0);
				}
				if (web != null && web != "" && !web.equals(""))
				{
					Web = Web + web;
					txtWeb.setText(Web);
				}else
				{
					TextView webLabel =(TextView) rootView.findViewById(R.id.webLabel);
					//((ViewGroup) webLabel.getParent()).removeView(webLabel);
					webLabel.setText("");
					webLabel.setHeight(0);
				}

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

