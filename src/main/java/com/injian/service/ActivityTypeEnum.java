package com.injian.service;

import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * @author Zhang Xinyu
 * @version 1.0
 * @date 2020/2/19 14:22
 */

public enum ActivityTypeEnum {


    /**
     * 1,秒杀活动;service的key即为seckillActivityService
     */
    SECKILL(1, "秒杀活动", "seckillActivityService"),

    /**
     * 2,折扣活动;service的key即为discountActivityService
     */
    DISCOUNT(2, "折扣活动", "discountActivityService");

    private Integer key;

    private String desc;

    /**
     * 对应的service实现类key
     */
    private String service;

    ActivityTypeEnum(Integer key, String desc, String service) {
        this.key = key;
        this.desc = desc;
        this.service = service;
    }

    public static ActivityTypeEnum getEnumByType(Integer key) {
        for (ActivityTypeEnum item : ActivityTypeEnum.values()) {
            if (item.getKey().equals(key)) {
                return item;
            }
        }
        throw new RuntimeException("通过key获取枚举类异常");
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
