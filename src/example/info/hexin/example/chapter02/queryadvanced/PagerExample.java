package info.hexin.mongo.example.chapter02.queryadvanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import info.hexin.mongo.client.core.dao.MongoDao;
import info.hexin.mongo.client.core.query.Query;

/**
 * 翻页查询实例
 * 
 * @author hexin
 * 
 */
public class PagerExample {

    @Test
    public void initData() {
        MongoDao dao = new MongoDao();
        List<Map<String, Object>> lit = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> user = new HashMap<String, Object>();
            user.put("no", i);
            user.put("name", "name_" + i);
            lit.add(user);
        }
        dao.drop("a");
        dao.merge("a", lit);
    }

    @Test
    public void viewData() {
        MongoDao dao = new MongoDao();
        System.out.println(dao.findAll("a"));
    }

    @Test
    public void page_1() {
        MongoDao dao = new MongoDao();
        List<Map<String, Object>> list = dao.find("a", Query.$().skip(20).limit(5));
        System.out.println(list);
    }

    @Test
    public void page_2() {
        MongoDao dao = new MongoDao();
        // 查询第一页数据
        List<Map<String, Object>> one = dao.find("a", Query.$().limit(5).desc("no"));
        System.out.println(one);

        // 第二页数据
        List<Map<String, Object>> next = dao.find("a", Query.where("no").lessThan(95).limit(5).desc("no"));
        System.out.println(next);
    }
}
