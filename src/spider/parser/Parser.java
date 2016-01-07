package spider.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import spider.html.AnswerNode;
import spider.html.HtmlPage;
import spider.html.ImageNode;
import spider.html.LinkNode;
import spider.html.Node;
import spider.scheduler.Request;
import spider.scheduler.RequestType;
import spider.util.UrlUtils;

public class Parser {

	private HtmlPage htmlPage;

	public Parser(){
		
	}
	public Parser(HtmlPage hp) {
		this.htmlPage = hp;
	}
	

	public List<Node> extractAllNodeThatMatch(Node...  node) {

		ArrayList<Node> tags = new ArrayList<Node>();
		for(int i = 0; i < node.length; i ++){
			Matcher matcher = node[i].getPattern().matcher(htmlPage.getHtmlText());
			while (matcher.find()) {
				Node n = node[i].create(matcher.group());
				tags.add(n);
			}
		}

		return tags;
	}
	
	public List<Request> extractPageRequests(){
		List<Request> pageRequests = new ArrayList<>();
		for(Node node : extractAllNodeThatMatch(new LinkNode())){
			LinkNode linkNode = (LinkNode) node;
			Request r = new Request(UrlUtils.canonicalizeUrl(linkNode.getLink()));
			pageRequests.add(r);
		}
		return pageRequests;
	}
	
	public List<Request> extractImageRequests(){
		List<Request> imageRequests = new ArrayList<>();
		for(Node node : extractAllNodeThatMatch(new ImageNode())){
			ImageNode imageNode = (ImageNode) node;
			Request r = new Request(imageNode.getOriginalLink())
					.setType(RequestType.Image);
			imageRequests.add(r);
		}
		return imageRequests;
	}
	
	public List<String> extractAnswers(){
		List<String> answers = new ArrayList<String>();
		List<Node> nodes =  extractAllNodeThatMatch(new AnswerNode());
		for(Node node : nodes){
			AnswerNode an = (AnswerNode) node;
			answers.add(an.toString());
		}
		return answers;
	}


	public HtmlPage getHtmlPage() {
		return htmlPage;
	}


	public void setHtmlPage(HtmlPage htmlPage) {
		this.htmlPage = htmlPage;
	}

}
