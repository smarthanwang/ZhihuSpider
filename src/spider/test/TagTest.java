package spider.test;

import java.net.URL;
import java.util.ArrayList;

import spider.downloader.Downloader;
import spider.html.ImageNode;
import spider.html.Node;
import spider.parser.Parser;
import spider.util.Print;

public class TagTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL("http://www.zhihu.com/question/37006507");
		Parser p = new Parser(url, "utf-8");

		ArrayList<Node> nodes = p.extractAllNodeThatMatch(new ImageNode(""), ImageNode.pattern);
		ArrayList<ImageNode> links = new ArrayList<ImageNode>();
		for (Node n : nodes) {
			ImageNode imageNode = (ImageNode) n;
			if (!links.contains(imageNode)) {
				links.add(imageNode);
				Print.println(imageNode.getOriginalLink());
			}
		}
		Downloader downloader = new Downloader();
		int len;
		Print.println( len = links.size());
		
		for (int i = 0; i < len; i++) {
			Print.println(downloader.downloadFile(
					new URL(links.get(i).getOriginalLink()), links.get(i).getImageName()));
		}
	}

}
