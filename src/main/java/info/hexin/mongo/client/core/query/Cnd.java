package info.hexin.mongo.client.core.query;

/**
 * 条件生成器
 * 
 * 加入要生成 <ui> <li>
 * <code> Cnd.or(Query.where("a").is("xx"), Query.where("b").is("xx")) <code>生成
 *  <code> { "$or" : [ { "a" : "xx"} , { "b" : "xx"}]}</code></li> <li>
 * Cnd.or(Query.where("a").is("xx"), Query.where("b").is("xx"),
 * Query.where("c").is("cc")) 生成 { "$or" : [ { "a" : "xx"} , { "b" : "xx"} , {
 * "c" : "cc"}]}</li> <li>
 * </li>Cnd.and(Query.where("a").is("xx"), Query.where("b").is("xx") 生成 { "$and"
 * : [ { "a" : "xx"} , { "b" : "xx"}]}<li>
 * </li> Cnd.and(Query.where("a").is("xx"), Query.where("b").is("xx"),
 * Query.where("c").is("cc")) 生成 { "$and" : [ { "a" : "xx"} , { "b" : "xx"} , {
 * "c" : "cc"}]}<li>
 * </li>Query.cnd( Cnd.and(Query.where("a").is("aa"),
 * Query.where("b").is("bb")), Cnd.or(Query.where("c").is("cc"),
 * Query.where("d").is("dd")) ) 生成 { "$and" : [ { "a" : "aa"} , { "b" : "bb"}] ,
 * "$or" : [ { "c" : "cc"} , { "d" : "dd"}]} <ui>
 * 
 * @author hexin
 * 
 */
public class Cnd {
	public static Query or(Query... query) {
		return Query.where("$or").is(query);
	}

	public static Query and(Query... query) {
		return Query.where("$and").is(query);
	}
}
