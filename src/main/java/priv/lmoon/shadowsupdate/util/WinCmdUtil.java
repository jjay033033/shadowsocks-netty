package priv.lmoon.shadowsupdate.util;

import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WinCmdUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WinCmdUtil.class);
	
	private static Process process = null;
	
	public static Process getProcess(){
		return process;
	}
	
	public static boolean checkExeHasDone(String exeName) {
		try {
			Process pr = Runtime.getRuntime().exec("tasklist");
			Scanner in = new Scanner(pr.getInputStream());
			while (in.hasNextLine()) {
				String p = in.nextLine();
				System.out.println(p);
				if (p.contains(exeName)) {
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			logger.error("restartExe:", e);
			e.printStackTrace();
			return true;
		}
	}

	public static void restartExe(String pathname) {
		try {
			if (process != null) {
				process.destroy();
			}
			process = Runtime.getRuntime().exec("\"" + pathname + "\"");
		} catch (IOException e) {
			logger.error("restartExe:", e);
			e.printStackTrace();
		}
	}

	public static void kill(String exeName) {
		try {
			Process pr = Runtime.getRuntime().exec("tasklist");
			Scanner in = new Scanner(pr.getInputStream());
			while (in.hasNextLine()) {
				String p = in.nextLine();
				System.out.println(p);
				if (p.contains(exeName)) {
					StringBuffer buf = new StringBuffer();
					for (int i = 0; i < p.length(); i++) {
						char ch = p.charAt(i);
						if (ch != ' ') {
							buf.append(ch);
						}
					}
					System.out.println(buf.toString().split("Console")[0]
							.substring(exeName.length()));
					Runtime.getRuntime().exec(
							"tskill "
									+ buf.toString().split("Console")[0]
											.substring(exeName.length()));
				}
			}
		} catch (IOException e) {
			logger.error("kill:", e);
			e.printStackTrace();
		}
	}

	public static boolean isExistProcess(){
		if (process != null) {
			try {
				//执行process.exitValue()，若报异常，则表明shadowsocks.exe程序仍在运行
				process.exitValue();
				return false;
			} catch (Exception e) {
				return true;
			}
		}
		return true;
	}
	
}
