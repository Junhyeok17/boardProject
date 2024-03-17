<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.io.PrintWriter" %>   
<%@ page import="projectdto.postdto"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Project1</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-4">
				<%
				String id = (String)session.getAttribute("id");
				if(id==null) id="";
				if(id.equals("")){
				%>
				<div id="loginSuccess" class="mt-5" style="display: none;">로그인 상태</div>
				<form action="index" method="post" style="display: block">
				<%
				} else {
				%>
				<div id="loginSuccess" class="mt-5 mb-2" style="display: block;">로그인 상태</div>
				<div class="row">
					<div class="col-3">
						<a href="signout" class="text-reset">로그아웃</a>
					</div>
					<div class="col-3">
						<a href="manageinfo" class="text-reset">내정보관리</a>
					</div>
				</div>
				<form action="index" method="post" style="display: none">
				<%
				}
				%>
					<div class="loginbox mt-5 mb-3">
						<div class="input-group mb-3">
							<input type="text" class="form-control" name="userid" placeholder="아이디 입력" aria-label="id" aria-describedby="basic-addon1">
						</div>
						<div class="input-group mb-3">
							<input type="password" class="form-control" name="userpw" placeholder="비밀번호 입력" aria-label="password" aria-describedby="basic-addon1">
						</div>
						<button type="submit" class="form-control btn btn-primary" aria-describedby="basic-addon1">로그인</button>
					</div>
					<a href="signup" class="text-reset">회원가입</a>
				</form>
			</div>
			<div class="col-1"></div>
			<div class="col-7 mt-5">
				<div class="title text-center">
					<h3><b>게시판</b></h3>
				</div>
				<div class="text-end">
					<button id="writePostBtn">글쓰기</button>
				</div>
				<table class="table table-hover table-striped text-center">
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>내용</th>
							<th>작성자</th>
							<th>작성시간</th>
						</tr>
					</thead>
					<tbody>
					<%
					ArrayList<postdto> postlist = (ArrayList)request.getAttribute("postlist");
					if(postlist!=null){
						for(int i=0;i<postlist.size();i++){
					%>
						<tr>
							<td><%=postlist.get(i).getNum() %></td>
							<td><%=postlist.get(i).getTitle() %></td>
							<td class="text-truncate postcontent" style="max-width: 200px;" data-postnum="<%=postlist.get(i).getNum() %>">
								<%=postlist.get(i).getContent() %>
							</td>
							<td><%=postlist.get(i).getNickname() %></td>
							<td><%=postlist.get(i).getRegdate() %></td>
						</tr>
					<%
						}
					}
					%>
					</tbody>
				</table>
				<nav class="navbar navbar-expand-sm justify-content-end" aria-label="Page navigation example">
					<ul class="pagination" style="font-size : 10px">
						${page}
					</ul>
				</nav>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {
        // 버튼 클릭 시 글쓰기 함수 호출
        $("#writePostBtn").click(function() {
            // AJAX를 사용하여 로그인 상태 확인
            $.ajax({
                url: "checklogin.jsp", // 로그인 상태를 확인하는 JSP 파일 경로
                type: "GET",
                success: function(response) {
                    // 성공했을 때 실행할 코드
                    response = response.trim();
                    if (response === "loggedIn") {
                        // 로그인 상태인 경우, 현재 페이지에 남거나 다음 페이지로 이동
                        // 여기에 필요한 처리를 추가하세요
						window.location.href="writepost";
                    } else {
                        // 로그인 상태가 아닌 경우, 로그인 화면으로 이동하거나 다른 처리 수행
                        alert("로그인이 필요합니다.");
                    }
                },
                error: function(xhr, status, error) {
                    // 실패했을 때 실행할 코드
                    console.error("로그인 상태 확인 실패: " + error);
                    // 실패 시 추가적으로 원하는 동작을 수행할 수 있습니다.
                }
            });
        });
        
        // 게시판 글 클릭하면 로그인 여부 확인 후 상세조회 화면으로 이동
        $("td.postcontent").click(function() {
        	var postNum = $(this).data("postnum");
            // AJAX를 사용하여 로그인 상태 확인
            $.ajax({
                url: "checklogin.jsp", // 로그인 상태를 확인하는 JSP 파일 경로
                type: "GET",
                success: function(response) {
                    // 성공했을 때 실행할 코드
                    response = response.trim();
                    if (response === "loggedIn") {
                        // 로그인 상태인 경우, 클릭한 포스트의 번호와 함께 다음 페이지로 이동
						window.location.href="post?postNum="+postNum;
                    } else {
                        // 로그인 상태가 아닌 경우, 로그인 화면으로 복귀
                        alert("자세히 보려면 로그인이 필요합니다.");
                    }
                },
                error: function(xhr, status, error) {
                    // 실패했을 때 실행할 코드
                    console.error("로그인 상태 확인 실패: " + error);
                    // 실패 시 추가적으로 원하는 동작을 수행할 수 있습니다.
                }
            });
        });
    });
</script>
</body>
</html>