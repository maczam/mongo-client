package info.hexin.mongo.client.config;

/**
 * 配置文件
 * 
 * @author hexin
 * 
 */
public class DbConfigFile extends MongoDbFactory {

    // 配置文件
    private String config;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
