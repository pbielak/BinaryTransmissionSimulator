package pwr.bts.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileBitInputStream extends ArrayBitInputStream {
	
	public FileBitInputStream(File file) throws IOException {
		super(Files.readAllBytes(file.toPath()));
	}
}
