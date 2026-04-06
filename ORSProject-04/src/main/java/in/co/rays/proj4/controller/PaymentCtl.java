package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PaymentBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;

import in.co.rays.proj4.model.PaymentModel;

import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PaymentCtl", urlPatterns = { "/ctl/PaymentCtl" })
public class PaymentCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		// TransactionId validation
		if (DataValidator.isNull(request.getParameter("transactionId"))) {

			request.setAttribute("transactionId", PropertyReader.getValue("error.require", "transactionId"));
			pass = false;

		} else if (!DataValidator.isInteger(request.getParameter("transactionId"))) {
			request.setAttribute("transactionId", "Invalid transactionId");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("payerName"))) {
			request.setAttribute("payerName", PropertyReader.getValue("error.require", "payerName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("payerName"))) {
			request.setAttribute("payerName", "Invalid payerName");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "amount"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("paymentDate"))) {
			request.setAttribute("paymentDate", PropertyReader.getValue("error.require", "paymentDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("paymentDate"))) {
			request.setAttribute("paymentDate", " invalid paymentDate ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("paymentStatus"))) {
			request.setAttribute("paymentStatus", PropertyReader.getValue("error.require", "paymentStatus"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("paymentStatus"))) {
			request.setAttribute("paymentStatus", PropertyReader.getValue("paymentStatus", " invalid paymentStatus"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		PaymentBean bean = new PaymentBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTransactionId(DataUtility.getString(request.getParameter("transactionId")));
		bean.setPayerName(DataUtility.getString(request.getParameter("payerName")));
		bean.setAmount(DataUtility.getInt(request.getParameter("amount")));
		bean.setPaymentDate(DataUtility.getDate(request.getParameter("paymentDate")));
		bean.setPaymentStatus(DataUtility.getString(request.getParameter("paymentStatus")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		PaymentModel model = new PaymentModel();

		if (id > 0) {
			try {
				PaymentBean bean = model.findByPK(id);
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
		System.out.println("Operation = " + op); 

		PaymentModel model = new PaymentModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			PaymentBean bean = (PaymentBean) populateBean(request);
			System.out.println(" id" + request.getParameter("id"));
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Payment added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Transaction ID already exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			PaymentBean bean = (PaymentBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Payment updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Transaction ID already exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PAYMENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PAYMENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.PAYMENT_VIEW;
	}

}