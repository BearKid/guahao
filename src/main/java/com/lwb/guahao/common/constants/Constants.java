package com.lwb.guahao.common.constants;

/**
 * Created by Lu Weibiao on 2015/2/16 14:18.
 */

/**
 * 常量
 */
public class Constants {
    public static final int DEFAULT_PAGE_SIZE = 10;//默认结果集分页大小
    public static final int INFINITE_PAGE_SIZE = -1;//结果集分页大小-一页显示全部

    //性别类型
    public static final class SexType{
        public static final int MALE = 1;
        public static final int FEMALE = 2;
    }
    public static final class AccountType {
        public static final int PER_USER = 0;
        public static final int HOSPITAL = 1;
        public static final int DOCTOR = 2;
        public static final int UNKNOWN = -1;
    }
    //登录信息
    public static final String LOGIN_HOSPITAL_ID = "login:hospital:id";
    //账户状态
    public static class AccountStatus {
        public static final int NORMAL = 0; //正常
        public static final int UN_VERIFIED = 1; //未验证
        public static final int FORBIDDEN = 2; //禁止
        public static final int DELETED = -1; //不存在
    }

    //订单状态
    public static class OrderStatus {
        public static final int UN_PAYED  = 0; //已下单，等待支付
        public static final int PAYED = 1; //已支付，待应约
        public static final int PRESENT = 2; //应约完成就诊
        public static final int ABSENCE = 3; //爽约未就诊
        public static final int CANCEL = 4; //取消预约
        public static final int EXPIRED = 5; //超过1小时未支付，订单过期。
    }
}
