<%@page import="in.co.rays.proj4.controller.PrescriptionCtl"%>
<%@page import="in.co.rays.proj4.bean.PrescriptionBean"%>

<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>

<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
    <title>Add </title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
    <form action="<%=ORSView.PRESCRIPTION_CTL%>" method="POST">
        <%@ include file="Header.jsp" %>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.PrescriptionBean" scope="request"></jsp:useBean>

        <div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (bean != null && bean.getId() > 0) {
                %>Update<%
                    } else {
                %>Add<%
                
                    }
                %>
               Prescription
            </h1>

		<%
			List<PrescriptionBean> artList = (List<PrescriptionBean>) request.getAttribute("artList");
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
                    <th align="left">Code<span style="color: red">*</span></th>
                    <td><input type="text" name="code" placeholder="Enter code" value="<%=DataUtility.getStringData(bean.getCode())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("code", request)%>
                        </font>
                    </td>
                </tr>
                

            
              <tr>
                    <th align="left">Name<span style="color: red">*</span></th>
                    <td><input type="text" name="name" placeholder="Enter Name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("name", request)%>                      </font>
                    </td>
                </tr>
            

              <tr>
					<th align="left">Medicines
					<span style="color: red">*</span></th>
					<td><input type="text"  name="medicines" placeholder="medicines" value="<%=DataUtility.getStringData(bean.getMedicines())%>" style="width: 98%"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("medicines", request)%></font></td>
				</tr>
				 <tr>
                    <th align="left">Status<span style="color: red">*</span></th>
                    <td><input type="text" name="status" placeholder="Enter status" value="<%=DataUtility.getStringData(bean.getStatus())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("status", request)%>
                        </font>
                    </td>
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
                        <input type="submit" name="operation" value="<%=PrescriptionCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=PrescriptionCtl.OP_CANCEL%>">
                    <%
                        } else {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=PrescriptionCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=PrescriptionCtl.OP_RESET%>">
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>