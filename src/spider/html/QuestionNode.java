package spider.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionNode extends Node{
	private String question;
	private String content;
	private Pattern pattern = Pattern.compile("<h2 class=\"zm-item-title zm-editable-content\">([^<>]*)</h2>.*?<div class=\"zm-editable-content\">(.*?)</div>", 
							Pattern.DOTALL);
	
	public QuestionNode(){
		
	}
	public QuestionNode(String rawText){
		setRawText(rawText);

		Matcher matcher = pattern.matcher(rawText);
		while(matcher.find()){
			this.question = executeQuestionText(matcher.group(1)).trim();
			this.content  = executeQuestionText(matcher.group(2));
		}
	}
	private String executeQuestionText(String question) {

		question = question.replaceAll("<br>", "\n");
		question = question.replaceAll("</?[bu]>", "");
		question = question.replaceAll("</?p>", "\n\t");
		return question;
	}
	
	public QuestionNode create(String rawText){
		return new QuestionNode(rawText);
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


	public Pattern getPattern() {
		return pattern;
	}


	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public String toString(){
		return "QUESTION:\t" + this.question + "\nDESCRIPTION:\t" + this.content;
	}
	
}
