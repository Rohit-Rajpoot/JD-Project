package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.PrescriptionBean;
import in.co.rays.proj4.model.PrescriptionModel;
import in.co.rays.proj4.bean.BaseBean;

import in.co.rays.proj4.exception.ApplicationException;


import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;


/**
 * @author Shruti Rathore
 * 
 *
 */
@WebServlet(name = "PrescriptionListCtl", urlPatterns = { "/ctl/PrescriptionListCtl" })

public class PrescriptionListCtl extends BaseCtl {

	@Override
		protected void preload(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
		PrescriptionModel model = new PrescriptionModel();

	    try {

	        List<PrescriptionBean> advertiseList = model.list();
	        request.setAttribute("advertiseList", advertiseList);

	    } catch (ApplicationException e) {
	        e.printStackTrace();
			   ServletUtility.handleException(e, request, response, getView());
	    }
		}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		PrescriptionBean bean = new PrescriptionBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCode(DataUtility.getString(request.getParameter("code")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setMedicines(DataUtility.getString(request.getParameter("medicines")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}
   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        PrescriptionBean bean = (PrescriptionBean) populateBean(request);
      PrescriptionModel model = new PrescriptionModel();

        try {

            List<PrescriptionBean> list = model.search(bean, pageNo, pageSize);
            List<PrescriptionBean> next = model.search(bean, pageNo + 1, pageSize);

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

        List<PrescriptionBean> list = null;
        List<PrescriptionBean> next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        PrescriptionBean bean = (PrescriptionBean) populateBean(request);
        PrescriptionModel model = new PrescriptionModel();

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

            	ServletUtility.redirect(ORSView.PRESCRIPTION_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                	PrescriptionBean deleteBean = new PrescriptionBean();

                    for (String id : ids) {

                        deleteBean.setId(DataUtility.getInt(id));
                        try {
							model.delete(deleteBean);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }

                    ServletUtility.setSuccessMessage("Record deleted successfully", request);

                } else {

                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.PRESCRIPTION_LIST_CTL, request, response);
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

        return ORSView.PRESCRIPTION_LIST_VIEW;
    }

}