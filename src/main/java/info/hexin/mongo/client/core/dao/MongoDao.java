package info.hexin.mongo.client.core.dao;

import com.mongodb.*;
import com.mongodb.MapReduceCommand.OutputType;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import info.hexin.mongo.client.auth.DefaultAuthDBImpl;
import info.hexin.mongo.client.callback.*;
import info.hexin.mongo.client.core.index.Index;
import info.hexin.mongo.client.core.mapper.OP;
import info.hexin.mongo.client.core.query.Group;
import info.hexin.mongo.client.core.query.Pager;
import info.hexin.mongo.client.core.query.Query;
import info.hexin.mongo.client.core.update.Update;
import info.hexin.mongo.client.exception.MongodbDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * mongodb操作模版类
 * 
 * 
 * @author hexin
 * 
 */
public class MongoDao extends AbstractMongoDao {
    private Logger logger = LoggerFactory.getLogger(MongoDao.class);

    private static final String ID = "_id";

    public MongoDao() {
        this.setAuthDB(new DefaultAuthDBImpl());
    }

    public void requestStart() {
        getDB().requestStart();
    }

    public void requestDone() {
        getDB().requestStart();
    }

    /**
     * 
     * @param collectionName
     * @return
     */
    public List<Map<String, Object>> findAll(String collectionName) {
        return findAll(collectionName, mapDBObjectCallbackImpl);
    }

    /**
     * 
     * @param collectionName
     * @param callback
     * @return
     */
    public <T> List<T> findAll(String collectionName, DBObjectCallback<T> callback) {
        return find(collectionName, null, callback);
    }

    /**
     * 
     * @param collectionName
     * @param query
     * @return
     */
    public List<Map<String, Object>> find(String collectionName, Query query) {
        return find(collectionName, query, mapDBObjectCallbackImpl);
    }

    /**
     * 
     * @param collectionName
     * @param query
     * @return
     */
    public Map<String, Object> findOne(String collectionName, Query query) {
        return findOne(collectionName, query, mapDBObjectCallbackImpl);
    }

    /**
     * 
     * @param collectionName
     * @param query
     * @param callback
     * @return
     */
    public <T> T findOne(String collectionName, Query query, DBObjectCallback<T> callback) {
        DBObject dbObject = null;
        if (query != null) {
            DBObject fieldObject = query.getFieldObject();
            if (fieldObject != null) {
                dbObject = getCollection(collectionName).findOne(query.getQueryObject(), fieldObject);
            }
            dbObject = getCollection(collectionName).findOne(query.getQueryObject());
        } else {
            dbObject = getCollection(collectionName).findOne();
        }
        logger.debug("查询 集合>>>> {} , 查询结果 >>>> {}", collectionName, dbObject);
        return callback.processDBObject(dbObject);
    }

    /**
     * 
     * @param collectionName
     * @param query
     * @param callback
     * @return
     */
    public <T> List<T> find(String collectionName, Query query, DBObjectCallback<T> callback) {
        List<T> list = new ArrayList<T>();
        DBCursor dbCursor = null;
        if (query != null) {
            DBObject queryObject = query.getQueryObject();
            logger.info("collectionName >>> {} 查询条件 >>> {}", collectionName, queryObject);
            DBObject fieldObject = query.getFieldObject();
            DBObject stortObject = query.getSortObject();
            Pager pager = query.getPager();
            if (pager == null) {
                if (fieldObject == null) {
                    dbCursor = getCollection(collectionName).find(queryObject).sort(stortObject);
                } else {
                    dbCursor = getCollection(collectionName).find(queryObject, fieldObject).sort(stortObject);
                }
            } else if (pager.getLimit() != Pager.NO_SET_VALUE && pager.getSkip() != Pager.NO_SET_VALUE) {
                if (fieldObject == null) {
                    dbCursor = getCollection(collectionName).find(queryObject).sort(stortObject).skip(pager.getSkip())
                            .limit(pager.getLimit());
                } else {
                    dbCursor = getCollection(collectionName).find(queryObject, fieldObject).sort(stortObject)
                            .skip(pager.getSkip()).limit(pager.getLimit());
                }
            } else if (pager.getLimit() != Pager.NO_SET_VALUE) {
                if (fieldObject == null) {
                    dbCursor = getCollection(collectionName).find(queryObject, fieldObject).sort(stortObject)
                            .limit(pager.getLimit());
                } else {
                    dbCursor = getCollection(collectionName).find(queryObject).sort(stortObject)
                            .limit(pager.getLimit());
                }
            } else if (pager.getSkip() != Integer.MIN_VALUE) {
                if (fieldObject == null) {
                    dbCursor = getCollection(collectionName).find(queryObject).sort(stortObject).skip(pager.getSkip());
                } else {
                    dbCursor = getCollection(collectionName).find(queryObject, fieldObject).sort(stortObject)
                            .skip(pager.getSkip());
                }
            }

        } else {
            dbCursor = getCollection(collectionName).find();
        }

        while (dbCursor.hasNext()) {
            T t = callback.processDBObject(dbCursor.next());
            list.add(t);
        }
        return list;
    }

