package com.yitongyin.modules.ad.form;

import java.util.*;

public class Config {
	/**
	 * 统一Sha1加密干扰串
	 */
	public static final String PWD_KEY = "7aoYQz";
	/**
	 * 清除当前时间之前多久的数据
	 */
	public static final int Log_Delete_Time = 1;
	/**
	 * 时间单位
	 */
	public static final int Log_Delete_Time_Unit = Calendar.DAY_OF_YEAR;
	
	public static final List<Module> ModuleList = new ArrayList<>();
	
	public static final Map<String, Module> ModuleMap = new TreeMap<>();
	
	public static final String TEXTAREA_BR = "&#13;&#10;";
	
	public static final String BLANK_SHOW = "—";
	
	public static final int NoticeLength = 5;
	
	public static  String WEB_PATH = null;
	
	public static  String WEB_INFO = null;
	
	public static String RESOURCE_DIR = "res";
	/**
	 * 存储资源地址
	 */
	public static  String WEB_RESOURCE = null;
	/**
	 * 表示一些无效状态时的值
	 */
	public static final Integer InvalidId = -1;
	
	public static final String Validate_NOTID = "noid";
	
	public static final String Validate_ALL = "all";
	
	public static final String Validate_UPDATE = "update";
	
	public static  String RES_NETPATH = null;
	
	public static boolean isTimerEnable = true;
	
	
}
