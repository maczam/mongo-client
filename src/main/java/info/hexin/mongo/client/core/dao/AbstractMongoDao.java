package info.hexin.mongo.client.core.dao;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;
import info.hexin.mongo.client.auth.AuthDB;
import info.hexin.mongo.client.callback.MapDBObjectCallbackImpl;
import info.hexin.mongo.client.config.MongoDbFactory;
import info.hexin.mongo.client.core.mapper.OP;
import info.hexin.mongo.client.core.mapper.id.IdGenerate;
import info.hexin.mongo.client.core.mapper.id.UUIDGenerate;
import info.hexin.mongo.client.exception.DBAuthException;
import info.hexin.mongo.client.exception.MongodbDaoException;
import info.hexin.mongo.client.util.Maps;
import info.hexin.mongo.client.util.MongoHelper;
import info.hexin.mongo.client.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * @author hexin
 */
public abstract class AbstractMongoDao {
    private Logger logger = LoggerFactory.getLogger(AbstractMongoDao.class);

    // 默认使用UUID生成策略，并提供set方法，支持替换
    private IdGenerate<? extends Serializable> idGenerate = new UUIDGenerate();
    protected MapDBObjectCallbackImpl mapDBObjectCallbackImpl = new MapDBObjectCallbackImpl();
    private AuthDB authDB;
    private MongoDbFactory dbFactory;
    private DB db;

    public void setAuthDB(AuthDB authDB) {
        this.authDB = authDB;
    }

    public void setDbFactory(MongoDbFactory dbFactory) {
        this.dbFactory = dbFactory;
    }

    /**
     * 判断是否存在"_id"
     *
     * @param map
     * @return
     */
    protected boolean saved(Map<String, Object> map) {
        return Strings.isNotBlank(Maps.get(map, OP.ID));
    }

    public IdGenerate<? extends Serializable> getIdGenerate() {
        return idGenerate;
    }

    public void setIdGenerate(IdGenerate<? extends Serializable> idGenerate) {
        this.idGenerate = idGenerate;
    }

    /**
     * 分析操作数据库结果
     *
     * @param w
     */
    protected void writeResultHandler(WriteResult w) {
        String error = w.getError();
        if (error != null) {
            logger.debug(" 错误原因 >>> {}", error);
            throw new MongodbDaoException(error);
        } else {
            logger.debug(" 收影响行数>>> {}", w.getN());
        }
    }

    /**
     * 分析执行命令结果
     *
     * @param commandResult
     */
    protected void CommandResultHandler(CommandResult commandResult) {
        if (commandResult.ok()) {
            logger.debug("执行命令成功");
        } else {
            String error = commandResult.getErrorMessage();
            logger.debug("执行命令失败 >>>> {}", error);
            throw new MongodbDaoException(error);
        }
    }

    protected DBCollection getCollection(String collectionName) {
        if (authDB.authCollection(collectionName)) {
            String tanentCollectionName = beforGetCollection(collectionName);
            return getDB().getCollection(tanentCollectionName);
        }
        throw new DBAuthException("没有操作collectionName >>> " + collectionName + " 的权限！");
    }

    protected GridFS getGridFS(String collectionName) {
        if (authDB.authCollection(collectionName)) {
            String tanentCollectionName = beforGetCollection(collectionName);
            return new GridFS(getDB(), tanentCollectionName);
        }
        throw new DBAuthException("没有操作collectionName >>> " + collectionName + " 的权限！");
    }

    protected DB getDB() {
        if (db == null) {
            db = MongoHelper.getDb(dbFactory);
        }
        return db;
    }

    /**
     * 验证集合是否有操作的权限
     *
     * @param collectionName
     * @return
     */
    protected boolean authcollectionName(String collectionName) {
        if (authDB.authCollection(collectionName)) {
            return true;
        }
        throw new DBAuthException("没有操作collectionName >>> " + collectionName + " 的权限！");
    }

    /**
     * 表权限验证
     *
     * @param collectionName
     * @return
     */
    protected String beforGetCollection(String collectionName) {
        return collectionName;
    }
}