    public <T> T findById(String collectionName, Object id, DBObjectCallback<T> callback) {
        return findOne(collectionName, Query.where(OP.ID), callback);
    }

    public Map<String, Object> findById(String collectionName, Object id) {
        return findOne(collectionName, Query.where(OP.ID).is(id), mapDBObjectCallbackImpl);
    }

    /**
     * 
     * @param collectionName
     * @param map
     * @return
     */
    public Object merge(String collectionName, Map<String, Object> map) {
        if (saved(map)) {
            DBObject saveObject = new BasicDBObject(map);
            saveDBObject(collectionName, saveObject);
            return map.get(ID);
        } else {
            return doInsert(collectionName, map);
        }
    }

    /**
     * 为了返回去的id和传递进来顺序一只. 单条插入
     * 
     * @param collectionName
     * @param list
     * @return
     */
    public Object merge(String collectionName, List<Map<String, Object>> list) {
        List<Object> ids = new ArrayList<Object>();
        for (Map<String, Object> map : list) {
            ids.add(merge(collectionName, map));
        }
        return list;
    }

    /**
     * 批量保存
     * 
     * @param collectionName
     * @param list
     * @return
     */
    private List<Object> saveListMap(String collectionName, List<Map<String, Object>> list) {
        List<Object> ids = new ArrayList<Object>(list.size());
        List<DBObject> lists = new ArrayList<DBObject>(list.size());
        for (Map<String, Object> map : list) {
            if (!saved(map)) { // 没有"_id"
                Object id = getIdGenerate().id();
                map.put(OP.ID, id);
                ids.add(id);
            } else {
                ids.add(map.get(OP.ID));
            }
            lists.add(new BasicDBObject(map));
        }
        saveListDBObject(collectionName, lists);
        return ids;
    }

    /**
     * @param collectionName
     * @param map
     * @return
     */
    public Object save(String collectionName, Map<String, Object> map) {
        if (saved(map)) {
            DBObject saveObject = new BasicDBObject(map);
            saveDBObject(collectionName, saveObject);
            return map.get(ID);
        } else {
            return doInsert(collectionName, map);
        }
    }
    
    public Object save(String collectionName, List<Map<String, Object>> list){
        return saveListMap(collectionName, list);
    }

    // // remove 开始 //////
    /**
     * 你组装好查询条件我帮你删除出，错会报异常
     * 
     * @param collectionName
     * @param query
     */
    public void remove(final String collectionName, final Query query) {
        execute(collectionName, new CollectionCallback<Object>() {
            @Override
            public Object doInCollection(DBCollection collection) {
                DBObject queryDbObject = query.getQueryObject();
                logger.debug("数据库删除操作 collectionName >>>> {},queryDbObject >> {}", collectionName, queryDbObject);
                WriteResult w = collection.remove(queryDbObject);
                writeResultHandler(w);
                return w;
            }
        });
    }

