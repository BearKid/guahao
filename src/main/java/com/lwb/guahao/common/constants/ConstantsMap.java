package com.lwb.guahao.common.constants;

/**
 * Created by Lu Weibiao on 2015/2/16 18:11.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 常量语义映射
 */
public class ConstantsMap {
    public static final Map<Integer, String> accountStatusMap = new HashMap();
    public static final Map<Integer, String> deptClassMap = new HashMap();
    public static final Map<Integer, String> areaMap = new HashMap();
    public static final Map<Integer, String> sexMap = new HashMap();

    static{
        /* 账户状态 */
        accountStatusMap.put(Constants.AccountStatus.NORMAL, "正常");
        accountStatusMap.put(Constants.AccountStatus.UN_VERIFIED, "未验证");
        accountStatusMap.put(Constants.AccountStatus.FORBIDDEN, "禁止");
        accountStatusMap.put(Constants.AccountStatus.DELETED, "不存在");

        /* 科室类目 (xxoo,科室名称)
         * 科室类目编号为四位数，xx规定一级，oo规定二级，xx00表示某一级类目
         */
        deptClassMap.put(1000,"内科");//内科
        deptClassMap.put(1001,"心血管内科");
        deptClassMap.put(1002,"呼吸内科");
        deptClassMap.put(1003,"消化内科");
        deptClassMap.put(1004,"神经内科");
        deptClassMap.put(1005,"肾内科");
        deptClassMap.put(1006,"血液科");
        deptClassMap.put(1007,"风湿免疫科");
        deptClassMap.put(1008,"感染科");
        deptClassMap.put(1009,"内分泌科");
        deptClassMap.put(1010,"过敏反应科");
        deptClassMap.put(1011,"普通内科");
        deptClassMap.put(1100,"外科");//外科
        deptClassMap.put(1101,"普外科");
        deptClassMap.put(1102,"胸外科");
        deptClassMap.put(1103,"心血管外科");
        deptClassMap.put(1104,"神经外科");
        deptClassMap.put(1105,"肝胆外科");
        deptClassMap.put(1106,"肛肠外科");
        deptClassMap.put(1107,"泌尿外科");
        deptClassMap.put(1108,"血管外科");
        deptClassMap.put(1109,"整形外科");
        deptClassMap.put(1110,"乳腺外科");
        deptClassMap.put(1111,"烧伤科");
        deptClassMap.put(1112,"器官移植");
        //TODO ：其他科室类目

        /* 行政区划代码
         * 第一、二位表示省（自治区、直辖市、特别行政区）。
         * 第三、四位表示市（地区、自治州、盟及国家直辖市所属市辖区和县的汇总码）。其中，01-20，51-70表示省直辖市；21-50表示地区（自治州、盟）。
         * 第五、六位表示县（市辖区、县级市、旗）。01-18表示市辖区或地区（自治州、盟）辖县级市；21-80表示县（旗）；81-99表示省直辖县级市。
         */
        //TODO : 补上剩余的地区
        areaMap.put(110000,"北京市");
        areaMap.put(120000,"天津市");
        areaMap.put(130000,"河北省");
        areaMap.put(130000,"山西省");
        areaMap.put(140000,"内蒙古自治区");
        areaMap.put(210000,"辽宁省");
        areaMap.put(220000,"吉林省");
        areaMap.put(230000,"黑龙江省");
        areaMap.put(310000,"上海市");
        areaMap.put(320000,"江苏省");
        areaMap.put(330000,"浙江省");
        areaMap.put(340000,"安徽省");
        areaMap.put(350000,"福建省");
        areaMap.put(360000,"江西省");
        areaMap.put(370000,"山东省");
        areaMap.put(380000,"河南省");
        areaMap.put(420000,"湖北省");
        areaMap.put(430000,"湖南省");

        areaMap.put(440000,"广东省"); //广东省
        areaMap.put(440100,"广州市");
        areaMap.put(440200,"韶关市");
        areaMap.put(440300,"深圳市");
        areaMap.put(440400,"珠海");
        areaMap.put(440500,"汕头市");
        areaMap.put(440600,"佛山市");
        areaMap.put(440700,"江门市");
        areaMap.put(440800,"湛江市");
        areaMap.put(440900,"茂名市");
        areaMap.put(441200,"肇庆市");
        areaMap.put(441300,"惠州市");
        areaMap.put(441400,"梅州市");
        areaMap.put(441500,"汕尾市");
        areaMap.put(441600,"河源市");
        areaMap.put(441700,"阳江市");
        areaMap.put(441800,"清远市");
        areaMap.put(441900,"东莞市");
        areaMap.put(442000,"中山市");
        areaMap.put(445100,"潮州市");
        areaMap.put(445200,"揭阳市");
        areaMap.put(445300,"云浮市");

        areaMap.put(450000,"广西壮族自治区");
        areaMap.put(460000,"海南省");
        areaMap.put(500000,"重庆市");
        areaMap.put(510000,"四川省");
        areaMap.put(520000,"贵州省");
        areaMap.put(530000,"云南省");
        areaMap.put(540000,"西藏自治区");
        areaMap.put(610000,"陕西省");
        areaMap.put(620000,"甘肃省");
        areaMap.put(630000,"青海省");
        areaMap.put(640000,"宁夏回族自治区");
        areaMap.put(650000,"新疆维吾尔自治区");
//        areaMap.put(710000,"台湾省");
//        areaMap.put(810000,"香港特别行政区");
//        areaMap.put(820000,"澳门特别行政区");

        /* 性别 */
        sexMap.put(Constants.SexType.MALE,"男");
        sexMap.put(Constants.SexType.FEMALE,"女");
    }
}
