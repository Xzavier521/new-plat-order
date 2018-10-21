package com.nsw.wx.order.server;

import com.github.pagehelper.PageInfo;
import com.nsw.wx.order.com.nsw.wx.order.dto.OrderDTO;
import com.nsw.wx.order.pojo.WeCharOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by 张维维
 *
 */
public interface OrderService {

    /**
     * 创建订单(只能买家抄作)
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单(只能卖家操作)
     * @param orderId
     * @return
     */
    OrderDTO finish (String orderId);

    /** 查询单个订单. */
    OrderDTO findOne(String orderId);

    /** 查询订单列表. */
    PageInfo <WeCharOrder> findList (Integer page, Integer limit );

    /** 取消订单. */
    OrderDTO cancel(String orderId);



    /** 查询订单列表. */
    Page<OrderDTO> findList(Pageable pageable);

}
