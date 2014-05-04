package info.hexin.mongo.client.core.dao;

import info.hexin.mongo.client.auth.DefaultAuthDBImpl;
import info.hexin.mongo.client.core.query.Query;
import org.junit.Before;
import org.junit.Test;


/**
 * gridfs相关
 * 
 * @author hexin
 * 
 */
public class TestMongoDaoGrid {
	MongoDao dao = new MongoDao();
	DefaultAuthDBImpl authDBImpl = new DefaultAuthDBImpl();

	@Before
	public void setUp() {
		dao.setAuthDB(authDBImpl);
	}
	@Test
	public void test1(){
		dao.removeGridFile("f", Query.where("filename").is("1.txt"));
//		dao.saveGridFile("f", "c:/1.txt");
	}
}
