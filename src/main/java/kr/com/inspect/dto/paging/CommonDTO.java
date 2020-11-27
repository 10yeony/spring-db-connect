package kr.com.inspect.dto.paging;

/**
 * 
 * @author Yeonhee Kim
 *
 */
public class CommonDTO {
	/**
	 * 
	 */
	private int limit;
	
	/**
	 * 
	 */
	private int offset;
	
	/**
	 * 
	 */
	private String pagination;

	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getPagination() {
		return pagination;
	}
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
}
