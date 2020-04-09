package com.injian.controller;

import com.injian.controller.viewobject.ActivityVO;
import com.injian.service.ActivityTypeEnum;
import com.injian.service.BaseActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Zhang Xinyu
 * @version 1.0
 * @date 2020/2/19 14:29
 */
@Controller("activity")
@Api(tags = {"活动接口"})
@RequestMapping("/activity")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ActivityController {


    //注入BaseActivityService子类的service,key为子类service的key

    @Autowired
    private Map<String, BaseActivityService> activityServiceMap;

    @ApiOperation(value = "查看活动类型")
    @RequestMapping(value = "/test",method = {RequestMethod.GET})
    @ResponseBody
    public String test(){
        ActivityVO vo = new ActivityVO();
        vo.setActivityType(ActivityTypeEnum.SECKILL.getKey());//设置活动类型为秒杀测试用
        //1,根据活动对象获取活动类型,
        Integer activityType = vo.getActivityType();
        //2,获取活动对象的service的key
        String servicekey = ActivityTypeEnum.getEnumByType(activityType).getService();
        System.out.println(servicekey);
        //3,获取对应的活动service
        BaseActivityService service = activityServiceMap.get(servicekey);
        //4,调用具体活动service的方法;add或者update等等
        if (service == null) {
            System.out.println("null service");
            return "null";
        } else {
            service.addActivity("1");
            service.upDateActivity("1");
            return "success";
        }

    }


}
