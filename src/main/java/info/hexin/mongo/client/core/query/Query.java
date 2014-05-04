package info.hexin.mongo.client.core.query;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import info.hexin.mongo.client.core.mapper.OP;
import info.hexin.mongo.client.exception.QueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 查询条件生成工具类
 * 
 * @author hexin
 * 
 */
public class Query {
    private Logger logger = LoggerFactory.getLogger(Query.class);

    private final Object NOT_SET_VALUE = new Object();
    private Object isValue = NOT_SET_VALUE;

    /**
     * 可以按照插入的顺序来生存query
     */
    private Map<String, Object> criteria = new LinkedHashMap<String, Object>();
    private DBObject queryMap = new BasicDBObject();
    private List<Query> queryList = new ArrayList<Query>();

    private String key;

    // order
    private Sort sort;
    private Pager pager;

    // 查询结果显示字段
    private Set<String> fieldSet;

    /**
     * 主要使用与统一key存在多个查询条件
     * 
     * @param query
     * @param key
     */
    private Query(Query query, String key) {
        this.key = key;
        this.queryList.addAll(query.queryList);
        this.queryList.add(this);
        this.queryMap.putAll(query.queryMap);

        // 此时this.sort == null
        if (query.sort != null) {
            this.sort = query.sort;
        }
        if (query.pager != null) {
            this.pager = query.pager;
        }
    }

    /**
     * 创建多个查询条件key
     * 
     * @param key
     */
    private Query(String key) {
        this.key = key;
        queryList.add(this);
    }

    private Query(Map<String, Object> map) {
        queryList.add(this);
        this.queryMap.putAll(map);
    }

    private Query() {
    }

    /**
     * xx 是指为了区别
     * 
     * @param json
     * @param xx
     */
    private Query(String json, String xx) {
        queryList.add(this);
        DBObject queryMap = (DBObject) JSON.parse(json);
        this.queryMap.putAll(queryMap);
    }

    // //// 需要重新创建 query /////
    /**
     * 统一使用Cnd.and
     * 
     * @param q
     * @return
     */
    @Deprecated
    public static Query cnd(Query... q) {
        Query query = new Query();
        for (Query qq : q) {
            query.queryList.add(qq);
        }
        return query;
    }

    /**
     * 错误使用： <li>
     * <code>Query.id().is("xx").where("aaa").is("124") </code>等价于
     * <code>Query.where("aaa").is("124") </code> 会丢失where之前的查询条件 <li>
     * <code>Query.where("aaa").is("124").id().is("xx") </code>等价于
     * <code>Query.id().is("xx") </code> 会丢失id之前的查询条件
     * 
     * <br>
     * 也就是会丢失第二个静态查询条件之前所有的查询条件 <br>
     * 正确使用 :<code>Query.id().is("xx").and("aaa").is("124")
     * 
     * 
     * @param key
     * @return
     */
    public static Query where(String key) {
        return new Query(key);
    }

    public static Query Json(String json) {
        return new Query(json, "json");
    }

    public static Query id() {
        return new Query(OP.ID);
    }

    public static Query map(Map<String, Object> map) {
        return new Query(map);
    }

    /**
     * 推荐使用$()
     * 
     * @return
     */
    @Deprecated
    public static Query sort() {
        return new Query();
    }

    /**
     * 此方法仅仅适用于没有查询条件，仅仅只是为了排序，使用会丢掉调用之前所有查询条件 <br>
     * 错误使用： <li>
     * <code>Query.where("name").is("xx").sort().asc("aa")</code>丢失name="xx"
     * 
     * @return
     */
    public static Query $() {
        return new Query();
    }

    public Query and(String key) {
        return new Query(this, key);
    }

    // /// 简单查询/////////
    public Query in(Object... o) {
        criteria.put(OP.IN, Arrays.asList(o));
        return this;
    }

    public Query in(String... o) {
        criteria.put(OP.IN, Arrays.asList(o));
        return this;
    }

    public Query nin(Object... o) {
        criteria.put(OP.NIN, Arrays.asList(o));
        return this;
    }

    public Query is(Object o) {
        if (this.isValue != NOT_SET_VALUE) {
            throw new QueryException(key + " 已经赋值 " + isValue + ", 不能重新赋值！ ");
        }

        if (criteria.size() > 0) {
            if (!lastIsNot()) {
                throw new QueryException(key + " 已经赋值 " + criteria + ", 不能重新赋值！ ");
            }
        }
        this.isValue = o;
        return this;
    }

    private boolean lastIsNot() {
        return this.criteria.size() > 0 && "$not".equals(this.criteria.keySet().toArray()[this.criteria.size() - 1]);
    }

    public Query is(Query... query) {
        BasicDBList bsonList = new BasicDBList();
        for (Query q : query) {
            bsonList.add(q.getQueryObject());
        }
        if (this.isValue != NOT_SET_VALUE) {
            throw new QueryException(key + " 已经赋值 " + isValue + ", 不能重新赋值！ ");
        }
        this.isValue = bsonList;
        return this;
    }

