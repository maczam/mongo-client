package info.hexin.mongo.client.core.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMongodbDaoMr {
	MongoDao dao = new MongoDao();

	/**
	 * 找到最小值 和最大值
	 */
//	@Test
	public void test1(){
//		saveData();
		
		StringBuilder map = new StringBuilder();
		map.append("function () {");
		map.append("    var x = { group : this.name,value : this.value};");
		map.append("    emit(this.group, { min : x , max : x } )");
		map.append("}");
		
		StringBuilder reduce = new StringBuilder();
		reduce.append("function (key, values) {");
		reduce.append("    var res = values[0];");
//		reduce.append("    res.count = 0");
		reduce.append("    for ( var i=1; i<values.length; i++ ) {");
//		reduce.append("        res.count += values[i];");
		reduce.append("        if ( values[i].min.value < res.min.value) {");
		reduce.append("           res.min = values[i].min;");
		reduce.append("         }");
		reduce.append("        if ( values[i].max.value > res.max.value) {");
		reduce.append("           res.max = values[i].max;");
		reduce.append("         }");
		reduce.append("    }");
		reduce.append("    return res;");
		reduce.append("}");
		
		List<Map<String, Object>> list = dao.mapReduce("c", map.toString(), reduce.toString());
		for (Map<String, Object> map2 : list) {
			System.out.println(map2);
		}
	}
	
//	@Test
	public void mr1() {
//		saveData();
		//select group ,sum(value),count(1),avg(value)
		//from xx
		//group by group
		StringBuilder mapfun = new StringBuilder();
		mapfun.append("function (){");
		mapfun.append("		emit(this.group,{value : this.value, sum : 0 , avg : 0});");
		mapfun.append("}");
		
		StringBuilder reduceFun = new StringBuilder();
		reduceFun.append("function (key,vals){");
		reduceFun.append("   var res = vals[0] ;");
		reduceFun.append("   var sum = 0 ;");
		reduceFun.append("   var count = 0 ;");
		reduceFun.append("   for(var i in vals) {");
		reduceFun.append("      count ++;");
		reduceFun.append("      sum += vals[i].value;");
		reduceFun.append("   }");
		reduceFun.append("   res.sum = sum;");
		reduceFun.append("   res.avg = sum/count;");
		reduceFun.append("   return res;");
		reduceFun.append("}");
		
		List<Map<String, Object>> list = dao.mapReduce("c", mapfun.toString(), reduceFun.toString());
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
//	@Test
	public void test2(){
		
		//select group ,sum(value),count(1),avg(value),max(value),min(value)
		//from xx
		//group by group
		StringBuilder mapfun = new StringBuilder();
		mapfun.append("function (){");
		mapfun.append("		emit(this.group,{group:this.group ,value : this.value});");
		mapfun.append("}");
		
		StringBuilder reduceFun = new StringBuilder();
		reduceFun.append("function (key,vals){");
		reduceFun.append("   var res = {} ;");
		reduceFun.append("   var sum = 0 ;");
		reduceFun.append("   var count = 0 ;");
		reduceFun.append("   var avg = 0 ;");
		reduceFun.append("   var max = vals[0].value;");
		reduceFun.append("   var min = vals[0].value;");
		reduceFun.append("   for(var i in vals) {");
		reduceFun.append("      var value =  vals[i].value;");
		reduceFun.append("      count ++;");
		reduceFun.append("      sum += value;");
		reduceFun.append("   	if(max < value){");
		reduceFun.append("   		max = value");
		reduceFun.append("   	}");
		reduceFun.append("   	if(min > value){");
		reduceFun.append("   		min = value");
		reduceFun.append("   	}");
		reduceFun.append("   }");
		reduceFun.append("   res.group = vals[0].group;");
		reduceFun.append("   res.sum = sum;");
		reduceFun.append("   res.avg = sum/count;");
		reduceFun.append("   res.max = max;");
		reduceFun.append("   res.min = min;");
		reduceFun.append("   return res;");
		reduceFun.append("}");
		
		List<Map<String, Object>> list = dao.mapReduce("c", mapfun.toString(), reduceFun.toString());
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	
	private void saveData() {
		dao.drop("c");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "hexin" + i);
			map.put("value", i);
			map.put("group", i%3);
			list.add(map);
		}
		dao.save("c", list);
	}
}
