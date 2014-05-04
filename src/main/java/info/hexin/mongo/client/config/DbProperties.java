package info.hexin.mongo.client.config;

/**
 * 数据库配置属性
 * 
 * @author hexin
 * 
 */
public class DbProperties extends MongoDbFactory {
    // 配置参数
    private String host;
    private String dbname;
    private String port;
    private String connectionsPerHost;
    private String threadsAllowedToBlockForConnectionMultiplier;
    private String connectTimeout;
    private String maxWaitTime;
    private String autoConnectRetry;
    private String socketKeepAlive;
    private String socketTimeout;
    private String slaveOk;
    private String writeNumber;
    private String writeTimeout;
    private String writeFsync;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(String connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public String getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(String threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public String getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(String maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public String getAutoConnectRetry() {
        return autoConnectRetry;
    }

    public void setAutoConnectRetry(String autoConnectRetry) {
        this.autoConnectRetry = autoConnectRetry;
    }

    public String getSocketKeepAlive() {
        return socketKeepAlive;
    }

    public void setSocketKeepAlive(String socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    public String getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(String socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String getSlaveOk() {
        return slaveOk;
    }

    public void setSlaveOk(String slaveOk) {
        this.slaveOk = slaveOk;
    }

    public String getWriteNumber() {
        return writeNumber;
    }

    public void setWriteNumber(String writeNumber) {
        this.writeNumber = writeNumber;
    }

    public String getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(String writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public String getWriteFsync() {
        return writeFsync;
    }

    public void setWriteFsync(String writeFsync) {
        this.writeFsync = writeFsync;
    }
}
