package spider.scheduler;

import java.util.LinkedList;

public class Scheduler {

	private LinkedList<Request> requestsQueue = new LinkedList<Request>();
	private DuplicateRemover duplicateRemover = new DuplicateRemover();
		
	/**
	 * 获取并移除队首request
	 * @return 队首request
	 */
	public  synchronized Request poll(){
		return requestsQueue.poll();
	}	
	/**
	 * request入队
	 * @param request
	 */
	public synchronized boolean push(Request request){
		if(duplicateRemover.isDuplicate(request)) return false;
		return requestsQueue.add(request);
	}
	

	
	public synchronized boolean isEmpty(){
		return requestsQueue.isEmpty();
	}
	/**
	 * @return 剩余的请求数
	 */
	public synchronized long toDoSize() {
		return requestsQueue.size();
	}
	/**
	 * @return 总的请求数
	 */
	public synchronized long totalCount(){
		return duplicateRemover.getTotalRequestsCount();
	}

	
}
