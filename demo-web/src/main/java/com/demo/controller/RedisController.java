package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands.BitOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.HbApp;
import com.demo.redis.RedisUtil;


/**
 * redis的使用
 */
@RestController
@RequestMapping("")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/useRedis")
    public String useRedis() {

        //分布式锁
        boolean lock = redisUtil.lock("lock", 5);

        if (!lock) {
            return "被锁了";
        }

        //测试监听到失效key
        redisUtil.set("testExpire", "2", 10);

        //测试bitmap布隆过滤器
        redisUtil.setBit("{count}:count1", 1, true);
        redisUtil.setBit("{count}:count1", 3, true);
        redisUtil.setBit("{count}:count1", 5, true);

        System.out.println(String.format("count1里面有%s条数据", redisUtil.bitCount("{count}:count1")));

        redisUtil.setBit("{count}:count2", 2, true);
        redisUtil.setBit("{count}:count2", 3, true);
        redisUtil.setBit("{count}:count2", 6, true);

        System.out.println(String.format("count2里面有%s条数据", redisUtil.bitCount("{count}:count2")));

        redisUtil.setBit("{count}:count3", 4, true);
        redisUtil.setBit("{count}:count3", 3, true);
        redisUtil.setBit("{count}:count3", 7, true);

        System.out.println(String.format("count3里面有%s条数据", redisUtil.bitCount("{count}:count3")));

        String[] keys = new String[]{"{count}:count1", "{count}:count2", "{count}:count3"};

        /**
         * CROSSSLOT Keys in request don't hash to the same slot
         * 要解决此错误，请使用哈希标签强制将密钥放入相同的哈希槽。当密钥包含“{...}”这种样式时，只有大括号“{”和“}”之间的子字符串得到哈希以获得哈希槽。
         */

        //逻辑或
        redisUtil.bitOp(BitOperation.AND, "{count}:jiaoji", keys);
        System.out.println("取交集的数量：" + redisUtil.bitCount("{count}:jiaoji"));//1

        //逻辑与
        redisUtil.bitOp(BitOperation.OR, "{count}:bingji", keys);
        System.out.println("取并集的数量：" + redisUtil.bitCount("{count}:bingji"));//7

        //对给定 key 求逻辑非
        redisUtil.bitOp(BitOperation.NOT, "{count}:not", "{count}:count1");
        System.out.println("逻辑非的数量：" + redisUtil.bitCount("{count}:not"));//5

        //逻辑异或
        redisUtil.bitOp(BitOperation.XOR, "{count}:xor", keys);
        System.out.println("逻辑异或的数量：" + redisUtil.bitCount("{count}:xor"));//7

        //存储对象
        HbApp app = new HbApp();
        app.setName("王某人");
        redisUtil.set("app", app);

        HbApp app1 = (HbApp) redisUtil.get("app");

        return app1.getName();

    }

}
