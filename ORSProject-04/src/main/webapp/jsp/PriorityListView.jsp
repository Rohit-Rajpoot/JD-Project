<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.PriorityBean"%>
<%@page import="in.co.rays.proj4.controller.PriorityListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Priority List</title>
</head>
<body>

<%@include file="Header.jsp"%>
    <div align="center">
    
        <h1 align="center" style="margin-bottom: -15; color: navy;">Priority List</h1>

        <div style="height: 15px; margin-bottom: 12px">
            <h3>
                <font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
            </h3>
            <h3>
                <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
        </div>

        <form action="PriorityListCtl" method="post">
            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

                @SuppressWarnings("unchecked")
                List<PriorityBean> list = (List<PriorityBean>) ServletUtility.getList(request);
                Iterator<PriorityBean> it = list.iterator();

                if (list.size() != 0) {
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">

            <table style="width: 100%">
                <tr>
                    <td align="center">
                        <label><b>Priority Code :</b></label>
                        <input type="text" name="priorityCode" placeholder="Enter Priority Code"
                               value="<%=ServletUtility.getParameter("priorityCode", request)%>">&emsp;
                        
                        <input type="submit" name="operation" value="<%=PriorityListCtl.OP_SEARCH%>">&nbsp;
                        <input type="submit" name="operation" value="<%=PriorityListCtl.OP_RESET%>">
                    </td>
                </tr>
            </table>
            <br>

            <table border="1" style="width: 100%; border: groove;">
                <tr style="background-color: #e1e6f1e3;">
                    <th width="5%"><input type="checkbox" id="selectall" /></th>
                    <th width="5%">S.No</th>
                    <th width="13%">Priority Code</th>
                    <th width="13%">Priority Level</th>
                    <th width="20%">Color Tag</th>  
                    <th width="20%">Priority Status</th>        
                    <th width="5%">Edit</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        PriorityBean bean = it.next();
                %>
                <tr>
                    <td style="text-align: center;"><input type="checkbox" class="case" name="ids" value="<%=bean.getId()%>"></td>
                    <td style="text-align: center;"><%=index++%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getPriorityCode()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getPriorityLevel()%></td>
                    <td style="text-align: center; text-transform: lowercase;"><%=bean.getColorTag()%></td>
                    <td style="text-align: center; text-transform: lowercase;"><%=bean.getPriorityStatus()%></td>
               
                    <%-- <%
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String date = sdf.format(bean.getCreated());
                    %> --%>
                    
                    <td style="text-align: center;"><a href="PriorityCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>

            <table style="width: 100%">
                <tr>
                    <td style="width: 25%">
                        <input type="submit" name="operation" value="<%=PriorityListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=PriorityListCtl.OP_NEW%>">
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=PriorityListCtl.OP_DELETE%>">
                    </td>
                    <td style="width: 25%" align="right">
                        <input type="submit" name="operation" value="<%=PriorityListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled"%>>
                    </td>
                </tr>
            </table>

            <%
                }
                if (list.size() == 0) {
            %>
            <table>
                <tr>
                    <td align="right">
                        <input type="submit" name="operation" value="<%=PriorityListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
            <%
                }
            %>
        </form>
</body>
</html>