    /**
     * 按照id来进行删除
     * 
     * @param collectionName
     * @param id
     */
    public void removeById(final String collectionName, final Object id) {
        remove(collectionName, Query.id().is(id));
    }

    // // remove 完成 //////

    // // mr 开始 ////
    public List<Map<String, Object>> mapReduce(String collectionName, String mapFun, String reduceFun) {
        return mapReduce(collectionName, mapFun, reduceFun, null, OutputType.INLINE, null);
    }

    public <T> List<T> mapReduce(String collectionName, String mapFun, String reduceFun, DBObjectCallback<T> callback) {
        return mapReduce(collectionName, mapFun, reduceFun, null, OutputType.INLINE, null, callback);
    }

    public <T> List<T> mapReduce(String collectionName, String mapFun, String reduceFun, String outputCollection,
            OutputType type, Query query, DBObjectCallback<T> callback) {
        DBCollection conCollection = getCollection(collectionName);
        buildMapReduceCommand(mapFun, reduceFun, outputCollection, type, query, conCollection);
        return mapReduce(collectionName, mapFun, reduceFun, null, OutputType.INLINE, null, callback);
    }

    /**
     * 按照条件做mr
     * 
     * @param collectionName
     * @param mapFun
     * @param reduceFun
     * @param query
     * @return
     */
    public List<Map<String, Object>> mapReduce(String collectionName, String mapFun, String reduceFun, Query query) {
        DBCollection conCollection = getCollection(collectionName);
        MapReduceCommand command = buildMapReduceCommand(mapFun, reduceFun, null, OutputType.INLINE, query,
                conCollection);
        return executeMapReduceCommand(conCollection, command, new MapDBObjectCallbackImpl());
    }

    public List<Map<String, Object>> mapReduce(String collectionName, String mapFun, String reduceFun,
            String outputCollection, OutputType type, Query query) {
        DBCollection conCollection = getCollection(collectionName);
        MapReduceCommand command = buildMapReduceCommand(mapFun, reduceFun, outputCollection, type, query,
                conCollection);
        return executeMapReduceCommand(conCollection, command, new MapDBObjectCallbackImpl());
    }

    /**
     * 构造MapReduceCommand对象
     * 
     * @param mapFun
     * @param reduceFun
     * @param outputCollection
     * @param type
     * @param query
     * @param conCollection
     * @return
     */
    protected MapReduceCommand buildMapReduceCommand(String mapFun, String reduceFun, String outputCollection,
            OutputType type, Query query, DBCollection conCollection) {
        MapReduceCommand command;
        if (query == null) {
            command = new MapReduceCommand(conCollection, mapFun, reduceFun, outputCollection, type, null);
        } else {
            command = new MapReduceCommand(conCollection, mapFun, reduceFun, outputCollection, type,
                    query.getQueryObject());
        }
        return command;
    }

    // // mr 完成 ////

    // / index 开始 ////
    /**
     * 创建索引,删除的时候是按照key来删除的
     * 
     * @param collectionName
     * @param idx
     */
    public final void ensureIndex(final String collectionName, final Index idx) {
        execute(collectionName, new CollectionCallback<Object>() {
            @Override
            public Object doInCollection(DBCollection collection) {
                DBObject indexKey = idx.getIndexKeys();
                DBObject indexOptions = idx.getIndexOptions();
                logger.debug("数据库创建索引操作 collectionName >>>> {},getIndexKeys >> {} ,indexOptions >>> {}", new Object[] {
                        collectionName, indexKey, indexOptions });
                if (indexOptions != null) {
                    collection.ensureIndex(indexKey, indexOptions);
                } else {
                    collection.ensureIndex(indexKey);
                }
                return null;
            }
        });
    }

