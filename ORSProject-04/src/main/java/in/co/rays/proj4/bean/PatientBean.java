package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * @author Rohit Rajpoot
 *
 */
public class PatientBean extends BaseBean {

	private String doctorname;
	private String patientname;
	private Date patientdateofbirth;
	private String gender;
	private String patientloginid;

	public String getDoctorname() {
		return doctorname;
	}

	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}

	public String getPatientname() {
		return patientname;
	}

	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	public Date getPatientdateofbirth() {
		return patientdateofbirth;
	}

	public void setPatientdateofbirth(Date patientdateofbirth) {
		this.patientdateofbirth = patientdateofbirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPatientloginid() {
		return patientloginid;
	}

	public void setPatientloginid(String patientloginid) {
		this.patientloginid = patientloginid;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public String getValue() {
		return null;
	}

}
