package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CertificateBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CertificateModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "CertificateCtl", urlPatterns = { "/ctl/CertificateCtl" })
public class CertificateCtl extends BaseCtl{
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		CertificateBean bean = new CertificateBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCertificateCode(DataUtility.getString(request.getParameter("certificateCode")));
		bean.setStudentName(DataUtility.getString(request.getParameter("studentName")));
		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		bean.setIssueDate(DataUtility.getDate(request.getParameter("issueDate")));
		bean.setCreatedBy(DataUtility.getString(request.getParameter("createdBy")));
		bean.setModifiedBy(DataUtility.getString(request.getParameter("modifiedBy")));
		
		return bean;
				
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("certificateCode"))) {
			request.setAttribute("certificateCode", PropertyReader.getValue("error.require", "Certificate Code"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("studentName"))) {
			request.setAttribute("studentName", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("courseName"))) {
			request.setAttribute("courseName", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("issueDate"))) {
			request.setAttribute("issueDate", PropertyReader.getValue("error.require", "Issue Date"));
			pass = false;
		}

		return pass;

	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long id = DataUtility.getLong(request.getParameter("id"));

		CertificateModel model = new CertificateModel();

		if (id > 0) {
			try {
				CertificateBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));

		CertificateModel model = new CertificateModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			CertificateBean bean = (CertificateBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Certificate Is Successfully Saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Certificate Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			CertificateBean bean = (CertificateBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);;
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Certificate Is Successfully Updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Certificate Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.CERTIFICATE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CERTIFICATE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {

		return ORSView.CERTIFICATE_VIEW;
	}

}