    /**
     * 删除索引
     * 
     * @param collectionName
     * @param idx
     */
    public final void dropIndex(final String collectionName, final Index idx) {
        execute(collectionName, new CollectionCallback<Object>() {
            @Override
            public Object doInCollection(DBCollection collection) {
                DBObject indexKey = idx.getIndexKeys();
                logger.debug("数据库删除索引操作 collectionName >>>> {},getIndexKeys >> {}", new Object[] { collectionName,
                        indexKey });
                collection.dropIndex(indexKey);
                // collection.dropIndex(name)
                return null;
            }
        });
    }

    /**
     * 复合索引必须使用名称删除,不能使用key来删除
     * 
     * @param collectionName
     * @param idxName
     */
    public final void dropIndex(final String collectionName, final String idxName) {
        execute(collectionName, new CollectionCallback<Object>() {
            @Override
            public Object doInCollection(DBCollection collection) {
                logger.debug("数据库删除索引操作 collectionName >>>> {},idxName >> {}", new Object[] { collectionName, idxName });
                collection.dropIndex(idxName);
                return null;
            }
        });
    }

    /**
     * 删除所有所有的索引
     * 
     * @param collectionName
     */
    public final void dropAllIndex(final String collectionName) {
        execute(collectionName, new CollectionCallback<Object>() {
            @Override
            public Object doInCollection(DBCollection collection) {
                logger.debug("数据库删除所有索引操作 collectionName >>>> {} ", collectionName);
                collection.dropIndexes();
                return null;
            }
        });
    }

    /**
     * 查询所有的索引
     * 
     * @param collectionName
     * @return
     */
    public final List<Map<String, Object>> findAllIndex(final String collectionName) {
        return findAllIndex(collectionName, new MapDBObjectCallbackImpl());
    }

    public final <T> List<T> findAllIndex(final String collectionName, final DBObjectCallback<T> dbObjectCallback) {
        return execute(collectionName, new CollectionCallback<List<T>>() {
            @Override
            public List<T> doInCollection(DBCollection collection) {
                logger.debug("数据库 查询操作 findAllIndex ");
                List<T> ts = new ArrayList<T>();
                List<DBObject> list = collection.getIndexInfo();
                for (DBObject dbObject : list) {
                    ts.add(dbObjectCallback.processDBObject(dbObject));
                }
                return ts;
            }
        });
    }

    // / index 完成 ////

    // / gridfs 开始 ////
    public void saveGridFile(final String collectionName, final String fileName) {
        logger.debug("数据库 gridFile 保存操作 collectionName >>>> {}", collectionName);
        executeGridFS(collectionName, new GridFSCallback<Object>() {
            @Override
            public Object doInGridFS(GridFS gridFS) {
                try {
                    File file = new File(fileName);
                    if (!file.exists()) {
                        throw new MongodbDaoException("保存文件 fileName >>>> " + fileName + "不存在");
                    }
                    GridFSInputFile fsInputFile = gridFS.createFile(file);
                    fsInputFile.setFilename(file.getName());
                    fsInputFile.setId(getIdGenerate().id());
                    fsInputFile.save();
                } catch (Exception e) {
                    throw new MongodbDaoException("保存文件 fileName >>>> " + fileName + e.getMessage());
                }
                return null;
            }
        });
    }

    public <T> List<T> findGridFile(final String collectionName, final Query query,
            final GridFSDBFileCallback<T> gridFSDBFileCallback) {
        logger.debug("数据库 gridFile 查询操作 collectionName >>>> {}", collectionName);
        return executeGridFS(collectionName, new GridFSCallback<List<T>>() {
            @Override
            public List<T> doInGridFS(GridFS gridFS) {
                List<GridFSDBFile> list = gridFS.find(query.getQueryObject());
                List<T> listT = new ArrayList<T>(list.size());
                for (GridFSDBFile gridFSDBFile : list) {
                    listT.add(gridFSDBFileCallback.processGridFSDBFile(gridFSDBFile));
                }
                return listT;
            }
        });
    }

