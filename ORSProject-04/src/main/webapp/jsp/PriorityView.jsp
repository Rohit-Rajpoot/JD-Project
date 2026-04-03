<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.PriorityCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Priority</title>
</head>
<body>

<form action="PriorityCtl" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PriorityBean"
			scope="request"></jsp:useBean>

		<%-- <%
			List list = (List) request.getAttribute("list");
		%> --%>

		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update
				<%
					} else {
				%>
				Add
				<%
					}
				%>
				Priority
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>
				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            
            
			<table>
				<tr>
					<th align="left">Priority code<span style="color: red">*</span></th>
					<td><input type="text" name="priorityCode"
						placeholder="Enter Priority Code"
						value="<%=DataUtility.getStringData(bean.getPriorityCode())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("priorityCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Priority Level<span style="color: red">*</span></th>
					<td>
						<%
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							map.put("Low", "Low");
							map.put("Medium", "Medium");
							map.put("High", "High");
							map.put("Minimal", "Minimal");
							map.put("Critical", "Critical");

							String htmlList = HTMLUtility.getList("priorityLevel", bean.getPriorityLevel(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("priorityLevel", request)%></font></td>
				</tr>
				
				<tr>
					<th align="left">Color Tag<span style="color: red">*</span></th>
					<td>
						<%
							LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
							map1.put("Red", "Red");
							map1.put("Orange", "Orange");
							map1.put("Yellow", "Yellow");
							map1.put("Green", "Green");
							map1.put("Blue", "Blue");

							String htmlList1 = HTMLUtility.getList("colorTag", bean.getColorTag(), map1);
						%>
						 <%=htmlList1%>
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("colorTag", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Priority Status<span style="color: red">*</span></th>
					<td><input type="text" name="priorityStatus"
						placeholder="Enter Priority Status"
						value="<%=DataUtility.getStringData(bean.getPriorityStatus())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("priorityStatus", request)%>
					</font></td>
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
				    	<input type="submit" name="operation" value="<%=PriorityCtl.OP_UPDATE%>"> 
						<input type="submit" name="operation" value="<%=PriorityCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td align="left" colspan="2">
			    		<input type="submit" name="operation" value="<%=PriorityCtl.OP_SAVE%>"> 
			    		<input type="submit" name="operation" value="<%=PriorityCtl.OP_RESET%>">
					</td>
					<%
						}
					%>
				</tr>
			</table>
		</div>
	</form>

</body>
</html>