package info.hexin.mongo.client.core.index;

import org.junit.Test;

import info.hexin.mongo.client.core.dao.MongoDao;
import info.hexin.mongo.client.exception.IndexException;

public class TestIndex1 {

	@Test(expected = IndexException.class)
	public void test1() {
	    MongoDao dao = new MongoDao();
		Index index = Index.$().on("a").named("123").named("1234");
		dao.ensureIndex("c", index);
		System.out.println(index.getIndexKeys() + "------" + index.getIndexOptions());
	}
}
