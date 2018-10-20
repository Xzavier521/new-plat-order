package com.nsw.wx.order.controller;


import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.nsw.wx.order.pojo.WeCharOrder;
import com.nsw.wx.order.server.WeCharOrderService;
import com.nsw.wx.order.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class WeCharOrdeController {
    @Autowired
    private WeCharOrderService weCharOrderService;

    @GetMapping("/test")
    public  BigDecimal test(){

       System.out.println(new BigDecimal("0.00"));
        return new BigDecimal("0.00") ;
    }
    @GetMapping("/insert")
    public int insert(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        WeCharOrder order = weCharOrderService.finaAllByid(23);
        System.out.println("----------------------->"+order.getOrderno());
        order.setOrderno("c415_201803161620520002");
        System.out.println("----------------------->"+order.getOrderno());

        System.out.println("============="+order.getOrderno());
        return weCharOrderService.insert(order);
    }
    @GetMapping("/findAll")
    public Object findAll(HttpServletRequest request, HttpServletResponse response){
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        System.out.println(page);
        System.out.println(pageSize);
        PageInfo<WeCharOrder> pageInfoList = weCharOrderService.pageSelect(page, pageSize);
        List<WeCharOrder> list = pageInfoList.getList();
        long count=pageInfoList.getTotal();
        String json = JSONArray.toJSONString(list);
        return JsonData.buildSuccess(count,list);
    }

    @GetMapping("/del")
    public int Del(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        return weCharOrderService.deleteByPrimaryKey("c415_201803161620520002");
    }

    @GetMapping("/finaAllByid")
    public WeCharOrder finaAllByid(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        return weCharOrderService.finaAllByid(23);
    }

    @GetMapping("/update")
    public int Update(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        WeCharOrder weCharOrder = new WeCharOrder();
        weCharOrder.setOrderno("c415_201712141146560001");
        weCharOrder.setOrderstate(2);
        return weCharOrderService.updateByPrimary(weCharOrder);
    }


}
