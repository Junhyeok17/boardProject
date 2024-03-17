package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/signout")
public class signout extends HttpServlet {
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
		    out.print("alert('로그인하세요.');");
		    out.print("location.href = 'index'");
		    out.print("</script>");
		}
		else {
			session.invalidate();
		    PrintWriter out = response.getWriter();
		    out.print("<script>");
		    out.print("alert('로그아웃되었습니다.');");
		    out.print("location.href = 'index'");
		    out.print("</script>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
