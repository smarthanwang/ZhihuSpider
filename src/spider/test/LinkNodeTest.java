package spider.test;


import java.util.List;

import spider.downloader.Downloader;
import spider.html.HtmlPage;
import spider.html.LinkNode;
import spider.html.Node;
import spider.parser.Parser;
import spider.scheduler.Request;
import spider.scheduler.RequestType;
import spider.util.UrlUtils;

public class LinkNodeTest {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Request request = new Request("https://www.zhihu.com/question/38360797")
					.setType(RequestType.Link);
		HtmlPage hp = new Downloader().download(request);
		Parser parser = new Parser();
		List<Node> list = parser.extractAllNodeThatMatch(hp, new LinkNode());
		System.out.println(list.size());
		for(Node node : list){
			System.out.println(node.getPattern().toString());
			LinkNode linkNode = (LinkNode) node;
			System.out.println(UrlUtils.canonicalizeUrl(linkNode.getLink(), "http://www.zhihu.com"));
		}
		
	}

}
