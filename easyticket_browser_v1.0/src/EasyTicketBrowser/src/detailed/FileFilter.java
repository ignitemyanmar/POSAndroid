package EasyTicketBrowser.src.detailed;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter{

	private String fileExtension;

	public FileFilter(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Override
	public boolean accept(File directory, String fileName) {
		return (fileName.endsWith(this.fileExtension));
	}
}



