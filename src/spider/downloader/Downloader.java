package spider.downloader;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import spider.html.HtmlPage;

public class Downloader {

	private String filePath =".\\Image\\";

	public HtmlPage downloadHtmlPage(URL url, String charset){

		HtmlPage hp = new HtmlPage(url, charset);
		try{
			InputStream in = configureURLConnection(url).getInputStream();
			StringBuilder sb = new StringBuilder();
			String line;

			BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			in.close();
			reader.close();
			hp.setHtmlText(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}

		return hp;
	}
	
	public boolean downloadFile(URL url, String fileName){
		 try{
			 InputStream in = configureURLConnection(url).getInputStream();
			 byte[] buffer = new byte[1024];
			 int len;
			 FileOutputStream fout = new FileOutputStream(filePath +fileName);
			 while((len = in.read(buffer)) > 0){
				 fout.write(buffer, 0, len);
				 fout.flush();
			 }
			 in.close();
			 fout.close();		
			 return true;
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return false;
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
