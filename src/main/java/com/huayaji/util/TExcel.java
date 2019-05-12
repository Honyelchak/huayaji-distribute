package com.huayaji.util;

import com.huayaji.entity.Product;
import com.huayaji.entity.TemporarySing;
import com.huayaji.entity.User;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;*/

public class TExcel {

	private static Logger log=LoggerFactory.getLogger(TExcel.class);
	public static void main(String[] args) throws IOException,
			InvalidFormatException {
		String path = TExcel.class.getResource(
				"/ModelData/附件1：农产品加工主要行业经济情况表.xls").getPath();
		Workbook workbook = getWorkbook(path);
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(6);
		Cell cell = row.getCell(0);
		cell.setCellValue("test");
		System.out.println(cell.getStringCellValue());
		String name = "d:/test.xls";
		saveWorkbook(workbook, name);
	}

	public static Workbook getWorkbok(InputStream in,File file) throws IOException{
		Workbook wb = null;
		if(file.getName().endsWith(".xls")){  //Excel 2003
			wb = new HSSFWorkbook(in);
		}else if(file.getName().endsWith(".xlsx")){  // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}
	public static void saveWorkbook(Workbook workbook, String name)
			throws FileNotFoundException, IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(name);
		workbook.write(fileOutputStream);
		fileOutputStream.close();
	}

	public static Workbook getWorkbook(String realpath)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException, InvalidFormatException {
		realpath = URLDecoder.decode(realpath, "utf-8");
		// System.out.println(realpath);
		InputStream inputStream = new FileInputStream(realpath);
		Workbook workbook = WorkbookFactory.create(inputStream);
		return workbook;
	}

	public static void setCellValue(Workbook workbook, int sheetindex,
									int rowindex, int cellindex, Object value,
									boolean... borderandbackground) {
		Sheet sheet = workbook.getSheetAt(sheetindex);
		Row row = sheet.getRow(rowindex);
		if (row == null)
			row = sheet.createRow(rowindex);
		Cell cell = row.getCell(cellindex);
		if (cell == null)
			cell = row.createCell(cellindex);
		CellStyle cellStyle = workbook.createCellStyle();
		if (borderandbackground.length == 2 && borderandbackground[1]) {
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE
					.getIndex());
			cellStyle.setFillPattern(FillPatternType.THIN_BACKWARD_DIAG);
		}
		if (borderandbackground.length >= 1 && borderandbackground[0]) {
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
		}
		if (cell != null) {
			if (borderandbackground != null)
				cell.setCellStyle(cellStyle);
			String type = value.getClass().toString();
			// System.out.println(type);
			if (type.equals("class java.lang.String"))
				cell.setCellValue((String) value);
			else if (type.equals("double")
					|| type.equals("class java.lang.Double")) {
				cell.setCellValue(Double.parseDouble(value.toString()));
			} else if (type.equals("int")
					|| type.equals("class java.lang.Integer")) {
				cell.setCellValue(Integer.parseInt(value.toString()));
			} else if (type.equals("class java.util.Date")) {
				try {
					cell.setCellValue((new SimpleDateFormat().parse(value
							.toString())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 导出多个实体集合到Excel数据 potoClasses和datasets两个数组的长度必须匹配
	 *
	 * @param title
	 *            工作表名字
	 * @param pojoClasses
	 *            实体Class数组
	 * @param datasets
	 *            List数组
	 * @param out
	 */
	public static <E> void exportExcelMutilEntity(String title,
												  Class<E>[] pojoClasses, List<E>[] datasets, OutputStream out) {
		try {
			if (datasets == null || datasets.length == 0) {
				throw new RuntimeException("导出数据为空！");
			}
			if (title == null || out == null || pojoClasses == null) {
				throw new RuntimeException("传入数据错误！");
			}
			// 声明一个工作薄
			//Workbook workbook = new HSSFWorkbook();
			//防止内存溢出
			Workbook workbook = new SXSSFWorkbook(100);
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);
			// 工作表的标题
			List<String> exportFieldTitle = new ArrayList<String>();
			// 工作表中每个标题的宽度
			List<Integer> exportFieldWidth = new ArrayList<Integer>();
			Map<String, List<Method>> map=new HashMap<String, List<Method>>();
			int[] pojoColumns = new int[pojoClasses.length];
			for (int k = 0; k < pojoClasses.length; k++) {
				// 拿到所有列名，以及导出的字段的get方法
				List<Method> methodObj = new ArrayList<Method>();
				// 得到所有字段
				Field fileds[] = pojoClasses[k].getDeclaredFields();
				pojoColumns[k] = 0;
				// 遍历整个filed
				for (int i = 0; i < fileds.length; i++) {
					Field field = fileds[i];
					BeanNote excel = field.getAnnotation(BeanNote.class);
					// 如果设置了annottion
					if (excel != null) {
						// 添加到标题
						exportFieldTitle.add(excel.name());
						// 添加到需要导出的字段的方法
						String fieldname = field.getName();
						// System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
						StringBuffer getMethodName = new StringBuffer("get");
						getMethodName.append(fieldname.substring(0, 1)
								.toUpperCase());
						getMethodName.append(fieldname.substring(1));

						Method getMethod = pojoClasses[k].getMethod(
								getMethodName.toString(), new Class[] {});

						methodObj.add(getMethod);
						pojoColumns[k]++;
					}
				}
				map.put(pojoClasses[k].getName(), methodObj);
			}
			int index = 0;
			// 产生表格标题行
			Row row = sheet.createRow(index);
			// 遍历设置工作表中的标题
			for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
				Cell cell = row.createCell(i);
				// cell.setCellStyle(style);
				/*RichTextString text = new HSSFRichTextString(
						exportFieldTitle.get(i));*/
				RichTextString text = new XSSFRichTextString(
						exportFieldTitle.get(i));
				cell.setCellValue(text);
			}

			// 设置每行的列宽
			for (int i = 0; i < exportFieldWidth.size(); i++) {
				// 256=65280/255
				sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
			}
			List<E> dataset = datasets[0];
			// 循环插入剩下的集合
			for(int j=0;j<dataset.size();j++) {
				// 从第二行开始写，第一行是标题
				index++;
				row = sheet.createRow(index);
				Object t = dataset.get(j);
				int colindex=0;
				for (int i = 0; i < pojoClasses.length; i++) {
					List<Method> methodObj=map.get(pojoClasses[i].getName());
					if(datasets[i]!=null)
						t=datasets[i].get(j);
					if(t==null) continue;
					for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
						Cell cell = null;
						if(i==0)
							cell=row.createCell(k);
						else {
							cell=row.createCell(colindex+k);
						}
						Method getMethod = methodObj.get(k);
						Object value = null;
						value = getMethod.invoke(t, new Object[] {});
						if(value instanceof Long)
						{
							Long longvalue=(Long)value;
							BeanNote annotation = getMethod.getAnnotation(BeanNote.class);
							if(annotation!=null&&!TStr.isNullorEmpty(annotation.format()))
							{
								if(annotation.format().equals("yyyy-MM-dd HH:mm:ss"))
									value=TDate.getDateTimeStr(longvalue);
								if(annotation.format().equals("yyyy-MM-dd"))
									value=TDate.getDateStr(longvalue);
								if(annotation.format().equals("HH:mm:ss"))
									value=TDate.getTimeStr(longvalue);
							}
						}
						setCellValue(cell, value);

					}
					colindex+=(methodObj.size());
				}
			}
			workbook.write(out);
		} catch (Exception e) {
			System.out.println("多实体导出到excel失败"+e.getMessage());
			e.printStackTrace();
		}
	}

	public static <E> void exportExcel(String title, Class<E> pojoClass,
									   Collection<E> dataset, OutputStream out) {
		try {
			if (dataset == null || dataset.size() == 0) {
				throw new RuntimeException("导出数据为空！");
			}
			if (title == null || out == null || pojoClass == null) {
				throw new RuntimeException("传入数据错误！");
			}
			// 声明一个工作薄
			//Workbook workbook = new HSSFWorkbook();
			//防止内存溢出
			Workbook workbook = new SXSSFWorkbook(100);
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);
			// 工作表的标题
			List<String> exportFieldTitle = new ArrayList<String>();
			// 工作表中每个标题的宽度
			List<Integer> exportFieldWidth = new ArrayList<Integer>();
			// 拿到所有列名，以及导出的字段的get方法
			List<Method> methodObj = new ArrayList<Method>();
			Map<String, Method> convertMethod = new HashMap<String, Method>();
			// 得到所有字段
			Field fileds[] = pojoClass.getDeclaredFields();

			// 遍历整个filed
			for (int i = 0; i < fileds.length; i++) {
				Field field = fileds[i];

				BeanNote excel = field.getAnnotation(BeanNote.class);
				// 如果设置了annottion
				if (excel != null) {
					if("user".equals(field.getName())){
						Field[] fields_user = User.class.getDeclaredFields();
						for(int j = 0; j < fields_user.length; j++) {
							Field field_user = fields_user[j];
							BeanNote excel1 = field_user.getAnnotation(BeanNote.class);
							if (excel1 != null) {
								// 添加到标题
								exportFieldTitle.add(excel1.name());
								// 添加到需要导出的字段的方法
								String fieldname = field_user.getName();
								exportFieldWidth.add(excel1.width());
								// System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
								StringBuffer getMethodName = new StringBuffer("get");
								getMethodName.append(fieldname.substring(0, 1)
										.toUpperCase());
								getMethodName.append(fieldname.substring(1));

								Method getMethod = User.class.getMethod(
										getMethodName.toString(), new Class[] {});

								methodObj.add(getMethod);
								//System.out.println(getMethodName);
							}
						}
					} else if("product".equals(field.getName())){
						Field[] fields_user = Product.class.getDeclaredFields();
						for(int j = 0; j < fields_user.length; j++) {
							Field field_user = fields_user[j];
							BeanNote excel1 = field_user.getAnnotation(BeanNote.class);
							if (excel1 != null) {
								// 添加到标题
								exportFieldTitle.add(excel1.name());
								// 添加到需要导出的字段的方法
								String fieldname = field_user.getName();
								exportFieldWidth.add(excel1.width());
								// System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
								StringBuffer getMethodName = new StringBuffer("get");
								getMethodName.append(fieldname.substring(0, 1)
										.toUpperCase());
								getMethodName.append(fieldname.substring(1));

								Method getMethod = Product.class.getMethod(
										getMethodName.toString(), new Class[] {});

								methodObj.add(getMethod);
								//System.out.println(getMethodName);
							}
						}
					} else {
						// 添加到标题
						exportFieldTitle.add(excel.name());
						// 添加到需要导出的字段的方法
						String fieldname = field.getName();
						exportFieldWidth.add(excel.width());
						// System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
						StringBuffer getMethodName = new StringBuffer("get");
						getMethodName.append(fieldname.substring(0, 1)
								.toUpperCase());
						getMethodName.append(fieldname.substring(1));

						Method getMethod = pojoClass.getMethod(
								getMethodName.toString(), new Class[] {});

						methodObj.add(getMethod);
						//System.out.println(getMethodName);
					}
				}
			}
			int index = 0;
			// 产生表格标题行
			Row row = sheet.createRow(index);
			// 遍历设置工作表中的标题
			for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
				Cell cell = row.createCell(i);
				CellStyle style = workbook.createCellStyle();
				Font ztFont = workbook.createFont();
				ztFont.setBold(true);
				style.setAlignment(HorizontalAlignment.CENTER);
				style.setFont(ztFont);
				cell.setCellStyle(style);
				// cell.setCellStyle(style);
				RichTextString text = new XSSFRichTextString(
						exportFieldTitle.get(i));
				//error替换  HSSF
				cell.setCellValue(text);
			}

			// 设置每行的列宽
			for (int i = 0; i < exportFieldWidth.size(); i++) {
				// 256=65280/255
				sheet.setColumnWidth(i, 200*exportFieldWidth.get(i));
			}
			Iterator<E> its = dataset.iterator();
			// 循环插入剩下的集合
			while (its.hasNext()) {
				// 从第二行开始写，第一行是标题
				index++;
				row = sheet.createRow(index);
				Object t = its.next();
				for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
					Cell cell = row.createCell(k);
					Method getMethod = methodObj.get(k);
					Object value = null;
					//System.out.println(getMethod.getDeclaringClass().getName());
					if("com.huayaji.entity.User".equals(getMethod.getDeclaringClass().getName())){
						User t1 = ((TemporarySing)t).getUser();
						value = getMethod.invoke(t1);
					} else if("com.huayaji.entity.Product".equals(getMethod.getDeclaringClass().getName())){
						Product t1 = ((TemporarySing)t).getProduct();
						value = getMethod.invoke(t1);
					} else {
						if (convertMethod.containsKey(getMethod.getName())) {
							Method cm = convertMethod.get(getMethod.getName());
							value = cm.invoke(t, new Object[] {});
						} else {
							value = getMethod.invoke(t, new Object[] {});
						}
					}
					if(value instanceof Long)
					{
						Long longvalue=(Long)value;
						BeanNote annotation = getMethod.getAnnotation(BeanNote.class);
						if(annotation!=null&&!TStr.isNullorEmpty(annotation.format()))
						{
							if(annotation.format().equals("yyyy-MM-dd HH:mm:ss"))
								value=TDate.getDateTimeStr(longvalue);
							if(annotation.format().equals("yyyy-MM-dd"))
								value=TDate.getDateStr(longvalue);
							if(annotation.format().equals("HH:mm:ss"))
								value=TDate.getTimeStr(longvalue);
						}
					}
					if("getId".equals(getMethod.getName())) {
						setCellValue(cell, "'"+value);
					} else {
						setCellValue(cell, value);
					}
				}
			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static <E> void exportRsExcel(String title,
										 ResultSet rs, OutputStream out) {
		try {
			if (rs == null) {
				throw new RuntimeException("导出数据为空！");
			}
			if (title == null || out == null) {
				throw new RuntimeException("传入数据错误！");
			}
			// 声明一个工作薄
			//Workbook workbook = new HSSFWorkbook();
			//防止内存溢出
			Workbook workbook = new SXSSFWorkbook(100);
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);
			// 工作表的标题
			List<String> exportFieldTitle = new ArrayList<String>();
			// 工作表中每个标题的宽度
			List<Integer> exportFieldWidth = new ArrayList<Integer>();
			// 得到所有字段
			ResultSetMetaData fileds = rs.getMetaData();

			// 遍历整个filed
			for (int i = 1; i <= fileds.getColumnCount(); i++) {
				// 添加到标题
				//String fieldname=fileds.getColumnName(i);
				//用列标签，用列名会是原始名称
				String fieldlabel=fileds.getColumnLabel(i);
				exportFieldTitle.add(fieldlabel);
				exportFieldWidth.add(fileds.getColumnDisplaySize(i));
			}
			int index = 0;
			// 产生表格标题行
			Row row = sheet.createRow(index);
			CellStyle style=workbook.createCellStyle();
			style.setWrapText(true);
			// 遍历设置工作表中的标题
			for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
				Cell cell = row.createCell(i);
				//cell.setCellStyle(style);
				RichTextString text = new HSSFRichTextString(
						exportFieldTitle.get(i));
				cell.setCellValue(text);
			}
			// 设置每行的列宽
			for (int i = 0; i < exportFieldWidth.size(); i++) {
				// 256=65280/255
				Integer width=exportFieldWidth.get(i);
//				if(width>200)
//					sheet.setColumnWidth(i, width);
//				else
				sheet.setColumnWidth(i, 255*10);
			}
			if(rs.isAfterLast())
				rs.beforeFirst();
			// 循环结果集
			while (rs.next()) {
				// 从第二行开始写，第一行是标题
				index++;
				row = sheet.createRow(index);
				for (int k = 0, exportFieldcount = exportFieldTitle.size(); k < exportFieldcount; k++) {
					Cell cell = row.createCell(k);
					cell.setCellStyle(style);
					Object value = rs.getObject(exportFieldTitle.get(k));
					setCellValue(cell, value);
				}
			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setCellValue(Cell cell, Object value) {
		if (value == null) {
			cell.setCellValue("");
		} else if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof java.sql.Time) {
			cell.setCellValue((new SimpleDateFormat(
					"HH:mm:ss").format(value)));
		} else if (value instanceof java.sql.Timestamp) {
			cell.setCellValue((new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(value)));
		} else if (value instanceof java.sql.Date) {
			cell.setCellValue((new SimpleDateFormat(
					"yyyy-MM-dd").format(value)));
		} else if (value instanceof Date) {
			cell.setCellValue((new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(value)));
		} else if (value instanceof Float) {
			cell.setCellValue((Float) value);
		} else if (value instanceof Long) {
//			//判断是否是时间日期
//			if(value!=null&&(value+"").length()==13)
//			{
//
//			}
			cell.setCellValue((Long) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else {
			cell.setCellValue(value.toString());
		}
	}
	/**
	 * 导入Excel数据
	 *
	 * @param file
	 * @param pojoClass
	 * @param pattern
	 * @return
	 */
	public static <E> List<E> importExcel(HttpServletRequest request, File file, Class<E> pojoClass,
                                          String... pattern) {
		List<E> dist = new ArrayList<E>();
		try {
			// 得到目标目标类的所有的字段列表
			Field filed[] = pojoClass.getDeclaredFields();
//			filed=TBean.getBeanDeclaredFields(pojoClass,true).toArray(filed);
			// 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
			Map<String, Method> fieldSetMap = new HashMap<String, Method>();
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				// 得到单个字段上的Annotation
				BeanNote excel = f.getAnnotation(BeanNote.class);
				// 如果标识了Annotationd的话
				if (excel != null) {
					// 构造设置了Annotation的字段的Setter方法
					String fieldname = f.getName();
					String setMethodName = "set"
							+ fieldname.substring(0, 1).toUpperCase()
							+ fieldname.substring(1);
					// 构造调用的method，
					Method setMethod = pojoClass.getMethod(setMethodName,
							new Class[] { f.getType() });
					// 将这个method以Annotaion的名字为key来存入。
					fieldSetMap.put(excel.name(), setMethod);
				}
			}
			if (!file.exists())
				return null;
			// 将传入的File构造为FileInputStream;
			FileInputStream in = new FileInputStream(file);
			// 得到工作表
			Workbook book = null;
			book = WorkbookFactory.create(in);
			// try {
			// book = new XSSFWorkbook(in);
			// } catch (Exception e) {
			// book = new HSSFWorkbook(in);
			// e.printStackTrace();
			// }
			Sheet sheet = book.getSheetAt(0);

			int indexsheet = 0;
			if (sheet == null) {
				return null;
			}
			// // 得到第一面的所有行
			Iterator<Row> row = sheet.rowIterator();
			while (!row.hasNext()) {
				indexsheet++;
				sheet = book.getSheetAt(indexsheet);
				row = sheet.rowIterator();
			}
			// 得到第一行，也就是标题行
			Row title = row.next();
			// 得到第一行的所有列
			Iterator<Cell> cellTitle = title.cellIterator();
			// 将标题的文字内容放入到一个map中。
			Map<Integer, String> titlemap = new HashMap<Integer, String>();
			// 从标题第一列开始
			int i = 0;
			// 循环标题所有的列
			while (cellTitle.hasNext()) {
				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();
				// 防止标题列为空
				if (!TStr.isNullorEmpty(value.trim())) {
					titlemap.put(i, value);
				}
				i = i + 1;
			}
			int s = 0;
			while (row.hasNext()) {
				// 标题下的第一行
				Row rown = row.next();
				// 行的所有列
				Iterator<Cell> cellbody = rown.cellIterator();
				// 得到传入类的实例
				E tObject = pojoClass.newInstance();
				int k = 0;
				// 遍历一行的列
				while (cellbody.hasNext()) {
					// 得到Excel中非空的字段值
					Cell cell = cellbody.next();
					// System.out.println("cell:" + cell);
					// 这里得到此列的对应的标题
					String titleString = (String) titlemap.get(cell
							.getColumnIndex());
					if (TStr.isNullorEmpty(titleString))
						continue;
					// System.out.println(titleString);
					// 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
					if (fieldSetMap.containsKey(titleString)) {

						Method setMethod = (Method) fieldSetMap
								.get(titleString);
						setFieldValue(tObject, cell, setMethod);
					}
				}
				dist.add(tObject);
				s=s+1;
				int rowNum=sheet.getLastRowNum();
				//DecimalFormat fnum = new DecimalFormat( "##0.00 ");
				//String  dqjd = fnum.format((s*100)/rowNum);
//				System.out.println("已读取:"+s+"条，共："+rowNum+"条，进度为:"+(s*29)/rowNum+"%");
				double progressInfo = 20.0*s/rowNum+10;
				request.getSession().setAttribute("progressInfo",progressInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dist;
	}
	/**
	 * 导入Excel数据
	 * @param file
	 * @param pojoClass
	 * @param pattern
	 * @param fieldmap 字段集合 英文字段是key 中文名为value
	 * @return
	 */
	public static <E> List<E> importExcel(HttpServletRequest request, File file, Class<E> pojoClass,
                                          Map<String, String> fieldmap, String... pattern) {
		List<E> dist = new ArrayList<E>();
		try {
			// 得到目标目标类的所有的字段列表
			Field filed[] = pojoClass.getDeclaredFields();
//			filed=TBean.getBeanDeclaredFields(pojoClass,true).toArray(filed);
			Map<String, Method> fieldSetMap = new HashMap<String, Method>();
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				String fieldname = f.getName();
				String setMethodName = "set"
						+ fieldname.substring(0, 1).toUpperCase()
						+ fieldname.substring(1);
				// 构造调用的method，
				Method setMethod = pojoClass.getMethod(setMethodName,new Class[] { f.getType() });
				// 将这个method以中文名字为key来存入。
				if(fieldmap.containsKey(fieldname)){
					fieldSetMap.put(fieldmap.get(fieldname), setMethod);}
			}
			if (!file.exists())
				return null;
			// 将传入的File构造为FileInputStream;
			FileInputStream in = new FileInputStream(file);
			// 得到工作表
			Workbook book = null;
			book = WorkbookFactory.create(in);
			Sheet sheet = book.getSheetAt(0);
			int indexsheet = 0;
			if (sheet == null) {
				return null;
			}
			Iterator<Row> row = sheet.rowIterator();
			while (!row.hasNext()) {
				indexsheet++;
				sheet = book.getSheetAt(indexsheet);
				row = sheet.rowIterator();
			}
			// 得到第一行，也就是标题行
			Row title = row.next();
			// 得到第一行的所有列
			Iterator<Cell> cellTitle = title.cellIterator();
			// 将标题的文字内容放入到一个map中。
			Map<Integer, String> titlemap = new HashMap<Integer, String>();
			// 从标题第一列开始
			int i = 0;
			// 循环标题所有的列
			while (cellTitle.hasNext()) {
				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();
				// 防止标题列为空
				if (!TStr.isNullorEmpty(value.trim())) {
					titlemap.put(i, value);
				}
				i = i + 1;
			}
			int s = 0;
			while (row.hasNext()) {
				// 标题下的第一行
				Row rown = row.next();
				// 行的所有列
				Iterator<Cell> cellbody = rown.cellIterator();
				// 得到传入类的实例
				E tObject = pojoClass.newInstance();
				int k = 0;
				// 遍历一行的列
				while (cellbody.hasNext()) {
					// 得到Excel中非空的字段值
					Cell cell = cellbody.next();
					// 这里得到此列的对应的标题
					String titleString = (String) titlemap.get(cell.getColumnIndex());
					if (TStr.isNullorEmpty(titleString))
						continue;
					// 如果在map中有这一列的标题，那么则调用此类的的set方法，进行设值
					if (fieldSetMap.containsKey(titleString)) {
						Method setMethod = (Method) fieldSetMap.get(titleString);
						setFieldValue(tObject, cell, setMethod);
					}
					// 下一列
					k = k + 1;
				}
				dist.add(tObject);
				s=s+1;
				int rowNum=sheet.getLastRowNum();
				double dqjd = (s*5)/rowNum;
				request.getSession().setAttribute("dqjd",dqjd);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dist;
	}
	/**
	 * 导出表
	 * @param title
	 * @param pojoClass
	 * @param dataset
	 * @param out
	 * @param headers 字段集合 英文字段是key 中文名为value
	 */
	public static <E> void exportExcel(String title, Class<E> pojoClass,
									   Collection<E> dataset, OutputStream out,Map<String, String> headers) {
		try {
			if (dataset == null || dataset.size() == 0) {
				log.error("导出数据为空！");
				return;
			}
			if (title == null || out == null || pojoClass == null) {
				log.error("传入数据错误！");
				return;
			}
			// 声明一个工作薄
			//Workbook workbook = new HSSFWorkbook();
			//防止内存溢出
			Workbook workbook = new SXSSFWorkbook(100);
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);
			// 工作表的标题
			List<String> exportFieldTitle = new ArrayList<String>();
			// 工作表中每个标题的宽度
			List<Integer> exportFieldWidth = new ArrayList<Integer>();
			// 拿到所有列名，以及导出的字段的get方法
			List<Method> methodObj = new ArrayList<Method>();
			Map<String, Method> convertMethod = new HashMap<String, Method>();
			// 得到所有字段
			Field fileds[] = pojoClass.getDeclaredFields();

			Set<String> keySet = headers.keySet();
			if (headers != null)
				for (String key : keySet) {
					// 遍历整个filed
					for (int i = 0; i < fileds.length; i++) {
						Field field = fileds[i];
						if (key != null) {
							String fieldname = field.getName();
							if(fieldname.equals(key)){
								// 添加到标题
								exportFieldTitle.add(headers.get(fieldname));
								// 添加到需要导出的字段的方法
								StringBuffer getMethodName = new StringBuffer("get");
								getMethodName.append(fieldname.substring(0, 1).toUpperCase());
								getMethodName.append(fieldname.substring(1));
								Method getMethod = pojoClass.getMethod(
										getMethodName.toString(), new Class[] {});
								methodObj.add(getMethod);
								continue;
							}
						}
					}
				}
			int index = 0;
			// 产生表格标题行
			Row row = sheet.createRow(index);
			// 遍历设置工作表中的标题
			for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
				Cell cell = row.createCell(i);
				// cell.setCellStyle(style);
				RichTextString text = new HSSFRichTextString(
						exportFieldTitle.get(i));
				cell.setCellValue(text);
			}
			// 设置每行的列宽
			for (int i = 0; i < exportFieldWidth.size(); i++) {
				// 256=65280/255
				sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
			}
			Iterator<E> its = dataset.iterator();
			// 循环插入剩下的集合
			while (its.hasNext()) {
				// 从第二行开始写，第一行是标题
				index++;
				row = sheet.createRow(index);
				Object t = its.next();
				for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
					Cell cell = row.createCell(k);
					Method getMethod = methodObj.get(k);
					Object value = null;
					if (convertMethod.containsKey(getMethod.getName())) {
						Method cm = convertMethod.get(getMethod.getName());
						value = cm.invoke(t, new Object[] {});
					} else {
						value = getMethod.invoke(t, new Object[] {});
					}
					if(value instanceof Long)
					{
						Long longvalue=(Long)value;
						BeanNote annotation = getMethod.getAnnotation(BeanNote.class);
						if(annotation!=null&&!TStr.isNullorEmpty(annotation.format()))
						{
							if(annotation.format().equals("yyyy-MM-dd HH:mm:ss"))
								value=TDate.getDateTimeStr(longvalue);
							if(annotation.format().equals("yyyy-MM-dd"))
								value=TDate.getDateStr(longvalue);
							if(annotation.format().equals("HH:mm:ss"))
								value=TDate.getTimeStr(longvalue);
						}
					}
					setCellValue(cell, value);

				}
			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private static <E> void setFieldValue(E tObject, Cell cell, Method setMethod)
			throws IllegalAccessException, InvocationTargetException {
		// 得到setter方法的参数
		Type[] ts = setMethod.getGenericParameterTypes();
		// 只要一个参数
		String xclass = ts[0].toString();
		try {
			// 判断参数类型
			if (xclass.equals("class java.lang.String")) {
				try {
					setMethod
							.invoke(tObject, cell.getStringCellValue());
				} catch (Exception e) {
					try {
//						BigDecimal bigDecimal = new BigDecimal(cell.getNumericCellValue());
                        final double currvalue = cell.getNumericCellValue();
                        String  re=currvalue+"";
                        if(re.endsWith(".0"))
							re=re.substring(0,re.lastIndexOf(".0"));
                        setMethod
                                .invoke(tObject, re);
					} catch (Exception ex) {
						FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
						Object value = evaluator.evaluate(cell).getNumberValue();
						setMethod
								.invoke(tObject, value + "");
					}
				}
			} else if (xclass.equals("class java.util.Date")) {
				DecimalFormat df = new DecimalFormat("0");// 格式化数字
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				if ("@".equals(cell.getCellStyle().getDataFormatString())) {
					if (cell.getNumericCellValue() >= 10) {
						String value = df.format(cell.getNumericCellValue());
						Date date = TDate.toDateTime(value);
						setMethod.invoke(tObject, date);
					} else {
						setMethod.invoke(tObject, null);
					}

				} else if ("General".equals(cell.getCellStyle()
						.getDataFormatString())) {
					String value = null;
					if (cell.getCellType() == CellType.STRING)
						value = cell.getStringCellValue();
					else {
						cell.setCellType(CellType.NUMERIC);
						//System.out.println(cell.getNumericCellValue());
						value = df.format(cell.getNumericCellValue());
					}
					Date date = TDate.toDateTime(value);
					setMethod.invoke(tObject, date);
				} else {
					String value = null;
					try {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					} catch (Exception e) {
						try {
							//value = sdf.format(HSSFDateUtil.getJavaDate(Double.parseDouble(cell.getStringCellValue())));
							value = cell.getStringCellValue();
						} catch (Exception ex) {
							// value = sdf.format(HSSFDateUtil.getJavaDate(Double.parseDouble(cell.getDateCellValue()+"")));
						}
					}

					Date date = TDate.toDateTime(value);
					setMethod.invoke(tObject, date);
				}
			} else if (xclass.equals("class java.lang.Boolean")
					|| xclass.equals("boolean")) {
				try {
					setMethod.invoke(tObject,
							cell.getBooleanCellValue());
				} catch (Exception e) {
					try {
						setMethod.invoke(tObject,
								Boolean.getBoolean(cell.getNumericCellValue() + ""));
					} catch (Exception e2) {
						setMethod.invoke(tObject,
								Boolean.getBoolean(cell.getStringCellValue()));
					}
				}
			} else if (xclass.equals("class java.lang.Integer")
					|| xclass.equals("int")) {
				//log.info("当前属性："+setMethod.getName());
				if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)
					setMethod.invoke(tObject,
							(int) cell.getNumericCellValue());
				else {
					String cellvaule = null;
					if (cell.getCellType() != CellType.STRING)
						cell.setCellType(CellType.STRING);
					cellvaule = cell.getStringCellValue();

					if (!TStr.isNullorEmpty(cellvaule)) {
						cellvaule = cellvaule.trim();
						cellvaule = cellvaule.replaceAll(String.valueOf(((char) 8194)), "");
						if (TStr.isInt(cellvaule))
							setMethod.invoke(tObject,
									Integer.parseInt(cellvaule));
						else {
							if (TStr.isInt(cellvaule))
								setMethod.invoke(tObject, Integer.valueOf(cellvaule));
						}
					} else {
						//setMethod.invoke(tObject,Integer.valueOf(null));
					}

				}
			} else if (xclass.equals("class java.lang.Long")
					|| xclass.equals("long")) {
				if (cell.getCellType() == CellType.NUMERIC|| cell.getCellType() == CellType.FORMULA
						) {
					//System.out.println(setMethod.getName()+":");
					setMethod.invoke(tObject,
							(long) cell.getNumericCellValue());
				} else {
					String cellvaule = cell.getStringCellValue();
					if (!TStr.isNullorEmpty(cellvaule)) {
						cellvaule = cellvaule.trim();
						//处理特殊空格
						cellvaule = cellvaule.replaceAll(String.valueOf(((char) 8194)), "");
					}
					if (TStr.isInt(cellvaule))
						setMethod.invoke(tObject,
								Long.parseLong(cellvaule));
					else
						setMethod.invoke(tObject, Long.valueOf(cellvaule.trim()));
				}
				//1、判断是否是数值格式
				if (cell.getCellType() == CellType.NUMERIC|| cell.getCellType() == CellType.FORMULA) {
					short format = cell.getCellStyle().getDataFormat();
					SimpleDateFormat sdf = null;
					double value = cell.getNumericCellValue();
					if (format == 176 || format == 14 || format == 31 || format == 57 || format == 58) {
						//日期
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date date = DateUtil.getJavaDate(value);
						setMethod.invoke(tObject, date.getTime());
					} else if (format == 20 || format == 32) {
						//时间
						sdf = new SimpleDateFormat("HH:mm");
						Date date = DateUtil.getJavaDate(value);
						setMethod.invoke(tObject, date.getTime());
					} else {
						setMethod.invoke(tObject, (long) value);
					}

				}
			} else if (xclass.equals("class java.lang.Double")
					|| xclass.equals("double")) {
				if (cell.getCellType() == CellType.NUMERIC|| cell.getCellType() == CellType.FORMULA)
					setMethod.invoke(tObject,
							cell.getNumericCellValue());
				else {
					String cellvaule = cell.getStringCellValue();
					if (TStr.isDouble(cellvaule))
						setMethod.invoke(tObject,
								Double.parseDouble(cellvaule));
				}
			} else if (xclass.equals("class java.lang.Float")
					|| xclass.equals("float")) {
				if (cell.getCellType() == CellType.NUMERIC|| cell.getCellType() == CellType.FORMULA)
					setMethod.invoke(tObject,
							cell.getNumericCellValue());
				else {
					String cellvaule = cell.getStringCellValue();
					if (TStr.isDouble(cellvaule))
						setMethod.invoke(tObject,
								Float.parseFloat(cellvaule));
				}

			}
		}
		catch (Exception e){
			throw new RuntimeException("数据导入异常："+e.getMessage());
		}
	}

}
