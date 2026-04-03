package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PriorityBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.PriorityModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PriorityCtl", urlPatterns = { "/ctl/PriorityCtl" })
public class PriorityCtl extends BaseCtl {
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		PriorityBean bean = new PriorityBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPriorityCode(DataUtility.getString(request.getParameter("priorityCode")));
		bean.setPriorityLevel(DataUtility.getString(request.getParameter("priorityLevel")));
		bean.setColorTag(DataUtility.getString(request.getParameter("colorTag")));
		bean.setPriorityStatus(DataUtility.getString(request.getParameter("priorityStatus")));
		bean.setCreatedBy(DataUtility.getString(request.getParameter("createdBy")));
		bean.setModifiedBy(DataUtility.getString(request.getParameter("modifiedBy")));
		
		return bean;
				
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("priorityCode"))) {
			request.setAttribute("priorityCode", PropertyReader.getValue("error.require", "Priority Code"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("priorityLevel"))) {
			request.setAttribute("priorityLevel", PropertyReader.getValue("error.require", "Priority Level"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("colorTag"))) {
			request.setAttribute("colorTag", PropertyReader.getValue("error.require", "Color Tag"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("priorityStatus"))) {
			request.setAttribute("priorityStatus", PropertyReader.getValue("error.require", "Priority Status"));
			pass = false;
		}

		return pass;

	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long id = DataUtility.getLong(request.getParameter("id"));

		PriorityModel model = new PriorityModel();

		if (id > 0) {
			try {
				PriorityBean bean = model.findByPk(id);
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

		PriorityModel model = new PriorityModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			PriorityBean bean = (PriorityBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Device Is Successfully Saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Device Name Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			PriorityBean bean = (PriorityBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Device is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Device Name Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PRIORITY_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PRIORITY_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {

		return ORSView.PRIORITY_VIEW;
	}

}
