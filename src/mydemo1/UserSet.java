package mydemo1;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import mydemo1.InitDB;

public class UserSet implements Serializable {    //�����û��ĺϷ���

	private HashSet<User>usersSet=null;
	public Connection connection=null;//�����ݿ⽨�����ӵ�����
	private Statement statement; 
	private InitDB db;  //�������ݿ��������
	public UserSet(){
		super();
		usersSet=new HashSet<User>();
		db=InitDB.getInitDb();  //ʵ����������
		connection=db.getConnection();//�������
		statement=db.getStatement();//�������״̬
		ResultSet resultSet=db.getrSet("select*from users");  //������ݿ��еĽ��
		try {
			while (resultSet.next()) {
				User user=new User(resultSet.getString("name"), resultSet.getString("password"), resultSet.getInt("job"));  //������е��û���Ϣ
				usersSet.add(user);	//�����е��û���Ϣ��ӵ��Ϸ��û���		
				}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  if (usersSet==null||usersSet.size()==0) {   //��ʼ������Ա�˺�
			usersSet.add(new User("admin", "123456", 2));
		}
		
	}
	public boolean isValid(User user) {   //�жϺϷ��û����ķ���
		boolean userValid=false;
		if (usersSet.contains(user)) {
			userValid=true;
		}
		return userValid;
	}

   public void addUser(User user) {  //����û��ķ���
	if (user!=null&&!usersSet.contains(user)) {//��֤�����ڹ���Ա���ڵ����������������
		String sqlStr="insert into users (name,password,job)"+"values('"+user.getName()+"',"+user.getPassword()+","+user.getJob()+")";
		try {
			statement.executeUpdate(sqlStr);//ˢ�����ݿ�
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
   public void modifyUser(User oldUser,User newUser) {//�����û���Ϣ
	String updateStr="update users set name='"+newUser.getName()+"',password='"+newUser.getPassword()+"',job="+newUser.getJob()+"where name='"+oldUser.getName()+"'and job="+oldUser.getJob();
	try {
		statement.executeUpdate(updateStr);//�������ݿ�
	} catch (SQLException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}
   public void delUser(User user) {//ɾ���û���Ϣ
	String delStr="delete*from users where name ='"+user.getName()+"'";
	try {
		statement.executeUpdate(delStr);//�������ݿ�
	} catch (SQLException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}
    public User findUser(String name) {//�����û�
		User user = null;
		String sql="select * from users where name ='"+name +"'";
		ResultSet rSet;
		try {
			rSet=db.getrSet(sql);//�õ����е��û������
			if (rSet.next()) {
				user=new User(rSet.getString(1), rSet.getString(2),rSet.getInt(3));//���ɱ����ҵ��û�����ʵ��
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return user;
	}
      public HashSet<User> getUsersSet() {
		return usersSet;
	}
}