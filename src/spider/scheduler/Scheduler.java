package spider.scheduler;

import java.util.LinkedList;

public class Scheduler {

	private LinkedList<Request> requestsQueue = new LinkedList<Request>();
	private DuplicateRemover duplicateRemover = new DuplicateRemover();
		
	/**
	 * 获取并移除队首元素
	 * @return 队首元素
	 */
	public  synchronized Request poll(){
		return requestsQueue.poll();
	}	
	/**
	 * 元素入队
	 * @param request
	 */
	public synchronized boolean push(Request request){
		if(duplicateRemover.isDuplicate(request)) return false;
		return requestsQueue.add(request);
	}
	

	
	public synchronized boolean isEmpty(){
		return requestsQueue.isEmpty();
	}
	
	public synchronized long size() {
		return requestsQueue.size();
	}
	

	
}
