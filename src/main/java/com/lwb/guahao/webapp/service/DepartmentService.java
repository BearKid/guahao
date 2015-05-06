package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.util.DeptClassUtil;
import com.lwb.guahao.model.Department;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.vo.DepartmentVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Lu Weibiao
 * Date: 2015/5/5 18:10
 */
@Service
@Transactional
public class DepartmentService {
    private final static Logger logger = Logger.getLogger(DepartmentService.class);
    @Resource
    private DoctorDao doctorDao;

    public List<DepartmentVo> getDepartmentTree(Integer hospitalId) {
        List<Department> departmentList = doctorDao.getDepartmentListOfAHospital(hospitalId);

        int maxListSize = departmentList.size();
        List<DepartmentVo> departmentVoList = new ArrayList<DepartmentVo>(maxListSize);
        for(Department department : departmentList){
            Integer deptClassCode = department.getDeptClassCode();
            Integer doctorNum = department.getDoctorNum();

            //如果找不到父级科室，新建父级科室
            Integer parentClassCode = DeptClassUtil.getParentDeptClassCode(deptClassCode);
            DepartmentVo parentDeptVo = findParentDeptVo(departmentVoList, deptClassCode);
            if(parentDeptVo == null){
                parentDeptVo = new DepartmentVo();
                parentDeptVo.setDeptClassCode(parentClassCode == null ? null : parentClassCode.toString());
                parentDeptVo.setDeptClassName(DeptClassUtil.getDeptClassName(parentClassCode));
                departmentVoList.add(parentDeptVo);
            }
            //如果同级科室列表为null,新建空的同级科室列表
            List<DepartmentVo> siblingDeptList = parentDeptVo.getSubDepartmentList();
            if(siblingDeptList == null){
                siblingDeptList = new ArrayList<DepartmentVo>(maxListSize);
                parentDeptVo.setSubDepartmentList(siblingDeptList);
            }
            //添加至同级科室列表
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDeptClassCode(deptClassCode == null ? null : deptClassCode.toString());
            departmentVo.setDeptClassName(DeptClassUtil.getDeptClassName(deptClassCode));
            departmentVo.setDoctorNum(doctorNum == null ? null : doctorNum.toString());
            departmentVo.setSubDepartmentList(null);
            siblingDeptList.add(departmentVo);
        }
        return departmentVoList;
    }

    /**
     * 查找指定科室类目的父级科室对象
     * 如果父级科室对象不在树型列表源，返回null
     * 备注:支持后续科室层级扩展
     * @param departmentList 树型科室类目列表源
     * @param deptClassCode 指定的科室类目
     * @return
     */
    private DepartmentVo findParentDeptVo(final List<DepartmentVo> departmentList, final Integer deptClassCode) {
        DepartmentVo parentDepartmentVo = null;
        if(DeptClassUtil.isSecondDeptClass(deptClassCode)){//二级科室，则直接找顶级科室
            String parentDeptClassCodeStr = DeptClassUtil.getParentDeptClassCode(deptClassCode).toString();
            for(DepartmentVo parentDepartmentVoTemp : departmentList){
                if(parentDeptClassCodeStr.equals(parentDepartmentVoTemp.getDeptClassCode())){
                    parentDepartmentVo = parentDepartmentVoTemp;
                    break;
                }
            }
        } else if(DeptClassUtil.isFirstDeptClass(deptClassCode)){
            throw new IllegalArgumentException("顶级科室类目[" + deptClassCode + "]没有父级");
        } else{
            throw new IllegalArgumentException("无效的科室类目编号[" + deptClassCode + "]");
        }
        return parentDepartmentVo;
    }
}
