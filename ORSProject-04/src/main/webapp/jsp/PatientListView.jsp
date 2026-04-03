<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.controller.PatientListCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.PatientBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>

<html>
<head>
<title>Patient List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PatientBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 style="color: navy;">Patient List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.PATIENT_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);
				Iterator<PatientBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


			<table width="100%">
				<tr>
					<td align="center"><b>Doctor Name :</b> <input type="text"
						name="doctorname"
						value="<%=ServletUtility.getParameter("doctorname", request)%>"
						placeholder="enter doctor name"> &nbsp;&nbsp; <b>Patient
							Name :</b> <input type="text" name="patientname"
						value="<%=ServletUtility.getParameter("patientname", request)%>"
						placeholder="enter patient name"> &nbsp;&nbsp; <b>Gender:
					</b> <%
 	HashMap<String, String> map = new HashMap<String, String>();
 		map.put("Male", "Male");
 		map.put("Female", "Female");

 		String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
 %> <%=htmlList%> &nbsp;&nbsp; <input type="submit" name="operation"
						value="<%=PatientListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=PatientListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>


			<table border="1" width="100%">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall"></th>
					<th width="5%">S.No</th>
					<th width="20%">Doctor Name</th>
					<th width="20%">patient Name</th>
					<th width="10%">Gender</th>
					<th width="20%">Login Id</th>
					<th width="15%">Date of Birth</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						while (it.hasNext()) {
							PatientBean pbean = it.next();
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=pbean.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=pbean.getDoctorname()%></td>
					<td align="center"><%=pbean.getPatientname()%></td>
					<td align="center"><%=pbean.getGender()%></td>
					<td align="center"><%=pbean.getPatientloginid()%></td>
					<td align="center"><%=pbean.getPatientdateofbirth() != null ? sdf.format(pbean.getPatientdateofbirth()) : ""%>
					</td>
					<td align="center"><a
						href="<%=ORSView.PATIENT_CTL%>?id=<%=pbean.getId()%>">Edit</a>
				</tr>

				<%
					}
				%>
			</table>

			<br>


			<table width="100%">
				<tr>
					<td width="25%"><input type="submit" name="operation"
						value="<%=PatientListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td width="25%" align="center"><input type="submit"
						name="operation" value="<%=PatientListCtl.OP_NEW%>"></td>

					<td width="25%" align="center"><input type="submit"
						name="operation" value="<%=PatientListCtl.OP_DELETE%>"></td>

					<td width="25%" align="right"><input type="submit"
						name="operation" value="<%=PatientListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>
			<%
				} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=PatientListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
				}
			%>

		</form>
	</div>

</body>
</html>