    public <T> T findOneGridFile(final String collectionName, final Query query,
            final GridFSDBFileCallback<T> gridFSDBFileCallback) {
        logger.debug("数据库 gridFile 查询操作 collectionName >>>> {}", collectionName);
        return executeGridFS(collectionName, new GridFSCallback<T>() {
            @Override
            public T doInGridFS(GridFS gridFS) {
                GridFSDBFile gridFSDBFile = gridFS.findOne(query.getQueryObject());
                return gridFSDBFileCallback.processGridFSDBFile(gridFSDBFile);
            }
        });
    }

    public void removeGridFile(final String collectionName, final Query query) {
        logger.debug("数据库 gridFile 删除操作 collectionName >>>> {}", collectionName);
        executeGridFS(collectionName, new GridFSCallback<Object>() {
            @Override
            public Object doInGridFS(GridFS gridFS) {
                gridFS.remove(query.getQueryObject());
                return null;
            }
        });

    }

    private <T> T executeGridFS(final String collectionName, GridFSCallback<T> gridFSCallback) {
        try {
            GridFS gridFS = getGridFS(collectionName);
            return gridFSCallback.doInGridFS(gridFS);
        } catch (Exception e) {
            throw new MongodbDaoException("GridFS 操作失败", e);
        }
    }

    // / gridfs 完成 ////
    /**
     * 
     * @param collectionName
     * @param query
     * @param update
     */
    public void updateOne(final String collectionName, Query query, Update update) {
        doUpdate(collectionName, query, update, false, false);
    }

    /**
     * 
     * @param collectionName
     * @param query
     * @param update
     * @param upsert
     */
    public void updateOne(final String collectionName, Query query, Update update, boolean upsert) {
        doUpdate(collectionName, query, update, upsert, false);
    }

    /**
     * 
     * @param collectionName
     * @param query
     * @param update
     */
    public void updateMulti(final String collectionName, Query query, Update update) {
        doUpdate(collectionName, query, update, false, true);
    }

    /**
     * 
     * @param collectionName
     * @param query
     * @param update
     * @param upsert
     */
    public void updateMulti(final String collectionName, Query query, Update update, boolean upsert) {
        doUpdate(collectionName, query, update, upsert, true);
    }

    protected Object doUpdate(final String collectionName, final Query query, final Update update,
            final boolean upsert, final boolean multi) {
        return execute(collectionName, new CollectionCallback<Object>() {
            @Override
            public Object doInCollection(DBCollection collection) {
                DBObject queryDbObject = query.getQueryObject();
                DBObject updateDbObject = update.getUdateObject();
                logger.debug(" 数据库更新操作 collectionName >>>> " + collectionName + " , query >>>  " + queryDbObject
                        + "   update >>> " + updateDbObject + " 是否更新多值 >>> " + multi);
                WriteResult w = collection.update(queryDbObject, update.getUdateObject(), upsert, multi);
                writeResultHandler(w);
                return w;
            }
        });
    }

    private Object doInsert(String collectionName, Map<String, Object> t) {
        Object id = getIdGenerate().id();
        DBObject saveObject = new BasicDBObject(t);
        saveObject.put(ID, id);
        saveDBObject(collectionName, saveObject);
        return id;
    }

    protected Object saveDBObject(final String collectionName, final DBObject dbObject) {
        logger.debug(" 数据库保存操作 collectionName >>>> {} , OBject {}>>> ", collectionName, dbObject);
        return execute(collectionName, new CollectionCallback<Object>() {
            @Override
            public Object doInCollection(DBCollection collection) {
                WriteResult w = collection.save(dbObject);
                writeResultHandler(w);
                return w;
            }
        });
    }

    protected Object saveListDBObject(final String collectionName, final List<DBObject> dbObjects) {
        logger.debug(" 数据库批量保存操作 collectionName >>>> {} , OBject {}>>> ", collectionName, dbObjects);
        return execute(collectionName, new CollectionCallback<Object>() {
            @Override
            public Object doInCollection(DBCollection collection) {
                WriteResult w = collection.insert(dbObjects);
                writeResultHandler(w);
                return w;
            }
        });
    }

