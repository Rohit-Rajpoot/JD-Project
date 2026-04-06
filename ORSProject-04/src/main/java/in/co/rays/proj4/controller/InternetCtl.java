package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.ArtBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.InternetBean;
import in.co.rays.proj4.controller.InternetCtl;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ArtModel;
import in.co.rays.proj4.model.InternetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "InternetCtl", urlPatterns = { "/ctl/InternetCtl" })
public class InternetCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getValue("error.require", "price"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("range"))) {
			request.setAttribute("range", PropertyReader.getValue("error.require", "range"));
			pass = false;
		}
		
		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		InternetBean bean = new InternetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setPrice(DataUtility.getInt(request.getParameter("price")));
		bean.setRange(DataUtility.getInt(request.getParameter("range")));
		

		populateDTO(bean, request);

		return bean;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
		long id = DataUtility.getLong(request.getParameter("id"));

		InternetModel model = new InternetModel();

		if (id > 0) {
			try {
				InternetBean bean = model.findByPk(id);
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

		InternetModel model = new InternetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			InternetBean bean = (InternetBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Role already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				 ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			InternetBean bean = (InternetBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Name already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				 ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INTERNET_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INTERNET_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}


	@Override
	protected String getView() {
		return ORSView.INTERNET_VIEW;
	}
}