package info.hexin.mongo.client.util;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import info.hexin.mongo.client.config.DbConfigFile;
import info.hexin.mongo.client.config.DbProperties;
import info.hexin.mongo.client.config.MongoDbFactory;
import info.hexin.mongo.client.exception.MongodbDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取数据库实例
 * 
 * @author hexin
 * 
 */
public class MongoHelper {
    private static final Logger LOG = LoggerFactory.getLogger(MongoHelper.class);

    private static String DEFAULT_CONFI_FILE_PROPERTIES = "database.properties";
    private MongoHelper() {

    }

    /**
     * 将配置文件转化成MongoDbFactory
     * 
     * @param configFile
     * @return
     */
    private static DB loadMongoDbFactory(String configFile) {
        try {
            return loadDbMongoFactory(loadProperties(configFile));
        } catch (Exception e) {
            throw new MongodbDaoException(e.getMessage(), e);
        }
    }

    /**
     * 配置文件加载顺序： 1. 先检查是否配置配置文件 2. 在根据配置项配置 3. 最后使用默认配置文件进行配置
     * 
     * @param dbFactory
     * @return
     */
    public static DB getDb(MongoDbFactory dbFactory) {
        if (dbFactory == null) {
            // 使用默认配置
            LOG.info("根据默认配置文件解析");
            return loadMongoDbFactory(DEFAULT_CONFI_FILE_PROPERTIES);
        }

        // 先检查是否配置配置文件
        if (dbFactory instanceof DbConfigFile) {
            LOG.info("根据配置的配置文件解析");
            return loadMongoDbFactory(((DbConfigFile) dbFactory).getConfig());
        } else if (dbFactory instanceof DbProperties) {
            LOG.info("根据配置的属性解析");
            DbProperties t = (DbProperties) dbFactory;
            if (Strings.isNotBlank(t.getHost()) && Strings.isNotBlank(t.getDbname())) {
                return loadDbMongoFactory(t);
            } else {
                throw new RuntimeException("数据库配置项 缺少 Host 或者 Dbname");
            }
        } else {
            throw new RuntimeException("配置参数不能识别");
        }
    }

    private static DB loadDbMongoFactory(DbProperties dbFactory) {
        return getDb(dbFactory.getHost(), dbFactory.getPort(), dbFactory.getDbname(), dbFactory.getAutoConnectRetry(),
                dbFactory.getConnectionsPerHost(), dbFactory.getMaxWaitTime(), dbFactory.getSocketTimeout(),
                dbFactory.getConnectTimeout(), dbFactory.getThreadsAllowedToBlockForConnectionMultiplier());
    }

    /**
     * 
     * @param host
     * @param port
     * @param dbname
     * @param autoConnectRetry
     * @param connectionsPerHost
     * @param maxWaitTime
     * @param socketTimeout
     * @param connectTimeout
     * @param threadsAllowedToBlockForConnectionMultiplier
     * @return
     */
    private static DB getDb(String host, String port, String dbname, String autoConnectRetry,
            String connectionsPerHost, String maxWaitTime, String socketTimeout, String connectTimeout,
            String threadsAllowedToBlockForConnectionMultiplier) {
        LOG.debug("host>>>{},port >>>> {},dbname >>>>{}",new String[]{host,port,dbname});
        try {
            Mongo mongo = null;
            if (!Strings.isNotBlank(port)) {
                mongo = new Mongo(host, Integer.parseInt(port));
            } else {
                mongo = new Mongo(host);
            }
            MongoOptions options = mongo.getMongoOptions();
            if (Strings.isNotBlank(autoConnectRetry)) {
                options.autoConnectRetry = Boolean.valueOf(autoConnectRetry);
            }

            if (Strings.isNotBlank(connectionsPerHost)) {
                options.connectionsPerHost = Integer.valueOf(connectionsPerHost);
            }
            if (Strings.isNotBlank(maxWaitTime)) {
                options.maxWaitTime = Integer.valueOf(maxWaitTime);
            }
            if (Strings.isNotBlank(socketTimeout)) {
                options.socketTimeout = Integer.valueOf(socketTimeout);
            }
            if (Strings.isNotBlank(connectTimeout)) {
                options.connectTimeout = Integer.valueOf(connectTimeout);
            }
            if (Strings.isNotBlank(threadsAllowedToBlockForConnectionMultiplier)) {
                options.threadsAllowedToBlockForConnectionMultiplier = Integer
                        .valueOf(threadsAllowedToBlockForConnectionMultiplier);
            }
            return mongo.getDB(dbname);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * load mongodb Properties from database.properties
     * 
     * @return
     * @throws IOException
     */
    private static DbProperties loadProperties(String configFile) {
        DbProperties dbFactory = new DbProperties();
        InputStream inputStream = null;
        inputStream = MongoHelper.class.getClassLoader().getResourceAsStream(configFile);
        try {
            // properties
            if (inputStream != null) {
                LOG.debug("从Properties配置文件中读取配置,configFile>>>>>"+configFile);
                Properties p = new Properties();
                p.load(inputStream);
                dbFactory.setHost(p.getProperty("host"));
                dbFactory.setPort(p.getProperty("port"));
                dbFactory.setDbname(p.getProperty("dbname"));
                dbFactory.setAutoConnectRetry(p.getProperty("autoConnectRetry"));
                dbFactory.setConnectionsPerHost(p.getProperty("connectionsPerHost"));
                dbFactory.setMaxWaitTime(p.getProperty("maxWaitTime"));
                dbFactory.setSocketTimeout(p.getProperty("socketTimeout"));
                dbFactory.setConnectTimeout(p.getProperty("connectTimeout"));
                dbFactory.setThreadsAllowedToBlockForConnectionMultiplier(p
                        .getProperty("threadsAllowedToBlockForConnectionMultiplier"));
            }
            // 从xml中获取
            else {
                LOG.debug("从xml配置文件中读取配置");
                dbFactory.setHost(Conf.sysConf().getString("mongo_host"));
                dbFactory.setPort(Conf.sysConf().getString("mongo_port"));
                dbFactory.setDbname(Conf.sysConf().getString("mongo_dbname"));
                dbFactory.setAutoConnectRetry(Conf.sysConf().getString("mongo_autoConnectRetry"));
                dbFactory.setConnectionsPerHost(Conf.sysConf().getString("mongo_connectionsPerHost"));
                dbFactory.setMaxWaitTime(Conf.sysConf().getString("mongo_maxWaitTime"));
                dbFactory.setSocketTimeout(Conf.sysConf().getString("mongo_socketTimeout"));
                dbFactory.setConnectTimeout(Conf.sysConf().getString("mongo_connectTimeout"));
                dbFactory.setThreadsAllowedToBlockForConnectionMultiplier(Conf.sysConf().getString(
                        "mongo_threadsAllowedToBlockForConnectionMultiplier"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dbFactory;
    }
}
