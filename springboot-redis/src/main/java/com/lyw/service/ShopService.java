package com.lyw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyw.dto.Result;
import com.lyw.pojo.Shop;

/**
* @author liuya
* @description 针对表【tb_shop】的数据库操作Service
* @createDate 2024-06-19 09:22:40
*/
public interface ShopService extends IService<Shop> {

    Result queryShopByType(Integer typeId, Integer current, Double x, Double y);

    Result queryById(Long id);

    Result saveShop(Shop shop);

    Result updateShopId(Shop shop);

    void loadGEOByShopType(Long TypeId);
}
