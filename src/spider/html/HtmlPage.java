package spider.html;

import java.net.URL;

public class HtmlPage {

	private String url;
	private String htmlText;
	
	public HtmlPage(URL url, String htmlText){
		this.url =url.toString();
		this.htmlText = htmlText;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}
	
}
