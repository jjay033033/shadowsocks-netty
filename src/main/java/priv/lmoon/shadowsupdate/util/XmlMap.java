package priv.lmoon.shadowsupdate.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import priv.lmoon.shadowsupdate.util.XmlUtil;

public class XmlMap {

	private Map map;
	
	private String path;

	public XmlMap(String path) throws FileNotFoundException {
		this.path = path;
		init();
	}

	private void init() throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(new File(path));
		map = XmlUtil.toMap(fis);
	}

	public Map getConfigMap() throws FileNotFoundException {
		if (map == null) {
			init();
		}
		return map;
	}
	
}
