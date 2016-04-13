package spider.downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spider.html.HtmlPage;
import spider.scheduler.Request;
import spider.scheduler.RequestType;
import spider.util.FileUtils;
import spider.util.UrlUtils;
public class Downloader {

	private static  final String FILEPATH = FileUtils.IMAGEPATH;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static Downloader downloader = new Downloader();
	
	static{
		File file =new File(FILEPATH);
		if(!file.exists()) file.mkdirs();
	}
	
	private Downloader(){
		
	}
	
	public HtmlPage download(Request request){
		try {
			URL url = new URL(request.getUrl());
			if(request.getType().equals(RequestType.Link)){
				return downloadHtmlPage(url, request.getCharSet());
			}
			else {
				downloadFile(url, UrlUtils.getFileNameByURL(url.toExternalForm()));
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public HtmlPage downloadHtmlPage(URL url, String charSet){
		try{
			InputStream in = configureURLConnection(url).getInputStream();
			logger.info("downloading page: "+url);
			StringBuilder sb = new StringBuilder();
			String line;

			BufferedReader reader = new BufferedReader(new InputStreamReader(in, charSet));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			in.close();
			reader.close();
			return new HtmlPage(sb.toString());
		}catch(Exception e){
			logger.info("downloading page error: "+url);
			 System.out.println(e);
			return null;
		}
	}
	
	public  void downloadFile(URL url, String fileName){
		
		 try{
			 InputStream in = configureURLConnection(url).getInputStream();
			 logger.info("downloading image: "+url);
			 byte[] buffer = new byte[1024*4];
			 int len;
			 FileOutputStream fout = new FileOutputStream(FILEPATH +fileName);
			 while((len = in.read(buffer)) > 0){
				 fout.write(buffer, 0, len);
				 fout.flush();
			 }
			 in.close();
			 fout.close();		
		 }catch(Exception e){
			 logger.info("downloading file error: "+url);
			 System.out.println(e);
		 }
	}

	private URLConnection configureURLConnection(URL url) {
		URLConnection uc = null;
		try {
			uc = url.openConnection();
			uc.setConnectTimeout(3000);
			uc.setReadTimeout(3000);
			uc.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return uc;
	}
	
	/**
	 * 使用单例模式，创建Downloader实例
	 * @return downloder
	 */
	public static Downloader getInstance(){
		return downloader;
	}
	
}
