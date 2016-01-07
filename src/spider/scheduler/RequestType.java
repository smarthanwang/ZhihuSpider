package spider.scheduler;

public enum RequestType {
	
	Image("ImageLink"),
	Link("HtmlLink");
	
	private String description; 
	private RequestType(String description){
		this.description =description;
	}
	
	public String toString(){
		return description;
	}
}
