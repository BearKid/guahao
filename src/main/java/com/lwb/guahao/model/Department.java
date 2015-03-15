package com.lwb.guahao.model;

/**
 * Created by Lu Weibiao on 2015/2/16 17:47.
 */

import javax.persistence.*;
import java.io.Serializable;

/**
 * 科室 （待定）
 */
@Deprecated
public class Department implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer doctorNum; //医生人数

    @Column(nullable = false)
    private Integer classId; //科室类目编号 参见：ConstantsMap.department

    @Column(nullable = false)
    @JoinColumn(nullable = false, unique = true, referencedColumnName = "id", table = "hospital")
    private Integer hospitalId; //医院id
}