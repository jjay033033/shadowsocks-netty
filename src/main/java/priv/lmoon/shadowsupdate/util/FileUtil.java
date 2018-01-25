package priv.lmoon.shadowsupdate.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static String readFile(String pathname) {
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			// ./gui-config.json
			file = new File(pathname);
			if (file == null || !file.exists()) {
				return null;
			}
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "utf-8");
			br = new BufferedReader(isr);
			String buf = null;
			while ((buf = br.readLine()) != null) {
				sb.append(buf);
				sb.append("\n");
			}
		} catch (Exception e) {
			logger.error("readFile:", e);
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void writeFile(String content, String pathname) {
		File file = null;
		FileOutputStream fos = null;
		content.getBytes();
		try {
			// ./gui-config.json
			file = new File(pathname);
			if (file.exists()) {
				file.delete();
			}
			fos = new FileOutputStream(file);
			fos.write(content.getBytes("utf-8"));
			fos.flush();
		} catch (Exception e) {
			logger.error("writeFile:", e);
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeFileReplaceWord(String pathname,String oldWord,String newWord) {
		String oldContent = readFile(pathname);
		String newContent = oldContent.replace(oldWord, newWord);
		writeFile(newContent, pathname);
	}

	public static void main(String[] args) {
//		StringBuffer a = new StringBuffer("aeb");
//		a.append("\n");
//		a.append("ttt");
		String b = readFile("tt.txt");
		System.out.println(b);
		writeFile(b, "tt.txt");
	}
	
}
