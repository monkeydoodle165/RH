package slidingmenu.model;

public class MenuItem {


	private int _id;
	private String _type;
	
	public MenuItem(int id, String type)
	{
		_id = id;
		_type = type;
	}
	
	public int getId() {
		return _id;
	}

	public String getType() {
		return _type;
	}
}
