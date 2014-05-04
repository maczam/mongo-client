package info.hexin.mongo.example.chapter02.result;


import org.junit.Assert;
import org.junit.Test;

import info.hexin.mongo.client.callback.DBObjectCallback;
import info.hexin.mongo.client.core.dao.MongoDao;
import info.hexin.mongo.client.core.query.Query;
import com.mongodb.DBObject;

public class Result {
    @Test
    public void test() {
        MongoDao dao = new MongoDao();
        A a = dao.findOne("a", Query.id().is("1"), new Ab());
        Assert.assertEquals("a", a.getKey());
    }
}

class A {
    private String _id;
    private String key;
    private String whois;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWhois() {
        return whois;
    }

    public void setWhois(String whois) {
        this.whois = whois;
    }
}

class Ab implements DBObjectCallback<A> {

    @Override
    public A processDBObject(DBObject dbObject) {
        A a = new A();
        a.set_id(dbObject.get("_id").toString());
        a.setKey(dbObject.get("key").toString());
        a.setWhois(dbObject.get("whois").toString());
        return a;
    }
}
