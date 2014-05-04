package info.hexin.mongo.client.core.dao;

import info.hexin.mongo.client.core.query.Query;
import info.hexin.mongo.client.core.update.Update;
import info.hexin.mongo.client.util.Maps;
import info.hexin.mongo.client.util.Strings;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;



public class TestMongoDaoFindAndModify {
	MongoDao dao = new MongoDao();
	
//	@Test
	public void test1(){
		Map<String, Object> map = dao.findAndModify("c", Query.where("name").is("hexin16"), true);
//		System.out.println(map);
	}
	
//	@Test
	public void test2(){
		Map<String, Object> map = dao.findAndModify("c", Query.where("name").is("hexin16"), Update.$().set("name", "hexin16"),true,true);
		System.out.println(map);
	}
	
//	@Test
	public void test3(){
	    Map<String, Object>  map =  dao.findAndModify("d", Query.id().is("xx"), Update.$().inc("seq",1), true, false);
	    int i = Strings.toInt(Maps.get(map, "seq"));
	    System.out.println(".......... "+ i);
	}
	
//	 @Test
    public void test6(){
        Map<String,Object> map1 = new HashMap<String, Object>();
        map1.put("name", "hexin2");
      dao.findAndModify("c", Query.where("_id").is("0431d4b029a84ff48b1105084311f673"), Update.$().addToSet("xxs", map1));
    }
	
//	@Test
	public void test4(){
	    Map<String,Object> map1 = new HashMap<String, Object>();
        map1.put("name", "hexin3");
	    dao.findAndModify("c", Query.where("xxs.name").is("hexin2"), Update.$().set("xxs.$",map1));
	}
//	@Test
	public void test5(){
	    Map<String,Object> map = new HashMap<String, Object>();
//	    map.put("_id", "1245");
	    map.put("name", "hexin3");
	    Update update =  Update.$().set(map);
	    dao.findAndModify("c", Query.id().is("0431d4b029a84ff48b1105084311f673"), update,true,true);
    }
	
	@Test
    public void test7() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("name", "hexin3");
        dao.findAndModify("c", Query.where("name").is("hexin4"),Update.$().set("name", "hexin5"));
    }
}
