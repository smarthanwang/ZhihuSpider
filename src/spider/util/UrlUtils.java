package spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

	private static String host = "http://www.zhihu.com";
	private static Pattern protocolPattern = Pattern.compile("\\w+://");
	private static Pattern hostPattern = Pattern.compile("\\w+://[^/]+");
	
	
	public static String removeProtocol(String url){		
		return  protocolPattern.matcher(url).replaceAll("");				
	}
	
	/**
	 * @param url
	 * @return the domain which the url is belong to;
	 *  get domain by url;
	 */
	public static String getDomain(String url){
		url = removeProtocol(url);
		int i = url.indexOf('/');
		if(i > 0){
			url = url.substring(0, i);
		}
		
		return url;
	}
	
	public static String getFileNameByURL(String url){
		return url.substring(url.lastIndexOf('/')+1);
	}
	
	/**
	 * @param url
	 * @return the host which this url is belong to;
	 * get the host by url;
	 */
	public static String getHost(String url){
		
		Matcher m = hostPattern.matcher(url);
		while(m.find()){
			return  m.group();
		}
		return url;
	}
	
	public static String canonicalizeUrl(String link){
		if((link.startsWith("/") || link.startsWith("?"))){
			link = host + link;
		}
		return link;
	}
	public static String canonicalizeUrl(String link, String host){
		if((link.startsWith("/") || link.startsWith("?"))){
			link = host + link;
		}
		return link;
	}
	
	public static boolean isValid(String link){
		if(link.startsWith("javascript:") || link.equals("#") || link.equals("mailto:")){
			return false;
		}
		
		return true;
	}
	
}
