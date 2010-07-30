package org.teamlet.jartools.ui;

import java.util.HashMap;
import java.util.Map;

/**
 * 界面的上下文环境，缓存对象。窗体、动作需要的其他对象从这里获取。
 * 
 * @author <a href="mailto:teamlet@gmail.com">David</a>
 * 
 */
public class GUIContext {

	private static Map<Object, Object> cache = new HashMap<Object, Object>();

	public static void put(Object key, Object value) {
		cache.put(key, value);
	}

	public static Object get(Object key) {
		return cache.get(key);
	}
}
