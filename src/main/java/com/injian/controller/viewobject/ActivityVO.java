package com.injian.controller.viewobject;

/**
 * @author Zhang Xinyu
 * @version 1.0
 * @date 2020/2/19 14:33
 */
public class ActivityVO {

    /**
     * 活动id
     */
    private Long id;
    /**
     * 活动类型:1,秒杀活动;2折扣活动
     */
    private Integer activityType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }
}
