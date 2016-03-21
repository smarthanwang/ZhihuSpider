package spider.test;

import spider.downloader.Downloader;
import spider.html.HtmlPage;
import spider.parser.Parser;
import spider.scheduler.Request;

public class QuestionNodeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HtmlPage hp = Downloader.getInstance().download(new Request("https://www.zhihu.com/question/38360797"));
		Parser parser = new Parser();
		System.out.println(parser.extractQuestion(hp));
	}

}
