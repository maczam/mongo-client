package info.hexin.mongo.client.core.mapper.id;

import java.io.Serializable;

/**
 * 生成ID
 * 
 * @author hexin
 * 
 */
public interface IdGenerate<T extends Serializable> {
    T id();
}
