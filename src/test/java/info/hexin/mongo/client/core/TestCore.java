package info.hexin.mongo.client.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import info.hexin.mongo.client.core.dao.TestDao;

@RunWith(Suite.class)
@SuiteClasses({ TestDao.class })
public class TestCore {

}
