package info.hexin.example.chapter02.groupmr;

import java.util.List;

import org.junit.Test;

import info.hexin.mongo.client.callback.DBObjectCallback;
import info.hexin.mongo.client.core.dao.MongoDao;
import info.hexin.mongo.client.core.query.Cnd;
import info.hexin.mongo.client.core.query.Group;
import info.hexin.mongo.client.core.query.Query;
import info.hexin.mongo.client.util.Maps;
import com.mongodb.DBObject;

public class GroupTest {
    @Test
    public void test1() {
        MongoDao dao = new MongoDao();
        dao.drop("a");
        dao.save("a", Maps.ofObject("_id", "1", "key", "a", "whois", "1"));
        dao.save("a", Maps.ofObject("_id", "2", "key", "a", "whois", "1"));
        dao.save("a", Maps.ofObject("_id", "3", "key", "a", "whois", "2"));
        dao.save("a", Maps.ofObject("_id", "4", "key", "b", "whois", "2"));
        dao.save("a", Maps.ofObject("_id", "5", "key", "b", "whois", "3"));
        dao.save("a", Maps.ofObject("_id", "6", "key", "c", "whois", "3"));
        dao.save("a", Maps.ofObject("_id", "7", "key", "c", "whois", "4"));

        Query q = Cnd.or(Query.where("whois").is("1"), Query.where("whois").is("2"), Query.where("whois").is("3"));

        // reduce函数
        StringBuilder r = new StringBuilder();
        r.append("function (v,k)");
        r.append("{");
        r.append(" k.count++");
        r.append("}");

        List<Object> o = dao.group("a", q, Group.key("key").reduce(r.toString()).initial("count", 0),
                new DBObjectCallback<Object>() {
                    @Override
                    public Object processDBObject(DBObject dbObject) {
                        return dbObject.toMap();
                    }
                });
        System.out.println(o);
    }
}
