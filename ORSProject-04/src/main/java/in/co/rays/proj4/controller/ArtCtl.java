package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;

import in.co.rays.proj4.bean.ArtBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ArtModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ArtCtl", urlPatterns = { "/ctl/ArtCtl" })
public class ArtCtl extends BaseCtl {

@Override
protected void preload(HttpServletRequest request, HttpServletResponse responses)
		throws IOException, ServletException {
	ArtModel artModel = new ArtModel();
	try {

		List artList = artModel.list();
		request.setAttribute("artList", artList);
		System.out.println("in list...."+ artList);
	} catch (ApplicationException e) {
		
		e.printStackTrace();
		ServletUtility.handleException(e, request, responses, getView());
		
		 return;
	}
}
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "title"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("title"))) {
			request.setAttribute("title", "Invalid title");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getValue("error.require", "price"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ArtBean bean = new ArtBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		bean.setPrice(DataUtility.getString(request.getParameter("price")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		ArtModel model = new ArtModel();

		if (id > 0) {
			try {
				ArtBean bean = model.findByPk(id);
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

		ArtModel model = new ArtModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			ArtBean bean = (ArtBean) populateBean(request);

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

			ArtBean bean = (ArtBean) populateBean(request);

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
			ServletUtility.redirect(ORSView.ART_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ART_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.ART_VIEW;
	}
}