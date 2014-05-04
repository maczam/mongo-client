package info.hexin.mongo.client.core.query;


import org.junit.Assert;
import org.junit.Test;

public class TestCnd {
	@Test
	public void test() {
		Query query = Cnd.or(Query.where("a").is("xx"), Query.where("b").is("xx"));
		Assert.assertEquals("{ \"$or\" : [ { \"a\" : \"xx\"} , { \"b\" : \"xx\"}]}", query.getQueryObject().toString());
	}

	@Test
	public void or1() {
		Query query = Cnd.or(Query.where("a").is("xx"), Query.where("b").is("xx"), Query.where("c").is("cc"));
		Assert.assertEquals("{ \"$or\" : [ { \"a\" : \"xx\"} , { \"b\" : \"xx\"} , { \"c\" : \"cc\"}]}", query
				.getQueryObject().toString());
	}

	@Test
	public void and() {
		Query query = Cnd.and(Query.where("a").is("xx"), Query.where("b").is("xx"));
		Assert.assertEquals("{ \"$and\" : [ { \"a\" : \"xx\"} , { \"b\" : \"xx\"}]}", query.getQueryObject().toString());
	}

	@Test
	public void and1() {
		Query query = Cnd.and(Query.where("a").is("xx"), Query.where("b").is("xx"), Query.where("c").is("cc"));
		System.out.println(query.getQueryObject());
		Assert.assertEquals("{ \"$and\" : [ { \"a\" : \"xx\"} , { \"b\" : \"xx\"} , { \"c\" : \"cc\"}]}", query
				.getQueryObject().toString());
	}

	@Test
	public void orand() {
		Query query = Cnd.and(
				Cnd.and(Query.where("a").is("aa"),
						Query.where("b").is("bb")),
				Cnd.or(Query.where("c").is("cc"), 
					   Query.where("d").is("dd"))
						);
		System.out.println(query.getQueryObject());
	}
}
