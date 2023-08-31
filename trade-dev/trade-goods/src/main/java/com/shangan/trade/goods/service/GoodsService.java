package com.shangan.trade.goods.service;

import com.shangan.trade.goods.domain.Goods;

public interface GoodsService {

    boolean insertGoods(Goods goods);

    Goods queryGoodsById(long id);
}
