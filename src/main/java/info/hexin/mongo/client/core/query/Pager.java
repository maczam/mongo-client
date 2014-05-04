package info.hexin.mongo.client.core.query;

/**
 * 翻页信息保存
 * 
 * @author hexin
 * 
 */
public class Pager {

    public final static int NO_SET_VALUE = Integer.MIN_VALUE;

    // pager
    private int skip = NO_SET_VALUE;
    private int limit = NO_SET_VALUE;

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
