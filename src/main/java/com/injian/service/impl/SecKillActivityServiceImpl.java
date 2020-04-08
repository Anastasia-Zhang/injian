package com.injian.service.impl;

import com.injian.service.SecKillActivityService;
import org.springframework.stereotype.Service;

/**
 * @author Zhang Xinyu
 * @version 1.0
 * @date 2020/2/19 14:20
 */
@Service("seckillActivityService")
public class SecKillActivityServiceImpl implements SecKillActivityService {
    @Override
    public Long addActivity(String id) {
        System.out.println("秒杀活动添加");
        return null;
    }

    @Override
    public void upDateActivity(String id) {
        System.out.println("秒杀活动更新");
    }
}
