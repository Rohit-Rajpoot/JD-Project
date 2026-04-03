package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TimeSlotBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TimeSlotModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TimeSlotCtl", urlPatterns = { "/ctl/TimeSlotCtl" })
public class TimeSlotCtl extends BaseCtl {

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TimeSlotBean bean = new TimeSlotBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSlotCode(DataUtility.getString(request.getParameter("slotCode")));
		bean.setStartTime(DataUtility.getDate(request.getParameter("startTime")));
		bean.setEndTime(DataUtility.getDate(request.getParameter("endTime")));
		bean.setSlotStatus(DataUtility.getString(request.getParameter("slotStatus")));
		bean.setCreatedBy(DataUtility.getString(request.getParameter("createdBy")));
		bean.setModifiedBy(DataUtility.getString(request.getParameter("modifiedBy")));

		return bean;

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("slotCode"))) {
			request.setAttribute("slotCode", PropertyReader.getValue("error.require", "Slot Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("startTime"))) {
			request.setAttribute("startTime", PropertyReader.getValue("error.require", "Start Time"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("endTime"))) {
			request.setAttribute("endTime", PropertyReader.getValue("error.require", "End Time"));
			pass = false;
		}

		if (!DataValidator.isValidDateRange(DataUtility.getDate(request.getParameter("startTime")),
				DataUtility.getDate(request.getParameter("endTime")))) {
			request.setAttribute("endTime", "Entered Date Is Before The Start Date");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("slotStatus"))) {
			request.setAttribute("slotStatus", PropertyReader.getValue("error.require", "Slot Status"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		TimeSlotModel model = new TimeSlotModel();

		if (id > 0) {
			try {
				TimeSlotBean bean = model.findByPk(id);
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

		TimeSlotModel model = new TimeSlotModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			TimeSlotBean bean = (TimeSlotBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Slot Code Is Successfully Saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Slot Code Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			TimeSlotBean bean = (TimeSlotBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
					;
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Slot Code Is Successfully Updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Slot Code Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMESLOT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMESLOT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {

		return ORSView.TIMESLOT_VIEW;
	}

}
