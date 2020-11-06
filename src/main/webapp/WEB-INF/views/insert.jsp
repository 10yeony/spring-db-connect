<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Register</title>

<!-- Custom fonts for this template-->
<link
	href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
</head>

<body class="bg-gradient-primary">

	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="row">
					<div class="col-lg-5 d-none d-lg-block">
						<image
							src="${pageContext.request.contextPath}/resource/img/post-thumb-760x630.jpg"
							width="450" height="450"></image>
					</div>
					<div class="col-lg-7">
						<div class="p-5">
							<div class="text-center">
								<h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
							</div>
							<form action="insertUser" method="post" name="insert">
								<table>
									<tbody>
										<div class="form-group">
											<input type="text" class="form-control" name="userid"
												id="userid" placeholder="User ID" required> <input
												type="button" class="form-control" id="check" value="중복체크">
										</div>
										<tr>
											<td colspan=3 id="idCheck"></td>
										</tr>
									</tbody>
								</table>
								<!-- <div class="col-sm-4 mb-3 mb-sm-0">
										<input type="submit" value="Check Account"
											class="form-control form-control-user">
									</div> -->
								<div class="form-group">
									<input type="password" class="form-control" name="pwd"
										placeholder="Password" required>
								</div>
								<div class="form-group">
									<input type="submit" value="Register Account"
										class="form-control form-control-user">
								</div>
								<hr>
								<div class="text-center">
									<a class="small" href="index.jsp">Already have an account?
										Login!</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript-->
	<script
		src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script
		src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script
		src="${pageContext.request.contextPath}/resource/js/sb-admin-2.min.js"></script>


</body>
<script type="text/javascript">
	$(document)
			.ready(
					function(e) {

						var idx = false;

						$('#check')
								.click(
										function() {
											$
													.ajax({
														url : "${pageContext.request.contextPath}/IdCheck.do",
														type : "GET",
														data : {
															"userId" : $(
																	'#userId')
																	.val()
														},
														success : function(data) {
															if (data == 0
																	&& $
																			.trim($(
																					'#userId')
																					.val()) != '') {
																idx = true;
																$('#userId')
																		.attr(
																				"readonly",
																				true);
																var html = "<tr><td colspan='3' style='color: green'>사용가능</td></tr>";
																$('#idCheck')
																		.empty();
																$('#idCheck')
																		.append(
																				html);
															} else {

																var html = "<tr><td colspan='3' style='color: red'>사용불가능한 아이디 입니다.</td></tr>";
																$('#idCheck')
																		.empty();
																$('#idCheck')
																		.append(
																				html);
															}
														},
														error : function(
																request,
																status, error) {
															alert("code:"
																	+ request.status
																	+ "\n"
																	+ "message:"
																	+ request.responseText
																	+ "\n"
																	+ "error:"
																	+ error
																	+ "서버에러");
														}
													});

										});

					});
</script>
</html>
