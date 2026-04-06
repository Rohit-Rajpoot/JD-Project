package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * ArtBean stores Art. this is a artbean
 * 
 * @author Rohit Rajpoot
 *
 */
public class ArtBean extends BaseBean {
	private String title;
	private String name;
	private Date date;
	private String price;

	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}