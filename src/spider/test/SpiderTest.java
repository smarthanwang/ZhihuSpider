package spider.test;

import spider.spider.Spider;

public class SpiderTest {

	public static void main(String args[]){
		new Spider()
		.thread(5)
		.addStartRequests("https://www.zhihu.com/topic/20022251")
		.start();;
	}
}
