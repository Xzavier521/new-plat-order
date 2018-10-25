//package com.nsw.wx.order.message;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.nsw.wx.order.util.JsonUtil;
//import com.rabbitmq.tools.json.JSONUtil;
//import common.ProductInfoOutput;
//import common.WeChatProductOutput;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * Mq接收商品信息
// *
// * @author 张维维
// * date: 2018/10/21/021 20:05
// */
//@Component
//@Slf4j
//@Transactional
//public class ProductInfoReceiver {
//private  static  final  String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @RabbitListener(queuesToDeclare = @Queue("productInfo2"))
//    public void  process(String message) {
//        log.info("从队列【{}】接收到消息:{}", "productInfo2", message);
//        System.out.println("你真棒");
//        List<WeChatProductOutput> weChatProductOutputList =
//                (List<WeChatProductOutput>) JsonUtil.fromJson(message,
//                        new TypeReference<List<WeChatProductOutput>>() {
//                        });
//        System.out.println("你真棒2" + weChatProductOutputList.size());
//        log.info("从队列【{}】接收到消息:{}", "productInfo2", message);
//        //储存到redis中
//        for (WeChatProductOutput weChatProductOutput:weChatProductOutputList){
//            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,weChatProductOutput.getId()),
//                    String.valueOf(weChatProductOutput.getStock()));
//        }
//    }
//
//
//
//}
