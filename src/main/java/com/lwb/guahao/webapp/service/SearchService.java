package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.model.Doctor;
import com.lwb.guahao.common.model.Hospital;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.HospitalDao;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.HospitalVo;
import com.lwb.guahao.common.qo.SearchQo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * Date: 2015/4/27 19:56
 *
 * @version 1.0
 * @autor: Lu Weibiao
 */
@Service
@Transactional(readOnly = true)
public class SearchService {
    private final static Logger logger = Logger.getLogger(SearchService.class);
    @Resource
    private DoctorDao doctorDao;
    @Resource
    private HospitalDao hospitalDao;

    /**
     * 根据公共搜索页的搜索条件查询医生分页
     * @param searchQo
     * @return
     */
    @Transactional(readOnly = true)
    public Paging<DoctorVo> getDoctorPagingBySearch(SearchQo searchQo) {
        Paging<Doctor> doctorPaging = doctorDao.getDoctorsPagingBy(searchQo);

        //视图转换
        List<Doctor> doctorList = doctorPaging.getItems();
        List<DoctorVo> doctorBySearchList = new ArrayList<DoctorVo>(doctorList.size());
        for(Doctor doctor : doctorList){
            Hospital hospital = hospitalDao.get(doctor.getHospitalId());
            DoctorVo doctorBySearch = DoctorVo.parse(doctor);
            doctorBySearch.setHospital(HospitalVo.parse(hospital));

            doctorBySearchList.add(doctorBySearch);
        }
        Paging<DoctorVo> doctorBySearchPaging = new Paging<DoctorVo>(doctorBySearchList, doctorPaging.getPn(), doctorPaging.getPageSize(), doctorPaging.getTotalSize());

        return doctorBySearchPaging;
    }

    /**
     * 根据公共搜索页的搜索条件查询医生分页
     * @param searchQo
     * @return
     */
    @Transactional(readOnly = true)
    public Paging<HospitalVo> getHospitalPagingBySearch(SearchQo searchQo) {
        Paging<Hospital> hospitalPaging = hospitalDao.getHospitalPagingBy(searchQo);

        //视图转换
        List<Hospital> hospitalList = hospitalPaging.getItems();
        List<HospitalVo> hospitalBySearchList = new ArrayList<HospitalVo>(hospitalList.size());
        for(Hospital hospital : hospitalList){
            HospitalVo hospitalBySearch = HospitalVo.parse(hospital);
            hospitalBySearchList.add(hospitalBySearch);
        }
        Paging<HospitalVo> hospitalBySearchPaging = new Paging<HospitalVo>(hospitalBySearchList, hospitalPaging.getPn(), hospitalPaging.getPageSize(), hospitalPaging.getTotalSize());

        return hospitalBySearchPaging;
    }
}
