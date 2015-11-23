package spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

	
	public static Pattern protocolPattern = Pattern.compile("\\w+://");
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
	
	
	private static Pattern hostPattern = Pattern.compile("\\w+://[^/]+");
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
	
	public String canonicalizeUrl(String link, String host){
		if(link.startsWith("/") || link.startsWith("?")){
			link = host + link;
		}
		if(link.startsWith("javascript:") || link.equals("#")){
			link = "";
		}
		return link;
	}
	
	
}
