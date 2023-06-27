package com.test.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {
	public static Connection getConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=Asia/Seoul", "root", "1q2w3e");
		}catch(Exception e){
			System.out.println("Exception[Connection]: "+e.getMessage());
		}
		
		return null;
	}
	public static void close(PreparedStatement pstmt, Connection conn) {
		if(pstmt != null) {
			try {
				if(!pstmt.isClosed()) pstmt.close(); //pstmt가 닫혀있지 않으면 닫기
			}catch(Exception e) {
				System.out.println("Exception[pstmt]: "+e.getMessage());
			}
		}
		if(conn != null) {
			try {
				if(!conn.isClosed()) conn.close();
			}catch(Exception e) {
				System.out.println("Exception[conn]: "+e.getMessage());
			}
		}
	}
	public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if(rs != null) {
			try {
				if(!rs.isClosed()) rs.close();
			}catch(Exception e) {
				System.out.println("Exception[rs]: "+e.getMessage());
			}
		}
		if(pstmt != null) {
			try {
				if(!pstmt.isClosed()) pstmt.close(); //pstmt가 닫혀있지 않으면 닫기
			}catch(Exception e) {
				System.out.println("Exception[pstmt]: "+e.getMessage());
			}
		}
		if(conn != null) {
			try {
				if(!conn.isClosed()) conn.close();
			}catch(Exception e) {
				System.out.println("Exception[conn]: "+e.getMessage());
			}
		}
	}
	
}
                                  