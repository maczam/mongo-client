package info.hexin.mongo.client.callback;

import com.mongodb.gridfs.GridFS;

public interface GridFSCallback<T> {
	public T doInGridFS(GridFS gridFS);
}
