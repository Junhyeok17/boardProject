<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="projectdto.postdto"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	</head>
	<body>
		<div class="container mt-5">
			<div class="row">
				<div class="col-3"></div>
			    <div class="card shadow col-6 justify-content-center">
			        <div class="card-body">
			        	<form action="modifypost" method="post" enctype="multipart/form-data" autocomplete="off">
					        <%
					       	postdto curpost = (postdto)request.getAttribute("curpost");
					       	%>
				            <h3 class="card-title text-center"><b>글 수정</b></h3>
				            <input type="hidden" name="postnum" value="<%=curpost.getNum() %>">
							<a class="btn btn-primary" href="modifypost" role="button">뒤로가기</a>
							<div class="modifyPostBox mt-5 mb-3">
								<div class="input-group mb-3">
									<input type="text" class="form-control" name="title" aria-label="id" aria-describedby="basic-addon1" value="<%=curpost.getTitle() %>">
								</div>
								<div class="form-floating mb-3">
									<textarea class="form-control" name="content" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 300px"><%=curpost.getContent() %></textarea>
									<label for="floatingTextarea2">Comments</label>
								</div>
								<div class="input-group mb-3">
									<input type="file" class="form-control" name="filename">
								</div>
								<div class="text-end">
									<button type="submit" class="form-control btn btn-primary" aria-describedby="basic-addon1">수정하기</button>
								</div>
							</div>
			            </form>
			        </div>
			    </div>
		    </div>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	</body>
</html>