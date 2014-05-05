package info.hexin.example.chapter03.authdb;

import info.hexin.mongo.client.auth.AuthDB;
import info.hexin.mongo.client.core.dao.MongoDao;

public class MongdbAuthdb {
    public static void main(String[] args) {
        MongoDao dao = new MongoDao();
        dao.setAuthDB(new xx());
        dao.findAll("d");
    }
}

class xx implements AuthDB {

    @Override
    public boolean authCollection(String collectionName) {
        if ("a".equals(collectionName)) {
            return true;
        }
        return false;
    }
}
