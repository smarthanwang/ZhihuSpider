package spider.test;

import java.util.HashSet;

import spider.scheduler.Request;
import spider.util.FileUtils;

public class Test {

	public static void main(String[] args) throws Exception {
		
		Request request = new Request("www.google.com");
		HashSet<Request> rs = new HashSet<>();
		rs.add(request);
		
		System.out.println(rs.contains(new Request("www.google.com")));
		
		System.err.println(FileUtils.ANSWERPATH);
		System.err.println(FileUtils.IMAGEPATH);
	}

}
