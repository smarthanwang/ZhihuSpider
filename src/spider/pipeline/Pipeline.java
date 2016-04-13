package spider.pipeline;

import java.util.List;

import spider.html.QuestionNode;


public interface Pipeline {

	public void process(List<String> list, QuestionNode question);
}
