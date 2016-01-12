package spider.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import spider.html.AnswerNode;
import spider.html.HtmlPage;
import spider.html.ImageNode;
import spider.html.LinkNode;
import spider.html.Node;
import spider.html.QuestionNode;
import spider.scheduler.Request;
import spider.scheduler.RequestType;
import spider.util.UrlUtils;

/**
 * 解析已经下载下来的页面，正则匹配想要的html标签，并解析出相应的链接
 * @author han
 */

public class Parser {


	public Parser(){
		
	}


	public List<Node> extractAllNodeThatMatch(HtmlPage htmlPage ,Node...  node) {

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
	
	public List<Request> extractPageRequests(HtmlPage htmlPage ){
		List<Request> pageRequests = new ArrayList<>();
		for(Node node : extractAllNodeThatMatch(htmlPage ,new LinkNode())){
			LinkNode linkNode = (LinkNode) node;
			Request r = new Request(UrlUtils.canonicalizeUrl(linkNode.getLink()));
			pageRequests.add(r);
		}
		return pageRequests;
	}
	
	public List<Request> extractImageRequests(HtmlPage htmlPage ){
		List<Request> imageRequests = new ArrayList<>();
		for(Node node : extractAllNodeThatMatch(htmlPage ,new ImageNode())){
			ImageNode imageNode = (ImageNode) node;
			Request r = new Request(imageNode.getOriginalLink())
					.setType(RequestType.Image);
			imageRequests.add(r);
		}
		return imageRequests;
	}
	
	public List<Request> extractAllRequests(HtmlPage htmlPage){
		List<Request> requests = new ArrayList<>();
		requests.addAll(extractPageRequests( htmlPage));
		requests.addAll(extractImageRequests(htmlPage));		
		return requests;
		
	}
	
	public String extractQuestion(HtmlPage htmlPage){
		QuestionNode questionNode = new QuestionNode();
		Matcher matcher = questionNode.getPattern().matcher(htmlPage.getHtmlText());
		while(matcher.find()){
			questionNode = questionNode.create(matcher.group());
		}
		return questionNode.toString();
	}
	
	public List<String> extractAnswers(HtmlPage htmlPage){
		List<String> answers = new ArrayList<String>();
		List<Node> nodes =  extractAllNodeThatMatch(htmlPage, new AnswerNode());
		for(Node node : nodes){
			AnswerNode an = (AnswerNode) node;
			answers.add(an.toString());
		}
		return answers;
	}


}
