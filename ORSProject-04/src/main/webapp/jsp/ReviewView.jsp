<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ReviewCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Review</title>
</head>
<body>

<form action="ReviewCtl" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ReviewBean"
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
				Review
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
					<th align="left">Reviewer Name : <span style="color: red">*</span></th>
					<td><input type="text" name="reviewerName"
						placeholder="Enter Reviewer Name"
						value="<%=DataUtility.getStringData(bean.getReviewerName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("reviewerName", request)%>
					</font></td>
				</tr>
				
				<%-- <tr>
					<th align="left">Rating : <span style="color: red">*</span></th>
					<td>
						<%
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							map.put("Low", "Low");
							map.put("Medium", "Medium");
							map.put("High", "High");
							map.put("Minimal", "Minimal");
							map.put("Critical", "Critical");

							String htmlList = HTMLUtility.getList("rating", bean.getRating(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("rating", request)%></font></td>
				</tr> --%>

				<tr>
					<th align="left">Rating : <span style="color: red">*</span></th>
					<td><input type="number" name="rating"
						placeholder="Enter Rating"
						style="width:163px"
						min="1"  max="5"
						value="<%=DataUtility.getStringData(bean.getRating())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("rating", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Comment : <span style="color: red">*</span></th>
					<td><input type="text" name="comment"
						placeholder="Enter Comment"
						value="<%=DataUtility.getStringData(bean.getComment())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("comment", request)%>
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
				    	<input type="submit" name="operation" value="<%=ReviewCtl.OP_UPDATE%>"> 
						<input type="submit" name="operation" value="<%=ReviewCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td align="left" colspan="2">
			    		<input type="submit" name="operation" value="<%=ReviewCtl.OP_SAVE%>"> 
			    		<input type="submit" name="operation" value="<%=ReviewCtl.OP_RESET%>">
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