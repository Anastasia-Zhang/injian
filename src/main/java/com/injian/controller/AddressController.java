package com.injian.controller;


import com.injian.controller.viewobject.AddressVO;
import com.injian.error.BusinessException;
import com.injian.response.CommonReturnType;
import com.injian.service.AddressService;
import com.injian.service.model.AddressModel;
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

@Api(tags = {"地址接口"})
@Controller("address")
@RequestMapping("/address")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class AddressController extends BaseController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "添加收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "详细地址", name = "addressDetail", dataType = "String"),
            @ApiImplicitParam(value = "邮政编码", name = "zipCode", dataType = "String"),
            @ApiImplicitParam(value = "收货人姓名", name = "rcvdName", dataType = "String"),
            @ApiImplicitParam(value = "收货人电话", name = "rcvdTel", dataType = "String")
    })
    @RequestMapping(value = "/addAddress",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addAddress(@RequestParam(name = "addressDetail") String addressDetail,
                                       @RequestParam(name = "zipCode") String zipCode,
                                       @RequestParam(name = "rcvdName") String rcvdName,
                                       @RequestParam(name = "rcvdTel") String rcvdTel) throws BusinessException {

        UserModel userModel = validateUserLogin();
        AddressModel addressModel = new AddressModel();
        addressModel.setUserId(userModel.getId());
        addressModel.setAddressDetail(addressDetail);
        addressModel.setZipCode(zipCode);
        addressModel.setRcvdName(rcvdName);
        addressModel.setRcvdTel(rcvdTel);

        addressService.addAddress(addressModel);

        return CommonReturnType.create(null);
    }

    @ApiOperation(value = "查询收货地址")
    @RequestMapping(value = "/listAddress",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType AddressList() throws BusinessException {
       UserModel userModel =  validateUserLogin();
        List<AddressModel> addressModelList = addressService.listAddress(userModel.getId());
        //将List内的model转化成itemVO并放进一个list里面
        List<AddressVO> addressVOList = addressModelList.stream().map(addressModel ->{
            AddressVO addressVO = this.convertVOFromModel(addressModel);
            return addressVO;
        }).collect(Collectors.toList());

        return CommonReturnType.create(addressVOList);
    }

    @ApiOperation(value = "删除收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "地址Id", name = "addressId", dataType = "String")
    })
    @RequestMapping(value = "/delAddress",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType delAddress(@RequestParam(name = "addressId") String addressId) throws BusinessException {
        UserModel userModel = validateUserLogin();
        addressService.delAddress(userModel.getId(),Integer.valueOf(addressId));
        return  CommonReturnType.create(null);
    }


    @ApiOperation(value = "更新收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "地址Id", name = "addressId", dataType = "String"),
            @ApiImplicitParam(value = "详细地址", name = "addressDetail", dataType = "String"),
            @ApiImplicitParam(value = "邮政编码", name = "zipCode", dataType = "String"),
            @ApiImplicitParam(value = "收货人姓名", name = "rcvdName", dataType = "String"),
            @ApiImplicitParam(value = "收货人电话", name = "rcvdTel", dataType = "String")
    })
    @RequestMapping(value = "/updateAddress",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType updateAddress(@RequestParam(name = "addressId") Integer addressId,
                                          @RequestParam(name = "addressDetail") String addressDetail,
                                          @RequestParam(name = "zipCode") String zipCode,
                                          @RequestParam(name = "rcvdName") String rcvdName,
                                          @RequestParam(name = "rcvdTel") String rcvdTel) throws BusinessException{

        UserModel userModel = validateUserLogin();
        AddressModel addressModel = addressService.getAddressInfoById(addressId);
        //更新model值
        if(!addressModel.getAddressDetail().equals(addressDetail)){
            addressModel.setAddressDetail(addressDetail);
        }
        if(!addressModel.getZipCode().equals(zipCode)){
            addressModel.setZipCode(zipCode);
        }
        if(!addressModel.getRcvdName().equals(rcvdName)){
            addressModel.setRcvdName(rcvdName);
        }
        if(!addressModel.getRcvdTel().equals(rcvdTel)){
            addressModel.setRcvdTel(rcvdTel);
        }
        addressService.updateAddress(userModel.getId(),addressModel);
        return CommonReturnType.create(null);
    }



    private AddressVO convertVOFromModel(AddressModel addressModel){
        AddressVO addressVO = new AddressVO();
        BeanUtils.copyProperties(addressModel,addressVO);
        return addressVO;
    }
}
