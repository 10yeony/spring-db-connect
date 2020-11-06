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

								<div class="form-group">

									<input type="text" class="form-control" name="userid"
										placeholder="User ID" required>
									<div class="check_font" id="id_check"></div>
								</div>

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
							</form>
							<hr>
							<div class="text-center">
								<a class="small" href="index.jsp">Already have an account?
									Login!</a>
							</div>
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
	<script>
		// 아이디 유효성 검사(1 = 중복 / 0 != 중복)
		$("#userid")
				.blur(
						function() {
							// id = "id_reg" / name = "userId"
							var user_id = $('#userid').val();
							$
									.ajax({
										url : '${pageContext.request.contextPath}/user/idCheck?userId='
												+ user_id,
										type : 'get',
										success : function(data) {
											console.log("1 = 중복o / 0 = 중복x : "
													+ data);

											if (data == 1) {
												// 1 : 아이디가 중복되는 문구
												$("#id_check").text(
														"사용중인 아이디입니다 :p");
												$("#id_check").css("color",
														"red");
												$("#reg_submit").attr(
														"disabled", true);
											} else {

												if (idJ.test(user_id)) {
													// 0 : 아이디 길이 / 문자열 검사
													$("#id_check").text("");
													$("#reg_submit").attr(
															"disabled", false);

												} else if (user_id == "") {

													$('#id_check').text(
															'아이디를 입력해주세요 :)');
													$('#id_check').css('color',
															'red');
													$("#reg_submit").attr(
															"disabled", true);

												} else {

													$('#id_check')
															.text(
																	"아이디는 소문자와 숫자 4~12자리만 가능합니다 :) :)");
													$('#id_check').css('color',
															'red');
													$("#reg_submit").attr(
															"disabled", true);
												}

											}
										},
										error : function() {
											console.log("실패");
										}
									});
						});
	</script>
</body>

</html>
