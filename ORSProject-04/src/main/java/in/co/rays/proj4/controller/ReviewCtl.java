package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ContactBean;
import in.co.rays.proj4.bean.ReviewBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ContactModel;
import in.co.rays.proj4.model.ReviewModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ReviewCtl", urlPatterns = { "/ctl/ReviewCtl" })
public class ReviewCtl extends BaseCtl{
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		ReviewBean bean = new ReviewBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setReviewerName(DataUtility.getString(request.getParameter("reviewerName")));
		bean.setRating(DataUtility.getInt(request.getParameter("rating")));
		bean.setComment(DataUtility.getString(request.getParameter("comment")));
		bean.setCreatedBy(DataUtility.getString(request.getParameter("createdBy")));
		bean.setModifiedBy(DataUtility.getString(request.getParameter("modifiedBy")));
		
		return bean;
				
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("reviewerName"))) {
			request.setAttribute("reviewerName", PropertyReader.getValue("error.require", "Reviewer Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("rating"))) {
			request.setAttribute("rating", PropertyReader.getValue("error.require", "Rating"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("comment"))) {
			request.setAttribute("comment", PropertyReader.getValue("error.require", "Comment"));
			pass = false;
		}

		return pass;

	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long id = DataUtility.getLong(request.getParameter("id"));

		ReviewModel model = new ReviewModel();

		if (id > 0) {
			try {
				ReviewBean bean = model.findByPk(id);
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

		ReviewModel model = new ReviewModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			ReviewBean bean = (ReviewBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Review Is Successfully Saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Review Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			ReviewBean bean = (ReviewBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);;
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Reviewer Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Reviewer Name Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.REVIEW_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.REVIEW_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {

		return ORSView.REVIEW_VIEW;
	}

}
