package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;

import projectdao.dao;
import projectdto.postdto;

@WebServlet("/index")
public class index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tpage = request.getParameter("tpage");
		if(tpage==null) tpage="1";
		
		dao mydb = new dao();
		
		String paging = mydb.selectPostPageNumber(Integer.parseInt(tpage));
		request.setAttribute("page", paging);
		
		ArrayList<postdto> postlist = mydb.selectAllPost(Integer.parseInt(tpage));
		request.setAttribute("postlist", postlist);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		String id = request.getParameter("userid");
		if(id==null) id="";
		String password = request.getParameter("userpw");
		if(password==null) password="";
		System.out.println(id+password);
		
		dao mydb = new dao();
		boolean result = false;
		try {
			result = mydb.selectMember(id, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result) {
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			System.out.println("�α��� ����");
		    PrintWriter out = response.getWriter();
		    out.print("<script>");
		    out.print("alert('�α��� ����.');");
		    out.print("location.href = 'index'");
		    out.print("</script>");
		    out.close();
		}
		else {
			System.out.println("�α��� ����");
		    PrintWriter out = response.getWriter();
		    out.print("<script>");
		    out.print("alert('�α��� ����.');");
		    out.print("location.href = 'index'");
		    out.print("</script>");
		    out.close();
		}		
		
		String tpage = request.getParameter("tpage");
		if(tpage==null) tpage="1";

		String paging = mydb.selectPostPageNumber(Integer.parseInt(tpage));
		request.setAttribute("page", paging);
		
		ArrayList<postdto> postlist = mydb.selectAllPost(Integer.parseInt(tpage));
		request.setAttribute("postlist", postlist);

		mydb.Close();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
}
