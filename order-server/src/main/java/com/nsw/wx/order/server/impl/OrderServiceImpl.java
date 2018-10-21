package com.nsw.wx.order.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import com.nsw.wx.order.com.nsw.wx.order.dto.OrderDTO;
import com.nsw.wx.order.controller.ProductClient;
import com.nsw.wx.order.enums.OrderStatusEnum;
import com.nsw.wx.order.enums.PayStatusEnum;
import com.nsw.wx.order.enums.ResultEnum;
import com.nsw.wx.order.exception.OrderException;
import com.nsw.wx.order.mapper.WeCharOrdeDetailMapper;
import com.nsw.wx.order.mapper.WeCharOrderMapper;
import com.nsw.wx.order.pojo.WeCharOrdeDetail;
import com.nsw.wx.order.pojo.WeCharOrder;
import com.nsw.wx.order.server.OrderService;
import com.nsw.wx.order.util.KeyUtil;
import common.DecreaseStockInput;
import common.WeChatProductOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by 张维维
 * 2018-10-19
 */
@Service
public class OrderServiceImpl implements OrderService {



@Autowired
private WeCharOrdeDetailMapper weCharOrdeDetailMapper;
@Autowired
private WeCharOrderMapper weCharOrderMapper;

    @Autowired
    private ProductClient productClient;
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
       //查询商品信息(调用商品服务) 获取商品信息
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(WeCharOrdeDetail::getProductid)
                .collect(Collectors.toList());
        //得到商品信息
        System.out.println();
        List<WeChatProductOutput> productInfoList = productClient.listForOrder(productIdList);

       //计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);

        BigDecimal orderAmoutSum = new BigDecimal(BigInteger.ZERO);

//订单商品入库

        for (WeCharOrdeDetail weCharOrdeDetail: orderDTO.getOrderDetailList()) {
            for (WeChatProductOutput productInfo: productInfoList) {
                if (productInfo.getId().equals(weCharOrdeDetail.getProductid())) {
                    //单价*数量
                    orderAmout = productInfo.getPrice()
                            .multiply(new BigDecimal(weCharOrdeDetail.getNum()));


                    weCharOrdeDetail.setUserprice(orderAmout);
                    BeanUtils.copyProperties(productInfo, weCharOrdeDetail);
                    weCharOrdeDetail.setOid(orderId);
                    weCharOrdeDetail.setProductname(productInfo.getTitle());
                    weCharOrdeDetail.setProductprice(productInfo.getPrice());
                    weCharOrdeDetail.setPricesum(orderAmout);
                    weCharOrdeDetail.setEnable(true);
                    weCharOrdeDetail.setInputtime(new Date());
                    weCharOrdeDetail.setCarttype(null);
                    weCharOrdeDetail.setSkuid(12);
                    BigDecimal aDouble =new BigDecimal(198);
                    weCharOrdeDetail.setGroupbuyprice(aDouble);
                    weCharOrdeDetail.setOffertype(1);
                    weCharOrdeDetail.setDeposit(aDouble);
                    weCharOrdeDetail.setRent(aDouble);
                    weCharOrdeDetail.setDay(320);
                    weCharOrdeDetail.setStatus(1212);
                    weCharOrdeDetail.setUserid(12);
                    orderAmoutSum =  orderAmoutSum.add(orderAmout);
                    //订单详情入库
                    weCharOrdeDetailMapper.insert(weCharOrdeDetail);
                }
            }
        }


       //扣库存(调用商品服务)
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductid(), e.getNum()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        //订单入库
        WeCharOrder orderMaster = new WeCharOrder();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderno(orderId);
        orderMaster.setTotal(orderAmoutSum);
        orderMaster.setOrderstate(OrderStatusEnum.NEW.getCode());
        orderMaster.setPaystate(PayStatusEnum.WAIT.getCode());
        weCharOrderMapper.insert(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //查询订单
       WeCharOrder weCharOrder = weCharOrderMapper.finaAllByid(Integer.parseInt(orderId));
        if(weCharOrder==null){
            throw  new OrderException(ResultEnum.CART_EMPTY.ORDER_NOT_EXIST);
        }
        //判断订单转态

        if (OrderStatusEnum.NEW.getCode()  != weCharOrder.getOrderstate()) {
            throw  new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改订单为完结转态
        weCharOrder.setOrderstate(OrderStatusEnum.FINISHED.getCode());

        weCharOrderMapper.updateOrderStatus(weCharOrder);
        System.out.println();

        //查看订单详情
        List<WeCharOrdeDetail> weCharOrdeDetails = weCharOrdeDetailMapper.findByOrderno(weCharOrder.getOrderno());
            if(CollectionUtils.isEmpty(weCharOrdeDetails)){
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
            OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(weCharOrder, orderDTO);
        orderDTO.setOrderDetailList(weCharOrdeDetails);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        WeCharOrder weCharOrder = weCharOrderMapper.finaAllByid(Integer.parseInt(orderId));
        if (weCharOrder ==null){
            throw  new OrderException(ResultEnum.CART_EMPTY.ORDER_NOT_EXIST);
        }
        //查看订单详情
        List<WeCharOrdeDetail> weCharOrdeDetails = weCharOrdeDetailMapper.findByOrderno(weCharOrder.getOrderno());
        if(CollectionUtils.isEmpty(weCharOrdeDetails)){
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(weCharOrder, orderDTO);
        orderDTO.setOrderDetailList(weCharOrdeDetails);
        return orderDTO;
    }


    @Override
    public PageInfo<WeCharOrder> findList(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<WeCharOrder> findlist = weCharOrderMapper.findList();
        PageInfo<WeCharOrder> pageInfoUserList =  new PageInfo<WeCharOrder>(findlist);
        return pageInfoUserList;
    }

    @Override
    public OrderDTO cancel(String orderId) {
        //查询订单
        //判断订单状态
        //修改订单状态
        //加库存
        //退款

        //查询订单
        WeCharOrder weCharOrder = weCharOrderMapper.finaAllByid(Integer.parseInt(orderId));
        if(weCharOrder==null){
            throw  new OrderException(ResultEnum.CART_EMPTY.ORDER_NOT_EXIST);
        }
        //判断订单转态

        if (!weCharOrder.getOrderstate().equals(OrderStatusEnum.NEW.getCode())) {
            throw  new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        weCharOrder.setOrderstate(OrderStatusEnum.CANCEL.getCode());

        weCharOrderMapper.updateOrderStatus(weCharOrder);
        //TODO 调用商品加库存方法

        return null;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        return null;
    }
}
