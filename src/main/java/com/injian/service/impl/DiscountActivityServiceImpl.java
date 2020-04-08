package com.injian.service.impl;

import com.injian.service.DiscountActivityService;
import org.springframework.stereotype.Service;

/**
 * @author Zhang Xinyu
 * @version 1.0
 * @date 2020/2/19 14:17
 */
@Service("discountActivityService")
public class DiscountActivityServiceImpl implements DiscountActivityService {
    @Override
    public Long addActivity(String id) {
        System.out.println("折扣活动添加");
        return -1L;
    }

    @Override
    public void upDateActivity(String id) {
        System.out.println("折扣活动更新");
    }
}
