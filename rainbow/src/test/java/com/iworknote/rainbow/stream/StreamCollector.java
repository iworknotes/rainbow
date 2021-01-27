package com.iworknote.rainbow.stream;

import com.alibaba.fastjson.JSON;
import com.iworknote.rainbow.cart.CartService;
import com.iworknote.rainbow.cart.Sku;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 常见收集器使用
 *
 * @author YL.huang
 * @date 01/26/2021
 */
public class StreamCollector {

    /**
     * 集合收集器
     */
    @Test
    public void toList() {
        List<Sku> list = CartService.getCartSkuList();

        List<Sku> result = list.stream()
                .filter(sku -> sku.getTotalPrice() > 1000)
                .collect(Collectors.toList());

        System.out.println(JSON.toJSONString(result, true));
    }

    /**
     * 分组
     */
    @Test
    public void group() {
        List<Sku> list = CartService.getCartSkuList();

        Map<Object, List<Sku>> group = list.stream()
                .collect(Collectors.groupingBy(Sku::getSkuCategory));

        System.out.println(JSON.toJSONString(group, true));
    }

    /**
     * 分区
     */
    @Test
    public void partition() {
        List<Sku> list = CartService.getCartSkuList();

        Map<Boolean, List<Sku>> partition = list.stream()
                // 总价是否大于1000进行分区
                .collect(Collectors.partitioningBy(
                        sku -> sku.getTotalPrice() > 1000));

        System.out.println(JSON.toJSONString(partition, true));

        // 创建一个包含人名称的流（英文名和中文名）
        Stream<String> stream = Stream.of("abc", "lili", "wangwu", "李四", "张三");
        // 通过判断人名称的首字母是否为英文字母，将其分为两个不同流
        final Map<Boolean, List<String>> map = stream.collect(Collectors.partitioningBy(s -> {
            // 如果是英文字母，则将其划分到英文人名，否则划分到中文人名
            int code = s.codePointAt(0);
            return (code >= 65 && code <= 90) || (code >= 97 && code <= 122);
        }));
        // 输出分组结果
        map.forEach((isEnglishName, names) -> {
            if (isEnglishName) {
                System.out.println("英文名称如下：");
            } else {
                System.out.println("中文名称如下：");
            }
            names.forEach(name -> System.out.println("\t" + name));
        });


    }

}
