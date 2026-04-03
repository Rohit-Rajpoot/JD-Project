<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.controller.DeviceCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Mobile Device</title>
</head>
<body>

	<form action="DeviceCtl" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DeviceBean"
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
				Device
			</h1>
			
			<%List list = (List) request.getAttribute("list"); %>

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
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            
			<table>
				<tr>
					<th align="left">Name<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter Mobile Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Type<span style="color: red">*</span><%HTMLUtility.getList("type", bean.getType(), list); %></th>
					<td>
						<%-- <%
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							map.put("Mobile", "Mobile");
							map.put("Tablet", "Tablet");
							map.put("Laptop", "Laptop");
							map.put("Smart Watch", "Smart Watch");

							String htmlList = HTMLUtility.getList("type", bean.getType(), map);
						%> <%=htmlList%> --%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("type", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Operating System<span style="color: red">*</span></th>
					<td><input type="text" name="operatingSystem"
						placeholder="Enter Operating System"
						value="<%=DataUtility.getStringData(bean.getOperatingSystem())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("operatingSystem", request)%>
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
				    	<input type="submit" name="operation" value="<%=DeviceCtl.OP_UPDATE%>"> 
						<input type="submit" name="operation" value="<%=DeviceCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td align="left" colspan="2">
			    		<input type="submit" name="operation" value="<%=DeviceCtl.OP_SAVE%>"> 
			    		<input type="submit" name="operation" value="<%=DeviceCtl.OP_RESET%>">
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