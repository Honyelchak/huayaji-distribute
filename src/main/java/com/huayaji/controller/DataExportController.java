package com.huayaji.controller;

import com.huayaji.util.TExcel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
public class DataExportController {

    /*@RequestMapping(value = "/exstudent",consumes = "application/json;charset=utf-8")
    public void export (@RequestBody Student student, HttpServletResponse response){
        OutputStream out = null;
        String name = "学生信息管理表";

        String opt = student.getOper();
        String query = student.getQuery();
        if (opt != null && opt.length() > 0) {
            if (opt.equals("allot")) {
                if (query != null && query.length() > 0)
                    student.setQuery(query + " and bunkno is null");
                else
                    student.setQuery(" bunkno is null");
            } else if (opt.equals("reback")) {
                if (query != null && query.length() > 0)
                    student.setQuery(query + " and bunkno>0");
                else
                    student.setQuery(" bunkno>0");
            }
        }
        student.setRows(0);
        if (query.contains("studentBunk."))
            student.setQuery(query.replaceAll("studentBunk.", ""));
        PageInfo<Student> pageInfo = studentService.getPageBySql(student);
        System.out.println(pageInfo.getList().size());
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(name, "UTF-8")+".xlsx");
            out = response.getOutputStream();
            TExcel.exportExcel(name,Student.class,pageInfo.getList(),out);
            System.out.println("下载ok");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{

        }
    }*/
}
