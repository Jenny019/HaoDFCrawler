package utils;

import java.io.IOException;
import java.io.InputStream;

public class PhantomUtil {
	public static InputStream runPhantom(String url) {
		Runtime rt = Runtime.getRuntime();
		String exec = "phantomjs.exe code.js " + url + " --load-images=no --disk-cache=yes";
		Process p = null;
		InputStream is = null;
		try {
			p = rt.exec(exec);
			is = p.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}
}
