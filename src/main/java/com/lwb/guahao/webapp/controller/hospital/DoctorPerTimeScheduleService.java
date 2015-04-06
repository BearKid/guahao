package com.lwb.guahao.webapp.controller.hospital;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.common.util.DateUtils;
import com.lwb.guahao.model.DoctorPerTimeSchedule;
import com.lwb.guahao.qo.DoctorDailyScheduleQo;
import com.lwb.guahao.webapp.dao.DoctorPerTimeScheduleDao;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleQoVo;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleVo;
import com.lwb.guahao.webapp.vo.DoctorPerTimeScheduleVo;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/23 20:56
 */
@Service
public class DoctorPerTimeScheduleService {
    @Resource
    private DoctorPerTimeScheduleDao doctorPerTimeScheduleDao;

    /**
     * 获取按天归类的排班分页
     * @param doctorDailyScheduleQoVo
     * @return
     */
    public ApiRet getPagingBy(DoctorDailyScheduleQoVo doctorDailyScheduleQoVo) {
        ApiRet apiRet = new ApiRet();

        DoctorDailyScheduleQo qo = DoctorDailyScheduleQo.parse(doctorDailyScheduleQoVo);
        if(qo.getPn() == null){
            qo.setPn(1);
        }
        if(qo.getPageSize() == null){
            qo.setPageSize(Constants.DEFAULT_PAGE_SIZE);
        }
        Paging<DoctorPerTimeSchedule> doctorPerTimeSchedulePaging= doctorPerTimeScheduleDao.getPagingBy(qo);
        List<DoctorDailyScheduleVo>  doctorDailyScheduleVoList = buildDoctorDailyScheduleVoList(doctorPerTimeSchedulePaging.getItems(),qo.getStartDay(),qo.getEndDay(),qo.getIgnoreNoScheduleDay());
        Paging<DoctorDailyScheduleVo> doctorDailyScheduleVoPaging = new Paging<DoctorDailyScheduleVo>(
                doctorDailyScheduleVoList,
                doctorPerTimeSchedulePaging.getPn(),
                doctorPerTimeSchedulePaging.getPageSize(),
                doctorPerTimeSchedulePaging.getTotalSize()
        );
        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setData(doctorDailyScheduleVoPaging);
        return apiRet;
    }

