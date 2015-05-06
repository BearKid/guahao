package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.model.Department;
import com.lwb.guahao.common.model.Doctor;
import com.lwb.guahao.common.qo.SearchQo;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 22:47
 */
@Repository
public class DoctorDao extends BaseHibernateDao<Doctor> {
    public Doctor uniqueByAccountAndPwd(final String accountName, final String pwd) {
        String hql = "from Doctor as d where d.accountName = ? and d.password = ?";
        Object[] params = new Object[]{
                accountName, pwd
        };
        return (Doctor) unique(hql, params);
    }

    public Paging<Doctor> getDoctorsPagingBy(final Integer hospitalId, final String name, final Integer deptClasCode, final String accountName,
                                           final int pn, final int pageSize) {
        final String selectHql = "select d";
        final String countHql = "select count(*)";
        final StringBuilder fromHqlBuilder = new StringBuilder(" from Doctor d where 1=1");
        final List params = new ArrayList();

        if (hospitalId != null) {
            fromHqlBuilder.append(" and d.hospital.id = ?");
            params.add(hospitalId);
        }
        if (!StringUtils.isEmpty(name)) {
            fromHqlBuilder.append(" and d.name = ?");
            params.add(name);
        }
        if (deptClasCode != null) {
            fromHqlBuilder.append(" and d.deptClassCode = ?");
            params.add(deptClasCode);
        }
        if (!StringUtils.isEmpty(accountName)) {
            fromHqlBuilder.append(" and d.accountName = ?");
            params.add(accountName);
        }
        fromHqlBuilder.append(" order by d.createDateTime");

        final String finalSelectHql = selectHql + fromHqlBuilder.toString();
        List<Doctor> doctors = pagingQuery(finalSelectHql, params, (pn-1)*pageSize, pageSize);

        final String finalCountHql = countHql + fromHqlBuilder.toString();

        Long totalSize = (Long)unique(finalCountHql,params);

        return new Paging<Doctor>(doctors,pn,pageSize,totalSize.intValue());
    }

    /**
     * 公共搜索页的搜索条件查询医生分页
     * @param searchQo
     * @return
     */
    public Paging<Doctor> getDoctorsPagingBy(final SearchQo searchQo) {
        Integer pn = searchQo.getPn();
        if(pn == null){
            pn = 1;
        }

        Integer pageSize = searchQo.getPageSize();
        if(pageSize == null){
            pageSize = Constants.INFINITE_PAGE_SIZE;
        }

        final String selectHql = "select d";
        final String countHql = "select count(*)";
        final StringBuilder fromHqlBuilder = new StringBuilder(" from Doctor d where 1=1");
        final List params = new ArrayList();

        Integer areaCode = searchQo.getAreaCode();
        if(areaCode != null){
            fromHqlBuilder.append(" and d.hospital.areaCode = ?");
            params.add(areaCode);
        }

        Integer deptClassCode = searchQo.getDeptClassCode();
        if(deptClassCode != null){
            fromHqlBuilder.append(" and d.deptClassCode = ?");
            params.add(deptClassCode);
        }

        Integer hospitalId = searchQo.getHospitalId();
        if(hospitalId != null){
            fromHqlBuilder.append(" and d.hospitalId = ?");
            params.add(hospitalId);
        }

        String keyWord = searchQo.getKeyWord();
        if(!StringUtils.isEmpty(keyWord)){
            fromHqlBuilder.append(" and (d.name like ? or d.goodAtTags like ? or d.brief like ?)");
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");
        }

        List<Doctor> doctorList = pagingQuery(selectHql + fromHqlBuilder.toString(),params, (pn -1) * pageSize, pageSize);
        Long totalSize = (Long)unique(countHql + fromHqlBuilder.toString(),params);
        Paging<Doctor> doctorPaging = new Paging<Doctor>(doctorList,pn,pageSize,totalSize.intValue());

        return doctorPaging;
    }

    /**
     * 获取指定医院的所有科室
     * @param hospitalId
     * @return
     */
    public List<Department> getDepartmentListOfAHospital(final Integer hospitalId) {
        final String hql = "select new map(d.deptClassCode as deptClassCode, count(*) as doctorNum) from Doctor d where d.hospitalId = ? group by d.deptClassCode";
        final Object[] params = new Object[]{
                hospitalId
        };

        List<Map<String,Object>> mapList = (List<Map<String,Object>>)hibernateTemplate.find(hql,params);
        List<Department> departmentList = new ArrayList<Department>(mapList.size());
        for(Map<String,Object> row : mapList){
            Department department = new Department();
            department.setDeptClassCode((Integer)row.get("deptClassCode"));
            department.setDoctorNum(((Long)row.get("doctorNum")).intValue());
            department.setHospitalId(hospitalId);
            departmentList.add(department);
        }
        return departmentList;
    }

    /**
     * 判断指定的账号名是否存在
     * @param accountName
     * @return
     */
    public boolean existsAccountName(String accountName) {
        String hql = "select d.id from Doctor d where d.accountName = ?";
        Integer id = (Integer)unique(hql,new Object[]{accountName});
        return id != null;
    }
}
