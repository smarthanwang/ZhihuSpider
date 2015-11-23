package spider.parser;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spider.downloader.Downloader;
import spider.html.HtmlPage;
import spider.html.Node;

public class Parser {

	private HtmlPage htmlPage;
	private String charSet ;


	public Parser(HtmlPage hp) {
		this.htmlPage = hp;
	}
	
	public Parser(URL url){
		if(charSet == null)
			setCharSet("");
		this.htmlPage = new Downloader().downloadHtmlPage(url, charSet);		
	}
	
	public Parser(URL url , String charSet){
		setCharSet(charSet);
		this.htmlPage = new Downloader().downloadHtmlPage(url, charSet);		

	}
	
	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public ArrayList<Node> extractAllNodeThatMatch(Node  node, Pattern p) {

		ArrayList<Node> tags = new ArrayList<Node>();
		
		Matcher matcher = p.matcher(htmlPage.getHtmlText());
		while (matcher.find()) {
			Node n = node.create(matcher.group());
			tags.add(n);
		}

		return tags;
	}

}
