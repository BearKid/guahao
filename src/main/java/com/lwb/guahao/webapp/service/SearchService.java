package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.HospitalDao;
import com.lwb.guahao.webapp.vo.search.DoctorBySearch;
import com.lwb.guahao.webapp.vo.search.HospitalBySearch;
import com.lwb.guahao.webapp.vo.search.SearchQo;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private DoctorDao doctorDao;
    @Resource
    private HospitalDao hospitalDao;

    /**
     * 根据公共搜索页的搜索条件查询医生分页
     * @param searchQo
     * @return
     */
    public Paging<DoctorBySearch> getDoctorBySearchPaging(SearchQo searchQo) {
        Paging<Doctor> doctorPaging = doctorDao.getDoctorsPagingBy(searchQo);

        //视图转换
        List<Doctor> doctorList = doctorPaging.getItems();
        List<DoctorBySearch> doctorBySearchList = new ArrayList<DoctorBySearch>(doctorList.size());
        for(Doctor doctor : doctorList){
            Hospital hospital = hospitalDao.get(doctor.getHospitalId());
            DoctorBySearch doctorBySearch = DoctorBySearch.parse(doctor,hospital);

            doctorBySearchList.add(doctorBySearch);
        }
        Paging<DoctorBySearch> doctorBySearchPaging = new Paging<DoctorBySearch>(doctorBySearchList, doctorPaging.getPn(), doctorPaging.getPageSize(), doctorPaging.getTotalSize());

        return doctorBySearchPaging;
    }

    /**
     * 根据公共搜索页的搜索条件查询医生分页
     * @param searchQo
     * @return
     */
    public Paging<HospitalBySearch> getHospitalBySearchPaging(SearchQo searchQo) {
        Paging<Hospital> hospitalPaging = hospitalDao.getHospitalPagingBy(searchQo);

        //视图转换
        List<Hospital> hospitalList = hospitalPaging.getItems();
        List<HospitalBySearch> hospitalBySearchList = new ArrayList<HospitalBySearch>(hospitalList.size());
        for(Hospital hospital : hospitalList){
            HospitalBySearch hospitalBySearch = HospitalBySearch.parse(hospital);
            hospitalBySearchList.add(hospitalBySearch);
        }
        Paging<HospitalBySearch> hospitalBySearchPaging = new Paging<HospitalBySearch>(hospitalBySearchList, hospitalPaging.getPn(), hospitalPaging.getPageSize(), hospitalPaging.getTotalSize());

        return hospitalBySearchPaging;
    }
}
