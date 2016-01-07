package spider.downloader;

import java.io.BufferedReader;
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
import spider.util.UrlUtils;
public class Downloader {

	private String filePath =".\\Image\\";
	private Logger logger = LoggerFactory.getLogger(getClass());
	
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
	
	private HtmlPage downloadHtmlPage(URL url, String charSet){
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
			return new HtmlPage(url, sb.toString());
		}catch(Exception e){
			logger.info("downloading page error: "+url);
			e.printStackTrace();
			return null;
		}
	}
	
	private void downloadFile(URL url, String fileName){
		
		 try{
			 InputStream in = configureURLConnection(url).getInputStream();
			 logger.info("downloading file: "+url);
			 byte[] buffer = new byte[1024];
			 int len;
			 FileOutputStream fout = new FileOutputStream(filePath +fileName);
			 while((len = in.read(buffer)) > 0){
				 fout.write(buffer, 0, len);
				 fout.flush();
			 }
			 in.close();
			 fout.close();		
		 }catch(Exception e){
			 logger.info("downloading file error: "+url);
			 e.printStackTrace();
		 }
	}

	private URLConnection configureURLConnection(URL url) {
		URLConnection uc = null;
		try {
			uc = url.openConnection();
			uc.setConnectTimeout(3000);
			uc.setReadTimeout(3000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return uc;
	}

}