    /**
     * 构建按天归类的排班
     * @param doctorPerTimeSchedulesList 明细排班记录集
     * @param startDate 查询的开始日期
     * @param endDate 查询的结束日期
     * @param ignoreNoScheduleDate 是否忽略空记录的日期
     * @return
     */
    private List<DoctorDailyScheduleVo> buildDoctorDailyScheduleVoList(List<DoctorPerTimeSchedule> doctorPerTimeSchedulesList,Date startDate, Date endDate, boolean ignoreNoScheduleDate) {
        if(doctorPerTimeSchedulesList == null) doctorPerTimeSchedulesList = new ArrayList<DoctorPerTimeSchedule>();
        Map<DateTime,List<DoctorPerTimeScheduleVo>> doctorDailyScheuleVoMap = new TreeMap<DateTime, List<DoctorPerTimeScheduleVo>>(new Comparator<DateTime>() {
            @Override
            public int compare(DateTime o1, DateTime o2) {
                int ret = 0;
                if(o1.isBefore(o2)) ret = 1;
                else if(o1.isAfter(o2)) ret = -1;
                return ret;
            }
        });//临时归类-每天排班映射 降序

        /*按天归类排班*/
        for(DoctorPerTimeSchedule doctorPerTimeSchedule : doctorPerTimeSchedulesList){
            DateTime day = new DateTime(doctorPerTimeSchedule.getStartDateTime()).withTimeAtStartOfDay();//排班对应的日期天
            List<DoctorPerTimeScheduleVo> doctorPerTimeScheduleVoList = doctorDailyScheuleVoMap.get(day);
            if(doctorPerTimeScheduleVoList == null){
                doctorPerTimeScheduleVoList = new LinkedList<DoctorPerTimeScheduleVo>();
                doctorDailyScheuleVoMap.put(day, doctorPerTimeScheduleVoList);
            }
            doctorPerTimeScheduleVoList.add(DoctorPerTimeScheduleVo.parse(doctorPerTimeSchedule));
        }

        /*没有排班的日期也参与占位*/
        if(!ignoreNoScheduleDate){
            DateTime startDay = new DateTime(startDate).withTimeAtStartOfDay();
            DateTime endDay = new DateTime(endDate).plusDays(1).withTimeAtStartOfDay();
            DateTime tempDay = startDay;
            while(tempDay.isBefore(endDay)){//遍历检查指定的那几天
                List<DoctorPerTimeScheduleVo> tempVoList = doctorDailyScheuleVoMap.get(tempDay);
                if(tempVoList == null) {//如果某天的排班列表为null,则创建一个空的排班列表
                    doctorDailyScheuleVoMap.put(tempDay, new ArrayList<DoctorPerTimeScheduleVo>(0));
                }
                tempDay = tempDay.plusDays(1);
            }
        }

        /*构建正式的每天排班列表中*/
        List<DoctorDailyScheduleVo> doctorDailyScheduleVoList = new LinkedList<DoctorDailyScheduleVo>();
        for(Map.Entry<DateTime,List<DoctorPerTimeScheduleVo>> entry : doctorDailyScheuleVoMap.entrySet()){
            DateTime day = entry.getKey();
            List<DoctorPerTimeScheduleVo> doctorPerTimeScheduleVoList = entry.getValue();//一天对应的所有排班
            int totalSources = 0;
            int oddSources = 0;
            /*计算总号源、总剩余号源*/
            for(DoctorPerTimeScheduleVo doctorPerTimeScheduleVo: doctorPerTimeScheduleVoList){
                totalSources += doctorPerTimeScheduleVo.getTotalSource();
                oddSources += doctorPerTimeScheduleVo.getOddSource();
            }
            Double price = 0.0;
            Integer doctorId = null;
            if(doctorPerTimeScheduleVoList!= null && !doctorPerTimeScheduleVoList.isEmpty()){
                DoctorPerTimeScheduleVo doctorPerTimeScheduleVoTemp = doctorPerTimeScheduleVoList.get(0);
                price = doctorPerTimeScheduleVoTemp.getPrice();
                doctorId = doctorPerTimeScheduleVoTemp.getDoctorId();
            }

            DoctorDailyScheduleVo doctorDailyScheduleVo = new DoctorDailyScheduleVo();
            doctorDailyScheduleVo.setId(doctorDailyScheduleVoList.size());
            doctorDailyScheduleVo.setTakeEffectiveDate(day.toString(DateUtils.yearMonthDayWeekPattern));
            doctorDailyScheduleVo.setTotalSource(totalSources);
            doctorDailyScheduleVo.setOddSource(oddSources);
            doctorDailyScheduleVo.setPrice(price);
            doctorDailyScheduleVo.setDoctorId(doctorId);
            doctorDailyScheduleVo.setDoctorPerTimeScheduleList(doctorPerTimeScheduleVoList);

            doctorDailyScheduleVoList.add(doctorDailyScheduleVo);
        }
        /*降序排序每天排班的明细安排*/
        for(DoctorDailyScheduleVo doctorDailyScheduleVo : doctorDailyScheduleVoList){
            Collections.sort(doctorDailyScheduleVo.getDoctorPerTimeScheduleList(), new Comparator<DoctorPerTimeScheduleVo>() {
                @Override
                public int compare(DoctorPerTimeScheduleVo o1, DoctorPerTimeScheduleVo o2) {
                    int ret = 0;
                    try{
                        Date date1 = DateUtils.timeFormatter.parse(o1.getStartTime());
                        Date date2 = DateUtils.timeFormatter.parse(o2.getStartTime());
                        if(date1.before(date2)){
                            ret  = -1;
                        } else if(date1.after(date2)){
                            ret = 1;
                        }
                    } catch (ParseException e){
                      throw new RuntimeException(e);
                    }
                    return ret;
                }
            });
        }
//        Collections.sort(doctorDailyScheduleVoList, new Comparator<DoctorDailyScheduleVo>() {
//            @Override
//            public int compare(DoctorDailyScheduleVo o1, DoctorDailyScheduleVo o2) {
//                int ret = 0;
//                try {
//                    Date day1 = DateUtils.yearMonthDayFormatter.parse(o1.getTakeEffectiveDate());
//                    Date day2 = DateUtils.yearMonthDayFormatter.parse(o2.getTakeEffectiveDate());
//                    if(day1.equals(day2)) ret = 0;
//                    else if(day1.before(day2)) ret = 1;
//                    else  ret = -1;
//                }catch (ParseException e){
//                    throw new RuntimeException(e);
//                }
//                return ret;
//            }
//        });
        return doctorDailyScheduleVoList;
    }

    /**
     * 保存、更新排班
     * @param doctorPerTimeScheduleList
     * @return
     */
    public ApiRet saveOrUpdate(List<DoctorPerTimeSchedule> doctorPerTimeScheduleList) {
        ApiRet apiRet = new ApiRet(ApiRet.RET_SUCCESS,"保存成功",null);
        doctorPerTimeScheduleDao.saveOrUpdate(doctorPerTimeScheduleList);
        return apiRet;
    }

    /**
     * 删除指定的排班
     * @param doctorPerTimeScheduleId
     */
    public void delete(Integer doctorPerTimeScheduleId) {
        doctorPerTimeScheduleDao.delete(doctorPerTimeScheduleId);
    }
}
