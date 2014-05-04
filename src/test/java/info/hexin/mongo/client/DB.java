package info.hexin.mongo.client;

/**
 * 数据库中的常量定义
 * 
 * @author hexin
 * 
 */
public class DB {

	/**
	 * 默认超级系统管理员
	 */
	public static String ADMIN_ROLE_NAME = "超级系统管理员";
	public static String ADMIN_NAME = "admin";
	public static String ADMIN_PASSWORD = "1";

	/**
	 * 系统默认提供服务名称
	 */
	public static String SERVER_BALANCE_NAME = "_balance_";

	/**
	 * 每个表中的id描述
	 */
	public static final String ID = "_id";

	/**
	 * app 表
	 */
	public static final class App {
		public static final String TABLE_NAME = "j_app";
		public static final String CREATE_TIME = "create_time";
		public static final String USER_ID = "user_id";
		public static final String NAME = "name";
		public static final String TYPE = "app_type";
		public static final String BALANCE_HOST = "balance_host_url";
		public static final String FILE = "file";
		public static final String URL = "url";
		public static final String SERVICE_NAME = "service_name";
		public static final String STATUS = "status";
		public static final String VERSION = "version";
		public static final String RESULT_PATH = "result_path";
		public static final String HDFS_RESULT_PATH = "hdfs_result_path";
	}

	public static interface AppStatus {
		public static final int STARTED_SUCCESS = 2, STARTED_FAIL = -2, STOPED = 3, START_END = 4, DEPLOY_SUCCESS = 1,
				DEPLOY_FAIL = -1, NO_DEPLOY = -3, DELETE = -5;
	}

	public static interface AppType {
		int JAR = 0, WEB = 1, MR = 2;
	}

	/**
	 * j_jvm表
	 */
	public static final class Jvm {
		public static final String TABLE_NAME = "j_jvm";
		public static final String IP = "host_ip";
		public static final String NAME = "name";
		public static final String USER_ID = "user_id";
		public static final String HOST_ID = "host_id";
		public static final String RESOURCETYPE = "resourcetype";
		public static final String HOST_NAME = "host_name";
		public static final String APP_NAME = "app_name";
		public static final String STATUS = "status";
	}

	public static interface JvmStatus {
		int STOPED = -1, STARTING = 1;
	}

	/**
	 * j_menu表
	 */
	public static final class Menu {
		public static final String TABLE_NAME = "j_menu";
		public static final String PATH = "path";
		public static final String ORDER = "order_num";
	}

	/**
	 * j_role
	 */
	public static final class Role {
		public static final String TABLE_NAME = "j_role";
		public static final String NAME = "name";
		public static final String RESOURCES = "resources";
	}

	/**
	 * bar
	 */
	public static final String BAR = "j_bar";

	/**
	 * j_user
	 */
	public static final class User {
		public static final String TABLE_NAME = "j_user";
		public static final String ROLES = "roles";
		public static final String NAME = "name";
		public static final String CREATE_TIME = "create_time";
		public static final String PASSWORD = "password";
	}

	/**
	 * j_app_jvm
	 */
	public static final class AppJvm {
		public static final String TABLE_NAME = "j_app_jvm";
		public static final String APP_ID = "app_id";
		public static final String APP_NAME = "app_name";
		public static final String APP_FILE = "app_file";
		public static final String HOST_NAME = "host_name";
		public static final String HOST_IP = "host_ip";
		public static final String JVM_ID = "jvm_id";
		public static final String STATUS = "status";
		public static final String USER_ID = "user_id";
		public static final String REAL_URL = "real_url";
		public static final String BALANCE_URL = "url";
		public static final String DEPLOY_TIME = "deploy_time";
	}

	/**
	 * j_host
	 */
	public static final class Host {
		public static final String TABLE_NAME = "j_host";
		public static final String NAME = "name";
		public static final String CLUSTER_ID = "clusterId";
		public static final String CLUSTER_NAME = "cluster_name";
		public static final String IP = "ip";
		public static final String STATUS = "status";
	}

	public static interface HostStatus {
		int SHUTDOWN = -2, STARTING = 0, ERROR = -1;
	}

	/**
	 * j_jvm_log
	 */

	public static class JvmLog {
		public static final String TABLE_NAME = "j_jvm_log";
		public static final String CAPACITY = "capacity";
		public static final String ENDTIME = "endTime";
		public static final String JDKVERSION = "jdkVersion";
		public static final String JVMID = "jvmId";
		public static final String JVMTYPE = "jvmType";
		public static final String KILLFLAG = "killFlag";
		public static final String MEMORYREMAINING = "memoryRemaining";
		public static final String MEMORYUSED = "memoryUsed";
		public static final String PID = "pid";
		public static final String PORT = "port";
		public static final String STARTTIMET = "startTime";
		public static final String TASKNAME = "taskName";
		public static final String TASKSTARTTIME = "taskStartTime";
		public static final String THRIFTPORT = "thriftPort";
		public static final String TOTALTIME = "totalTime";
		public static final String INSERT_DATE = "insert_date";
		public static final String USED = "used";
		public static final String UUID = "jvm_id";
		public static final String WORKDIRECTORY = "workDirectory";
	}

	/**
	 * j_cluster
	 */
	public static final String CLUSTER = "j_cluster";
	public static final String CLUSTER_NAME = "name";

	/**
	 * j_vhost 给用户显示用的东西
	 */
	public static class Vhost {
		public static final String TABLE_NAME = "j_vhost";
		public static final String NAME = "name";
		public static final String USER_ID = "user_id";
		// 演示 vhost和jvm的关系 是一对一的关系，但是可以有多个appid
		public static final String JVM_IDS = "jvm_ids";
		public static final String STATUS = "status";
		public static final String APPIDS = "app_ids";
	}

	/**
	 * app he vhost 关系
	 */
	public static class AppVhost {
		public static final String TABLE_NAME = "j_app_vhost";
		public static final String APP_ID = "app_id";
		public static final String APP_NAME = "app_name";
		public static final String HOST_NAME = "host_name";
		public static final String HOST_IP = "host_ip";
		public static final String VHOST_ID = "vhost_id";
		public static final String VHOST_NAME = "vhsot_name";
		public static final String STATUS = "status";
		public static final String USER_ID = "user_id";
		public static final String URL = "real_url";
		public static final String BALANCE_URL = "url";
		public static final String DEPLOY_TIME = "deploy_time";
	}

	/**
	 * seq生成
	 */
	public static final String SEQ = "j_seq";
	public static final int SEQ_ID = 1;
	public static final String SEQ_VHOST = "vhost_seq";

	/**
	 * j_app_balance
	 */
	public static class AppBalance {
		public static final String TABLE_NAME = "j_app_balance";
		public static final String USER = "user_name";
		public static final String DATE = "date";
		public static final String URL = "url";
		public static final String APP_NAME = "app_name";
	}
}
