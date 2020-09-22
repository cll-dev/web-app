package com.yitongyin.modules.ad.msg;

import com.yitongyin.common.utils.UtilString;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesHelper {

	private final Properties properties;

	private static final Pattern INTEGER_PATTERN = Pattern.compile("^\\d+$");
	private static final Pattern NUMBER_PATTERN = Pattern
			.compile("[^0]\\d*.\\d+$");


	static Map<String, PropertiesHelper> HELPERS = new HashMap<String, PropertiesHelper>();

	/**
	 * 解析properties文件的某一串，赋值到Map里
	 * @param helperKey	conf/global/xml/properties.xml文件中helper标签里的key值
	 * @param key helperKey对应的值的路径所对应的文件中的key值
	 * @param splite key对应的值中的第一层分隔符
	 * @param splite2 key对应的值中的第二层分隔符
	 * @return
	 */
	public static Map<String, String> getMapByKey(String helperKey, String key, String splite, String splite2) {
		return UtilString.toMap(PropertiesHelper.getHelper(helperKey).getStringList(key, splite), splite2);
	}
	
	
	public static String getValueByKey(String helperKey, String key, String splite, String splite2, String key2) {
		Map<String, String> map = PropertiesHelper.getMapByKey(helperKey, key, splite, splite2);
		return map.get(key2);
	}
	
	public static PropertiesHelper crateHelper(String path) {
		PropertiesHelper helper;
		try {
			helper = new PropertiesHelper(Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(path));
			return helper;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PropertiesHelper getHelper(String key){
		return HELPERS.get(key);
	}

	private PropertiesHelper(InputStream in) throws IOException {
		properties = new Properties();
		properties.load(new BufferedReader(new InputStreamReader(in,"UTF-8")));
	}

	private PropertiesHelper(Reader reader) throws IOException {
		properties = new Properties();
		properties.load(reader);
	}
	/**
	 * 
	 * @param search  例子: mod\\.\\S+
	 * @return
	 */
	public Set<String> getSet(final String search){
		Set<String> keys = new HashSet<String>();
		Set<String> alls = properties.stringPropertyNames();
		Pattern pattern = Pattern.compile(search);
		for(String key:alls){
			Matcher matcher = pattern.matcher(key);
			while(matcher.find()){
				keys.add(matcher.group());
			}
		}
		return keys;
	}

	public String getString(final String key) {
		String value = properties.getProperty(key);
		if (StringUtils.isNotEmpty(value)) {
			value = value.trim();
		}
		return value;
	}

	public String getString(final String key, final String defaultValue) {
		String value = getString(key);
		if (StringUtils.isEmpty(value)) {
			value = defaultValue;
		}
		return value;
	}

	public Long getLong(final String key) {
		return parseLong(getString(key));
	}

	public Long parseLong(final String value) {
		if (StringUtils.isNotEmpty(value)) {
			Matcher matcher = INTEGER_PATTERN.matcher(value);
			if (matcher.matches()) {
				return Long.parseLong(value);
			}
		}
		return null;
	}

	public Long getLong(final String key, final Long defaultValue) {
		Long value = getLong(key);
		if (null == value) {
			value = defaultValue;
		}
		return value;
	}

	public Integer getInt(final String key) {
		return parseInt(getString(key));
	}

	public Integer parseInt(final String value) {
		if (null == value) {
			return null;
		}
		return (int) (long) parseLong(value);
	}

	public Integer getInt(final String key, final Integer defaultValue) {
		Integer value = getInt(key);
		if (null == value) {
			value = defaultValue;
		}
		return value;
	}

	public Boolean getBoolean(final String key) {
		String value = getString(key);
		if (null == value) {
			return null;
		}
		return parseBoolean(value);
	}

	private Boolean parseBoolean(final String value) {
		if ("true".equalsIgnoreCase(value)) {
			return Boolean.parseBoolean(value);
		} else if ("false".equalsIgnoreCase(value)) {
			return Boolean.parseBoolean(value);
		} else {
			return null;
		}
	}

	public Boolean getBoolean(final String key, final Boolean defaultValue) {
		Boolean value = getBoolean(key);
		if (null == value) {
			value = defaultValue;
		}
		return value;
	}

	public Double getDouble(final String key) {
		return parseDouble(getString(key));
	}

	private Double parseDouble(final String value) {
		if (null == value) {
			return null;
		}
		Matcher matcher = NUMBER_PATTERN.matcher(value);
		if (matcher.matches()) {
			return Double.parseDouble(value);
		}
		return null;
	}

	public Double getDouble(final String key, final Double defaultValue) {
		Double value = getDouble(key);
		if (null == value) {
			value = defaultValue;
		}
		return value;
	}

	public List<String> getStringList(final String key, final String split) {
		String value = getString(key);
		String[] temps = value.split(split);
		List<String> list = new LinkedList<String>();
		for (String temp : temps) {
			if (StringUtils.isNotEmpty(temp)
					&& StringUtils.isNotEmpty(temp.trim())) {
				list.add(temp);
			}
		}
		return list;
	}

	public List<Integer> getIntList(final String key, final String split) {
		String value = getString(key);
		String[] temps = value.split(split);
		List<Integer> list = new LinkedList<Integer>();
		for (String temp : temps) {
			if (StringUtils.isNotEmpty(temp)
					&& StringUtils.isNotEmpty(temp.trim())) {
				list.add(parseInt(temp));
			}
		}
		return list;
	}

	public List<Long> getLongList(final String key, final String split) {
		String value = getString(key);
		String[] temps = value.split(split);
		List<Long> list = new LinkedList<Long>();
		for (String temp : temps) {
			if (StringUtils.isNotEmpty(temp)
					&& StringUtils.isNotEmpty(temp.trim())) {
				list.add(parseLong(temp));
			}
		}
		return list;
	}

	public List<Boolean> getBolList(final String key, final String split) {
		String value = getString(key);
		String[] temps = value.split(split);
		List<Boolean> list = new LinkedList<Boolean>();
		for (String temp : temps) {
			if (StringUtils.isNotEmpty(temp)
					&& StringUtils.isNotEmpty(temp.trim())) {
				list.add(parseBoolean(temp));
			}
		}
		return list;
	}

	public List<Double> getDoubleList(final String key, final String split) {
		String value = getString(key);
		String[] temps = value.split(split);
		List<Double> list = new LinkedList<Double>();
		for (String temp : temps) {
			if (StringUtils.isNotEmpty(temp)
					&& StringUtils.isNotEmpty(temp.trim())) {
				list.add(parseDouble(temp));
			}
		}
		return list;
	}

	public Properties getProperties() {
		return properties;
	}
	
}

class PropertiesHandler extends DefaultHandler {
	private String key;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("helper".equalsIgnoreCase(qName)) {
			key = attributes.getValue("key").trim();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (null != key) {
			String classPath = new String(ch).substring(start, start + length)
					.trim();
			PropertiesHelper helper = PropertiesHelper.crateHelper(classPath);
			PropertiesHelper.HELPERS.put(key, helper);
			key = null;
		}
	}

}
