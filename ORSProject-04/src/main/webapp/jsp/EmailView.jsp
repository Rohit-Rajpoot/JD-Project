<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.proj4.controller.EmailCtl"%>
<%@page import="in.co.rays.proj4.bean.EmailBean"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
    <title>Add Email</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
    <form action="<%=ORSView.EMAIL_CTL%>" method="POST">
        <%@ include file="Header.jsp" %>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.EmailBean" scope="request"></jsp:useBean>

        <div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (bean != null && bean.getId() > 0) {
                %>Update<%
                    } else {
                %>Add<%
                
                    }
                %>
               Email
            </h1>

		<%
			List<EmailBean> list = (List<EmailBean>) request.getAttribute("list");
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
         
                <%--  <tr>
					<th align="left">Name<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("name", String.valueOf(bean.getName()) ,artList)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr> --%>
            
                  <tr>
                    <th align="left">Email Code :<span style="color: red">*</span></th>
                    <td><input type="text" name="emailCode" placeholder="Enter Code" value="<%=DataUtility.getStringData(bean.getEmailCode())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("emailCode", request)%>                      </font>
                    </td>
                </tr>

              <tr>
					<th align="left">Address :<span style="color: red">*</span></th>
					<td><input type="text" name="address" placeholder="Enter Address" value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
				</tr>
				
				 <tr>
					<th align="left">Subject :<span style="color: red">*</span></th>
					<td><input type="text" name="subject" placeholder="Enter Subject" value="<%=DataUtility.getStringData(bean.getSubject())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("subject", request)%></font></td>
				</tr>
				
				 <tr>
					<th align="left">Status :<span style="color: red">*</span></th>
					<td>
						<%
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							map.put("Pending", "Pending");
							map.put("Sent", "Sent");
							map.put("Failed", "Failed");

							String htmlList = HTMLUtility.getList("status", bean.getStatus(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%></font></td>
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
                        <input type="submit" name="operation" value="<%=EmailCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=EmailCtl.OP_CANCEL%>">
                    <%
                        } else {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=EmailCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=EmailCtl.OP_RESET%>">
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>

</body>
</html>