<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.proj4.controller.InvestorCtl"%>
<%@page import="in.co.rays.proj4.bean.InvestorBean"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ArtCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<html>
<head>
    <title>Add Investor</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
    <form action="<%=ORSView.INVESTOR_CTL%>" method="POST">
        <%@ include file="Header.jsp" %>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.InvestorBean" scope="request"></jsp:useBean>

        <div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (bean != null && bean.getId() > 0) {
                %>Update<%
                    } else {
                %>Add<%
                
                    }
                %>
               Investor
            </h1>

		<%
			List<InvestorBean> artList = (List<InvestorBean>) request.getAttribute("artList");
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
                    <th align="left">Investor Name :<span style="color: red">*</span></th>
                    <td><input type="text" name="investorName" placeholder="Enter Name" value="<%=DataUtility.getStringData(bean.getInvestorName())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("investorName", request)%>                      </font>
                    </td>
                </tr>

              <tr>
					<th align="left">Investment Amount :<span style="color: red">*</span></th>
					<td><input type="text" name="investmentAmount" placeholder="enter investment" value="<%=(bean.getInvestmentAmount() == 0) ? "" : bean.getInvestmentAmount()%>" style="width: 98%"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("investmentAmount", request)%></font></td>
				</tr>
				
				 <tr>
					<th align="left">Investment Type :<span style="color: red">*</span></th>
					<td>
						<%
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							map.put("Offline", "Offline");
							map.put("Online", "Online");

							String htmlList = HTMLUtility.getList("investmentType", bean.getInvestmentType(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("investmentType", request)%></font></td>
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
                        <input type="submit" name="operation" value="<%=InvestorCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=InvestorCtl.OP_CANCEL%>">
                    <%
                        } else {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=InvestorCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=InvestorCtl.OP_RESET%>">
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>