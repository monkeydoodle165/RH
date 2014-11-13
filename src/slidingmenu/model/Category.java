package slidingmenu.model;

public class Category {
	private int _id;
	private String _name;
	private String _parent;
	
	public Category(String _name, String _parent) {
        this(0, _name, _parent);
	}
	
	public Category(int _id, String _name, String _parent) {
		this._id = _id;
		this._name = _name;
		this._parent = _parent;
	}
	
	public int getId() {
		return _id;
	}
	public void setId(int _id) {
		this._id = _id;
	}
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	public String getParent() {
		return _parent;
	}
	public void setParent(String _parent) {
		this._parent = _parent;
	}
    public boolean isRootElement() {
        return "0".equals(getParent());
    }
}
