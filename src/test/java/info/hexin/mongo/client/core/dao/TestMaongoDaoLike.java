package info.hexin.mongo.client.core.dao;

import info.hexin.mongo.client.core.query.Query;
import org.junit.Test;

import java.util.Map;

public class TestMaongoDaoLike {
	MongoDao dao = new MongoDao();

	@Test
	public void test1() {
		Map<String, Object> map = dao.findOne("c", Query.where("name").endWith("16"));
//		System.out.println(map);
	}
	
	@Test
	public void test2(){
		Map<String, Object> map = dao.findOne("c", Query.where("name").startWith("he"));
//		System.out.println(map);
	}
	@Test
	public void test3(){
		Map<String, Object> map = dao.findOne("c", Query.where("name").like("xin"));
//		System.out.println(map);
	}
}
