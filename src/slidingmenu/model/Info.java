package slidingmenu.model;

public class Info {
	//private variables
	private int _id;
	private int _catid;
	private String _title;
	private String _phNum;
	private String _email;
	private String _address;
	private String _postal;
	private String _fax;
	private String _web;
	private String _regions;
	private String _introtext;
	// Empty constructor
	public Info(){
		
	}
	// constructor
	public Info(int id, int catid, String title, String phNum, String email, String address, 
			String postal, String fax, String web, String regions, String introtext ){
		this._id = id;
		this._catid = catid;
		this._title = title;
		this._phNum = phNum;
		this._email = email;
		this._address = address;
		this._postal = postal;
		this._fax = fax;
		this._web = web;
		this._regions = regions;
		this._introtext = introtext;
	}
	
	// constructor
	public Info(int catid, String title, String phNum, String email, String address, 
			String postal, String fax, String web, String regions, String introtext ){
		this._catid = catid;
		this._title = title;
		this._phNum = phNum;
		this._email = email;
		this._address = address;
		this._postal = postal;
		this._fax = fax;
		this._web = web;
		this._regions = regions;
		this._introtext = introtext;
	}
	
	// getting id
		public int getID(){
			return this._id;
		}
		
		// setting id
		public void setID(int id){
			this._id = id;
		}
	// getting catid
	public int getCatid(){
		return this._catid;
	}
	
	// setting catid
	public void setCatid(int catid){
		this._catid = catid;
	}
	
	// getting tilte
	public String getTitle(){
		return this._title;
	}
	
	// setting title
	public void setTitle(String title){
		this._title = title;
	}
	

	public String getPhNum() {
		return _phNum;
	}
	public void setPhNum(String _phNum) {
		this._phNum = _phNum;
	}
	public String getEmail() {
		return _email;
	}
	public void setEmail(String _email) {
		this._email = _email;
	}
	public String getAddress() {
		return _address;
	}
	public void setAddress(String _address) {
		this._address = _address;
	}
	public String getPostal() {
		return _postal;
	}
	public void setPostal(String _postal) {
		this._postal = _postal;
	}
	public String getFax() {
		return _fax;
	}
	public void setFax(String _fax) {
		this._fax = _fax;
	}
	public String getWeb() {
		return _web;
	}
	public void setWeb(String _web) {
		this._web = _web;
	}
	public String getRegions() {
		return _regions;
	}
	public void setRegions(String _regions) {
		this._regions = _regions;
	}
	
	// getting introtext
	public String getIntrotext(){
		return this._introtext;
	}
				
	// setting introtext
	public void setIntrotext(String introtext){
		this._introtext = introtext;
	}
	
	

}
