package priv.lmoon.shadowsupdate.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
 
@SuppressWarnings({"unchecked","rawtypes"})
public class XmlUtil {
	 
	/**
	 * 将xml字符串转成map对象
	 * @param xml -- xml字符串
	 * @return -- map对象
	 */
	public static Map toMap(String xml) {
		
		ByteArrayInputStream sin = new ByteArrayInputStream(xml.getBytes());
		return toMap(sin);
	}
	
	/**
	 * 将xml输入流转成map对象
	 * @param in -- xml输入流
	 * @return -- map对象
	 */
	public static Map toMap(InputStream in) {

		try {			
			SAXReader reader = new SAXReader();
			Document doc = reader.read(in);
			return toMap(doc.getRootElement());
		} catch (Exception ex) {
			throw new RuntimeException("解析xml成map对象异常", ex);
		} 
	}
	
	/**
	 * 将dom4j的Element对象转成map对象
	 * @param elem -- dom4j Element对象
	 * @return -- map对象
	 */
	public static Map toMap(Element elem) {
		 
		if (null == elem) {
			throw new IllegalArgumentException("无效XML Element 对象, 不能为null");
		}
		List<Element> elems = elem.elements();
		Map tar = new LinkedHashMap();
		for (Element item : elems) {
			generateMap(tar, item);
		}
		return tar;
	}
	 
	/**
	 * 嵌套调用, 将一个dom4j Element对象本身及其子节点转成map对象
	 * @param container -- 当前dom4j Element对应map对象容器
	 * @param elem -- dom4j Element对象
	 * @return -- map对象
	 */
	private static Map generateMap(Map container, Element elem) {
		
		String name = elem.getName();
		Object obj = container.get(name);
		// 处理存在多个相同名称元素的情况
		if (null != obj) {
			if (!List.class.isInstance(obj)) { // 已经存在相同节点,但是非List对象(此对象是List的第一个元素)
				List<Object> newBean = new LinkedList<Object>();
				newBean.add(obj);
				container.put(name, newBean);
				generateMap(container, elem);
			} else { // 已经存在相同节点,并且是List对象(说明当前elem非List的第一个元素)
				List<Object> bean = (List<Object>)obj;
				if (elem.isTextOnly()) {
					bean.add(elem.getStringValue());
				} else {
					List<Element> subs = elem.elements();
					Map nodes = new LinkedHashMap();
					bean.add(nodes);
					for (Element item : subs) {
						generateMap(nodes, item);
					}
				}
			}
			return container;
		}
		// 处理非多个相同名称元素的情况
		if (elem.isTextOnly()) { // 只是文本xml节点,没有子节点
			container.put(name, elem.getStringValue());
		} else { 
			List<Element> subs = elem.elements();
			Map nodes = new LinkedHashMap();
			container.put(name, nodes);
			for (Element item : subs) {
				generateMap(nodes, item);
			} 
		} 
		return container;
	}
		
}

