package com.shangan.trade.web.manager.controller;

import com.shangan.trade.goods.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class ManagerController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping("/index")
    public String index(){
        return "index";

    }

    //接收前端传来的参数



}
