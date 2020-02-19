package com.injian.service;

/**
 * @author Zhang Xinyu
 * @version 1.0
 * @date 2020/2/19 14:12
 */
public interface BaseActivityService {
    /**
     * 添加活动
     * @return
     */
    // ActivityModel
    Long addActivity(String id);
    /**
     * 更新活动
     * @return
     */
    // ActivityModel
    void upDateActivity(String id);

}
