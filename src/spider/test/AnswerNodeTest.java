package spider.test;

import java.util.List;

import spider.downloader.Downloader;
import spider.html.AnswerNode;
import spider.html.HtmlPage;
import spider.html.Node;
import spider.parser.Parser;
import spider.scheduler.Request;
import spider.scheduler.RequestType;

public class AnswerNodeTest {

	public static void main(String[] args) {
		Request request = new Request("https://www.zhihu.com/question/38360797")
				.setType(RequestType.Link);
	HtmlPage hp = Downloader.getInstance().download(request);
	Parser parser = new Parser();
	List<Node> list = parser.extractAllNodeThatMatch(hp,new AnswerNode());
	System.out.println(list.size());
	for(Node node : list){
		//System.out.println(node.getPattern().toString());
		AnswerNode linkNode = (AnswerNode) node;
		System.out.println(linkNode);
	}
	
}
		
	

}
