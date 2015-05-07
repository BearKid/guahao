package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.util.lang.DateUtils;
import com.lwb.guahao.common.model.Doctor;
import com.lwb.guahao.common.model.DoctorPerTimeSchedule;
import com.lwb.guahao.common.qo.DoctorDailyScheduleQo;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.DoctorPerTimeScheduleDao;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleVo;
import com.lwb.guahao.webapp.vo.DoctorPerTimeScheduleVo;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/23 20:56
 */
@Service
@Transactional
public class DoctorPerTimeScheduleService {
    private final static Logger logger = Logger.getLogger(DoctorPerTimeScheduleService.class);
    @Resource
    private DoctorPerTimeScheduleDao doctorPerTimeScheduleDao;
    @Resource
    private DoctorDao doctorDao;

    /**
     * 获取按天归类的排班分页
     * @param doctorDailyScheduleQo
     * @return
     */
    @Transactional(readOnly = true)
    public ApiRet<Paging<DoctorDailyScheduleVo>> getPagingBy(final DoctorDailyScheduleQo doctorDailyScheduleQo) throws ParseException{
        ApiRet apiRet = new ApiRet();

        Paging<DoctorPerTimeSchedule> doctorPerTimeSchedulePaging= doctorPerTimeScheduleDao.getPagingBy(doctorDailyScheduleQo);
        Doctor doctor = doctorDao.get(doctorDailyScheduleQo.getDoctorId());
        List<DoctorDailyScheduleVo>  doctorDailyScheduleVoList = buildDoctorDailyScheduleVoList(doctorPerTimeSchedulePaging.getItems(), doctor, doctorDailyScheduleQo);
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
     * @param doctor 排班记录集所属的医生
     * @param doctorDailyScheduleQo 排班查询条件
     * @return
     */
    private List<DoctorDailyScheduleVo> buildDoctorDailyScheduleVoList(List<DoctorPerTimeSchedule> doctorPerTimeSchedulesList, final Doctor doctor, final DoctorDailyScheduleQo doctorDailyScheduleQo) {
        Date startDate = doctorDailyScheduleQo.getStartDay();//查询的开始日期
        Date endDate = doctorDailyScheduleQo.getEndDay();//查询的结束日期
        boolean ignoreNoScheduleDate = doctorDailyScheduleQo.getIgnoreNoScheduleDay();//是否忽略空记录的日期

        if(doctorPerTimeSchedulesList == null) doctorPerTimeSchedulesList = new ArrayList<DoctorPerTimeSchedule>();
        Map<DateTime,List<DoctorPerTimeScheduleVo>> doctorDailyScheuleVoMap = new TreeMap<DateTime, List<DoctorPerTimeScheduleVo>>(new Comparator<DateTime>() {
            @Override
            public int compare(DateTime o1, DateTime o2) {
                int ret = 0;
                if(o1.isBefore(o2)) ret = -1;
                else if(o1.isAfter(o2)) ret = 1;
                return ret;
            }
        });//临时归类-每天排班映射 升序

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
            Double price;
            if(doctorPerTimeScheduleVoList!= null && !doctorPerTimeScheduleVoList.isEmpty()){
                DoctorPerTimeScheduleVo doctorPerTimeScheduleVoTemp = doctorPerTimeScheduleVoList.get(0);
                price = doctorPerTimeScheduleVoTemp.getPrice();
            } else{
                price = doctor.getPrice();
            }

            DoctorDailyScheduleVo doctorDailyScheduleVo = new DoctorDailyScheduleVo();
            doctorDailyScheduleVo.setId(doctorDailyScheduleVoList.size());
            doctorDailyScheduleVo.setTakeEffectiveDate(day.toString(DateUtils.yearMonthDayWeekPattern));
            doctorDailyScheduleVo.setTotalSource(totalSources);
            doctorDailyScheduleVo.setOddSource(oddSources);
            doctorDailyScheduleVo.setPrice(price);
            doctorDailyScheduleVo.setDoctorId(doctor.getId());
            doctorDailyScheduleVo.setDoctorPerTimeScheduleList(doctorPerTimeScheduleVoList);

            doctorDailyScheduleVoList.add(doctorDailyScheduleVo);
        }
        /*升序排序每天排班的明细安排*/
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
    public ApiRet saveOrUpdate(final List<DoctorPerTimeSchedule> doctorPerTimeScheduleList) {
        ApiRet apiRet = new ApiRet();
        Date today = DateTime.now().withTimeAtStartOfDay().toDate();
        for(DoctorPerTimeSchedule doctorPerTimeSchedule : doctorPerTimeScheduleList){
            Date startDateTime = doctorPerTimeSchedule.getStartDateTime();
            if(startDateTime == null || startDateTime.before(today)){
                apiRet.setRet(ApiRet.RET_FAIL);
                apiRet.setMsg("不能创建或修改旧的排班");
                return apiRet;
            }
        }
        doctorPerTimeScheduleDao.saveOrUpdate(doctorPerTimeScheduleList);
        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setMsg("保存成功");
        return apiRet;
    }

    /**
     * 删除指定的排班
     * @param doctorPerTimeScheduleId
     */
    public void delete(final Integer doctorPerTimeScheduleId) {
        doctorPerTimeScheduleDao.delete(doctorPerTimeScheduleId);
    }

    /**
     * 校验指定排班是否允许被预约
     * 1.验证是否排班未过期
     * 2.验证是否有剩余号源
     * @param doctorPerTimeScheduleId
     * @return
     */
    @Transactional(readOnly = true)
    public ApiRet<Boolean> isReservable(final Integer doctorPerTimeScheduleId) {
        ApiRet apiRet;

        //验证是否排班未过期
        apiRet = isExpired(doctorPerTimeScheduleId);
        if(apiRet.getRet() == ApiRet.RET_FAIL || apiRet.getData().equals(Boolean.TRUE)){
            apiRet.setRet(ApiRet.RET_FAIL);
            return apiRet;
        }
        //验证是否有剩余号源
        apiRet = hasOddSource(doctorPerTimeScheduleId);
        if (apiRet.getRet() == ApiRet.RET_FAIL || apiRet.getData().equals(Boolean.FALSE)) {
            apiRet.setRet(ApiRet.RET_FAIL);
            return apiRet;
        }

        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setMsg("指定的医生排班还有剩余号源");
        apiRet.setData(Boolean.TRUE);
        return apiRet;
    }

    /**
     * 判断排班是否过期
     * @param doctorPerTimeScheduleId
     * @return
     */
    @Transactional(readOnly = true)
    public ApiRet<Boolean> isExpired(final Integer doctorPerTimeScheduleId) {
        ApiRet<Boolean> apiRet = new ApiRet<Boolean>();

        DoctorPerTimeSchedule doctorPerTimeSchedule = doctorPerTimeScheduleDao.get(doctorPerTimeScheduleId);
        if(doctorPerTimeSchedule == null){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("指定的医生排班不存在");
            return apiRet;
        }

        Date expireDateTime = doctorPerTimeSchedule.getStartDateTime();
        Date now = new Date();
        if(expireDateTime.after(now)){//未过期
            apiRet.setRet(ApiRet.RET_SUCCESS);
            apiRet.setMsg("指定的医生排班未过期");
            apiRet.setData(Boolean.FALSE);
            return apiRet;
        }

        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setMsg("指定的医生排班已过期");
        apiRet.setData(Boolean.TRUE);
        return apiRet;
    }

    /**
     * 判断指定排班是否有剩余号源
     * @param doctorPerTimeScheduleId
     * @return
     */
    @Transactional(readOnly = true)
    public ApiRet<Boolean> hasOddSource(final Integer doctorPerTimeScheduleId) {
        ApiRet apiRet = new ApiRet();

        DoctorPerTimeSchedule doctorPerTimeSchedule = doctorPerTimeScheduleDao.get(doctorPerTimeScheduleId);
        if(doctorPerTimeSchedule == null){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("指定医生排班不存在");
            return apiRet;
        }

        if(doctorPerTimeSchedule.getOddSource() < 1){
            apiRet.setRet(ApiRet.RET_SUCCESS);
            apiRet.setMsg("指定医生排班没有剩余号源啦");
            apiRet.setData(Boolean.FALSE);
        }

        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setMsg("指定医生排班还有剩余号源");
        apiRet.setData(Boolean.TRUE);
        return apiRet;
    }
}
