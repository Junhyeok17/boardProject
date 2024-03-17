<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    String userId = (String) session.getAttribute("id");
	if(userId==null) userId="";
	System.out.println(userId);
    if (userId.equals("")) {
        out.print("notLoggedIn"); // 현재 비로그인 상태를 나타내는 값 반환
    } else {
        out.print("loggedIn"); // 현재 로그인 상태를 나타내는 값 반환
    }
%>