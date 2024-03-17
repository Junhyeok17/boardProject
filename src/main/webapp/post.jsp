<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="projectdto.postdto"%>
<%@ page import="projectdto.replydto"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
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
			        	<form action="modifypost" method="get">
					        <%
					       	postdto curpost = (postdto)request.getAttribute("curpost");
					       	%>
					        <div class="col">
						    	<h3 class="card-title text-center"><b><%=curpost.getTitle() %></b></h3>
							<%
							String isWriterCheck = (String)request.getAttribute("writerCheck");
							if(isWriterCheck.equals("Y")){
							%>
							<div class="row justify-content-end">
								<button type="submit" class="btn btn-outline-primary col-2 pl-2 pr-2">수정하기</button>
								<button type="button" id="deletebtn" class="btn btn-outline-danger col-2" data-toggle="modal" data-target="#exampleModal2">삭제하기</button>
							</div>
								<%
								}
								%>
						    </div>
							<input type="hidden" name="postnum" value=<%=curpost.getNum() %>>
							<a class="btn btn-primary mb-3" href="index" role="button">뒤로가기</a>
							<div class="row">
								<p class="text-start col-4">작성자 : <%=curpost.getNickname() %></p>
								<p class="text-end col-8">작성일자 : <%=curpost.getRegdate() %></p>
							</div>
							<div class="postbox mt-5 mb-5">
								<div class="form-floating mb-3">
									<textarea class="form-control" name="content" readonly placeholder="Leave a comment here" id="floatingTextarea2" style="height: 300px"><%=curpost.getContent() %></textarea>
								</div>
								<%
								if(curpost.getRandfilename()!=null && !curpost.getRandfilename().equals("")){
								%>
		  							<img src=<%="http://localhost:8080/project1/upload/"+curpost.getRandfilename() %> width="500" height="500">
								<%
								}
								%>
							</div>
							<div class="card shadow">
					        <%
					        ArrayList<replydto> curreplies = (ArrayList<replydto>)request.getAttribute("curreplies");
					       	%>
								<div class="card-body">
									<p><b>댓글 목록</b></p>
									<%
									String id = (String)session.getAttribute("id");
									if(id==null) id="";
									if(curreplies!=null){
										for(int i=0;i<curreplies.size();i++){
									%>
									<div class="form-floating mb-3 mt-4">
										<div class="position-relative mb-3">
										    <label for="floatingTextarea<%=curreplies.get(i).getNum() %>" style="font-size: 20px; position: absolute; top: -20px;"><%=curreplies.get(i).getNickname() %> (<%=curreplies.get(i).getRegdate() %>)</label>
										    <textarea class="form-control" name="content" readonly id="floatingTextarea2" style="height: auto; overflow: hidden; resize: vertical;">
										        <%=curreplies.get(i).getContent() %>
										    </textarea>
										    <%
										    if(curreplies.get(i).getId()!=null && curreplies.get(i).getId().equals(id)){
										    %>
										    <p class="text-body-secondary position-absolute top-0 end-0 mb-0 me-2" style="z-index: 1;">
										        <a href="deletereply?postNum=<%=curreplies.get(i).getPostnum() %>&replyNum=<%=curreplies.get(i).getNum() %>" class="text-reset">삭제하기</a>
										    </p>
										    <%
										    }
										    %>
										</div>
									</div>
									<%
										}
									}
									%>
									<div class="form-floating mb-3">
										<textarea class="form-control" name="content" placeholder="댓글을 남겨보세요." id="floatingTextarea3" style="height: 50px"></textarea>
										<label for="floatingTextarea2">Comments</label>
									</div>
									<div class="text-right">
										<button type="button" id="regReplyBtn" onclick="regReply(<%=curpost.getNum() %>)">등록하기</button>
									</div>
								</div>
							</div>
						</form>
			        </div>
			    </div>
		    </div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h1 class="modal-title fs-5" id="exampleModalLabel">현재 게시물을 삭제하려고 합니다.</h1>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		        정말로 삭제하시겠습니까?
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
		        <button type="button" class="btn btn-primary" onclick="deletePost(<%=curpost.getNum() %>)">삭제하기</button>
		      </div>
		    </div>
		  </div>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
		<script>
		$('#deletebtn').click(function(e){
			$('#exampleModal2').modal("show");
		});
		function regReply(postNum) {
			var content = document.querySelector('#floatingTextarea3').value;
			$.ajax({
				url: "regreply",
				type: "POST",
				data: { postNum: postNum, content: content },
				success: function(response){
					window.location.href="post?postNum="+postNum;
				},
				error: function(xhr, status, error){
					console.error("댓글 등록 실패: "+error);
				}
			});
		}
		function deletePost(postNum){
			$.ajax({
				url: "deletepost",
				type: "POST",
				data: { postNum: postNum },
				success: function(response){
					alert("게시물을 삭제했습니다.");
					window.location.href="index";
				},
				error: function(xhr, status, error){
					console.error("게시물 삭제 실패: "+error);
				}
			});
		}
		</script>
	</body>
</html>