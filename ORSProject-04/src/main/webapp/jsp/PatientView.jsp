<%@page import="in.co.rays.proj4.controller.PatientCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Patient</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.PATIENT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PatientBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>Update<%
					} else {
				%>Add<%
					}
				%>
				Patient
			</h1>
			<h1>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h1>
			<!-- Hidden fields -->
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Doctor Name<span style="color: red">*</span></th>
					<td><input type="text" name="doctorname"
						value="<%=DataUtility.getStringData(bean.getDoctorname())%>"
						placeholder="Enter Doctor Name"></td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("doctorname", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Patient Name<span style="color: red">*</span></th>
					<td><input type="text" name="patientname"
						value="<%=DataUtility.getStringData(bean.getPatientname())%>"
						placeholder="Enter Patient Name"></td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("patientname", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Date of Birth<span style="width: 98%"
						style="color: red">*</span></th>
					<td><input type="text" id="udate" name="patientdateofbirth"
						placeholder="Select Date of Birth"
						value="<%=DataUtility.getDateString(bean.getPatientdateofbirth())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("patientdateofbirth", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Gender<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("Male", "Male");
							map.put("Female", "Female");
							String genderList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=genderList%>
					</td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Login Id<span style="color: red">*</span></th>
					<td><input type="text" name="patientloginid"
						value="<%=DataUtility.getStringData(bean.getPatientloginid())%>"
						placeholder="Enter Login Id"></td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("patientloginid", request)%></font></td>
				</tr>

				<tr>
					<td align="center" colspan="3">
						<%
							if (bean != null && bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=PatientCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=PatientCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=PatientCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=PatientCtl.OP_RESET%>"> <%
 	}
 %>
					</td>
				</tr>



			</table>
		</div>

	</form>

</body>
</html>
