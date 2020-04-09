package com.injian.controller;


import com.injian.controller.viewobject.ItemVO;
import com.injian.error.BusinessException;
import com.injian.response.CommonReturnType;
import com.injian.service.ItemService;
import com.injian.service.model.ItemModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@Api(tags = {"商品接口"})
@Controller("item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    //创建商品
    @ApiOperation(value = "添加商品")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商品名称", name = "title", dataType = "String"),
            @ApiImplicitParam(value = "商品描述", name = "description", dataType = "String"),
            @ApiImplicitParam(value = "价格", name = "price", dataType = "BigDecimal"),
            @ApiImplicitParam(value = "库存", name = "stock", dataType = "Integer"),
            @ApiImplicitParam(value = "商品图片Url", name = "imgUrl", dataType = "String"),
            @ApiImplicitParam(value = "商品种类", name = "category", dataType = "Integer")
    })
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl,
                                       @RequestParam(name = "category") Integer category) throws BusinessException {
        //封装service请求创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        itemModel.setCategoryId(category);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);

        ItemVO itemVO = convertVOFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVO);
    }

    //商品详情页浏览
    @ApiOperation(value = "查询商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商品Id", name = "id", dataType = "Integer"),
    })
    @RequestMapping(value = "/get",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id)
    {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    //商品列表浏览页面
    @ApiOperation(value = "查询商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "类目Id", name = "categoryId", dataType = "Integer"),
    })
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(@RequestParam(name = "categoryId") Integer categoryId){
        List<ItemModel> itemModelList = itemService.listItem(categoryId);
        //将List内的model转化成itemVO并放进一个list里面
        List<ItemVO> itemVOList = this.convertVOListFromModel(itemModelList);
        return CommonReturnType.create(itemVOList);
    }

    //搜索商品
    @ApiOperation(value = "搜索商品")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "关键字", name = "keyWord", dataType = "String"),
    })
    @RequestMapping(value = "/search",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType searchItem(@RequestParam(name = "keyWord" )String keyWord){
        System.out.println(keyWord);
        List<ItemModel> itemModelList = itemService.searchItem(keyWord);
        List<ItemVO> itemVOList = this.convertVOListFromModel(itemModelList);
        System.out.println(itemVOList.size());
        return CommonReturnType.create(itemVOList);
    }
    //活动商品
    @ApiOperation(value = "活动商品展示")
    @RequestMapping(value = "/promo",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType promoItem(){
        List<ItemModel> itemModelList = itemService.promoItem();
        List<ItemVO> itemVOList = this.convertVOListFromModel(itemModelList);
        return CommonReturnType.create(itemVOList);
    }




    private ItemVO convertVOFromModel(ItemModel itemModel){
        if(itemModel == null)
        {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        if(itemModel.getPromoModel()!=null){
            //有正在进行或者即将进行的秒杀活动
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            itemVO.setPromoStatus(0);
        }

        return itemVO;
    }

    private List<ItemVO> convertVOListFromModel(List<ItemModel> itemModelList){
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return itemVOList;
    }
}
