package info.hexin.mongo.client.core.dao;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import info.hexin.mongo.client.auth.DefaultAuthDBImpl;
import info.hexin.mongo.client.core.index.Index;

/**
 * index 相关
 * 
 * @author hexin
 * 
 */
public class TestMongoDaoIndex {

	static MongoDao dao = new MongoDao();

	@BeforeClass
	public static void beforeClass() {
		dao.setAuthDB(new DefaultAuthDBImpl());
	}

	@Test
	@Ignore()
	public void test() {
//		List<Map<String, Object>> l = dao.findAllIndex("c");
//		for (Map<String, Object> map : l) {
//			System.out.println(map);
//		}
//		dao.ensureIndex("c", Index.$().on("name").named("name_1"));
		List<Map<String, Object>> ll = dao.findAllIndex("c");
		for (Map<String, Object> map : ll) {
			System.out.println(map);
		}

		dao.dropIndex("c", Index.$().on("name"));
//		dao.dropIndex("c","aa");
		List<Map<String, Object>> lista = dao.findAllIndex("c");
		for (Map<String, Object> map : lista) {
			System.out.println(map);
		}
	}
	
	@Test
	public void comIndex(){
//		dao.ensureIndex("c", Index.$().on("name").named("name_1").on("age"));
		List<Map<String, Object>> listaa = dao.findAllIndex("c");
		for (Map<String, Object> map : listaa) {
			System.out.println(map);
		}
		
//		dao.dropIndex("c", Index.$().on("name").on("age"));
//		dao.dropIndex("c", "name_1");
		List<Map<String, Object>> lista = dao.findAllIndex("c");
		for (Map<String, Object> map : lista) {
			System.out.println(map);
		}
	}
}
