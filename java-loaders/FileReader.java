import java.nio.file.Files;
import java.nio.charset.Charset;
import java.io.IOException;
import java.nio.file.Paths;

class FileReader {

	static String readFile(String path, Charset encoding) 
 	 throws IOException 
	{
  		byte[] encoded = Files.readAllBytes(Paths.get(path));
  		return new String(encoded, encoding);
	}

}
