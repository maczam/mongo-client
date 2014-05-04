package info.hexin.mongo.client.core.dao;

import com.mongodb.util.JSON;
import info.hexin.mongo.client.core.mapper.id.UUIDGenerate;
import info.hexin.mongo.client.core.query.Query;
import info.hexin.mongo.client.core.update.Update;
import info.hexin.mongo.client.util.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestMongoDaoArray {
    MongoDao dao = new MongoDao();

//    @Test
    public void test1() {
        dao.drop("d");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("_id", 1);
        dao.save("d", map);
        dao.findAndModify("d", Query.id().is(1), Update.$().addToSet("jvms", Maps.ofObject("jvm_id", 123, "start_time", 123, "end_time", 234)));
        dao.findAndModify("d", Query.where("jvms.jvm_id").is(123), Update.$().set("jvms.$.start_time", 456).set("jvms.$.end_time",789));
    }
    
    @Test
    public void test(){
//        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("name", "集群1");
//        map.put("resourcetype", "ClusterResource");
        UUIDGenerate generate = new UUIDGenerate();
//        System.out.println(generate.id());
        dao.save("j_host", (Map<String, Object>) JSON.parse("{\"_id\":\"f8b300d824164a1f952c222abf325f1a\",\"id\":\"f8b300d824164a1f952c222abf325f1a\",\"clusterName\":\"集群1\",\"status\":-1,\"describe\":\"\",\"name\":\"dlxa182\",\"path\":\"mb3dc1cf39f624f0f9b0bf85123ef13ae.children.mbe4264a38c374a088e66818bbdd44e53.children.mae1a7073b04c46408ceb66f21f16063b\",\"resourcetype\":\"HostResource\",\"clusterId\":\"mbe4264a38c374a088e66818bbdd44e53\",\"ip\":\"172.16.40.182\"}"));
    }
}
