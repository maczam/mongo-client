package info.hexin.mongo.client.core.dao;

import org.junit.Before;
import org.junit.Test;

import info.hexin.mongo.client.auth.DefaultAuthDBImpl;
import info.hexin.mongo.client.core.query.Query;
import info.hexin.mongo.client.core.update.Update;

public class TestMongoDao {
	MongoDao dao = new MongoDao();
	DefaultAuthDBImpl authDBImpl = new DefaultAuthDBImpl();

	@Before
	public void setUp() {
		dao.setAuthDB(authDBImpl);
//		tenantId();
	}
	
	public void tenantId(){
		for (int i = 0; i < 20; i++) {
			if (i % 2 == 0) {
				dao.updateMulti("c", Query.id().is(i), Update.$().set("tenantId", "123"));
			} 
		}
	}

	public void mr() {
		for (int i = 0; i < 20; i++) {
			if (i % 2 == 0) {
				dao.updateMulti("c", Query.id().is(i), Update.$().set("c", "0"));
			} else {
				dao.updateMulti("c", Query.id().is(i), Update.$().set("c", "1"));

			}
		}
		// dao.mr("c", mapFun, reduceFun);
	}

	@Test
	public void xx() {
		// List<Map<String, Object>> list =
		// dao.find("e",Query.where("xxx").is("123").desc("order"),
		// new MapDBObjectCallbackImpl());
		// for (Map<String, Object> map : list) {
		// System.out.println(map);
		// }

		// Map<String,Object> user = new HashMap<String, Object>();
		// user.put("_id", "1223");
		// user.put("name", "1111");
		// Object id = dao.merge("e", user);
		// System.out.println(id);
		//
		// Map<String, Object> map = dao.findOne("e",
		// Query.where(DB.ID).is("123"));
		// System.out.println(map);

		// dao.remove("e", Query.where("_id").is("1223"));

		// System.out.println(dao.findById("j_app",
		// "m1ef9a7498f4e409a8cd861c79630fe0c"));
		// System.out.println(dao.find("e", Query.where("xx").size(1)));
	}

	@Test
	public void count() {
	    System.out.println(dao.find("a",Query.where("key").is("a").skip(1).limit(1)));
	}
}
