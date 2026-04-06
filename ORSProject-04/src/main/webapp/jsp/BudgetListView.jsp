<%@page import="in.co.rays.proj4.controller.BudgetListCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.Collections"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>

<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.BudgetBean"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
    <title>Budget List</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
    <%@include file="Header.jsp"%>
    <div align="center">
        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.BudgetBean" scope="request"></jsp:useBean>
        <h1 align="center" style="margin-bottom: -15; color: navy;">Budget List</h1>

        <div style="height: 15px; margin-bottom: 12px">
            <h3>
                <font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
            </h3>
            <h3>
                <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
        </div>

        <form action="<%=ORSView.BUDGET_LIST_CTL%>" method="POST">
            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

           
                List<BudgetBean> broadcastList = (List<BudgetBean>) request.getAttribute("broadcastList");
                List<BudgetBean> list = (List<BudgetBean>) ServletUtility.getList(request);
                Iterator<BudgetBean> it = list.iterator();

                if (list.size() != 0) {
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">

            <table style="width: 100%">
                <tr>
                    <td align="center">
                     <label><b> Amount :</b></label>
						<input type="text" name="amount" placeholder="Enter  amount" value="<%=ServletUtility.getParameter("amount", request)%>">&emsp;
                        
                        <input type="submit" name="operation" value="<%=BudgetListCtl.OP_SEARCH%>">&nbsp;
                        <input type="submit" name="operation" value="<%=BudgetListCtl.OP_RESET%>">
                    </td>
                </tr>
            </table>
            <br>

            <table border="1" style="width: 100%; border: groove;">
                <tr style="background-color: #e1e6f1e3;">
                    <th width="5%"><input type="checkbox" id="selectall" /></th>
                    <th width="5%">S.No</th>
                   
                    <th width="25%">Amount</th>
                    <th width="15%">Spent Amount</th>
                    <th width="10%">Department</th>
                  
                      <th width="10%">Edit</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        bean = it.next();
                %>
                <tr>
                    <td style="text-align: center;">
                        <input type="checkbox" class="case" name="ids" value="<%=bean.getId()%>">
                    </td>
                    <td style="text-align: center;"><%=index++%></td>
                    
                    
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getAmount()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getSpentAmount()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getDepartment()%></td>
                          
                    <td style="text-align: center;"><a href="BudgetCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>

            <table style="width: 100%">
                <tr>
                    <td style="width: 25%">
                        <input type="submit" name="operation" value="<%=BudgetListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=BudgetListCtl.OP_NEW%>">
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=BudgetListCtl.OP_DELETE%>">
                    </td>
                    <td style="width: 25%" align="right">
                        <input type="submit" name="operation" value="<%=BudgetListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled" %>>
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
                        <input type="submit" name="operation" value="<%=BudgetListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
            <%
                }
            %>
        </form>
    </div>
</body>
</html>