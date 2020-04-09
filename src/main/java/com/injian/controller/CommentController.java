package com.injian.controller;

import com.injian.controller.viewobject.CommentVO;
import com.injian.error.BusinessException;
import com.injian.response.CommonReturnType;
import com.injian.service.CommentService;
import com.injian.service.model.CommentModel;
import com.injian.service.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@Api(tags = {"商品评价接口"})
@Controller("comment")
@RequestMapping("/comment")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class CommentController extends BaseController{
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "查看某一商品的商品评价")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商品Id", name = "itemId", dataType = "Integer")
    })
    @RequestMapping(value = "/listComment",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType commentList(@RequestParam(name = "itemId") Integer itemId) throws BusinessException {
        List<CommentModel> commentModelList = commentService.listItemComments(itemId);
        List<CommentVO> commentVOList = this.convertListFromModelList(commentModelList);
        return CommonReturnType.create(commentVOList);
    }

    @ApiOperation(value = "查看回复")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商品Id", name = "itemId", dataType = "Integer"),
            @ApiImplicitParam(value = "评论Id", name = "commentId", dataType = "Integer")
    })
    @RequestMapping(value = "/listReply",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType replyList(@RequestParam(name = "itemId") Integer itemId,
                                      @RequestParam(name = "commentId") Integer commentId) throws BusinessException {


        List<CommentModel> commentModelList = commentService.getReply(itemId,commentId);
        List<CommentVO> commentVOList = this.convertListFromModelList(commentModelList);
        return CommonReturnType.create(commentVOList);
    }

    @ApiOperation(value = "添加评论")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商品Id", name = "itemId", dataType = "Integer"),
            @ApiImplicitParam(value = "评论内容", name = "content", dataType = "String")
    })
    @RequestMapping(value = "/addComments",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addComment(@RequestParam(name = "itemId") Integer itemId,
                                       @RequestParam(name = "content") String content) throws BusinessException {
        UserModel userModel = validateUserLogin();
        CommentModel commentModel = new CommentModel();
        commentModel.setFromUserId(userModel.getId());
        commentModel.setToUserId(0);
        commentModel.setParentId(0);
        commentModel.setContent(content);
        commentModel.setItemId(itemId);
        commentModel.setCreateTime(new DateTime());
        commentService.addComment(commentModel);
        return CommonReturnType.create(null);
    }

    @ApiOperation(value = "添加回复")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商品Id", name = "itemId", dataType = "Integer"),
            @ApiImplicitParam(value = "回复内容", name = "content", dataType = "String"),
            @ApiImplicitParam(value = "父节点Id", name = "parentId", dataType = "Integer"),
            @ApiImplicitParam(value = "回复用户Id", name = "toUserId", dataType = "Integer")
    })
    @RequestMapping(value = "/addReply",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addReply(@RequestParam(name = "itemId") Integer itemId,
                                       @RequestParam(name = "content") String content,
                                       @RequestParam(name = "parentId") Integer parentId,
                                       @RequestParam(name = "toUserId") Integer toUserId) throws BusinessException {
        UserModel userModel = validateUserLogin();
        CommentModel commentModel = new CommentModel();
        commentModel.setFromUserId(userModel.getId());
        commentModel.setToUserId(toUserId);
        commentModel.setParentId(parentId);
        commentModel.setContent(content);
        commentModel.setItemId(itemId);
        commentModel.setCreateTime(new DateTime());
        commentService.addComment(commentModel);
        return CommonReturnType.create(null);
    }

    private CommentVO convertFromModel(CommentModel commentModel){
        if(commentModel == null){
            return null;
        }
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(commentModel,commentVO);
        commentVO.setCreateTime(commentModel.getCreateTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        return commentVO;
    }

    private List<CommentVO> convertListFromModelList(List<CommentModel> commentModelList){
        List<CommentVO> commentVOList = commentModelList.stream().map(commentModel -> {
            CommentVO commentVO = this.convertFromModel(commentModel);
            return commentVO;
        }).collect(Collectors.toList());
        return commentVOList;
    }
}
