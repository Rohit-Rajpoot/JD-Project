package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PaymentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.PaymentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PaymentListCtl", urlPatterns = { "/ctl/PaymentListCtl" })

public class PaymentListCtl extends BaseCtl {
	
@Override
	protected void preload(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		 PaymentModel model = new PaymentModel();

		    try {

		        List<PaymentBean> paymentList = model.list();
		        request.setAttribute("paymentList", paymentList);

		    } catch (ApplicationException e) {
		        e.printStackTrace();
		        ServletUtility.handleException(e, request, response, getView());
		    }
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

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        PaymentBean bean = (PaymentBean) populateBean(request);
     PaymentModel model = new PaymentModel();

        try {

            List<PaymentBean> list = model.search(bean, pageNo, pageSize);
            List<PaymentBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);

            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {

            e.printStackTrace();
            ServletUtility.handleException(e, request, response, getView());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<PaymentBean> list = null;
        List<PaymentBean> next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        PaymentBean bean = (PaymentBean) populateBean(request);
        PaymentModel model = new PaymentModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op)) {

                pageNo = 1;

            } else if (OP_NEXT.equalsIgnoreCase(op)) {

                pageNo++;

            } else if (OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.PAYMENT_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                	PaymentBean deleteBean = new PaymentBean();

                    for (String id : ids) {

                        deleteBean.setId(DataUtility.getInt(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage("Record deleted successfully", request);

                } else {

                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.PAYMENT_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {

                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);

            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {

            e.printStackTrace();
            ServletUtility.handleException(e, request, response, getView());
        }

    }

    @Override
    protected String getView() {

        return ORSView.PAYMENT_LIST_VIEW;
    }

}