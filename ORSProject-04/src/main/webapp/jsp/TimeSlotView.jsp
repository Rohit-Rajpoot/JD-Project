<%@page import="in.co.rays.proj4.controller.TimeSlotCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Time Slot</title>
</head>
<body>

<form action="TimeSlotCtl" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TimeSlotBean"
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
				Time Slot
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
					<th align="left">Slot code<span style="color: red">*</span></th>
					<td><input type="text" name="slotCode"
						placeholder="Enter Slot Code"
						value="<%=DataUtility.getStringData(bean.getSlotCode())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("slotCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Start Time<span style="color: red">*</span></th>
					<td><input type="date" name="startTime"
						placeholder="Enter Start Time"
						style="width:165px"
						value="<%=DataUtility.getStringData(bean.getStartTime())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("startTime", request)%></font></td>
				</tr>
				
				<tr>
					<th align="left">End Time<span style="color: red">*</span></th>
					<td><input type="date" name="endTime"
						placeholder="Enter End Time"
						style="width:165px"
						value="<%=DataUtility.getStringData(bean.getEndTime())%>"></td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("endTime", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Slot Status<span style="color: red">*</span></th>
					<td><input type="text" name="slotStatus"
						placeholder="Enter Slot Status"
						value="<%=DataUtility.getStringData(bean.getSlotStatus())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("slotStatus", request)%>
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
				    	<input type="submit" name="operation" value="<%=TimeSlotCtl.OP_UPDATE%>"> 
						<input type="submit" name="operation" value="<%=TimeSlotCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td align="left" colspan="2">
			    		<input type="submit" name="operation" value="<%=TimeSlotCtl.OP_SAVE%>"> 
			    		<input type="submit" name="operation" value="<%=TimeSlotCtl.OP_RESET%>">
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