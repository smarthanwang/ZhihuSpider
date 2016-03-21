package spider.main;

import spider.pipeline.FilePipeline;
import spider.spider.Spider;
public class ZhihuSpider {
	public static void main(String args[]){
		Spider zhihuSpider = new Spider();
		zhihuSpider.setHost("http://www.zhihu.com")
				   .setPipeline(new FilePipeline())
				   .thread(5)
				   .addStartRequests("https://www.zhihu.com/topic/20022251/top-answers",
						   "https://www.zhihu.com/topic/19552207/top-answers")
				   .start();;		
	}
}
