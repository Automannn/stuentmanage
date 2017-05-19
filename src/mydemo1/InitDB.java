package mydemo1;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class InitDB {
	private String DBDriver;
	private String url;
	private String user;
	private String password;
	private Connection connection;
	private Statement statement;
	private ResultSet rSet;
	private static final InitDB INIT_DB=new InitDB();
	
	
	public Connection getConnection() {
		return this.connection;
	}
	public Statement getStatement() {
		return statement;
	}
	public ResultSet getrSet(String sql) {
		if (sql.toLowerCase().indexOf("select")!=-1) {
			try {
				rSet=(ResultSet) statement.executeQuery(sql);
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return rSet;
	}
	public static InitDB getInitDb() {
		return INIT_DB;
	}
	
    public InitDB(){//数据库连接驱动方法
    	DBDriver="com.mysql.jdbc.Driver";//驱动实例对象
    	url="jdbc:mysql://127.0.0.1:3306/studentscore?useUnicode=true&characterEncoding=utf-8";//数据库的地址
    	user="root";//用户名
    	password="chenkaihai";//密码
    	try {
			Class.forName(DBDriver);
			connection = (Connection) DriverManager.getConnection(url, user, password);//获得与数据库的链接
			statement=(Statement) connection.createStatement();//用statement来执行SQL语句
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void closeDB(){//关闭数据库连接的方法
    	try {
			rSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    }
}
