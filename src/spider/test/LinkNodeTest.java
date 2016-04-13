package spider.test;


import java.util.List;

import spider.downloader.Downloader;
import spider.html.HtmlPage;
import spider.html.LinkNode;
import spider.parser.Parser;
import spider.scheduler.Request;
import spider.scheduler.RequestType;
import spider.util.UrlUtils;

public class LinkNodeTest {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Request request = new Request("https://www.zhihu.com/question/38360797")
					.setType(RequestType.Link);
		HtmlPage hp = Downloader.getInstance().download(request);
		Parser parser = Parser.getInstance();
		List<LinkNode> list = parser.extractAllNodeThatMatch(hp, LinkNode.class);
		System.out.println(list.size());
		for(LinkNode node : list){
			System.out.println(UrlUtils.canonicalizeUrl(node.getLink(), "http://www.zhihu.com"));
		}
		
	}

}
