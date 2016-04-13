package spider.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnswerNode extends Node {

	private String authorLink;
	private String author;
	private String votersCount;
	private String content;

	private Pattern pattern = Pattern.compile(
			"(<a class=\"author-link\".*?href=\"(.*?)\">([^<>]*)</a>.*?)?data-votecount=\"(.*?)\".*?<div class=\"zm-editable-content clearfix\">(.*?)</div>",
			Pattern.DOTALL);

	public String getText() {
		return content;
	}

	public void setText(String text) {
		this.content = text;
	}

	private String executeAnswerText(String answer) {

		answer = answer.replaceAll("<br>", "\n");
		answer = answer.replaceAll("</?[bu]>", "");
		answer = answer.replaceAll("</?p>", "\n\t");
		answer = answer.replaceAll("<.*>", "");
		return answer;
	}
	public AnswerNode(){
		
	}
	
	public AnswerNode(String rawText) {
		setRawText(rawText);
		
		Matcher m = pattern.matcher(rawText);
		while (m.find()) {
			this.authorLink = m.group(2);
			this.author = m.group(3);
			if(author == null) author ="ƒ‰√˚”√ªß";
			this.votersCount = m.group(4);
			this.content = executeAnswerText(m.group(5));
		}
	}

	public AnswerNode create(String rawText) {
		return new AnswerNode(rawText);
	}

	public String toString() {

		return "author:" + this.author+ "\nauthor-link:"+ this.authorLink +"\nvoters:" + this.votersCount + "\ncontent£∫" + this.content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVotersCount() {
		return votersCount;
	}

	public void setVotersCount(String votersCount) {
		this.votersCount = votersCount;
	}


	public String getAuthorLink() {
		return authorLink;
	}

	public void setAuthorLink(String authorLink) {
		this.authorLink = authorLink;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

}
