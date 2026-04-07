package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.InvestorBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.InvestorModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "InvestorCtl", urlPatterns = { "/ctl/InvestorCtl" })
public class InvestorCtl extends BaseCtl {
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		InvestorBean bean = new InvestorBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setInvestorName(DataUtility.getString(request.getParameter("investorName")));
		bean.setInvestmentAmount(DataUtility.getInt(request.getParameter("investmentAmount")));
		bean.setInvestmentType(DataUtility.getString(request.getParameter("investmentType")));

		populateDTO(bean, request);

		return bean;

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("investorName"))) {
			request.setAttribute("investorName", PropertyReader.getValue("error.require", "Investor Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("investmentAmount"))) {
			request.setAttribute("investmentAmount", PropertyReader.getValue("error.require", "Investment Amount"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("investmentType"))) {
			request.setAttribute("investmentType", PropertyReader.getValue("error.require", "Investment Type"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		InvestorModel model = new InvestorModel();

		if (id > 0) {
			try {
				InvestorBean bean = model.findByPk(id);
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

		InvestorModel model = new InvestorModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			InvestorBean bean = (InvestorBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Investor Is Successfully Saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Investor  Name Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			InvestorBean bean = (InvestorBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Investor is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Investor Name Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INVESTOR_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INVESTOR_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.INVESTOR_VIEW;
	}

}
