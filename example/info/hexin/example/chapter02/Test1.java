package info.hexin.example.chapter02;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import info.hexin.mongo.client.auth.AuthDB;
import info.hexin.mongo.client.core.dao.MongoDao;
import info.hexin.mongo.client.core.query.Query;
import info.hexin.mongo.client.core.update.Update;
import info.hexin.mongo.client.util.Maps;

public class Test1 {
    @Test
    public void testQuery() {
        MongoDao mongoDao = new MongoDao();
        List<Map<String, Object>> maps = mongoDao.find("c", Query.id().is("1"));
        
        Query.where("name").is("a");
        Query.where("age").lessThan(23);
        Query.where("age").greaterThan(20);
        Query.where("name").like("a");
        Query.where("name").startWith("a");
        
    }

    @SuppressWarnings({ "rawtypes", "unused" })
    @Test
    public void testInsert() {
        MongoDao mongoDao = new MongoDao();
        Map m = Maps.of("name","a","password","124");
        Object id = mongoDao.save("c", m);
    }
    
    public void testUpdate(){
        MongoDao mongoDao = new MongoDao();
        mongoDao.updateOne("c", Query.where("name").is("a"),
                Update.$().set("password", "234"));
    }
    
    public void testRemove(){
        MongoDao mongoDao = new MongoDao();
        mongoDao.remove("c", Query.where("name").is("a"));
    }
    
    public void testClientMongoDao(){
        
        MongoDao dao = new MongoDao();
        dao.setAuthDB(new AuthDB() {
            @Override
            public boolean authCollection(String collectionName) {
                if("a".equals(collectionName)){
                    return true;
                }
                return false;
            }
        });
    }
}
