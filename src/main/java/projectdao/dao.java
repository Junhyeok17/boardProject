package projectdao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import projectdto.memberdto;
import projectdto.postdto;
import projectdto.replydto;

public class dao {
	private final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
	private final String DB_URL ="jdbc:mysql://localhost:3306/project?serverTimezone=UTC&characterEncoding=utf8";
	private final String USERNAME = "root";
	private final String PASSWORD = "";

	private java.sql.Connection con = null;
	private static java.sql.Statement stmt = null;
	private static ResultSet rs = null;
	private PreparedStatement pstmt;

	private static int view_rows = 10;
	private static int counts = 10;
	
	public dao() {
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Database 연결 에러");
		}
	}

	public void Close() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Database 연결 에러");
		}
	}
	
	public boolean insertMember(String id, String nickname, String email, String password) {
		String sql = "insert into t_member values (?,?,?,?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, nickname);
			pstmt.setString(3, email);
			pstmt.setString(4, password);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean selectMember(String id, String password) throws SQLException {
		String sql = "select count(*) as cnt from t_member where id = ? and password = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, password);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			int cnt = Integer.parseInt(rs.getString("cnt"));
			if(cnt >= 1) {
				pstmt.close();
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<memberdto> selectMyInfo(String id) throws SQLException {
		String sql = "select id, nickname, email from t_member "
				+ "where id = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		
		ArrayList<memberdto> list = new ArrayList<>();
		while(rs.next()) {
			memberdto data = new memberdto();
			data.setId(rs.getString("id"));
			data.setNickname(rs.getString("nickname"));
			data.setEmail(rs.getString("email"));
			list.add(data);
		}
		pstmt.close();
		return list;
	}
	
	public void modifyMember(String id, String nickname, String email, String password) {
		if(password!=null) {
			String sql = "update t_member set nickname = ?, email = ?, "
					+ "password = ? where id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
				pstmt.setString(2, email);
				pstmt.setString(3, password);
				pstmt.setString(4, id);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			String sql = "update t_member set nickname = ?, email = ? "
					+ "where id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
				pstmt.setString(2, email);
				pstmt.setString(3, id);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void deleteMember(String id) throws SQLException {
		String sql = "delete from t_member where id = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	// 현재 사용자의 비밀번호가 맞는지 확인
	public boolean checkChangePassword(String id, String password) throws SQLException {
		String sql = "select count(*) from t_member "
				+ "where id = ? and password = ?";
		
		boolean result = false;
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, password);
		rs = pstmt.executeQuery();
		int count = 0;
		if(rs.next()) {
			count = rs.getInt(1);
			rs.close();
		}
		if(count > 0) {
			result = true;
		}
		pstmt.close();
		return result;
	}
	
	public boolean insertPost(String title, String content, String id,
			String realfilename, String randfilename) throws SQLException {
		String sql = "select nickname from t_member where id = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		String nickname = null;
		if(rs.next()) {
			nickname = rs.getString("nickname");
		}
		
		sql = "insert into t_board (title, content, id, nickname, realfilename, randfilename, regdate)"
				+ " values (?, ?, ?, ?, ?, ?, now())";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, id);
			pstmt.setString(4, nickname);
			pstmt.setString(5, realfilename);
			pstmt.setString(6, randfilename);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public postdto selectOnePost(String num){
		String sql = "SELECT title, content, nickname, regdate, randfilename "
				+ "FROM t_board where num = ?";
		postdto data = new postdto();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				data.setNum(num);
				data.setTitle(rs.getString("title"));
				data.setContent(rs.getString("content"));
				data.setNickname(rs.getString("nickname"));
				data.setRandfilename(rs.getString("randfilename"));
				data.setRegdate(rs.getString("regdate"));
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	// 전체 게시물 개수
	public int selectPostTotalRecord() {
		int total_pages = 0;
		String sql = "select count(*) from t_board";
		ResultSet pageset = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pageset = pstmt.executeQuery();
			
			if(pageset.next()) {
				total_pages = pageset.getInt(1);
				pageset.close();
			}
		} catch (Exception e) {
			System.out.println("selectPostTotalRecord catch 에러");
		}
		return total_pages;
	}
	
	public String selectPostPageNumber(int tpage) {
		String str = "";
		int total_pages = selectPostTotalRecord();
		int page_count = total_pages / counts + 1;
		
		if(total_pages % counts == 0)
			page_count--;
		if(tpage < 1)
			tpage = 1;
		
		int start_page = tpage - (tpage % view_rows) + 1;
		int end_page = start_page + (counts-1);
		
		if(end_page > page_count)
			end_page = page_count;
		
		if(start_page > view_rows) {
			str += "<li class=\"page-item\"><a class=\"page-link\" href='index?tpage=1&'>First</a></li>";
			str += "<li class=\"page-item\"><a class=\"page-link\" href='index?tpage="
					+ (start_page-view_rows);
			str += "'>Previous</a></li>";
		}
		
		for(int i=start_page; i<=end_page;i++) {
			if(i==tpage)
				str += "<li class=\"page-item active\" aria-current=\"page\">"
						+ "<a class=\"page-link\" href='#'>"+i+"</a></li>";
			else
				str += "<li class=\"page-item\"><a class=\"page-link\" href='index?tpage="
						+i+"'>"+i+"</a></li>";
		}
		if(page_count > end_page) {
			str += "<li class=\"page-item\"><a class=\"page-link\" href='index?tpage="
					+(end_page+1)+">Next</a></li>";
			str += "<li class=\"page-item\"><a class=\"page-link\" href='index?tpage="
					+page_count+">Last</a></li>";
		}
		return str;
	}
	
	public ArrayList<postdto> selectAllPost(int tpage){
		ArrayList<postdto> list = new ArrayList<>();
		
		String sql = "SELECT num, title, content, nickname, regdate "
				+ "FROM t_board "
				+ "order by regdate desc";
		try {
			int absolutepage = (tpage-1)*counts+1;
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				// 첫 번째 페이지 N개만 담기
				rs.absolute(absolutepage);
				int count = 0;
				while(count < counts) {
					postdto data = new postdto();
					data.setNum(rs.getString("num"));
					data.setTitle(rs.getString("title"));
					data.setContent(rs.getString("content"));
					data.setNickname(rs.getString("nickname"));
					data.setRegdate(rs.getString("regdate"));
					list.add(data);
					
					if(rs.isLast())
						break;
					rs.next();
					count++;
				}
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	// 유저와 작성자가 동일한지 확인
	public String isUserWriter(String num, String id) {
		String sql = "SELECT id FROM t_board WHERE num = ?";
		String result = "N";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(id.equals(rs.getString("id")))
					result = "Y";
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("writer : "+result);
		return result;
	}
	
	public void modifyPost(String num, String title, String content, String realfilename, String randfilename) {
		String sql = "update t_board set title = ?, content = ?,"
				+ " realfilename = ?, randfilename = ? "
				+ " where num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, realfilename);
			pstmt.setString(4, randfilename);
			pstmt.setString(5, num);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deletePost(String num) throws SQLException {
		String sql = "delete from t_board where num = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, num);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public void insertReply(String id, String postnum, String content) throws SQLException {
		String sql = "select nickname from t_member where id = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		String nickname = null;
		if(rs.next()) {
			nickname = rs.getString("nickname");
		}
		
		sql = "insert into t_reply (postnum, content, id, nickname, regdate) "
				+ "values (?,?,?,?, now())";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, postnum);
		pstmt.setString(2, content);
		pstmt.setString(3, id);
		pstmt.setString(4, nickname);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public ArrayList<replydto> selectReply(String postnum){
		ArrayList<replydto> list = new ArrayList<>();
		
		String sql = "SELECT num, postnum, content, id, nickname, regdate "
				+ "FROM t_reply "
				+ "WHERE postnum = ? order by regdate desc";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, postnum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				replydto data = new replydto();
				data.setNum(rs.getString("num"));
				data.setPostnum(rs.getString("postnum"));
				data.setContent(rs.getString("content"));
				data.setId(rs.getString("id"));
				data.setNickname(rs.getString("nickname"));
				data.setRegdate(rs.getString("regdate"));
				list.add(data);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void deleteReply(String num) throws SQLException {
		String sql = "delete from t_reply where num = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, num);
		pstmt.executeUpdate();
		pstmt.close();
	}
}
