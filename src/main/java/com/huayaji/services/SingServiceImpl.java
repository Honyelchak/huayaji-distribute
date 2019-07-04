package com.huayaji.services;

import com.huayaji.controller.SingleController;
import com.huayaji.dao.SingDao;
import com.huayaji.entity.Sing;
import com.huayaji.entity.TemporarySing;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;

@Service
@Transactional
public class SingServiceImpl implements SingService {

    @Resource
    private SingDao singDao;

    @Override
    public void save(Sing sing) {
        singDao.save(sing);
    }

    @PostConstruct
    public void updateStatus() throws ParseException {
        System.out.println("定时器启动...");
        final long PERIOD_DAY = 24 * 60 * 60 * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1); //凌晨1点
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); //第一次执行定时任务的时间
        //如果第一次执行定时任务的时间 小于当前的时间
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }
        Timer timer = new Timer();
        SingleController task = new SingleController();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(task,date,PERIOD_DAY);
    }
    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
    public static long getDaysBetweenTwoDates(Date a, Date b) throws Exception {
        //判断这两个时间的大小
        if(a.equals(b)) return 0;
        if(!a.before(b)){//保证返回的值为正数
            Date temp;
            temp=a;
            a=b;
            b=temp;
        }
        Calendar c = Calendar.getInstance();//获取calendar对像
        c.setTime(a);//设置时间 date  转 calendar 类型
        long t1 = c.getTimeInMillis();//获取时间戳
        c.setTime(b);
        long t2 = c.getTimeInMillis();
        //计算天数
        long days = (t2 - t1) / (24 * 60 * 60 * 1000);
        return days;
    }
    @Override
    public List<Sing> findAll() {
        return singDao.findAll();
    }

    @Override
    public Sing findById(Long id) {
        return singDao.findById(id);
    }

    @Override
    public void update(Sing sing) {
        singDao.update(sing);
    }

    @Override
    public void delete(Long id) {
        singDao.delete(id);
    }

    @Override
    public void saveTemporary(TemporarySing sing) {
        singDao.saveTemporary(sing);
    }

    @Override
    public List<TemporarySing> findTemporaryAll() {
        return singDao.findTemporaryAll();
    }

    @Override
    public TemporarySing findTemporaryById(Long id) {
        return singDao.findTemporaryById(id);
    }

    @Override
    public void updateTemporary(TemporarySing sing) {
    singDao.updateTemporary(sing);
    }

    @Override
    public void deleteTemporary(Long id) {
        singDao.deleteTemporary(id);
    }

    @Override
    public void deleteAllTem() {
        singDao.deleteAllTem();
    }

    @Override
    public Sing findByUseridAndProductidAnddate(TemporarySing t) {
        return singDao.findByUseridAndProductidAnddate(t);
    }

    @Override
    public List<TemporarySing> findTemporaryByPage(Integer page, Integer limit, String search) {
        return singDao.findTemporaryByPage( page, limit,search);
    }

    @Override
    public List<Sing> findByPage(Integer page, Integer limit, String search) {
        return singDao.findByPage( page,  limit,  search) ;
    }

    @Override
    public void modifyStatus(String id) {
        singDao.modifyStatus(id);
    }

    @Override
    public List<Sing> findByUseridAndProductid(String phone, String s) {
        return singDao.findByUseridAndProductid(phone,s);
    }

    @Override
    public List<TemporarySing> findDilivering() {return singDao.findDilivering();}

    @Override
    public long getCount(String search) {
        return singDao.getCount(search);
    }
    @Override
    public int getCount() {
        return singDao.getCount();
    }
}
