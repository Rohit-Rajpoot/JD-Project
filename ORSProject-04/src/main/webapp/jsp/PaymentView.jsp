
<%@page import="in.co.rays.proj4.controller.PaymentCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>

<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.PaymentBean"%>
<html>
<head>
    <title>Add </title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
    <form action="<%=ORSView.PAYMENT_CTL%>" method="POST">
        <%@ include file="Header.jsp" %>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.PaymentBean" scope="request"></jsp:useBean>

        <div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (bean != null && bean.getId() > 0) {
                %>Update<%
                    } else {
                %>Add<%
                
                    }
                %>
               Payment
            </h1>

		<%
			List<PaymentBean> artList = (List<PaymentBean>) request.getAttribute("artList");
		%>

            <div style="height: 15px; margin-bottom: 12px">
                <h3 align="center">
                    <font color="green">
                        <%=ServletUtility.getSuccessMessage(request)%>
                    </font>
                </h3>
                <h3 align="center">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage(request)%>
                    </font>
                </h3>
            </div>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

            <table>
                <tr>
                    <th align="left">Id<span style="color: red">*</span></th>
                    <td><input type="text" name="transactionId" placeholder="Enter transactionId " value="<%=DataUtility.getStringData(bean.getTransactionId())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("transactionId", request)%>
                        </font>
                    </td>
                </tr>
                
<tr>
                    <th align="left">Name<span style="color: red">*</span></th>
                    <td><input type="text" name="payerName" placeholder="Enter payerName" value="<%=DataUtility.getStringData(bean.getPayerName())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("payerName", request)%>
                        </font>
                    </td>
                </tr>
                
                <tr>
                    <th align="left">Amount<span style="color: red">*</span></th>
                    <td><input type="text" name="amount" placeholder="Enter " value="<%= (bean.getAmount() == 0) ? "" : bean.getAmount()%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("amount", request)%>
                        </font>
                    </td>
                </tr>
                
            
           <%--       <tr>
                    <th align="left">Name<span style="color: red">*</span></th>
                    <td><input type="text" name="name" placeholder="Enter Name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("name", request)%>                      </font>
                    </td>
                </tr>
           --%>  

              <tr>
					<th align="left">Payment Date
					<span style="color: red">*</span></th>
					<td><input type="text" id="udate" name="paymentDate" placeholder="enter date" value="<%=DataUtility.getDateString(bean.getPaymentDate())%>" style="width: 98%"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("paymentDate", request)%></font></td>
				</tr>
				
                
                <tr>
            <th align="left">Status<span style="color: red">*</span></th>
            <% HashMap<String, String> map = new HashMap<String, String>();
            map.put("online","online");
            map.put("offline","offline");
          
            String htmlutility= HTMLUtility.getList("paymentStatus", bean.getPaymentStatus(), map);
      
            
            
            %>
               <td>  <%=htmlutility%></td>
                </tr>

                <tr>
                    <th></th>
                    <td></td>
                </tr>

                <tr>
                    <th></th>
                    <%
                        if (bean != null && bean.getId() > 0) {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=PaymentCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=PaymentCtl.OP_CANCEL%>">
                    <%
                        } else {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=PaymentCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=PaymentCtl.OP_RESET%>">
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>