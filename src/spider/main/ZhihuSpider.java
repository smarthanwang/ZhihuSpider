package spider.main;

import spider.spider.Spider;
public class ZhihuSpider {
	public static void main(String args[]){
		Spider zhihuSpider = new Spider();
		zhihuSpider.setHost("http://www.zhihu.com")
				   .thread(5)
				   .addStartRequests("https://www.zhihu.com/topic/20022251","https://www.zhihu.com/topic/19552207/top-answers")
				   .start();;		
	}
}