    // 原子操作
    /**
     * 只能修改一条记录，查询并删除，如果查询不到，返回null
     * 
     * @param collectionName
     * @param query
     * @param remove
     */
    public Map<String, Object> findAndModify(String collectionName, Query query, boolean remove) {
        return findAndModify(collectionName, query, null, null, remove, false, false);
    }

    /**
     * 
     * 只能修改一条记录
     * 
     * @param collectionName
     * @param query
     * @param update
     */
    public Map<String, Object> findAndModify(String collectionName, Query query, Update update) {
        return findAndModify(collectionName, query, null, update, false, false, false);
    }

    /**
     * 
     * 只能修改一条记录，如果returnNew为true，但是isInsert为false，当数据库没有查询到修改的记录时，返回返回值为null
     * 
     * @param collectionName
     *            集合名称
     * @param query
     *            查询条件，里面可以附带排序
     * @param update
     *            更新器
     * @param isInsert
     *            如果修改记录不存在是否插入
     * @param returnNew
     *            是否返回需改后的值
     */
    public Map<String, Object> findAndModify(String collectionName, Query query, Update update, boolean isInsert,
            boolean returnNew) {
        return findAndModify(collectionName, query, null, update, false, isInsert, returnNew);
    }

    private Map<String, Object> findAndModify(String collectionName, Query query, DBObject field, Update update,
            boolean remove, boolean isInsert, boolean returnNew) {
        return dofindAndModify(collectionName, query, field, update, remove, isInsert, returnNew,
                this.mapDBObjectCallbackImpl);
    }

    /**
     * 只能修改一条记录，查询并删除，如果查询不到，返回null
     * 
     * @param collectionName
     * @param query
     * @param remove
     */
    public <T> T findAndModify(String collectionName, Query query, boolean remove, DBObjectCallback<T> dbObjectCallback) {
        return dofindAndModify(collectionName, query, null, null, remove, false, false, dbObjectCallback);
    }

    /**
     * 
     * 只能修改一条记录
     * 
     * @param collectionName
     * @param query
     * @param update
     */
    public <T> T findAndModify(String collectionName, Query query, Update update, DBObjectCallback<T> dbObjectCallback) {
        return dofindAndModify(collectionName, query, null, update, false, false, false, dbObjectCallback);
    }

    /**
     * 
     * 只能修改一条记录，如果returnNew为true，但是isInsert为false，当数据库没有查询到修改的记录时，返回返回值为null
     * 
     * @param collectionName
     *            集合名称
     * @param query
     *            查询条件，里面可以附带排序
     * @param update
     *            更新器
     * @param isInsert
     *            如果修改记录不存在是否插入
     * @param returnNew
     *            是否返回需改后的值
     */
    public <T> T findAndModify(String collectionName, Query query, Update update, boolean isInsert, boolean returnNew,
            DBObjectCallback<T> dbObjectCallback) {
        return dofindAndModify(collectionName, query, null, update, false, isInsert, returnNew, dbObjectCallback);
    }

    private <T> T dofindAndModify(final String collectionName, final Query query, final DBObject field,
            final Update update, final boolean remove, final boolean isInsert, final boolean returnNew,
            final DBObjectCallback<T> dbObjectCallback) {
        return execute(collectionName, new CollectionCallback<T>() {
            @Override
            public T doInCollection(DBCollection collection) {
                DBObject dbQuery = query.getQueryObject();
                DBObject dbObject = null;
                Object[] param = null;
                if (update != null) {
                    dbObject = collection.findAndModify(dbQuery, field, query.getSortObject(), remove,
                            update.getUdateObject(), returnNew, isInsert);
                    param = new Object[] { collectionName, dbQuery, update.getUdateObject(), remove, isInsert,
                            returnNew };
                } else {
                    dbObject = collection.findAndModify(dbQuery, field, query.getSortObject(), remove, null, returnNew,
                            isInsert);
                    param = new Object[] { collectionName, dbQuery, null, remove, isInsert, returnNew };
                }
                logger.debug(
                        "数据库 dofindAndModify 操作 collectionName >>>> {}，query >>> {},update >>> {} , remove >>{} , isInsert >>> {} ,returnNew >>> {}",
                        param);
                // 没有返回结果
                if (dbObject == null) {
                    return null;
                } else {
                    return dbObjectCallback.processDBObject(dbObject);
                }
            }
        });
    }

