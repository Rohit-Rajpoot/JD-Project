<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.controller.TimeSlotListCtl"%>
<%@page import="in.co.rays.proj4.bean.TimeSlotBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Time Slot List</title>
</head>
<body>

<%@include file="Header.jsp"%>
    <div align="center">
    
        <h1 align="center" style="margin-bottom: -15; color: navy;">Time Slot List</h1>

        <div style="height: 15px; margin-bottom: 12px">
            <h3>
                <font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
            </h3>
            <h3>
                <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
        </div>

        <form action="TimeSlotListCtl" method="post">
            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

                @SuppressWarnings("unchecked")
                List<TimeSlotBean> list = (List<TimeSlotBean>) ServletUtility.getList(request);
                Iterator<TimeSlotBean> it = list.iterator();

                if (list.size() != 0) {
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">

            <table style="width: 100%">
                <tr>
                    <td align="center">
                        <label><b>Time Slot Code</b></label>
                        <input type="text" name="slotCode" placeholder="Enter Priority Code"
                               value="<%=ServletUtility.getParameter("slotCode", request)%>">&emsp;
                        
                        <input type="submit" name="operation" value="<%=TimeSlotListCtl.OP_SEARCH%>">&nbsp;
                        <input type="submit" name="operation" value="<%=TimeSlotListCtl.OP_RESET%>">
                    </td>
                </tr>
            </table>
            <br>

            <table border="1" style="width: 100%; border: groove;">
                <tr style="background-color: #e1e6f1e3;">
                    <th width="5%"><input type="checkbox" id="selectall" /></th>
                    <th width="5%">S.No</th>
                    <th width="13%">Time Slot Code</th>
                    <th width="13%">Start Time</th>
                    <th width="20%">End Time</th>  
                    <th width="20%">Slot Status</th>        
                    <th width="5%">Edit</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        TimeSlotBean bean = it.next();
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(bean.getStartTime());
                        String date1 = sdf.format(bean.getEndTime());

                %>
                <tr>
                    <td style="text-align: center;"><input type="checkbox" class="case" name="ids" value="<%=bean.getId()%>"></td>
                    <td style="text-align: center;"><%=index++%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getSlotCode()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=date%></td>
                    <td style="text-align: center; text-transform: lowercase;"><%=date1%></td>
                    <td style="text-align: center; text-transform: lowercase;"><%=bean.getSlotStatus()%></td>
                    
                    <td style="text-align: center;"><a href="TimeSlotCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>

            <table style="width: 100%">
                <tr>
                    <td style="width: 25%">
                        <input type="submit" name="operation" value="<%=TimeSlotListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=TimeSlotListCtl.OP_NEW%>">
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=TimeSlotListCtl.OP_DELETE%>">
                    </td>
                    <td style="width: 25%" align="right">
                        <input type="submit" name="operation" value="<%=TimeSlotListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled"%>>
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
                        <input type="submit" name="operation" value="<%=TimeSlotListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
            <%
                }
            %>
        </form>

</body>
</html>