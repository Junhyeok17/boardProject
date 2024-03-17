package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projectdao.dao;

@WebServlet("/regreply")
public class regreply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		String content = (String)request.getParameter("content");
		String postNum = (String)request.getParameter("postNum");
		System.out.println("regreply : "+content);
		dao mydb = new dao();
		try {
			mydb.insertReply(id, postNum, content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mydb.Close();
	}
}
