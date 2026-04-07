package in.co.rays.proj4.bean;

public class InvestorBean extends BaseBean{
	private String investorName;
	private int investmentAmount;
	private String investmentType;
	
	public String getInvestorName() {
		return investorName;
	}
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
    public int getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(int investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	public String getInvestmentType() {
		return investmentType;
	}
	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return investorName ;
	}
	
	

}
