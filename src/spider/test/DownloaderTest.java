package spider.test;



import static spider.util.Print.*;

import java.net.URL;
import java.util.ArrayList;

import spider.downloader.Downloader;
import spider.html.HtmlPage;
import spider.html.LinkNode;
import spider.html.Node;
import spider.parser.Parser;


public class DownloaderTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Downloader downloader = new Downloader();
		HtmlPage hp = downloader.downloadHtmlPage(new URL("http://www.zhihu.com/question/36996703"), "utf-8");
		Parser parser = new Parser(hp);
		ArrayList<Node>  tags = parser.extractAllNodeThatMatch(new LinkNode(""), LinkNode.pattern);
		for(Node n: tags){
			//LinkNode ln = new LinkNode();
			println(n.toString());
		}
		
	}

}
