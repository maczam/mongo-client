package info.hexin.mongo.example.chapter02.queryadvanced;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import info.hexin.mongo.client.core.dao.MongoDao;
import info.hexin.mongo.client.core.query.Query;
import info.hexin.mongo.client.util.Maps;

public class FieldExample {
    MongoDao dao = new MongoDao();

    public void initData() {
        dao.drop("a");
        dao.save("a", Maps.ofObject("_id", "1", "key", "a", "whois", "1"));
        dao.save("a", Maps.ofObject("_id", "2", "key", "a", "whois", "1"));
        dao.save("a", Maps.ofObject("_id", "3", "key", "a", "whois", "2"));
    }

    @Test
    public void test() {
        List<Map<String,Object>> m =
                dao.find("a", Query.where("whois").is("1").field("_id").field("key"));
        System.out.println(m);
    }
}
