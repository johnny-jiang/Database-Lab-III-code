package labIII;

import java.sql.Connection;  
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;  

public class DB {
	 public static final String url = "jdbc:mysql://localhost/student";  
	 public static final String name = "com.mysql.jdbc.Driver";  
	 public static final String user = "root";  
	 public static final String password = "";  
	  
	 public Connection conn = null;  
	 public PreparedStatement pst = null;  
	  
	 public DB(String sql) {
		 try {  
			    Class.forName(name);//指定连接类型  
	            conn = DriverManager.getConnection(url, user, password);//获取连接  
	            pst = conn.prepareStatement(sql);//准备执行语句  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	 public void close() {  
		 try {  
	            this.conn.close();  
	           // this.pst.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }
	 public void showMetaData() throws Exception
	 {
		 DatabaseMetaData dbmd = conn.getMetaData();        //获得所连接数据库的属性信息
	     System.out.println("JDBC驱动程序："+dbmd.getDriverName()+"，"+dbmd.getDriverVersion()
	            +"\nJDBC URL："+dbmd.getURL()+"\n数据库："+dbmd.getDatabaseProductName()
	            +"，版本："+dbmd.getDatabaseProductVersion()+"，用户名："+dbmd.getUserName()+"\n");
	 }

}
