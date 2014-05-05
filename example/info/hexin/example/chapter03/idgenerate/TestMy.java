package info.hexin.example.chapter03.idgenerate;

import org.junit.Test;

import info.hexin.mongo.client.core.dao.MongoDao;
import info.hexin.mongo.client.util.Maps;

public class TestMy {
    @Test
    public void test1(){
        MyIdGenerate idGenerate = new MyIdGenerate();
        MongoDao dao = new MongoDao();
        dao.setIdGenerate(idGenerate);
        dao.save("d", Maps.ofObject("name","001"));
        dao.save("d", Maps.ofObject("name","002"));
        dao.save("d", Maps.ofObject("name","003"));
    }
}
