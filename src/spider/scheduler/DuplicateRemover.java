package spider.scheduler;

import java.util.HashSet;
import java.util.Set;


public class DuplicateRemover {
	
	private Set<String>  urls = new HashSet<String>();
	
	public boolean isDuplicate(Request request){
		
		return !urls.add(request.getUrl());
	}
	/**
	 * 重置所有请求
	 */
    public void resetDuplicateCheck(){
        urls.clear();
    }
    /**
     * 获取所有请求数
     * @return
     */
    public int getTotalRequestsCount() {
        return urls.size();
    }

}
