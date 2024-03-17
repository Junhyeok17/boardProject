package controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/modifypost")
@MultipartConfig
public class modifypost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("postnum");
		
		dao mydb = new dao();
		
		postdto curpost = mydb.selectOnePost(num);
		request.setAttribute("curpost", curpost);
		
		mydb.Close();

		RequestDispatcher dispatcher = request.getRequestDispatcher("/modifypost.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
        
		String num = request.getParameter("postnum");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

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

		mydb.modifyPost(num, title, content, realfilename, randfilename);
		
		postdto curpost = mydb.selectOnePost(num);
		request.setAttribute("curpost", curpost);

		String writerCheck = mydb.isUserWriter(num, id);
		request.setAttribute("writerCheck", writerCheck);
		
		mydb.Close();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/post.jsp");
		dispatcher.forward(request, response);
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
