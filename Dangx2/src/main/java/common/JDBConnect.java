package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBConnect {
    public Connection con;
    //public Statement stmt;  
    public PreparedStatement psmt;  
    public ResultSet rs;

    // 기본 생성자
    public JDBConnect() {
    	connect();
    }
    
    // 연결
    public void connect() {
    	try {
            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB에 연결
            String url = "jdbc:mysql://localhost/dangx2?serverTimezone=Asia/Seoul";  
            String id = "root";
            String pwd = "1234";
//            String url = "jdbc:mysql://192.168.0.187:3306/dangx2?serverTimezone=Asia/Seoul";  
//            String id = "dangx2";
//            String pwd = "1234";
            con = DriverManager.getConnection(url, id, pwd);

            //System.out.println("JDBC 연결 성공");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 상태확인, 재연결
    public void checkConnect() {
    	try {
            if (
            		rs == null || 
            		//stmt != null || 
            		psmt != null || 
            		con != null
        		) {
            	connect();
            }
            //System.out.println("JDBC 재연결 성공");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 연결 해제(자원 반납)
    public void close() {
        try {
            if (rs != null) rs.close(); 
            //if (stmt != null) stmt.close();
            if (psmt != null) psmt.close();
            if (con != null) con.close(); 

            //System.out.println("JDBC 해제 성공");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}