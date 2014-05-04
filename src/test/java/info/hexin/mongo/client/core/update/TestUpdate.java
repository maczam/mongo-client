package info.hexin.mongo.client.core.update;

import org.junit.Test;

import info.hexin.mongo.client.core.dao.MongoDao;
import info.hexin.mongo.client.core.query.Query;

public class TestUpdate {
	private static final String ID = "_id";

	MongoDao dao = new MongoDao();
	
	private Update getUpdate(){
//		Update u = Update.$().set("xx", new String[]{"123","234"});
//		Update u = Update.$().unset("xx");
		Update u = Update.$().addToSet("xx", "你好1");
//		Update u = Update.$().pullAll("xx", "123","你好","你好1");
		return u;
	}
	
	
	@Test
	public void printUpdate(){
		System.out.println(getUpdate().getUdateObject());
	}
	
	@Test
	public void xx() {
//		 dao.remove("e", Query.where(ID).is("123"));
		// dao.updateOne("e", Query.where(ID).is("123"), Update.$().set("bbb",
		// "1211"));
		 dao.updateOne("e", Query.where(ID).is("123"),getUpdate());
		 
	}
	
	@Test
	public void tt() {
		// Update u =
		// Update.$().set(Maps.ofObject("a","123","b","234")).set("c","345");
		// System.out.println(u.getUdateObject());
	}
}
