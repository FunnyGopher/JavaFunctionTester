package javafunctiontester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileResult {
	
	private File file;
	private List<Result> results = new ArrayList<Result>();
	private boolean pass;
	
	public FileResult(File file, List<Result> results) {
		this.file = file;
		this.results = results;
		this.pass = true;
		
		for(Result result : results) {
			if(!result.didPass())
				pass = false;
		}
	}
	
	public File getFile() {
		return file;
	}
	
	public List<Result> getResults() {
		return results;
	}
	
	public boolean getPass() {
		return pass;
	}
}
