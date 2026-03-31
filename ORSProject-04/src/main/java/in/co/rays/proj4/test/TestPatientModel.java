package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import in.co.rays.proj4.bean.PatientBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.PatientModel;

public class TestPatientModel {

	public static PatientModel model = new PatientModel();

	public static void main(String[] args)
			throws ParseException, ApplicationException, CommunicationsException, DuplicateRecordException {

		// testAdd();
		// testUpdate();
		testDelete();
		// testFindByLoginid();
		// testSearch();
	}

	public static void testAdd()
			throws ParseException, ApplicationException, CommunicationsException, DuplicateRecordException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		PatientBean bean = new PatientBean();
		bean.setDoctorname("Dr.Deepak ");
		bean.setPatientname("Lucky");
		bean.setPatientdateofbirth(sdf.parse("2000-12-15"));
		bean.setGender("male");
		bean.setPatientloginid("lucky@gmail.com");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
	}

	public static void testUpdate()
			throws ParseException, ApplicationException, CommunicationsException, DuplicateRecordException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		PatientBean bean = new PatientBean();
		bean.setId(1);
		bean.setDoctorname("Dr.SN Gupta");
		bean.setPatientname("Rohit Rajpoot");
		bean.setPatientdateofbirth(sdf.parse("2003-12-15"));
		bean.setGender("male");
		bean.setPatientloginid("neeraj@gmail.com");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.update(bean);
	}

	public static void testDelete() throws ApplicationException, CommunicationsException {
		PatientBean bean = new PatientBean();
		bean.setId(3);
		model.delete(bean);
	}

	public static void testFindByLoginid() throws ApplicationException, CommunicationsException {
		PatientBean bean = new PatientBean();
		bean = model.findbylogin("neeraj@gmail.com");

		if (bean != null) {
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getDoctorname());
			System.out.print("\t" + bean.getPatientname());
			System.out.print("\t" + bean.getPatientdateofbirth());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getPatientloginid());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("User not Found");
		}
	}

	public static void testSearch() throws ApplicationException {
		PatientBean bean = new PatientBean();
		// bean.setId(1);

		List<PatientBean> list;
		list = model.search(bean, 0, 0);

		Iterator<PatientBean> it = list.iterator();

		while (it.hasNext()) {
			bean = it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getDoctorname());
			System.out.print("\t" + bean.getPatientname());
			System.out.print("\t" + bean.getPatientdateofbirth());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getPatientloginid());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		}

	}

}