    public Query lessThan(Object o) {
        criteria.put(OP.LT, o);
        return this;
    }

    public Query greaterThan(Object o) {
        criteria.put(OP.GT, o);
        return this;
    }

    public Query lessEqThan(Object o) {
        criteria.put(OP.LTE, o);
        return this;
    }

    public Query greaterEqThan(Object o) {
        criteria.put(OP.GTE, o);
        return this;
    }

    public Query size(int n) {
        criteria.put(OP.SIZE, n);
        return this;
    }

    // exists
    public Query exists(boolean exists) {
        if (this.isValue != NOT_SET_VALUE) {
            throw new QueryException(key + " 已经赋值 " + isValue + ", 不能重新赋exists值！ ");
        }
        criteria.put(OP.EXISTS, exists);
        return this;
    }

    // not
    public Query not() {
        criteria.put(OP.NOT, null);
        return this;
    }

    public Query not(Object o) {
        criteria.put(OP.NOT, o);
        return this;
    }

    // /mod
    public Query ne(Object o) {
        criteria.put(OP.NE, o);
        return this;
    }

    // /正则 like
    public Query like(Object o) {
        Pattern pattern = Pattern.compile("^.*" + o.toString() + ".*$", Pattern.MULTILINE);
        criteria.put(OP.REGEX, pattern);
        return this;
    }

    public Query startWith(Object o) {
        Pattern pattern = Pattern.compile("^" + o.toString(), Pattern.MULTILINE);
        criteria.put(OP.REGEX, pattern);
        return this;
    }

    public Query endWith(Object o) {
        Pattern pattern = Pattern.compile(o.toString() + "$", Pattern.MULTILINE);
        criteria.put(OP.REGEX, pattern);
        return this;
    }

    /************ 排序开始 ****************/
    public Query asc(String orderKey) {
        if (sort == null) {
            sort = new Sort();
        }
        sort.on(orderKey, OP.ASC);
        return this;
    }

    public Query desc(String orderKey) {
        if (sort == null) {
            sort = new Sort();
        }
        sort.on(orderKey, OP.DESC);
        return this;
    }

    /************ 排序完成 ****************/

    /************ 翻页开始 ****************/
    public Query skip(int skip) {
        if (this.pager == null) {
            this.pager = new Pager();
        }
        this.pager.setSkip(skip);
        return this;
    }

    public Query limit(int limit) {
        if (this.pager == null) {
            this.pager = new Pager();
        }
        this.pager.setLimit(limit);
        return this;
    }

    /************ 翻页结束 ****************/
    /************ 过滤字段开始 ****************/

    public Query field(String field) {
        if (this.fieldSet == null) {
            this.fieldSet = new HashSet<String>();
        }
        fieldSet.add(field);
        return this;
    }

    /************ 过滤字段结束 ****************/

    // / getMongodb
    public DBObject getQueryObject() {

        DBObject dbo = new BasicDBObject();
        boolean not = false;
        for (String k : this.criteria.keySet()) {
            Object value = this.criteria.get(k);
            if (not) {
                DBObject notDbo = new BasicDBObject();
                notDbo.put(k, value);
                dbo.put("$not", notDbo);
                not = false;
            } else {
                if ("$not".equals(k) && value == null) {
                    not = true;
                } else {
                    dbo.put(k, value);
                }
            }
        }

        DBObject queryObject = new BasicDBObject();
        for (Query query : queryList) {

            // 一个key is只能使用一次
            if (query.criteria.size() > 0) {
                DBObject keyQuery = null;
                if (queryObject.containsField(query.key)) {
                    Object keyvalue = queryObject.get(query.key);
                    if (keyvalue instanceof DBObject) {
                        // TODO
                    }
                    System.out.println(keyvalue);
                } else {
                    keyQuery = new BasicDBObject();
                    for (String queryKey : query.criteria.keySet()) {
                        keyQuery.put(queryKey, query.criteria.get(queryKey));
                    }
                }
                logger.debug("key >>> {}, query >>>{}", query.key, keyQuery);
                queryObject.put(query.key, keyQuery);
            }

            // is 赋值 放到前面 会出现对同一个key赋值两次情况
            if (query.isValue != query.NOT_SET_VALUE) {
                queryObject.put(query.key, query.isValue);
            }
        }

        queryObject.putAll(queryMap);
        return queryObject;
    }

    public DBObject getSortObject() {
        DBObject sortDbObject = new BasicDBObject();
        if (this.sort != null) {
            Map<String, Integer> orderMap = this.sort.orderMap();
            sortDbObject.putAll(orderMap);
        }
        return sortDbObject;
    }

    /**
     * 返回将要显示的字段
     * 
     * @return
     */
    public DBObject getFieldObject() {
        if (fieldSet == null) {
            return null;
        }

        DBObject fieldDbObject = new BasicDBObject();
        for (String key : fieldSet) {
            fieldDbObject.put(key, 1);
        }
        return fieldDbObject;
    }

    /**
     * 获取当前Query的翻页信息
     * 
     * @return
     */
    public Pager getPager() {
        return pager;
    }

}
