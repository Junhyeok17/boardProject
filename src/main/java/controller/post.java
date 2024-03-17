package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projectdao.dao;
import projectdto.postdto;
import projectdto.replydto;

@WebServlet("/post")
@MultipartConfig
public class post extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		
        HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		if(id==null) id="";
		
		if(id.equals("")) {
			PrintWriter out = response.getWriter();
		    out.print("<script>");
		    out.print("alert('로그인 후 이용해주세요.');");
		    out.print("location.href = 'index'");
		    out.print("</script>");
		}
		
		String num = (String)request.getParameter("postNum");
		System.out.println("get post.java : "+num);
		
		dao mydb = new dao();
		
		postdto curpost = mydb.selectOnePost(num);
		request.setAttribute("curpost", curpost);
		
		String writerCheck = mydb.isUserWriter(num, id);
		request.setAttribute("writerCheck", writerCheck);
		
		ArrayList<replydto> curreplies = mydb.selectReply(num);
		request.setAttribute("curreplies", curreplies);
		
		mydb.Close();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/post.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		if(id==null) id="";
		
		if(id.equals("")) {
			PrintWriter out = response.getWriter();
		    out.print("<script>");
		    out.print("alert('로그인 후 이용해주세요.');");
		    out.print("location.href = 'index'");
		    out.print("</script>");
		}
		
		String num = (String)request.getParameter("postNum");
		System.out.println("post post.java : "+num);
		
		dao mydb = new dao();
		
		postdto curpost = mydb.selectOnePost(num);
		request.setAttribute("curpost", curpost);
		
		String writerCheck = mydb.isUserWriter(num, id);
		request.setAttribute("writerCheck", writerCheck);

		ArrayList<replydto> curreplies = mydb.selectReply(num);
		request.setAttribute("curreplies", curreplies);
		
		mydb.Close();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/post.jsp");
		dispatcher.forward(request, response);
	}
}
