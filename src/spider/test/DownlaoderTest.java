package spider.test;


import spider.downloader.Downloader;
import spider.scheduler.Request;
import spider.scheduler.RequestType;;
public class DownlaoderTest {

	private Request request ;
	
	public void download() throws Exception{
		request= new Request("https://pic3.zhimg.com/3c750eaa32a4f9b525437e6b319ad07a_b.jpeg")
				.setType(RequestType.Image);
		Downloader.getInstance().download(request);
	}
	
	public static void main(String args[]) throws Exception{
		new DownlaoderTest().download();
	}
	
}
