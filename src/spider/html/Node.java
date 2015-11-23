package spider.html;


import java.util.regex.Pattern;

/**
 * 
 * @author han
 *
 */
public  class Node {

	public String rawText;


	public  static  Pattern pattern  = Pattern.compile("<.*?>[^<]*(</[^<>]*>)?");
	
	public Node(){
		
	}
	
	public Node(String rawText){
		this.rawText = rawText;
	}
	
	
	public void setRawText(String rawText) {
		this.rawText = rawText;
	}

	public String getRawText(){
		return this.rawText;
	}
	
	public Node create(String rawText){
		return new Node(rawText);
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = 
				"Node" +"{rawtext:"+ rawText+"}";
		return s;
	}
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		if(rawText != null? !rawText.equals(node.rawText):node.rawText != null) return false;
		return true;
	}

	public LinkNode createBySelf() {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkNode createNodeBySelf() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
