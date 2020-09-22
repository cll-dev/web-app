package com.yitongyin.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * 字符串相关工具类
 * 
 * @author ZCJ
 * @data 2011-12-22
 */
public class UtilString {
	/**
	 * 获取map中最大的key
	 * @param map
	 * @return
	 */
	public static Integer getMaxKey(Map<Integer, List<?>> map) {
		if (map == null){
			return null;
		}
		Set<Integer> set = map.keySet();
		Object[] obj = set.toArray();
		Arrays.sort(obj);
		return Integer.parseInt(obj[obj.length - 1].toString());
	}

	public static Map<String, String> toMap(List<String> list, String splite) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		if (null == list || list.isEmpty())
			return result;
		for (String str : list) {
			String[] temps = str.trim().split(splite);
			if (temps.length == 1) {
				result.put(temps[0].trim(), temps[0].trim());
			} else {
				result.put(temps[0].trim(), temps[1].trim());
			}
		}
		return result;
	}
	
	/** String通过英文逗号分隔符转换成List格式 */
	public static List<String> stringToList(String str) {
		return UtilString.stringToList(str, ",");
	}
	
	/** String通过指定分隔符splite转换成List格式 */
	public static List<String> stringToList(String str, String splite) {
		List<String> result = new ArrayList<String>();
		String[] temp = str.trim().split(splite);
		for (String s : temp) {
			if (StringUtils.isNotBlank(s)) {
				result.add(s);
			}
		}
		return result;
	}
	
	/** List格式转换成用英文逗号分隔的String */
	public static String listToString(List<String> list) {
		return UtilString.listToString(list, ",");
	}
	
	/** List格式转换成用指定分隔符splite分隔的String */
	public static String listToString(List<String> list, String splite) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (String a : list) {
			if (isFirst) {
				sb.append(a);
			} else {
				sb.append(splite);
				sb.append(a);
			}
			isFirst = false;
		}
		return sb.toString();
	}
	public static String join(final List<String> items,final String sperate,int size){
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for(String item:items){
			if(i++<size){
				sb.append("\""+item+"\""+sperate);
			}else{
				break;
			}
		}
		return sb.substring(0,sb.length()-sperate.length());
	}
	
	public static String join(final List<String> items){
		return join(items,",",items.size());
	}
	
	/**
	 * 网络地址对应的图片转为Base64编码数据
	 * @deprecated Linux下报错
	 * @param filrUrl
	 * @return
	 * @throws IOException
	 */
//	public static String filrUrlToBase64(String filrUrl) throws IOException {
//		URL url = new URL(filrUrl);
//		URLConnection urlConnection = url.openConnection();
//		InputStream is = urlConnection.getInputStream();
//
//		System.out.println(is.available());
//
//		byte[] buffer = new byte[1024];
//		int length = 0;
//		byte[] totalBuffer = new byte[1024*100];
//
//		for(int lastLength = 0; (length = is.read(buffer)) > 0;lastLength =  lastLength + length) {
//		    System.arraycopy(buffer, 0, totalBuffer, lastLength, length);
//		}
//
//		BASE64Encoder encoder = new BASE64Encoder();
//		return encoder.encode(totalBuffer);
//	}
	
	
}
