package spider.test;

import java.util.List;

import spider.downloader.Downloader;
import spider.html.AnswerNode;
import spider.html.HtmlPage;
import spider.parser.Parser;
import spider.scheduler.Request;
import spider.scheduler.RequestType;

public class AnswerNodeTest{



	public static void main(String[] args) {
		Request request = new Request("https://www.zhihu.com/question/38360797")
				.setType(RequestType.Link);
	HtmlPage hp = Downloader.getInstance().download(request);
	Parser parser = Parser.getInstance();
	List<AnswerNode> list = parser.extractAllNodeThatMatch(hp,AnswerNode.class);
	System.out.println(list.size());
	for(AnswerNode node : list){
		//System.out.println(node.getPattern().toString());
		System.out.println(node);
	}
	
}
		
	

}
