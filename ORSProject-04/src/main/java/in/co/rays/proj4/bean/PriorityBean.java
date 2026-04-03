package in.co.rays.proj4.bean;

public class PriorityBean extends BaseBean{
	
	private String priorityCode ;
	private String priorityLevel ;
	private String colorTag ;
	private String priorityStatus ;

	public String getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}

	public String getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public String getColorTag() {
		return colorTag;
	}

	public void setColorTag(String colorTag) {
		this.colorTag = colorTag;
	}
	
	public String getPriorityStatus() {
		return priorityStatus;
	}

	public void setPriorityStatus(String priorityStatus) {
		this.priorityStatus = priorityStatus;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return priorityCode;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return priorityCode;
	}

}
