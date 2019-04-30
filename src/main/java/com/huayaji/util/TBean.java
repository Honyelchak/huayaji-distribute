package com.huayaji.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TBean {

	/**
	 * copy有相同名字字段的两个bean
	 * @param copybean 源bean
	 * @param targetBean  目标bean
	 * @return  目标bean
	 * @throws Exception
	 */
	public static <TargetBean,SourceBean> TargetBean beanCopy(SourceBean sourceBean,TargetBean targetBean) throws Exception {
//		Field[] copyfields=copybean.getClass().getDeclaredFields();
//		Field[] targetfields=targetBean.getClass().getDeclaredFields();
//		for (Field targetfield : targetfields) {
//			for (Field copyfield : copyfields) {
//				if(copyfield.getName().equals(targetfield.getName()))
//				{
//					copyfield.setAccessible(true);
//					Object value=copyfield.get(copybean);
//					targetfield.setAccessible(true);
//					
//					targetfield.set(targetfield, value);
//				}
//			}
//		}
		//上述方法有类型转换问题，直接用spring的BeanUtil
		BeanUtils.copyProperties(sourceBean, targetBean);
		return targetBean;
	}

	/**
	 *  获取实体类的字段
	 * @param pojo 实体类
	 * @param ishassuperclass 是否包含父类字段默认false
	 * @return
	 */
	public  static List<Field> getBeanDeclaredFields(Class pojo,boolean ishassuperclass)
    {
        List<Field> fields=new ArrayList<>();
        while (pojo!=null)
        {
            fields.addAll(Arrays.asList(pojo.getDeclaredFields()));
            if(ishassuperclass)
               pojo=pojo.getSuperclass();
            else
            	pojo=null;
        }
        return  fields;
    }
}
