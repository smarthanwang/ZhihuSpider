package spider.pipeline;

import java.io.File;

import java.io.PrintWriter;
import java.util.List;

import spider.html.QuestionNode;
import spider.util.FileUtils;


public class FilePipeline implements Pipeline {
	
	public  static final String FILEPATH =  FileUtils.ANSWERPATH; 
	static{
		File file =new File(FILEPATH);
		if(!file.exists()) file.mkdirs();
		
	}
	@Override
	public void Process(List<String	> list, QuestionNode question) {
		String filename =  question.getQuestion() ;
		if(filename == null ) return ;
		try {			
			PrintWriter printWriter = new PrintWriter(FILEPATH + FileUtils.validateFilename(filename) + ".txt", "utf-8");

			printWriter.print(question);
			printWriter.flush();
			for(String string : list){

				printWriter.println(string);
				printWriter.flush();
			}

			printWriter.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
}
