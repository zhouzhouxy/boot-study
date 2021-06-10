package com.asura.util;

import com.asura.kafka.KafkaApplication;
import com.asura.kafka.entity.BookTest;
import com.asura.kafka.entity.Books;
import com.asura.kafka.service.BooksService;
import com.asura.kafka.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zzyx 2021/6/8/008
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KafkaApplication.class)
public class RedisUtilsTest {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private BooksService booksService;

    @Test
    public void test1() {
        ArrayList<BookTest> list = new ArrayList<>();
        list.add(new BookTest(1L, "zhouyi"));
        list.add(new BookTest(2L, "zhouyi"));
        list.add(new BookTest(3L, "zhouyi"));
        list.add(new BookTest(4L, "zhouyi"));

        Map<Long, BookTest> collect = list.parallelStream().collect(Collectors.toMap(BookTest::getId, a -> a));

        redisUtils.set("map", collect);
        System.out.println("---------==================>");
        Map<Long, BookTest> map = (Map<Long, BookTest>) redisUtils.get("map");
        for (Map.Entry<Long, BookTest> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "---->" + entry.getValue());
        }
        Map<Long, BookTest> map2 = new HashMap<>();
        map2.put(1L, new BookTest(1L, "zhouer"));
        map2.put(2L, new BookTest(2L, "zhouer"));
        map2.put(5L, new BookTest(5L, "zhouer"));
        map.putAll(map2);
        //set
        redisUtils.delete("map");
        redisUtils.set("map", map);

        System.out.println("---------==================>");
        System.out.println("---------==================>");
        System.out.println("---------==================>");
        System.out.println("---------==================>");
        Map<Long, BookTest> map3 = (Map<Long, BookTest>) redisUtils.get("map");
        for (Map.Entry<Long, BookTest> entry : map3.entrySet()) {
            System.out.println(entry.getKey() + "---->" + entry.getValue());
        }

    }

    @Test
    public void testSaveOrBatch() {
        List<Books> list=new ArrayList<Books>();
        list.add(new Books(1,"水浒传加强版",30,"zhouyi"));
        list.add(new Books(null,"水浒传加强版",40,"zhouer"));
        this.booksService.saveOrUpdateBatch(list);
    }
}
