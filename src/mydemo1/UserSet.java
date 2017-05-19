package mydemo1;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import mydemo1.InitDB;

public class UserSet implements Serializable {    //检验用户的合法性

	private HashSet<User>usersSet=null;
	public Connection connection=null;//与数据库建立连接的驱动
	private Statement statement; 
	private InitDB db;  //连接数据库的驱动类
	public UserSet(){
		super();
		usersSet=new HashSet<User>();
		db=InitDB.getInitDb();  //实例化驱动类
		connection=db.getConnection();//获得连接
		statement=db.getStatement();//获得连接状态
		ResultSet resultSet=db.getrSet("select*from users");  //获得数据库中的结果
		try {
			while (resultSet.next()) {
				User user=new User(resultSet.getString("name"), resultSet.getString("password"), resultSet.getInt("job"));  //获得所有的用户信息
				usersSet.add(user);	//将所有的用户信息添加到合法用户集		
				}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  if (usersSet==null||usersSet.size()==0) {   //初始化管理员账号
			usersSet.add(new User("admin", "123456", 2));
		}
		
	}
	public boolean isValid(User user) {   //判断合法用户集的方法
		boolean userValid=false;
		if (usersSet.contains(user)) {
			userValid=true;
		}
		return userValid;
	}

   public void addUser(User user) {  //添加用户的方法
	if (user!=null&&!usersSet.contains(user)) {//保证至少在管理员存在的情况进行其它操作
		String sqlStr="insert into users (name,password,job)"+"values('"+user.getName()+"',"+user.getPassword()+","+user.getJob()+")";
		try {
			statement.executeUpdate(sqlStr);//刷新数据库
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
   public void modifyUser(User oldUser,User newUser) {//更新用户信息
	String updateStr="update users set name='"+newUser.getName()+"',password='"+newUser.getPassword()+"',job="+newUser.getJob()+"where name='"+oldUser.getName()+"'and job="+oldUser.getJob();
	try {
		statement.executeUpdate(updateStr);//更新数据库
	} catch (SQLException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}
   public void delUser(User user) {//删除用户信息
	String delStr="delete*from users where name ='"+user.getName()+"'";
	try {
		statement.executeUpdate(delStr);//更新数据库
	} catch (SQLException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}
    public User findUser(String name) {//查找用户
		User user = null;
		String sql="select * from users where name ='"+name +"'";
		ResultSet rSet;
		try {
			rSet=db.getrSet(sql);//得到所有的用户结果集
			if (rSet.next()) {
				user=new User(rSet.getString(1), rSet.getString(2),rSet.getInt(3));//生成被查找的用户对象实例
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