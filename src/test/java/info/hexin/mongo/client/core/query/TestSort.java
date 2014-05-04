package info.hexin.mongo.client.core.query;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import info.hexin.mongo.client.core.dao.MongoDao;

public class TestSort {
//    @Test
    public void test1() {
        MongoDao dao = new MongoDao();
        List<Map<String, Object>> l = dao.find("a", Query.$().desc("_id"));
        System.out.println(l);
    }
    
    @Test
    public void test2(){
        MongoDao dao = new MongoDao();
        Query q = Cnd.and(Query.id().is("c3e1ef17b90144cb9a9c26b2b115a7aa"),Query.where("no").is(90));
        System.out.println(q.getQueryObject());
        System.out.println(dao.find("a", q));
        
        System.out.println("xxxxxxxxxxx");
        
        Query q1 = Query.cnd(Query.id().is("c3e1ef17b90144cb9a9c26b2b115a7aa"),Query.where("no").is(9011));
        System.out.println(q1.getQueryObject());
        System.out.println(dao.find("a", q1));
    }
}