    // grouop 语句
    public List<Map<String, Object>> group(String collectionName, Query query, Group groupBy) {
        return group(collectionName, query, groupBy, this.mapDBObjectCallbackImpl);
    }

    public <T> List<T> group(final String collectionName, Query query, Group groupBy, DBObjectCallback<T> callback) {
        authcollectionName(collectionName);

        DBObject dbo = groupBy.getGroupObject();
        dbo.put("ns", collectionName);

        if (query == null) {
            dbo.put("cond", null);
        } else {
            dbo.put("cond", query.getQueryObject());
        }
        checkGroup(dbo);
        DBObject command = new BasicDBObject("group", dbo);
        return runCommand(command, callback);
    }

    // count

    public long count(String collectionName) {
        return count(collectionName, null);
    }

    public long count(String collectionName, final Query query) {
        return execute(collectionName, new CollectionCallback<Long>() {
            @Override
            public Long doInCollection(DBCollection collection) {
                if (query == null) {
                    return collection.count();
                }
                return collection.count(query.getQueryObject());
            }
        });
    }

    /**
     * 检查生成的group 命令
     * 
     * @param command
     */
    private void checkGroup(DBObject command) {
        if (!command.containsField("initial")) {
            logger.error("group 没有初始化参数");
        }
    }

    // ddl 语句开始
    public void drop(String collectionName) {
        execute(collectionName, new CollectionCallback<Object>() {
            @Override
            public Object doInCollection(DBCollection collection) {
                collection.drop();
                return null;
            }
        });
    }

    public void create(final String collectionName) {
        this.authcollectionName(collectionName);
        execute(new DBCallBack<Object>() {
            @Override
            public Object doInDB(DB db) {
                db.createCollection(collectionName, new BasicDBObject());
                return null;
            }
        });
    }

    public <T> List<T> runCommand(final DBObject command, final DBObjectCallback<T> callback) {
        logger.debug("数据库 执行命令 command >>>> {}", command);
        return execute(new DBCallBack<List<T>>() {
            @Override
            public List<T> doInDB(DB db) {
                CommandResult commandResult = db.command(command);
                CommandResultHandler(commandResult);
                @SuppressWarnings("unchecked")
                Iterable<DBObject> resultSet = (Iterable<DBObject>) commandResult.get("retval");
                List<T> list = new ArrayList<T>();
                for (DBObject dbObject : resultSet) {
                    list.add(callback.processDBObject(dbObject));
                }
                return list;
            }
        });
    }

    private <T> List<T> executeMapReduceCommand(DBCollection dbCollection, MapReduceCommand command,
            DBObjectCallback<T> callback) {
        try {
            logger.debug("数据库MapReduce操作 MapReduceCommand >> {}", command);
            MapReduceOutput reduceOutput = dbCollection.mapReduce(command);
            List<T> list = new ArrayList<T>();
            for (DBObject dbObject : reduceOutput.results()) {
                list.add(callback.processDBObject(dbObject));
            }
            return list;
        } catch (Exception e) {
            throw new MongodbDaoException("执行execMapReduceCommand 出错 !," + command + "/n" + e.getMessage());
        }
    }

    /**
     * 
     * @param collectionName
     * @param callback
     * @return
     */
    public <T> T execute(String collectionName, CollectionCallback<T> callback) {
        try {
            DBCollection collection = getCollection(collectionName);
            return callback.doInCollection(collection);
        } catch (Exception e) {
            throw new MongodbDaoException(e);
        }
    }

    public <T> T execute(DBCallBack<T> dbCallBack) {
        DB db = getDB();
        return dbCallBack.doInDB(db);
    }
}
