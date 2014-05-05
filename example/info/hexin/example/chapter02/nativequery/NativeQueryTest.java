package info.hexin.example.chapter02.nativequery;

import org.junit.Test;
import info.hexin.mongo.client.core.dao.MongoDao;
import info.hexin.mongo.client.core.query.Query;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class NativeQueryTest {
    @Test
    public void test1() {
        MongoDao dao = new MongoDao();
        DBObject dbObject = new BasicDBObject();
        dbObject.put("no", new BasicDBObject("$lt", 5));
        Query q = Query.map(dbObject.toMap());
        System.out.println(dao.find("a", q));
    }
}
