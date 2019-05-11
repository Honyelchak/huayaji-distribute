package com.huayaji.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TStr {
	/**
	 * 判断字符串示范为空
	 * @param str
	 * @return
	 */
	public static boolean isNullorEmpty(String str) {
		if (str == null || str.length() == 0||str.trim().length()==0||str.trim().equals("undefined"))
			return true;
		return false;
	}
	/**
	 * 判断字符串示范不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNullorEmpty(String str) {
		return !isNullorEmpty(str);
	}

	/**
	 * 判断字符串是否为双整型数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (isNullorEmpty(str)) {
			return false;
		}
		Pattern p = Pattern.compile("-*\\d*.\\d*");
		return p.matcher(str).matches();
	}

	/**
	 * 判断字符串是否为整字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str) {
		if (isNullorEmpty(str)) {
			return false;
		}
		Pattern p = Pattern.compile("-*\\d*");
		return p.matcher(str).matches();
	}

	/**
	 * 判断是否为纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否为汉字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isGBK(String str) {
		char[] chars = str.toCharArray();
		boolean isGBK = false;
		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 2) {
				int[] ints = new int[2];
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;
				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
						&& ints[1] <= 0xFE) {
					isGBK = true;
					break;
				}
			}
		}
		return isGBK;
	}

	public static Long toLong(String str) {
		try{
			Long result= isNullorEmpty(str)?null:Long.parseLong(str);
			return result;
		}
		catch (Exception e) {
			System.out.println("转化字符串："+str+"为long失败！"+e.getMessage());
			return null;
		}
		
	}
	public static Integer toInt(String str) {
		try{
			Integer result= isNullorEmpty(str)?null:Integer.parseInt(str.trim());
			return result;
		}
		catch (Exception e) {
			System.out.println("转化字符串："+str+"为Int失败！"+e.getMessage());
			return null;
		}
	}
}
