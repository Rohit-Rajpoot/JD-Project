package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PatientBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.PatientModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * @author Rohit Rajpoot
 *
 */
@WebServlet(name = "PatientCtl", urlPatterns = { "/ctl/PatientCtl" })
public class PatientCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(PatientCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("PatientCtl validate started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("doctorname"))) {
			request.setAttribute("doctorname", PropertyReader.getValue("error.require", "Doctor Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("doctorname"))) {
			request.setAttribute("doctorname", "Invalid Doctor Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("patientname"))) {
			request.setAttribute("patientname", PropertyReader.getValue("error.require", "Patient Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("patientname"))) {
			request.setAttribute("patientname", "Invalid Patient Name");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("patientdateofbirth"))) {
			request.setAttribute("patientdateofbirth", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("patientdateofbirth"))) {
			request.setAttribute("patientdateofbirth", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("patientloginid"))) {
			request.setAttribute("patientloginid", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("patientloginid"))) {
			request.setAttribute("patientloginid", PropertyReader.getValue("error.email", "patientloginid "));
			pass = false;
		}

		log.debug("PatientCtl validate ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("PatientCtl populateBean started");

		PatientBean bean = new PatientBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setDoctorname(DataUtility.getString(request.getParameter("doctorname")));
		bean.setPatientname(DataUtility.getString(request.getParameter("patientname")));
		bean.setPatientdateofbirth(DataUtility.getDate(request.getParameter("patientdateofbirth")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setPatientloginid(DataUtility.getString(request.getParameter("patientloginid")));

		populateDTO(bean, request);

		log.debug("PatientCtl populateBean ended");
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("PatientCtl doGet started");

		long id = DataUtility.getLong(request.getParameter("id"));
		PatientModel model = new PatientModel();

		if (id > 0) {
			try {
				PatientBean bean = model.findbypk(id);
				System.out.println(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("PatientCtl doGet ended");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("PatientCtl doPost started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		PatientModel model = new PatientModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			PatientBean bean = (PatientBean) populateBean(request);
			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Patient added successfully", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			PatientBean bean = (PatientBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Patient updated successfully", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PATIENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("PatientCtl doPost ended");
	}

	@Override
	protected String getView() {
		return ORSView.PATIENT_VIEW;
	}
}
