package com.injian.controller;



import com.injian.controller.viewobject.ShopCarListVO;
import com.injian.controller.viewobject.ShopCarProductVO;
import com.injian.error.BusinessException;
import com.injian.response.CommonReturnType;
import com.injian.service.ShopCarService;
import com.injian.service.model.ShopCarModel;
import com.injian.service.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"购物车接口"})
@Controller("shopCar")
@RequestMapping("/shopCar")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ShopCarController extends BaseController{

    @Autowired
    private ShopCarService shopCarService;



    @ApiOperation(value = "添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商品Id", name = "itemId", dataType = "Integer"),
            @ApiImplicitParam(value = "促销Id", name = "promoId", dataType = "Integer"),
            @ApiImplicitParam(value = "商品数量", name = "amount", dataType = "Integer")
    })
    @RequestMapping(value = "/addShopCar",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "promoId",required = false)Integer promoId,
                                        @RequestParam(name = "amount")Integer amount) throws BusinessException {

        //判断用户是否登录
        UserModel userModel = validateUserLogin();
        //加入购物车
        ShopCarModel shopCarModel = shopCarService.addShopCar(userModel.getId(),itemId,promoId,amount);
        return CommonReturnType.create(null);
    }

    //修改商品数量
    @ApiOperation(value = "更新购物车")
    @RequestMapping(value = "/updateShopCarItemAmount",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    @CrossOrigin(origins = {"*"},allowCredentials = "true")
    public CommonReturnType UpdateItemAmount(@RequestParam(name = "itemId")Integer itemId,
                                             @RequestParam(name = "amount")Integer amount) throws BusinessException {

        //判断用户是否登录
        UserModel userModel = validateUserLogin();
        //修改购物车数量
        shopCarService.updateAmount(userModel.getId(),itemId,amount);
        return CommonReturnType.create(null);
    }

    //删除购物车单个商品
    @ApiOperation(value = "删除购物车商品")
    @RequestMapping(value = "/delShopCarItem",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    @CrossOrigin(origins = {"*"},allowCredentials = "true")
    public CommonReturnType deleteItem(@RequestParam(name = "itemId")Integer itemId) throws BusinessException {

        //判断用户是否登录
        UserModel userModel = validateUserLogin();
        //修改购物车数量
        shopCarService.delItem(userModel.getId(),itemId);
        return CommonReturnType.create(null);
    }

    @ApiOperation(value = "查看用户购物车")
    @RequestMapping(value = "/userShopCarlist",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getShopCarList() throws BusinessException {
        UserModel userModel = validateUserLogin();
        List<ShopCarModel> shopCarModelList = shopCarService.getShopCar(userModel.getId());
        //将List内的model转化成itemVO并放进一个list里面
        ShopCarListVO shopCarListVO = new ShopCarListVO();
        List<ShopCarProductVO>  shopCarProductVOList = shopCarModelList.stream().map(shopCarModel -> {
            ShopCarProductVO shopCarProductVO = this.convertShopCarVOFromModel(shopCarModel);
            return shopCarProductVO;
        }).collect(Collectors.toList());
        shopCarListVO.setShopCarProductVoLists(shopCarProductVOList);
        return CommonReturnType.create(shopCarListVO);
    }

    private ShopCarProductVO convertShopCarVOFromModel(ShopCarModel shopCarModel){
        if(shopCarModel == null){
            return null;
        }
        ShopCarProductVO shopCarProductVO = new ShopCarProductVO();
        BeanUtils.copyProperties(shopCarModel,shopCarProductVO);
        shopCarProductVO.setProductChecked(0);//设置商品选中的初始状态
        return  shopCarProductVO;
    }
}
