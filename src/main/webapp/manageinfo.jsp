<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="projectdto.memberdto"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>회원가입</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<div class="container mt-5">
		<div class="row">
			<div class="col-3"></div>
		    <div class="card shadow col-6 justify-content-center">
		        <div class="card-body">
		        	<form action="manageinfo" method="post">
			            <h3 class="card-title text-center"><b>내 정보 관리</b></h3>
						<a class="btn btn-primary" href="index" role="button">뒤로가기</a>
			        	<%
			        	ArrayList<memberdto> data = (ArrayList<memberdto>)request.getAttribute("list");
			        	if(data!=null){
			        	%>
						<div class="infobox mt-5 mb-3">
							<p class="text-start">아이디</p>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="userid" readonly aria-label="id" aria-describedby="basic-addon1" value="<%=data.get(0).getId() %>">
							</div>
							<p class="text-start">닉네임</p>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="nickname" placeholder="닉네임 입력" aria-describedby="basic-addon1" value="<%=data.get(0).getNickname() %>">
							</div>
							<p class="text-start">이메일</p>
							<div class="input-group mb-3">
								<input type="text" class="form-control" name="usermail" placeholder="이메일 주소 입력" value="<%=data.get(0).getEmail() %>">
							</div>
							<p class="text-start">현재 비밀번호</p>
							<div class="input-group mb-3">
								<input type="password" class="form-control" name="userpw" placeholder="현재 비밀번호 입력" aria-describedby="basic-addon1">
							</div>
							<p class="text-start">변경할 비밀번호</p>
							<div class="input-group mb-3">
								<input type="password" class="form-control" name="userpw2" placeholder="변경할 비밀번호 입력" aria-describedby="basic-addon1">
							</div>
							<div class="row">
								<div class="col-4 offset-4 text-center">
									<button type="submit" class="form-control btn btn-primary" aria-describedby="basic-addon1">수정하기</button>
								</div>
								<div class="col-4 text-end">
									<button type="button" class="form-control btn btn-danger" data-bs-toggle="modal" data-bs-target="#exampleModal">탈퇴하기</button>
								</div>
							</div>
						</div>
						<%
			        	}
						%>
		            </form>
		        </div>
		    </div>
	    </div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">정말로 탈퇴하시겠습니까?</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소하기</button>
	        <button type="button" class="btn btn-primary" onclick="deleteAccount()">탈퇴하기</button>
	      </div>
	    </div>
	  </div>
	</div>
	<script>
	function deleteAccount() {
		$.ajax({
			url: "deleteaccount",
			type: "POST",
            success: function(response) {
                if (response === "success") {
                    alert("회원 탈퇴가 완료되었습니다.");
					window.location.href="index";
                } else {
                    alert("회원 탈퇴에 실패했습니다.");
                }
            },
            error: function(xhr, status, error) {
                console.error("회원 탈퇴 요청 실패: " + error);
            }
		});
	}
</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>