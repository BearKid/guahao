package com.lwb.guahao.common.option.util;

/**
 * Created by Lu Weibiao on 2015/2/18 22:03.
 */

import com.lwb.guahao.common.option.Area;
import com.lwb.guahao.common.option.OptionMap;
import org.apache.commons.logging.impl.Log4JLogger;

import java.util.*;

/**
 * 地区类工具
 */
public class AreaUtil {
    private final static AreaUtil areaUtil;
    private AreaUtil(){}
    static {
        areaUtil = new AreaUtil();
    }

    private final static int PROVINCE_MASK = 10000;
    private final static int CITY_MASK = 100;
    public final static List<Area> areaList = new ArrayList<Area>();

    static {
        /*
         * 预处理地区列表为树状列表，用于客户端呈现
         */
        for(Map.Entry<Integer,String> entry : OptionMap.areaMap.entrySet()){//遍历areaMap
            if(areaUtil.isProvice(entry.getKey())){ //新地区是省份
                Area newArea = new Area(entry.getKey(), entry.getValue(), true);//创建新地区为省份节点
                boolean isExisted =false;
                for(Area area : areaList){//检查地区是否已经在列表中
                    if(newArea.getCode() == area.getCode()){
                        isExisted = true;
                        break;
                    }
                }
                if(!isExisted) {
                    areaList.add(newArea);
                }
            } else if(areaUtil.isCity(entry.getKey())){ //新地区是城市
                Area newArea = new Area(entry.getKey(), entry.getValue());//创建新地区为城市节点
                int provinceCode = areaUtil.getProvinceCode(newArea.getCode()); //对应省份代码
                Area province = null;
                for(Area area : areaList){//查找对应省份Area
                    if(area.getCode() == provinceCode){
                        province = area;
                        break;
                    }
                }
                if(province == null){//如果对应省份Area不在列表中，创建并添加
                    province = new Area(provinceCode,getAreaName(provinceCode), true);
                    areaList.add(province);
                }
                province.getSubAreaList().add(newArea);//添加新地区到所属省份的子列表
            } else {
                //既不是省份节点，也不是城市节点。用于扩展至区、街道、村镇
                new Log4JLogger().error("当前地区节点既不是省份节点，又不是城市节点");
            }
        }
        adjustAreasOrder(areaList);//调整列表中地区的排列顺序
    }

    /**
     * 调整列表中地区的排列顺序
     * @param areaList
     */
    private static void adjustAreasOrder(List<Area> areaList){
        Collections.sort(areaList, new AreaComparatorByCode());//按地区代码大小排序
        for(Area area : areaList){ //调整子地区排列顺序
            if(area.getSubAreaList() != null){
                adjustAreasOrder(area.getSubAreaList());
            }
        }
    }
    /**
     * 地区Comparator - 按代码大小从小到大排序
     */
    private static class AreaComparatorByCode implements Comparator<Area> {
        @Override
        public int compare(Area o1, Area o2) {
            if(o1.getCode() == o2.getCode()) return 0;
            else if(o1.getCode() > o2.getCode()) return 1;
            else return -1;
        }
    }

    /**
     * 判断代码对应地区是否为省份
     * @param areaCode
     * @return
     */
    private boolean isProvice(int areaCode){
        return areaCode % PROVINCE_MASK == 0;
    }
    /**
     * 判断代码对应地区是否为城市
     * @param areaCode
     * @return
     */
    private boolean isCity(int areaCode){
        return areaCode % CITY_MASK == 0;
    }

    /**
     * 获取地区代码对应的省份代码
     * @param areaCode
     * @return
     */
    public static int getProvinceCode(int areaCode){
        return (areaCode / PROVINCE_MASK) * PROVINCE_MASK;
    }
    /**
     * 获取地区代码对应的城市代码
     * @param areaCode
     * @return
     */
    public static int getCityCode(int areaCode){
        return (areaCode / CITY_MASK) * CITY_MASK;
    }

    /**
     * 获取地区代码对应地区的中文名称
     * @param areaCode
     * @return
     */
    public static String getAreaName(int areaCode){
        return OptionMap.areaMap.get(areaCode);
    }
}
