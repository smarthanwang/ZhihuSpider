package spider.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkNode extends Node{
	
	private String link;

	private String linkText;
	
	private Pattern pattern = 
				Pattern.compile("<a[^<>]*?href=\"([^\"]*?/question/\\d+)\".*?>(.*?)</a>", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);

	public LinkNode(){
		
	}
	public  LinkNode(String rawText) {
		super(rawText);
		if(rawText != null){
			Matcher m = pattern.matcher(rawText);
			while(m.find()){
				this.link = m.group(1);
				this.linkText = m.group(2);
			}
		}		
	}
	
	public LinkNode create(String rawText){
		
		return new LinkNode(rawText);
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
		
	public String getLink() {
		return link;
	}

	public String getLinkText() {
		return linkText;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = 
				"LinkNode:\n" +"{link:"+ link+",\n"
						+ "linkText:"+linkText+",\n"+"rawText:"+rawText+"}";
		return s;
	}


}
