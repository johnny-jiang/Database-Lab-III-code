package labIII;

//import QueryJFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class QueryJFrame extends JFrame {
	private Connection conn; // 数据库连接对象
	String driver = "com.mysql.jdbc.Driver"; // 指定MySQL JDBC驱动程序
	String url = "jdbc:mysql://localhost/student?user=root";

	// 构造方法，driver指定JDBC驱动程序，url指定数据库URL，table指定数据库中表名
	public QueryJFrame(String table) throws ClassNotFoundException,
			SQLException {
		super(table);
		this.setBounds(300, 240, 700, 320);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Class.forName(driver); // 指定JDBC驱动程序
		this.conn = DriverManager.getConnection(url); // 返回数据库连接对象
		JTable jtable = query(table); // 执行数据查询，创建表格
		this.getContentPane().add(new JScrollPane(jtable));// 滚动窗格（包含表格）添加到框架内容窗格中部
		this.setVisible(true);
		pack();
	}

	// 执行数据查询的SELECT语句，将table表的所有列名及其中全部数据显示在JTable中
	public JTable query(String table) throws SQLException {
		DefaultTableModel tablemodel = new DefaultTableModel(); // 表格模型
		String sql = "SELECT * FROM " + table + ";";
		Statement stmt = this.conn.createStatement();// 1003,1007); //创建语句对象
		ResultSet rset = stmt.executeQuery(sql); // 执行数据查询SELECT语句

		// 获得表中列数及各列名，作为表格组件的标题
		ResultSetMetaData rsmd = rset.getMetaData(); // 返回表属性对象
		int count = rsmd.getColumnCount(); // 获得列数
		for (int j = 1; j <= count; j++)
			// 将各列名添加到表格模型作为标题，列序号≥1
			tablemodel.addColumn(rsmd.getColumnLabel(j));

		// 将结果集中各行数据添加到表格模型，一次遍历
		Object[] columns = new Object[count]; // 创建列对象数组，数组长度为列数
		while (rset.next()) // 迭代遍历结果集，从前向后访问每行
		{
			for (int j = 1; j <= columns.length; j++)
				// 获得每行各列值
				columns[j - 1] = rset.getString(j);
			tablemodel.addRow(columns); // 表格模型添加一行，参数指数各列值
		}
		rset.close();
		stmt.close();
		return new JTable(tablemodel); // 创建表格，指定表格模型
	}

	public static void main(String args[]) throws ClassNotFoundException,
			SQLException {
		String driver = "com.mysql.jdbc.Driver"; // 指定MySQL JDBC驱动程序
		String url = "jdbc:mysql://localhost/student?user=root";
		new QueryJFrame("User");
	}

	public void finalize() throws SQLException // 析构方法，关闭数据库连接
	{
		this.conn.close();
	}

}
