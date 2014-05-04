package info.hexin.mongo.client.core.mapper.id;

import java.util.UUID;

/**
 * UUID 生成器
 * 
 * @author hexin
 * 
 */
public class UUIDGenerate implements IdGenerate<String> {

    @Override
    public String id() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
