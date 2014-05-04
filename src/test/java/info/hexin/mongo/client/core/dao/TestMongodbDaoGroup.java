package info.hexin.mongo.client.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.hexin.mongo.client.core.query.Group;
import info.hexin.mongo.client.core.query.Query;

public class TestMongodbDaoGroup {
	MongoDao dao = new MongoDao();

//	@Test
	public void test1() {
		// dao.save("c", Maps.ofObject("name", "hexin"));
		StringBuilder r = new StringBuilder();
		r.append("function (v,k)");
		r.append("{");
		r.append(" k.count++");
		r.append("}");
		List<Map<String, Object>> list = dao.group("c", null, Group.key("name").reduce(r.toString())
				.initial("count", 0));
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		// dao.drop("c");
	}

	/**
	 * { "_id" : "04ef269abd1d4b8c9635d9e00bb0d5f1", "name" : "hexin0", "value"
	 * : 0, "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 0 } { "_id"
	 * : "6bb372e0d2d944b5ad9881e641b3d1ce", "name" : "hexin1", "value" : 1,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 1 } { "_id" :
	 * "aef8dc63f46f4e038458b0343ba87631", "name" : "hexin2", "value" : 2,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 2 } { "_id" :
	 * "843e6948fcc04f4081b1073dd424b616", "name" : "hexin3", "value" : 3,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 0 } { "_id" :
	 * "aabb357b1aae4cb0b97f9f6d1288fdc0", "name" : "hexin4", "value" : 4,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 1 } { "_id" :
	 * "02812c0e5e564612b57227030d124bd0", "name" : "hexin5", "value" : 5,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 2 } { "_id" :
	 * "d28a7418b54e476f957cfa8ca8c62063", "name" : "hexin6", "value" : 6,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 0 } { "_id" :
	 * "51766ab6382447d1b2a57ed4306b4da0", "name" : "hexin7", "value" : 7,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 1 } { "_id" :
	 * "ca9c852ceace4d789dab1d842da3ee54", "name" : "hexin8", "value" : 8,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 2 } { "_id" :
	 * "e8115835d2f944418fc906676417b289", "name" : "hexin9", "value" : 9,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 0 } { "_id" :
	 * "9ad95a7d78c74c8ca7941369b051d758", "name" : "hexin10", "value" : 10,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 1 } { "_id" :
	 * "f57c3b6a614d497b81f3ea76e1549019", "name" : "hexin11", "value" : 11,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 2 } { "_id" :
	 * "f8100636b7904bdf96059956631b88d9", "name" : "hexin12", "value" : 12,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 0 } { "_id" :
	 * "79f415fc1cc64e2c8b5dea94355e1803", "name" : "hexin13", "value" : 13,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 1 } { "_id" :
	 * "78c4cc1daa12491081c66b938265f7b2", "name" : "hexin14", "value" : 14,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 2 } { "_id" :
	 * "d1f9ddffb9e74b9c949af547e836332f", "name" : "hexin15", "value" : 15,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 0 } { "_id" :
	 * "789471b601e540ab983129db9ff15aa2", "name" : "hexin16", "value" : 16,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 1 } { "_id" :
	 * "560e41f0f9f5406b877dfb779c37c9f3", "name" : "hexin17", "value" : 17,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 2 } { "_id" :
	 * "5f6b2efa9bd14c9880c25bd463547d7d", "name" : "hexin18", "value" : 18,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 0 } { "_id" :
	 * "8e842cc36db44494a61200c04f1e1945", "name" : "hexin19", "value" : 19,
	 * "date" : ISODate("2012-12-19T11:48:07.151Z"), "group" : 1 }
	 */

	// 类似于
	/**
	 * select date , group , sum(value) from xx where name like "hexin%" group
	 * by group , date
	 * 
	 * 
	 */
	
//	@Test
	public void test2() {
		// saveData();

		StringBuilder keyfun = new StringBuilder();
		keyfun.append("function(d) {");
		keyfun.append("  return { ");
		keyfun.append("     goup : d.group ,");
		keyfun.append("     d_o_f: d.date.getDay() ");
		keyfun.append("   } ;");
		keyfun.append(" }");

		StringBuffer reduce = new StringBuffer();
		reduce.append("function ( curr, result) {");
		reduce.append("		result.total += curr.value; ");
		reduce.append("     result.count++;");
		reduce.append("}");

		// 最后给结果跑一遍修改结果的值
		StringBuffer finalize = new StringBuffer();
		finalize.append("function(result){");
		finalize.append(" var weekdays = [ '星期天', '星期一', '星期二',");
		finalize.append("    '星期三', '星期四', ");
		finalize.append("      '星期五', '星期六' ];");
		finalize.append("  result.d_o_f = weekdays[result.d_o_f];  ");
		finalize.append("  result.avg = Math.round(result.total / result.count);  ");
		finalize.append("}");

		Group group = Group.keyFunction(keyfun.toString()).initial("count", 0).initial("total", 0)
				.reduce(reduce.toString()).finalizeFunction(finalize.toString());
		List<Map<String, Object>> list = dao.group("c", Query.where("name").startWith("hexin"), group);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		// dao.drop("c");
	}

	private void saveData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 20; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "hexin" + i);
			map.put("value", +i);
			map.put("group", i % 3);
			map.put("date", new Date());
			list.add(map);
		}
		dao.save("c", list);
	}
}
