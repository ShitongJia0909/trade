package com.shangan.trade.goods.dao;

import com.shangan.trade.goods.domain.Goods;

public interface GoodsDao {

    boolean insertGoods(Goods goods);

    boolean deleteGoods(long id);

    Goods queryGoodsById(long id);

    boolean updateGoods(Goods goods);

}
