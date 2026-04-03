<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.controller.CertificateCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Certificate</title>
</head>
<body>

<form action="CertificateCtl" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CertificateBean"
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
				Certificate
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
					<th align="left">Certificate Code : <span style="color: red">*</span></th>
					<td><input type="text" name="certificateCode"
						placeholder="Enter Certificate Code"
						value="<%=DataUtility.getStringData(bean.getCertificateCode())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("certificateCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Student Name : <span style="color: red">*</span></th>
					<td><input type="text" name="studentName"
						placeholder="Enter Student Name"
						value="<%=DataUtility.getStringData(bean.getStudentName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("studentName", request)%></font></td>
				</tr>
				
				<tr>
					<th align="left">Course Name : <span style="color: red">*</span></th>
					<td><input type="text" name="courseName"
						placeholder="Enter Course Name"
						value="<%=DataUtility.getStringData(bean.getCourseName())%>"></td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Issue Date : <span style="color: red">*</span></th>
					<td><input type="date" name="issueDate"
						placeholder="Enter Issue Date"
						value="<%=DataUtility.getStringData(bean.getIssueDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("issueDate", request)%>
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
				    	<input type="submit" name="operation" value="<%=CertificateCtl.OP_UPDATE%>"> 
						<input type="submit" name="operation" value="<%=CertificateCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td align="left" colspan="2">
			    		<input type="submit" name="operation" value="<%=CertificateCtl.OP_SAVE%>"> 
			    		<input type="submit" name="operation" value="<%=CertificateCtl.OP_RESET%>">
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