/**
 * 
 */
package priv.lmoon.shadowsupdate.util;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.zip.ZipFile;

/**
 * @author LMoon
 * @date 2017年8月3日
 * 
 */
public final class CloseUtil {
	public static void closeSilently(Connection rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(Statement rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(ResultSet rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(InputStream rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(OutputStream rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(Closeable rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(ServerSocket rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(Socket rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(DatagramSocket rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(ZipFile rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}

	public static void closeSilently(Scanner rsc) {
		if (rsc != null) {
			try {
				rsc.close();
			} catch (Exception arg1) {
				;
			}

			rsc = null;
		}

	}
}
