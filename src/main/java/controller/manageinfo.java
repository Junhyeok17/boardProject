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

import projectdao.dao;
import projectdto.memberdto;
import projectdto.postdto;

@WebServlet("/manageinfo")
public class manageinfo extends HttpServlet {
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
		
		dao mydb = new dao();
		
		ArrayList<memberdto> list = null;
		try {
			list = mydb.selectMyInfo(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mydb.Close();
		
		request.setAttribute("list", list);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/manageinfo.jsp");
		dispatcher.forward(request, response);
	}

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
        
		String nickname = request.getParameter("nickname");
		if(nickname==null) nickname="";
		String email = request.getParameter("usermail");
		if(email==null) email="";
		String password = request.getParameter("userpw");
		if(password==null) password="";
		String password2 = request.getParameter("userpw2");
		if(password2==null) password2="";
		
		System.out.println(password + password2);
		
		dao mydb = new dao();
		try {
			// 변경할 비밀번호가 있을 때
			if(!password2.equals("")) {
				if(password.equals("")) {
					PrintWriter out = response.getWriter();
				    out.print("<script>");
				    out.print("alert('현재 비밀번호를 입력하세요.');");
				    out.print("location.href = 'manageinfo'");
				    out.print("</script>");
				}
				else {
					// 현재 비밀번호가 일치할 때
					if(mydb.checkChangePassword(id, password)) {
						mydb.modifyMember(id, nickname, email, password2);
						PrintWriter out = response.getWriter();
					    out.print("<script>");
					    out.print("alert('회원 정보가 수정되었습니다.');");
					    out.print("</script>");

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
					else {
						PrintWriter out = response.getWriter();
					    out.print("<script>");
					    out.print("alert('현재 비밀번호가 일치하지 않습니다.');");
					    out.print("location.href = 'manageinfo'");
					    out.print("</script>");
					}
				}
			}
			// 비밀번호는 그대로일 때
			else {
				if(!nickname.equals("") || !email.equals("")) {
					mydb.modifyMember(id, nickname, email, null);
					PrintWriter out = response.getWriter();
				    out.print("<script>");
				    out.print("alert('회원 정보가 수정되었습니다.');");
				    out.print("</script>");

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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
