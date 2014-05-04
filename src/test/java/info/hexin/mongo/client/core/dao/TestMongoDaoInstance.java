package info.hexin.mongo.client.core.dao;

import info.hexin.mongo.client.config.DbConfigFile;
import info.hexin.mongo.client.config.DbProperties;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * 测试实例化代码
 * 
 * @author hexin
 * 
 */
public class TestMongoDaoInstance {
    
//    @Test
    public void test1(){
        MongoDao dao = new MongoDao();
        List<Map<String, Object>> list = dao.findAll("c");
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }
    }
    
//    @Test
    public void test2(){
        DbConfigFile dbFactory = new DbConfigFile();
        dbFactory.setConfig("database.properties");
        MongoDao dao = new MongoDao();
        dao.setDbFactory(dbFactory);
        List<Map<String, Object>> list = dao.findAll("c");
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }
        List<Map<String, Object>> list1 = dao.findAll("c");
        for (Map<String, Object> map : list1) {
            System.out.println(map);
        }
    }
    
    @Test
    public void test3(){
        DbProperties dbFactory = new DbProperties();
        dbFactory.setHost("127.0.0.1");
        dbFactory.setDbname("jmp");
        MongoDao dao = new MongoDao();
        dao.setDbFactory(dbFactory);
        List<Map<String, Object>> list = dao.findAll("c");
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }
        List<Map<String, Object>> list1 = dao.findAll("c");
        for (Map<String, Object> map : list1) {
            System.out.println(map);
        }
    }
}
