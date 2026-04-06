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
import in.co.rays.proj4.model.ArtModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ArtListCtl", urlPatterns = { "/ctl/ArtListCtl" })
public class ArtListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ArtModel artModel = new ArtModel();
		try {

			List artList = artModel.list();
			 System.out.println("asdfghjkl;"+artList);
			request.setAttribute("artList", artList);
		} catch (ApplicationException e) {
			e.printStackTrace();
			 ServletUtility.handleException(e, request, response, getView());
		}
	}

		
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));


		ArtBean bean = (
				ArtBean) populateBean(request);
		ArtModel model = new ArtModel();

		try {
			List<ArtBean> list = model.search(bean, pageNo, pageSize);
			List<ArtBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
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
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		ArtBean bean = (ArtBean) populateBean(request);
		ArtModel model = new ArtModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ART_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					ArtBean deletebean = new ArtBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						try {
							model.delete(deletebean);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ServletUtility.setSuccessMessage("is deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ART_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ART_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
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
		     return;
		}
	}

	@Override
	protected String getView() {
		return ORSView.ART_LIST_VIEW;
	}
}