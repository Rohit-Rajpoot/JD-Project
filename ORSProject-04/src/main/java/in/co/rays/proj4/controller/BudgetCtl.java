package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.BudgetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BudgetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "BudgetCtl", urlPatterns = { "/ctl/BudgetCtl" })
public class BudgetCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "amount"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("amount"))) {
			request.setAttribute("amount", "Invalid amount");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("spentAmount"))) {
			request.setAttribute("spentAmount", PropertyReader.getValue("error.require", "spentAmount"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("department"))) {
			request.setAttribute("department", PropertyReader.getValue("error.require", "department"));
			pass = false;
		}
		
		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		BudgetBean bean = new BudgetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setAmount(DataUtility.getInt(request.getParameter("amount")));
		bean.setSpentAmount(DataUtility.getInt(request.getParameter("spentAmount")));
		
		bean.setDepartment(DataUtility.getString(request.getParameter("department")));

		populateDTO(bean, request);

		return bean;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
		long id = DataUtility.getLong(request.getParameter("id"));

		BudgetModel model = new BudgetModel();

		if (id > 0) {
			try {
				BudgetBean bean = model.findByPk(id);
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

		BudgetModel model = new BudgetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			BudgetBean bean = (BudgetBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(" already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if (OP_UPDATE.equalsIgnoreCase(op)) {

			BudgetBean bean = (BudgetBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(" already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.BUDGET_LIST_CTL, request, response);
			return;
		}
		
		 else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.BUDGET_CTL, request, response);
				return;
			}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.BUDGET_VIEW;
	}
}