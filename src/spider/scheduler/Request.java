package spider.scheduler;

public class Request {

	private String url;
	private RequestType type;
	private String charSet;
	
	
	public Request(String url){

		this.url = url;
		this.charSet = "utf-8";
		this.type =RequestType.Link;
	}
	public String getUrl() {
		return url;
	}
	public Request setUrl(String url) {
		this.url = url;
		return this;
	}
	public RequestType getType() {
		return type;
	}
	public Request setType(RequestType type) {
		this.type = type;
		return this;
	}
	public String getCharSet() {
		return charSet;
	}
	public Request setCharSet(String charSet) {
		this.charSet = charSet;
		return this;
	}
	
	public boolean equals(Object o){
		if(this == o ) return true;
		if(o != null && getClass() != o.getClass()) return false;
		Request oTask = (Request) o;
		if(url != null ? !url.equals(oTask.getUrl()): oTask.getUrl() != null) return false;
		//if(type != null ? !type.equals(oTask.getType()) : oTask.getType() != null) return false;
		return true;
	}
	
	public String toString(){
		return "Reqest[url: " + url +" type: " +type +" charSet: "+charSet+" ]";
	}
	

}
