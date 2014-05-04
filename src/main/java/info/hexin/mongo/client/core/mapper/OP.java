package info.hexin.mongo.client.core.mapper;

public class OP {
	public static final String ID = "_id";
	
	// compare and conditon
	public static final String GT = "$gt";
	public static final String GTE = "$gte";
	public static final String LT = "$lt";
	public static final String LTE = "$lte";
	public static final String NE = "$ne";
	public static final String NOT = "$not";
	public static final String AND = "$and";
	public static final String OR = "$or";
	public static final String IN = "$in";
	public static final String NIN = "$nin";
	public static final String MOD = "$mod";
	public static final String ALL = "$all";
	public static final String SIZE = "$size";
	public static final String EXISTS = "$exists";
	public static final String REGEX = "$regex";
	
	// 2d and geo
	public static final String NEAR = "$near";
	public static final String WITHIN = "$within";
	public static final String CENTER = "$center";
	public static final String BOX = "$box";
	
	// modify
	public static final String SET = "$set";
	public static final String UNSET = "$unset";
	public static final String INC = "$inc";
	public static final String PUSH = "$push";
	public static final String PUSHALL ="$pushAll";
	public static final String ADDTOSET ="$addToSet";
	public static final String EACH ="$each";
	public static final String PULL = "$pull";
	public static final String PULLAll = "$pullAll";
	
	// aggregation
	public static final String PROJECT = "$project";
	public static final String MATCH = "$match";
	public static final String LIMIT = "$limit";
	public static final String SKIP = "$skip";
	public static final String UNWIND = "$unwind";
	public static final String GROUP = "$group";
	public static final String SORT = "$sort";

	// order
	public static final int ASC = 1;
	public static final int DESC = -1;
}
