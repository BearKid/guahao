package com.lwb.guahao.webapp.controller.pub;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.webapp.component.PagingComponent;
import com.lwb.guahao.webapp.service.SearchService;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.HospitalVo;
import com.lwb.guahao.qo.SearchQo;
import org.apache.log4j.Logger;
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
    private final static Logger logger = Logger.getLogger(SearchController.class);
    @Resource
    private SearchService searchService;

    @Resource
    private PagingComponent pagingComponent;

    @RequestMapping(value = "")
    public String generalSearch(HttpServletRequest request, Model model){
        //组装查询条件
        SearchQo searchQo = new SearchQo();
        searchQo.setKeyWord(request.getParameter("keyWord"));

        String areaCodeStr = request.getParameter("areaCode");
        searchQo.setAreaCode(StringUtils.isEmpty(areaCodeStr) ? null : Integer.valueOf(areaCodeStr));

        String deptClassCodeStr = request.getParameter("deptClassCode");
        searchQo.setDeptClassCode(StringUtils.isEmpty(deptClassCodeStr) ? null : Integer.valueOf(deptClassCodeStr));

        String pnStr = request.getParameter("pn");
        searchQo.setPn(StringUtils.isEmpty(pnStr) ? null : Integer.valueOf(pnStr));
        searchQo.setPageSize(Constants.DEFAULT_PAGE_SIZE);

        //条件查询
        Paging<DoctorVo> doctorBySearchPaging = searchService.getDoctorPagingBySearch(searchQo);
        Paging<HospitalVo> hospitalBySearchPaging = searchService.getHospitalPagingBySearch(searchQo);

        String queryStringWithOutPn = pagingComponent.getQueryStringWithoutPn(request);

        model.addAttribute("doctorBySearchPaging",doctorBySearchPaging);
        model.addAttribute("hospitalBySearchPaging",hospitalBySearchPaging);
        model.addAttribute("searchQo",searchQo);
        model.addAttribute("queryStringWithOutPn",queryStringWithOutPn);
        model.addAttribute("queryStringWithOutAreaCode", getQueryStringWithOutAreaCode(request));
        model.addAttribute("queryStringWithOutDeptClassCode", getQueryStringWithOutDeptClassCode(request));
        model.addAttribute("queryStringWithOutKeyWord", getQueryStringWithOutKeyWord(request));
        return "/search/generalSearch";
    }

    private Object getQueryStringWithOutKeyWord(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString)) {
            Matcher matcher = Pattern.compile("(keyWord=.*?&)|(&?keyWord=.*?$)").matcher(queryString);
            queryString = matcher.replaceFirst("");
        }
        return queryString;
    }

    private String getQueryStringWithOutDeptClassCode(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString)) {
            Matcher matcher = Pattern.compile("(deptClassCode=\\d+?&)|(&?deptClassCode=\\d+?$)").matcher(queryString);
            queryString = matcher.replaceFirst("");
        }
        return queryString;
    }

    private String getQueryStringWithOutAreaCode(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString)) {
            Matcher matcher = Pattern.compile("(areaCode=\\d+?&)|(&?areaCode=\\d+?$)").matcher(queryString);
            queryString = matcher.replaceFirst("");
        }
        return queryString;
    }
}
