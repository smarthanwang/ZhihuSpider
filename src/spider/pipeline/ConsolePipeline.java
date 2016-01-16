package spider.pipeline;

import java.util.List;

import spider.html.QuestionNode;


public class ConsolePipeline implements Pipeline {

	
	public void Process(List<String> list, QuestionNode question) {
		
		System.out.println(question);
		// TODO Auto-generated method stub
		for(String str : list){
			System.out.println(str);
		}		
	}

}
