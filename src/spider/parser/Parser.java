package spider.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import spider.html.AnswerNode;
import spider.html.HtmlPage;
import spider.html.ImageNode;
import spider.html.LinkNode;
import spider.html.QuestionNode;
import spider.scheduler.Request;
import spider.scheduler.RequestType;
import spider.util.UrlUtils;

/**
 * 解析已经下载下来的页面，正则匹配想要的html标签，并解析出相应的链接
 * @author han
 */

public class Parser {
	
	private Logger logger = Logger.getLogger(getClass());

	private  Parser(){
		
	}
	
	public static Parser getInstance() {
		return new Parser();
	}

	public  <T> List<T> extractAllNodeThatMatch(HtmlPage htmlPage ,Class<T> c) {
		ArrayList<T> nodes = new ArrayList<T>();
		try {

			Object node = c.newInstance();
			Field patternF = c.getDeclaredField("pattern");
			patternF.setAccessible(true);
			Pattern pattern = (Pattern) patternF.get(node);

			Method method = c.getDeclaredMethod("create", String.class);			
			Matcher matcher = pattern.matcher(htmlPage.getHtmlText());
			while (matcher.find()) {
				try{
					@SuppressWarnings("unchecked")
					T n = (T) method.invoke(node, matcher.group());
					nodes.add(n);
				}catch (Exception e) {
					logger.error("Parser work error :"+e.getMessage());

				}
			}			
			return nodes;			
		} catch (Exception e) {
			logger.error("Parser work error :" +e.getMessage());
			return nodes;
		}
		

	}
	
	public List<Request> extractPageRequests(HtmlPage htmlPage ){
		List<Request> pageRequests = new ArrayList<>();
		for(LinkNode node : extractAllNodeThatMatch(htmlPage ,LinkNode.class)){
			Request r = new Request(UrlUtils.canonicalizeUrl(node.getLink()));
			pageRequests.add(r);
		}
		return pageRequests;
	}
	
	public List<Request> extractImageRequests(HtmlPage htmlPage ){
		List<Request> imageRequests = new ArrayList<>();
		for(ImageNode node : extractAllNodeThatMatch(htmlPage ,ImageNode.class)){
			Request r = new Request(node.getOriginalLink()).setType(RequestType.Image);
			imageRequests.add(r);
		}
		return imageRequests;
	}
	
	public void process(HtmlPage htmlPage){
		
		htmlPage.addAllRequests(extractAllRequests(htmlPage));
		htmlPage.setQuestion(extractQuestion(htmlPage));
		if(htmlPage.getQuestion() == null ){
			htmlPage.setSkip(true);//跳过
			return;
		}
		htmlPage.addAllAnswers(extractAnswers(htmlPage));
 		
	}
	
	public List<Request> extractAllRequests(HtmlPage htmlPage){
		List<Request> requests = new ArrayList<>();
		requests.addAll(extractPageRequests( htmlPage));
		requests.addAll(extractImageRequests(htmlPage));		
		return requests;
		
	}
	
	public QuestionNode extractQuestion(HtmlPage htmlPage){
		List<QuestionNode> list;
		return ( list =extractAllNodeThatMatch(htmlPage, QuestionNode.class)).size() > 0 ? list.get(0) : null;
	}
	
	
	public List<String> extractAnswers(HtmlPage htmlPage){
		List<String> answers = new ArrayList<String>();
		List<AnswerNode> nodes =  extractAllNodeThatMatch(htmlPage, AnswerNode.class);
		for(AnswerNode node : nodes){
			answers.add(node.toString());
		}
		return answers;
	}


}
