package com.shangan.trade.goods.dao.impl;

import com.shangan.trade.goods.dao.GoodsDao;
import com.shangan.trade.goods.domain.Goods;
import com.shangan.trade.goods.mapper.GoodsMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class GoodsDaoImpl implements GoodsDao {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public boolean insertGoods(Goods goods) {
        int result = goodsMapper.insert(goods);
        if(result >0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteGoods(long id) {
        int result = goodsMapper.deleteByPrimaryKey(id);
        return result > 0 ;
    }

    @Override
    public Goods queryGoodsById(long id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        return goods;
    }

    @Override
    public boolean updateGoods(Goods goods) {
        int result = goodsMapper.updateByPrimaryKey(goods);
        return result>0;
    }
}
