package info.hexin.mongo.example.chapter02.faq;

import org.junit.Test;

import info.hexin.mongo.client.core.query.Query;

public class FAQ248 {
    @Test
    public void question1_1() {
        Query q = Query.where("a1").is("1").asc("aa").desc("bb").and("b1").is("2");
        System.out.println(q.getQueryObject());
        System.out.println(q.getSortObject());
    }
    
    @Test
    public void question1_2() {
        Query q = Query.where("age").lessEqThan(23).greaterThan(20).and("name").like("a").asc("name");
        System.out.println(q.getQueryObject());
        System.out.println(q.getSortObject());
    }
    
    @Test
    public void question2_1() {
        
    }
}
