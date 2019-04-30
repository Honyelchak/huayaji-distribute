package com.huayaji.util;

public class TNum {

	public static boolean isNullorZero(Long num)
	{
		return num==null||num==0;
	}
	public static boolean isNullorZero(Integer num)
	{
		return num==null||num==0;
	}
	public static boolean isNullorZero(Double num)
	{
		return num==null||num<=0.0;
	}
	public static boolean isNullorZero(Float num)
	{
		return num==null||num<=0.0;
	}
	public static Integer toInt(String statusstr) {
		if(TStr.isNullorEmpty(statusstr))
			return null;
		if(TStr.isInt(statusstr))
			return Integer.parseInt(statusstr);
		return null;
	}
}
