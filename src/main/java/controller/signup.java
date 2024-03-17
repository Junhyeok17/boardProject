package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import projectdao.dao;

@WebServlet("/signup")
public class signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("signup.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		String id = request.getParameter("userid");
		if(id==null) id="";
		String nickname = request.getParameter("nickname");
		if(nickname==null) nickname="";
		String email = request.getParameter("usermail");
		if(email==null) email="";
		String password = request.getParameter("userpw");
		if(password==null) password="";
		
		if(id.equals("") || nickname.equals("") || email.equals("") || password.equals("")) {
		    PrintWriter out = response.getWriter();
		    out.print("<script>");
		    out.print("alert('모든 정보를 입력하세요.');");
		    out.print("location.href = 'signup'");
		    out.print("</script>");	
		    return;
		}
		
		dao mydb = new dao();
		mydb.insertMember(id, nickname, email, password);
		mydb.Close();
		
		PrintWriter out = response.getWriter();
	    out.print("<script>");
	    out.print("alert('회원가입이 완료되었습니다.');");
	    out.print("location.href = 'index'");
	    out.print("</script>");	
	}
}
