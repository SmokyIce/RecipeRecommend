package com.douyin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.entity.Recipe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2025-3-3
 */
public interface VoucherMapper extends BaseMapper<Recipe> {

    List<Recipe> queryVoucherOfShop(@Param("shopId") Long shopId);
}
