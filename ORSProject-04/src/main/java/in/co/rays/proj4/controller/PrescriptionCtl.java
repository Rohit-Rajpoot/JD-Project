package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.ArtBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PrescriptionBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ArtModel;
import in.co.rays.proj4.model.PrescriptionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PrescriptionCtl", urlPatterns = { "/ctl/PrescriptionCtl" })
public class PrescriptionCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("code"))) {
			request.setAttribute("code", PropertyReader.getValue("error.require", "code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("medicines"))) {
			request.setAttribute("medicines", PropertyReader.getValue("error.require", "medicines"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		PrescriptionBean bean = new PrescriptionBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCode(DataUtility.getString(request.getParameter("code")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setMedicines(DataUtility.getString(request.getParameter("medicines")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		PrescriptionModel model = new PrescriptionModel();

		if (id > 0) {
			try {
				PrescriptionBean bean = model.findByPk(id);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		PrescriptionModel model = new PrescriptionModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			PrescriptionBean bean = (PrescriptionBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Name already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				 ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			PrescriptionBean bean = (PrescriptionBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Role already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				 ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PRESCRIPTION_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PRESCRIPTION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}


	@Override
	protected String getView() {
		return ORSView.PRESCRIPTION_VIEW;
	}
}