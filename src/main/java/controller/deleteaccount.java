package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projectdao.dao;

@WebServlet("/deleteaccount")
public class deleteaccount extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		if(id==null) id="";
		
		dao mydb = new dao();
		PrintWriter out = response.getWriter();
        try {
            // ����� ���� ���� ���� ��
            // ���⿡ ����� ���� ���� �ڵ� �ۼ�
			mydb.deleteMember(id);
			session.invalidate();
            out.print("success");
        } catch (Exception e) {
            // ����� ���� ���� ���� ��
            e.printStackTrace();
            out.print("fail");
        }
		mydb.Close();
	}
}
