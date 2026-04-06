package in.co.rays.proj4.bean;

public class BudgetBean  extends BaseBean{


	private int amount;
	private int spentAmount;
	private String department;
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getSpentAmount() {
		return spentAmount;
	}
	public void setSpentAmount(int spentAmount) {
		this.spentAmount = spentAmount;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
}