package spider.test;

import spider.downloader.Downloader;
import spider.html.HtmlPage;
import spider.parser.Parser;
import spider.pipeline.FilePipeline;
import spider.pipeline.Pipeline;
import spider.scheduler.Request;

public class PipelineTest {

	public static void main(String args[]){
		//Pipeline pipeline = new ConsolePipeline();
		Pipeline pipeline = new FilePipeline();
		HtmlPage hp = new Downloader().download(new Request("https://www.zhihu.com/question/38360797"));
		Parser parser = new Parser();
		pipeline.Process(parser.extractAnswers(hp), parser.extractQuestion(hp));
	}
}
