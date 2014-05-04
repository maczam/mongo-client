package info.hexin.mongo.client.callback;

import com.mongodb.gridfs.GridFSDBFile;

public interface GridFSDBFileCallback<T> {
	
	T processGridFSDBFile(GridFSDBFile gridFSDBFile);
}
