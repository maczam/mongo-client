package info.hexin.mongo.client.core.query;

import org.junit.Assert;
import org.junit.Test;

import info.hexin.mongo.client.exception.QueryException;
import info.hexin.mongo.client.util.Maps;

public class TestQuery {

    @Test
    public void map() {
        Query query = Query.map(Maps.ofObject("a", "b", "c", "d")).and("e").is("f");
        Assert.assertEquals("{ \"e\" : \"f\" , \"c\" : \"d\" , \"a\" : \"b\"}", query.getQueryObject().toString());
        Assert.assertEquals("{ }", query.getSortObject().toString());
    }

    @Test
    public void query1() {
        Query query = Query.id().is("123").and("111").is("bb");
        Assert.assertEquals("{ \"_id\" : \"123\" , \"111\" : \"bb\"}", query.getQueryObject().toString());
        Assert.assertEquals("{ }", query.getSortObject().toString());
    }

    @Test
    public void sort() {
        Query query = Query.where("a1").is("1").asc("aa").desc("bb").and("b1").is("2");
        Assert.assertEquals("{ \"a1\" : \"1\" , \"b1\" : \"2\"}", query.getQueryObject().toString());
        Assert.assertEquals("{ \"aa\" : 1 , \"bb\" : -1}", query.getSortObject().toString());
    }

    @Test
    public void in() {
        Query query = Query.where("xx").in("123", "1234", "4343");
        Assert.assertEquals("{ \"xx\" : { \"$in\" : [ \"123\" , \"1234\" , \"4343\"]}}", query.getQueryObject()
                .toString());
        Assert.assertEquals("{ }", query.getSortObject().toString());
    }

    @Test
    public void query() {
        Query query = Query.where("sss").lessThan("123").greaterThan("1234");
        Assert.assertEquals("{ \"sss\" : { \"$lt\" : \"123\" , \"$gt\" : \"1234\"}}", query.getQueryObject().toString());
        Assert.assertEquals("{ }", query.getSortObject().toString());
    }

    @Test
    public void testjson() {
        String json = "{ \"e\" : \"f\" , \"c\" : \"d\" , \"a\" : \"b\"}";
        Query query = Query.Json(json).and("aa").is("123");
        Assert.assertEquals("{ \"aa\" : \"123\" , \"e\" : \"f\" , \"c\" : \"d\" , \"a\" : \"b\"}", query
                .getQueryObject().toString());
    }

    @Test
    public void testjson1() {
        String json = "{ \"e\" : \"f\" , \"c\" : \"d\" , \"a\" : \"b\"}";
        Query query = Query.Json(json).desc("e");
        Assert.assertEquals("{ \"e\" : \"f\" , \"c\" : \"d\" , \"a\" : \"b\"}", query.getQueryObject().toString());
        Assert.assertEquals("{ \"e\" : -1}", query.getSortObject().toString());
    }

    @Test
    public void sort1() {
        Query query = Query.where("sss").lessThan("123").and("xxxx").greaterThan("1234").asc("xx").asc("abc");
        Assert.assertEquals("{ \"sss\" : { \"$lt\" : \"123\"} , \"xxxx\" : { \"$gt\" : \"1234\"}}", query
                .getQueryObject().toString());
        Assert.assertEquals("{ \"abc\" : 1 , \"xx\" : 1}", query.getSortObject().toString());
    }

    @Test(expected = QueryException.class)
    public void exist() {
        Query.where("a").exists(true).is("xx");
    }

    @Test(expected = QueryException.class)
    public void exist1() {
        Query q = Query.where("a").is("xx").exists(true);
        System.out.println(q.getQueryObject());
    }

    @Test()
    public void exist2() {
        Query query = Query.where("a").in("xx").exists(true);
        Assert.assertEquals("{ \"a\" : { \"$in\" : [ \"xx\"] , \"$exists\" : true}}", query.getQueryObject().toString());
    }

    @Test
    public void not() {
        Query q = Query.where("a").not().is("1");
        Assert.assertEquals("{ \"a\" : \"1\"}", q.getQueryObject().toString());
    }

    @Test
    public void ne() {
        Query q = Query.where("a").is("1").and("b").ne("bb");
        Assert.assertEquals("{ \"a\" : \"1\" , \"b\" : { \"$ne\" : \"bb\"}}", q.getQueryObject().toString());
    }
    
    
    @Test
    public void skip(){
        Query q = Query.$().skip(1).and("aaa").is("124");
        System.out.println(q.getQueryObject());
        System.out.println(q.getPager());
    }

    @Test
    public void chageKey() {
        Query q = Query.id().is("xx").where("124").is("aaa");
        Assert.assertEquals("{ \"124\" : \"aaa\"}", q.getQueryObject().toString());
    }
}
