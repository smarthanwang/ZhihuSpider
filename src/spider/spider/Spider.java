package spider.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spider.downloader.Downloader;
import spider.html.HtmlPage;

import spider.parser.Parser;
import spider.pipeline.ConsolePipeline;
import spider.pipeline.Pipeline;
import spider.scheduler.Request;
import spider.scheduler.Scheduler;

/**
 * Spider用来将各个部分功能聚合在一起，实现可自动抓取知乎数据的爬虫
 * @author han
 *
 */
public class Spider implements Runnable{
	private int threadNum = 1;
	private ExecutorService executorService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String host;
	private Downloader downloader;
	private Parser parser;
	private Scheduler scheduler;
	private Pipeline pipeline;
	private List<Request> startRequests = new ArrayList<Request>();
	private boolean completed = false ; 
	private AtomicInteger threadAlive = new AtomicInteger(0);

	private void init(){
		this.downloader = new Downloader();
		this.parser = new Parser();
		this.scheduler =new Scheduler();
		if(pipeline == null) pipeline = new ConsolePipeline();
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
			while(threadAlive.get() >= threadNum){
				try {
					TimeUnit.MILLISECONDS.sleep(47);
				} catch (InterruptedException e) {
					logger.info("Spider is interrupted!");
				}
			}
			threadAlive.incrementAndGet();
			if(request != null){
				completed = false;
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						processRequest(request);
						threadAlive.decrementAndGet();
					}
				});
			}
			else{
				if(completed) break;				
				waitNewUrl();
			}	
		}
		stop();		
	}
	public void processRequest(Request request){
	
		HtmlPage hp = downloader.download(request);
		if(hp == null ) return;
		
		List<Request> requests = parser.extractAllRequests(hp);
		for(Request r : requests){
			scheduler.push(r);
		}
		pipeline.Process(parser.extractAnswers(hp), parser.extractQuestion(hp));
		//long todo = scheduler.toDoSize(), total = scheduler.totalCount();
		//logger.info("Remains:" +todo + "\tTotal:" + total+ "\tProcessed:" +( total - todo));
													
	}
	public void  waitNewUrl(){
		completed = true;
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.warn("wait new url is interrupted :"+ e);
		}
	}
	
	public void start(){
		new Thread(this).start();
	}
	
	public void stop(){
		executorService.shutdown();
		logger.info("Spider Stop");
	}

	public String getHost() {
		return host;
	}

	public Spider setHost(String host) {
		this.host = host;
		return this;
	}

	public Spider setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
		return this;
	}

}
