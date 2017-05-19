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
	
    public InitDB(){//���ݿ�������������
    	DBDriver="com.mysql.jdbc.Driver";//����ʵ������
    	url="jdbc:mysql://127.0.0.1:3306/studentscore?useUnicode=true&characterEncoding=utf-8";//���ݿ�ĵ�ַ
    	user="root";//�û���
    	password="chenkaihai";//����
    	try {
			Class.forName(DBDriver);
			connection = (Connection) DriverManager.getConnection(url, user, password);//��������ݿ������
			statement=(Statement) connection.createStatement();//��statement��ִ��SQL���
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void closeDB(){//�ر����ݿ����ӵķ���
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
