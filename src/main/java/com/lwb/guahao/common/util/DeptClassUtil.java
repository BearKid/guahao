package com.lwb.guahao.common.util;

import com.lwb.guahao.common.DeptClass;
import com.lwb.guahao.common.constants.ConstantsMap;
import org.apache.commons.logging.impl.Log4JLogger;

import java.util.*;

/**
 * User: Lu Weibiao
 * Date: 2015/3/15 19:44
 */
public class DeptClassUtil {
    private final static int FIRST_DEPT_CLASS_MASK = 100;
    private final static int SECOND_DEPT_CLASS_MASK = 1;
    public final static List<DeptClass> deptClassList = new ArrayList<DeptClass>();
    static {
        /*
         * 预处理科室类目列表为树状列表，用于客户端呈现
         */
        for(Map.Entry<Integer,String> entry : ConstantsMap.deptClassMap.entrySet()){//遍历areaMap
            if(DeptClassUtil.isFirstDeptClass(entry.getKey())){ //新科室类目是一级科室
                DeptClass newDeptClass = new DeptClass(entry.getKey(), entry.getValue(), true);//创建新科室类目为一级科室节点
                boolean isExisted =false;
                for(DeptClass area : deptClassList){//检查科室类目是否已经在列表中
                    if(newDeptClass.getCode() == area.getCode()){
                        isExisted = true;
                        break;
                    }
                }
                if(!isExisted) {
                    deptClassList.add(newDeptClass);
                }
            } else if(DeptClassUtil.isSecondDeptClass(entry.getKey())){ //新科室类目是二级科室
                DeptClass newDeptClass = new DeptClass(entry.getKey(), entry.getValue());//创建新科室类目为二级科室节点
                int firstDeptClassCode = DeptClassUtil.getFirstDeptClassCode(newDeptClass.getCode()); //对应一级科室代码
                DeptClass firstDeptClass = null;
                for(DeptClass area : deptClassList){//查找对应一级科室DeptClass
                    if(area.getCode() == firstDeptClassCode){
                        firstDeptClass = area;
                        break;
                    }
                }
                if(firstDeptClass == null){//如果对应一级科室DeptClass不在列表中，创建并添加
                    firstDeptClass = new DeptClass(firstDeptClassCode,getDeptClassName(firstDeptClassCode), true);
                    deptClassList.add(firstDeptClass);
                }
                firstDeptClass.getSubDeptClassList().add(newDeptClass);//添加新科室类目到所属一级科室的子列表
            } else {
                //既不是一级科室节点，也不是二级科室节点。用于扩展至下级科室
                new Log4JLogger().error("当前科室类目节点既不是一级科室节点，又不是二级科室节点");
            }
        }
        adjustDeptClassesOrder(deptClassList);//调整列表中科室类目的排列顺序
    }

    private static void adjustDeptClassesOrder(List<DeptClass> deptClassList) {
        Collections.sort(deptClassList, new DeptClassComparatorByCode());//按科室类目代码大小排序
        for(DeptClass deptClass : deptClassList){ //调整子科室类目排列顺序
            if(deptClass.getSubDeptClassList() != null){
                adjustDeptClassesOrder(deptClass.getSubDeptClassList());
            }
        }
    }
    /**
     * 科室类目Comparator - 按代码大小从小到大排序
     */
    private static class DeptClassComparatorByCode implements Comparator<DeptClass> {
        @Override
        public int compare(DeptClass o1, DeptClass o2) {
            if(o1.getCode() == o2.getCode()) return 0;
            else if(o1.getCode() > o2.getCode()) return 1;
            else return -1;
        }
    }

    public static String getDeptClassName(int deptClassCode) {
        return ConstantsMap.deptClassMap.get(deptClassCode);
    }

    private static int getFirstDeptClassCode(int deptClassCode) {
        return (deptClassCode / FIRST_DEPT_CLASS_MASK) * FIRST_DEPT_CLASS_MASK;
    }

    private static boolean isSecondDeptClass(Integer deptClassCode) {
        return ((deptClassCode % FIRST_DEPT_CLASS_MASK) != 0) && ((deptClassCode % SECOND_DEPT_CLASS_MASK) == 0);
    }

    private static boolean isFirstDeptClass(Integer deptClassCode) {
        return (deptClassCode % FIRST_DEPT_CLASS_MASK) == 0;
    }
}
