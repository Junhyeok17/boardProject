package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import projectdao.dao;
import projectdto.postdto;

@WebServlet("/writepost")
@MultipartConfig
public class writepost extends HttpServlet {
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
		response.sendRedirect("writepost.jsp");
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
		
		String title = request.getParameter("title");
		if(title==null) title="";
		String content = request.getParameter("content");
		if(content==null) content="";
        Part part = request.getPart("filename");
        String realpath = "", realfilename = "", randfilename = "";
        System.out.println("part : "+part);
    	realfilename = part.getSubmittedFileName();
    	if(realfilename==null) realfilename = "";
        if(!realfilename.equals("")) {
        	System.out.println("path : "+request.getContextPath());
	    	realpath =request.getSession().getServletContext().getRealPath("/");
	    	System.out.println("realpath : "+realpath);
			randfilename = fileupload(part, realpath);
        }
		
		dao mydb = new dao();
		boolean result = false;
		try {
			result = mydb.insertPost(title, content, id, realfilename, randfilename);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result) {
			PrintWriter out = response.getWriter();
		    out.print("<script>");
		    out.print("alert('글이 등록되었습니다.');");
		    out.print("location.href = 'index'");
		    out.print("</script>");
		}
		else {
			PrintWriter out = response.getWriter();
		    out.print("<script>");
		    out.print("alert('등록을 실패했습니다.');");
		    out.print("location.href = 'index'");
		    out.print("</script>");
		}
	}

	String fileupload(Part part, String realpath) {
		String dir = realpath+"upload\\";
        String filename = UUID.randomUUID().toString();
        try {
        	System.out.println("write : "+dir+filename);
			part.write(dir+filename);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error fileupload");
		}

        return filename;
	}
}
