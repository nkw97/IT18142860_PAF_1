<%@page import="com.Hospital"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HOSPITAL DETAILS</title>
</head>
<body>
	<link rel="stylesheet" href="View/bootstrap.min.css">
	<script src="Componant/jquery-3.2.1.min.js"></script>
	<script src="Componant/Hospital.js"></script>
	<meta charset="ISO-8859-1">

	<meta charset="ISO-8859-1">
	<title>ADD HOSPITAL</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">

				<h1>ADD HOSPITAL</h1>
				<form id="formHospital" name="formHospital" method="post" action="hospital.jsp">
					Hospital Name: 
					<input id="hospitalName" name="hospitalName" type="text"class="form-control form-control-sm">
								
					 <br> 
					 Telephone Number:
					<input id="telNo" name="telNo" type="text"class="form-control form-control-sm"> 
					<br>
					 Email: 
					<input id="email" name="email" type="text"class="form-control form-control-sm"> 
					<br>
					 Address: 
					<input id="address" name="address" type="text"class="form-control form-control-sm">
					 <br> 
					<input id="btnSave" name="btnSave" type="button" value="Save"class="btn btn-primary">
		<input type="hidden" id="hidHospitalIDSave" name="hidHospitalIDSave" value="">
				</form>


				<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
  
   <br>
   <div id="divHospitalGrid">
   
   <%
   
      Hospital hos1 = new Hospital();
      out.print(hos1.viewHospital());
   %>
   </div>

			</div>
		</div>
	</div>

</body>
</html>