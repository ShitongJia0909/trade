package com.shangan.trade.goods.service.impl;

import com.shangan.trade.goods.dao.GoodsDao;
import com.shangan.trade.goods.domain.Goods;
import com.shangan.trade.goods.service.GoodsService;
import com.shangan.trade.goods.service.SearchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsDao goodsDao;
    @Resource
    private SearchService searchService;

    @Override
    public boolean insertGoods(Goods goods) {

        boolean b = goodsDao.insertGoods(goods);
        searchService.addGoodsToEs(goods);
        return b;
    }

    @Override
    public Goods queryGoodsById(long id) {
        return goodsDao.queryGoodsById(id);
    }
}
