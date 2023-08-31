package com.shangan.trade.web.portal.controller;

import com.shangan.trade.goods.domain.Goods;
import com.shangan.trade.goods.service.GoodsService;
import com.shangan.trade.goods.service.SearchService;
import com.shangan.trade.web.portal.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class PortalController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private SearchService searchService;

    @RequestMapping("/goods/{goodsId}")
    public ModelAndView itemPage(@PathVariable long goodsId){
        Goods goods = goodsService.queryGoodsById(goodsId);
        String showPrice = CommonUtils.changeF2Y(goods.getPrice());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("goods", goods);
        modelAndView.addObject("showPrice",showPrice);
        modelAndView.setViewName("goods_detail");
        return modelAndView;

    }

    @RequestMapping("/search")
    public String searchPage(){
        return "search";
    }

    @RequestMapping("/searchAction")
    public String search(@RequestParam("searchWords") String searchWords, Map<String, Object> resultMap){

        log.info("search searchWords:{}", searchWords);
        List<Goods> goodsList = searchService.searchGoodsList(searchWords, 0, 10);
        resultMap.put("goodsList",goodsList);
        return "search";

    }





}
