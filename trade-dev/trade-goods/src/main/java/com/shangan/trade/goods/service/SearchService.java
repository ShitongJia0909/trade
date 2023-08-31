package com.shangan.trade.goods.service;

import com.shangan.trade.goods.domain.Goods;

import java.util.List;

public interface SearchService {

    void addGoodsToEs(Goods goods);

    List<Goods> searchGoodsList(String keyword, int from, int size);




}
