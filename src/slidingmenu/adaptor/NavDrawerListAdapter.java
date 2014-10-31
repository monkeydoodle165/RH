package slidingmenu.adaptor;

import java.util.List;

import slidingmenu.Utility;
import slidingmenu.model.NavDrawerItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rh.R;
 
public class NavDrawerListAdapter extends BaseAdapter {
     
    private Context context;
    private List<NavDrawerItem> navDrawerItems;
     
    public NavDrawerListAdapter(Context context, List<NavDrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }
    
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }
 
    @Override
    public Object getItem(int position) {       
        return navDrawerItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
        String title = navDrawerItems.get(position).getTitle();
        
        
        
        convertView.setBackgroundResource(Utility.findColour(title));
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
                 
        txtTitle.setText(title);
         
        return convertView;
    }
 
}