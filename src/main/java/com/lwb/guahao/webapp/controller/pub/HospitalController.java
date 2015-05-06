package com.lwb.guahao.webapp.controller.pub;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.option.util.DeptClassUtil;
import com.lwb.guahao.webapp.service.DepartmentService;
import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.service.SearchService;
import com.lwb.guahao.webapp.vo.DepartmentVo;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.HospitalVo;
import com.lwb.guahao.common.qo.SearchQo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/5/5 17:06
 */
@Controller(value = "publicHospitalController")
@RequestMapping(value = "/hospital")
public class HospitalController {
    private final static Logger logger = Logger.getLogger(HospitalController.class);
    @Resource
    private HospitalService hospitalService;
    @Resource
    private SearchService searchService;
    @Resource
    private DepartmentService departmentService;

    @RequestMapping(value = "{hospitalId}/detail")
    public String hospitalDetail(HttpServletRequest request, Model model, @PathVariable Integer hospitalId){
        HospitalVo hospitalVo = hospitalService.getHospital(hospitalId);
        List<DepartmentVo> departmentList = departmentService.getDepartmentTree(hospitalId);

        model.addAttribute("hospital",hospitalVo);
        model.addAttribute("departmentList",departmentList);
        return "/pub/hospital/detail";
    }

    @RequestMapping(value = "{hospitalId}/deptment/{deptClassCode}")
    public String deptment(HttpServletRequest request,Model model, @PathVariable Integer hospitalId, @PathVariable Integer deptClassCode) {
        SearchQo searchQo = new SearchQo();
        searchQo.setHospitalId(hospitalId);
        searchQo.setDeptClassCode(deptClassCode);
        searchQo.setPn(1);
        searchQo.setPageSize(Constants.INFINITE_PAGE_SIZE);

        Paging<DoctorVo> doctorVoPaging = searchService.getDoctorPagingBySearch(searchQo);
        List<DoctorVo> doctorList = doctorVoPaging == null ? null : doctorVoPaging.getItems();

        DepartmentVo departmentVo = new DepartmentVo();
        departmentVo.setDeptClassCode(deptClassCode == null ? null : deptClassCode.toString());
        departmentVo.setDeptClassName(DeptClassUtil.getDeptClassName(deptClassCode));

        model.addAttribute("doctorList",doctorList);
        model.addAttribute("department",departmentVo);
        return "/pub/hospital/deptment";
    }
}
