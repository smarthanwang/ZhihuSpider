package spider.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spider.downloader.Downloader;
import spider.html.HtmlPage;
import spider.html.ImageNode;
import spider.html.LinkNode;
import spider.html.Node;
import spider.parser.Parser;
import spider.scheduler.Request;
import spider.scheduler.RequestType;
import spider.scheduler.Scheduler;
import spider.util.UrlUtils;

public class Spider implements Runnable{
	private int threadNum = 1;
	private ExecutorService executorService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String host;
	private Downloader downloader;
	private Parser parser;
	private Scheduler scheduler;
	private List<Request> startRequests = new ArrayList<Request>();
	
	
	private void init(){
		this.downloader = new Downloader();
		this.parser = new Parser();
		this.scheduler =new Scheduler();
		if(host == null) host = "http://www.zhihu.com";
		if(executorService == null) executorService = Executors.newFixedThreadPool(threadNum);
		if(startRequests.size() > 0){
			for(Request r: startRequests){
				scheduler.push(r);
			}
			startRequests.clear();
		}
		
	}
	
	public Spider addStartRequests(String... requests){
		for(int i = 0; i < requests.length; i++){
			startRequests.add(new Request(requests[i]));
		}
		return this;
	}
	
	public Spider thread(int num){
		this.threadNum = num ;
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should be more than one!");
        }
		return this;
	}


	@Override
	public void run() {
		logger.info("Spider start!");
		init();
		while(true){
			Request request = scheduler.poll();
			if(request != null){
				executorService.execute(new Runnable() {
					//Downloader down = new Downloader();
					//Parser parser = new Parser();
					@Override
					public void run() {
						processRequest(request);
					}
				});
			}
	
		}
	}
	public void processRequest(Request request){
		HtmlPage hp = downloader.download(request);
		if(hp == null ) return;
		
		parser.setHtmlPage(hp);
		List<Node> list = parser.extractAllNodeThatMatch(new LinkNode(), new ImageNode());
		for(Node node : list){
			if(node instanceof LinkNode){
				LinkNode linkNode = (LinkNode) node;
				Request r = new Request(UrlUtils.canonicalizeUrl(linkNode.getLink(), host));
				scheduler.push(r);
				continue;
			}
			if(node instanceof ImageNode){
				ImageNode imageNode = (ImageNode) node;
				Request r = new Request(imageNode.getOriginalLink())
						.setType(RequestType.Image);
				scheduler.push(r);
			}
		}
		
		/*for(String answer : parser.extractAnswers()){
			System.out.println(answer);
		}*/
	}
	
	public void start(){
		new Thread(this).start();
	}

	public String getHost() {
		return host;
	}

	public Spider setHost(String host) {
		this.host = host;
		return this;
	}

}
