package com.lwb.guahao.webapp.controller;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.common.util.AreaUtil;
import com.lwb.guahao.common.util.DeptClassUtil;
import com.lwb.guahao.webapp.component.PagingComponent;
import com.lwb.guahao.webapp.service.SearchService;
import com.lwb.guahao.webapp.vo.search.DoctorBySearch;
import com.lwb.guahao.webapp.vo.search.HospitalBySearch;
import com.lwb.guahao.webapp.vo.search.SearchQo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p></p>
 * Date: 2015/4/27 19:04
 *
 * @version 1.0
 * @autor: Lu Weibiao
 */
@Controller
@RequestMapping(value="/search")
public class SearchController {
    @Resource
    private SearchService searchService;

    @Resource
    private PagingComponent pagingComponent;

    @RequestMapping(value = "")
    public String generalSearch(HttpServletRequest request, Model model){
        //组装查询条件
        SearchQo searchQo = new SearchQo();
        searchQo.setKeyWord(request.getParameter("keyWord"));
        searchQo.setAreaCode(Integer.valueOf(request.getParameter("areaCode")));
        searchQo.setDeptClassCode(Integer.valueOf(request.getParameter("keyWord")));
        Integer pn = Integer.valueOf(request.getParameter("pn"));
        searchQo.setPn(pn);
        searchQo.setPageSize(Constants.DEFAULT_PAGE_SIZE);

        //条件查询
        Paging<DoctorBySearch> doctorBySearchPaging = searchService.getDoctorBySearchPaging(searchQo);
        Paging<HospitalBySearch> hospitalBySearchPaging = searchService.getHospitalBySearchPaging(searchQo);

        String queryStringWithOutPn = pagingComponent.getQueryStringWithoutPn(request);

        model.addAttribute("doctorBySearchPaging",doctorBySearchPaging);
        model.addAttribute("hospitalBySearchPaging",hospitalBySearchPaging);
        model.addAttribute("searchQo",searchQo);
        model.addAttribute("queryStringWithOutPn",queryStringWithOutPn);
        model.addAttribute("queryStringWithOutAreaCode", getQueryStringWithOutAreaCode(request));
        model.addAttribute("queryStringWithOutDeptClassCode", getQueryStringWithOutDeptClassCode(request));
        model.addAttribute("queryStringWithOutKeyWord", getQueryStringWithOutKeyWord(request));
        model.addAttribute("areaList", AreaUtil.areaList);
        model.addAttribute("deptClassList", DeptClassUtil.deptClassList);
        return "/search/generalSearch";
    }

    private Object getQueryStringWithOutKeyWord(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString)) {
            Matcher matcher = Pattern.compile("keyWord=\\d+").matcher(queryString);
            queryString = matcher.replaceFirst("");
        }
        return queryString;
    }

    private String getQueryStringWithOutDeptClassCode(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString)) {
            Matcher matcher = Pattern.compile("deptClassCode=\\d+").matcher(queryString);
            queryString = matcher.replaceFirst("");
        }
        return queryString;
    }

    private String getQueryStringWithOutAreaCode(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString)) {
            Matcher matcher = Pattern.compile("areaCode=\\d+").matcher(queryString);
            queryString = matcher.replaceFirst("");
        }
        return queryString;
    }
}
