package spider.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextNode extends Node{

	private String text;

	public static Pattern pattern = 
			Pattern.compile("<div class=\"zm-editable-content clearfix\">(.*?)</div>", Pattern.DOTALL);
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	private String executeAnswerText(String answer){
		
		answer =answer.replaceAll("<br>", "\n");
		answer =answer.replaceAll("</?[bu]>", "");
		answer = answer.replaceAll("</?p>", "\n");
		return answer;		
	}
	
	public TextNode (String rawText){
		super(rawText);
		Matcher m = pattern.matcher(rawText);
		while(m.find()){
			setText(executeAnswerText(m.group(1)));
		}
	}
	
	public TextNode create(String rawText){
		return new TextNode(rawText);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = 
				"TextNode" +"{text:"+ this.text+"}"+"{rawtext:"+ this.rawText+"}";
		return s;
	}
	
}
