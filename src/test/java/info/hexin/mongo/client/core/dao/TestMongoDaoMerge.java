package info.hexin.mongo.client.core.dao;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import info.hexin.mongo.client.auth.DefaultAuthDBImpl;
import info.hexin.mongo.client.core.query.Cnd;
import info.hexin.mongo.client.core.query.Query;
import info.hexin.mongo.client.util.Maps;

public class TestMongoDaoMerge {
	MongoDao dao = new MongoDao();
	DefaultAuthDBImpl authDBImpl = new DefaultAuthDBImpl();

	@Before
	public void setUp() {
		dao.setAuthDB(authDBImpl);
		// tenantId();
	}

	 @Test
	public void save() {
		dao.merge("d", Maps.ofObject("a", "a1", "b", "b1"));
		dao.merge("d", Maps.ofObject("a", "a2", "b", "b2"));
		dao.merge("d", Maps.ofObject("a", "a3", "b", "b3"));
		dao.merge("d", Maps.ofObject("a", "a4", "b", "b4"));
		dao.merge("d", Maps.ofObject("a", "a5", "b", "b5"));
//		dao.remove("d", Query.cnd(Cnd.or(Query.where("a").is("1"), Query.where("a").is("3"))));
	}

	@Test
	public void query() {
		print(Cnd.and(Query.where("a").is("1"), Query.where("b").is("2")));
		print(Cnd.or(Query.where("a").is("1"), Query.where("a").is("3")));
		print(Query.where("a").in("1","3"));
		
		Query query = Query.cnd(
				Cnd.and(Query.where("a").is("a1"),
						Query.where("b").is("a1")),
				Cnd.or(Query.where("a").is("a2"), 
					   Query.where("a").is("a3"))
						);
		print(query);
	}

	public void print(Query query) {
		List<Map<String, Object>> list = dao.find("d", query);
		// System.out.println("xxx");
		System.out.println(list);
		// System.out.println("xxx");
	}
}
