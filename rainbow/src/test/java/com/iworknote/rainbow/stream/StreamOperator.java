package com.iworknote.rainbow.stream;

import com.alibaba.fastjson.JSON;
import com.iworknote.rainbow.cart.CartService;
import com.iworknote.rainbow.cart.Sku;
import com.iworknote.rainbow.cart.SkuCategoryEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * <p> 流的使用
 * <p> 中间操作 无状态：随着流的遍历而执行的操作
 * <p> 中间操作 有状态：流中元素都遍历完，才去执行的操作；
 * <p> 终端操作 短路：符合条件时，终止操作
 * <p> 终端操作 非短路：遍历所有元素后进行操作
 *
 * @author YL.huang
 * @date 01/26/2021
 */
public class StreamOperator {

    List<Sku> list;

    @Before
    public void init() {
        list = CartService.getCartSkuList();
    }

    /**
     * <p> filter：过滤不符合断言判断的数据
     * <p> 数据筛选
     * <p> 中间操作：无状态
     */
    @Test
    public void filterTest() {
        list.stream()
                .filter(sku ->
                        SkuCategoryEnum.BOOKS
                                .equals(sku.getSkuCategory()))
                .forEach(item ->
                        System.out.println(
                                JSON.toJSONString(item)));
    }

    /**
     * <p> map：将一个元素转换成另一个元素
     * <p> 数据转换
     * <p> 中间操作：无状态
     */
    @Test
    public void mapTest() {
        list.stream()
                .map(sku -> sku.getSkuName())
                .forEach(item ->
                        System.out.println(
                                JSON.toJSONString(item)));
    }

    /**
     * <p> flatMap：将一个对象转换成流
     * <p> 数据转换
     * <p> 中间操作：无状态
     */
    @Test
    public void flatMapTest() {
        list.stream()
                .flatMap(sku ->
                        Arrays.stream(
                                sku.getSkuName().split("")))
                .forEach(item ->
                        System.out.println(
                                JSON.toJSONString(item)));
    }

    /**
     * <p> peek：对流中元素进行遍历，与forEach类似，但不会销毁流元素
     * <p> 用于调试(或修改数据)
     * <p> 中间操作：无状态
     */
    @Test
    public void peekTest() {
        list.stream()
                .peek(sku ->
                        System.out.println(sku.getSkuName()))
                .forEach(item ->
                        System.out.println(
                                JSON.toJSONString(item)));
    }

    /**
     * <p> distinct：对流元素进行去重
     * <p> 中间操作：有状态
     */
    @Test
    public void distinctTest() {
        list.stream()
                .map(sku -> sku.getSkuCategory())
                .distinct()
                .forEach(item ->
                        System.out.println(
                                JSON.toJSONString(item)));
    }

    /**
     * <p> sort：对流中元素进行排序
     * <p> 中间操作：有状态
     */
    @Test
    public void sortedTest() {
        list.stream()
                .sorted(Comparator.comparing(Sku::getTotalPrice)
                        .reversed())
                .forEach(item ->
                        System.out.println(
                                JSON.toJSONString(item)));
    }

    /**
     * <p> skip：跳过前N条数据
     * <p> 中间操作：有状态
     */
    @Test
    public void skipTest() {
        list.stream()
                .sorted(Comparator.comparing(Sku::getTotalPrice).reversed())
                .skip(3)
                .forEach(item ->
                        System.out.println(
                                JSON.toJSONString(item, true)));
    }

    /**
     * <p> limit：取前N条数据
     * <p> 中间操作：有状态
     * <p> limit、skip结合使用，可以实现分页效果
     */
    @Test
    public void limitTest() {
        list.stream()
                .sorted(Comparator.comparing(Sku::getTotalPrice).reversed())
                .limit(3)
                .forEach(item ->
                        System.out.println(
                                JSON.toJSONString(item, true)));
    }

    /**
     * <p> allMatch：所有元素匹配,满足条件返回true;如果发现未满足条件的数据，直接终止操作返回false
     * <p> 终端操作：短路
     * <p> 所有元素都满足条件才返回true
     */
    @Test
    public void allMatchTest() {
        boolean match = list.stream()
                .peek(sku ->
                        System.out.println(
                                JSON.toJSONString(sku)))
                .allMatch(sku ->
                        sku.getTotalPrice() > 100);
        System.out.println(match);
    }

    /**
     * <p> anyMatch：任意一个元素满足条件就返回true
     * <p> 终端操作：短路
     */
    @Test
    public void anyMatchTest() {
        boolean match = list.stream()
                .peek(sku ->
                        System.out.println(
                                JSON.toJSONString(sku)))
                .anyMatch(sku ->
                        sku.getTotalPrice() < 2000);
        System.out.println(match);
    }

    /**
     * <p> noneMatch：任何元素都不匹配时，返回true
     * <p> 终端操作：短路
     */
    @Test
    public void noneMatchTest() {
        boolean match = list.stream()
                .peek(sku ->
                        System.out.println(
                                JSON.toJSONString(sku)))
                .noneMatch(sku ->
                        sku.getTotalPrice() > 30_00);
        System.out.println(match);
    }

    /**
     * <p> findFirst：找到第一个
     * <p> 终端操作：短路
     */
    @Test
    public void findFirstTest() {
        Optional<Sku> optional = list.stream()
                .peek(sku -> System.out.println(JSON.toJSONString(sku)))
                .findFirst();

        System.out.println(JSON.toJSONString(optional, true));
    }

    /**
     * <p> findAny：找任意一个
     * <p> 终端操作：短路
     */
    @Test
    public void findAnyTest() {
        Optional<Sku> optional = list.stream()
                .peek(sku -> System.out.println(JSON.toJSONString(sku)))
                .findAny();

        System.out.println(JSON.toJSONString(optional, true));
    }

    @Test
    public void maxTest() {
        OptionalDouble optionalDouble = list.stream()
                .mapToDouble(Sku::getTotalPrice)
                .max();

        System.out.println(optionalDouble.getAsDouble());
    }

    @Test
    public void minTest() {
        OptionalDouble optionalDouble = list.stream()
                .mapToDouble(Sku::getTotalPrice)
                .min();

        System.out.println(optionalDouble.getAsDouble());
    }

    @Test
    public void countTest() {
        long count = list.stream().count();
        System.out.println(count);
    }

    /**
     * 使用ThreadLocalRandom方法生成随机数
     */
    @Test
    public void ThreadLocalRandomTest() {
        Stream numbers = ThreadLocalRandom.current()
                .ints(0, 100)
                .limit(10)
                // boxed方法：将XxxStream转化为Stream<Xxx>
                .boxed();
        numbers.forEach(System.out::println);
    }

    /**
     * 生成5位数的验证码
     */
    @Test
    public void verifyCode() {
        final String db = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer code = new StringBuffer(5);
        ThreadLocalRandom.current()
                .ints(0, db.length() + 1)
                .limit(5)
                .forEach(index ->
                        code.append(
                                db.charAt(index)));

        System.out.println(code.toString());
    }

}
