package spider.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageNode extends Node {

	private String link;
	private String originalLink;
	private String imageName;


	private Pattern pattern = 
			Pattern.compile("<img[^<>]+src=\"(.*?)\".*?data-original=\"(.*?)\".*?>", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
	
	
	public ImageNode(){
		
	}
	public String getImageName() {
		return imageName;
	}
	public String getLink() {
		return this.link;
	}
	public String getOriginalLink() {
		return this.originalLink;
	}
	
	public  ImageNode (String rawText) {
		super(rawText);
		Matcher m = pattern.matcher(rawText);
		while(m.find()){
			this.link = m.group(1);
			this.originalLink = m.group(2);
			this.imageName =originalLink.substring(originalLink.lastIndexOf('/'));
		}		
	}
	
	public ImageNode create(String rawText){
		return new ImageNode(rawText);
	}
	
	
	public String toString() {
		// TODO Auto-generated method stub
		String s = 
				"ImageNode:\n" +"{link:"+ link+",\n"
						+ "originalLink:"+originalLink+",\n"+"rawText:"+rawText+"}";
		return s;
	}
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		ImageNode node = (ImageNode) o;
		if(originalLink != null? !originalLink.equals(node.originalLink):node.originalLink != null) return false;
		return true;
	}
	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
		
	
	
}
