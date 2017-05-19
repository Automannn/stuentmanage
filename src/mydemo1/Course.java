package mydemo1;

import java.io.Serializable;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;

public class Course implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private int id;
	public Course(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public Course(int courseID,String name, String type) {
		super();
		this.name = name;
		this.type = type;
		this.id=courseID;
	}
	private float score;
	private int techerID=-1;
	public String  getname() {
		return name;
	}
	public void setname(String name) {
		this.name =name;
	}
   public String  gettype() {
	return type;		
	
}
   public int getId() {
	return id;
}
public void setId(int courseID) {
	this.id = courseID;
}
public void settype(String type) {
	this.type =type;
}
   public float getscore() {
	return score;
}
   public void setscore(float score) {
	this.score=score;
}
   public int gettecherID() {
	return techerID;
}
   public void settecherID(int teacherID) {
	this.techerID=teacherID;
}
   public static Course getFromDB(int courseID){
	   ResultSet rSet=InitDB.getInitDb().getrSet("select*from course where id="+courseID);
	   try {
		if (rSet.next()) {
			return new Course(rSet.getInt(1),rSet.getString(2),rSet.getString(3));
		}else {
			return null;
		}
	} catch (SQLException e) {
		// TODO: handle exception
		return null;
	}	   
      }
   public  static Course getFromDB(String name,String type) {
	   ResultSet rSet =InitDB.getInitDb().getrSet("select*from course where name ='"+name+"'and type ='"+type+"'");
	   try {
		if (rSet.next()) {
			return new Course(rSet.getInt(1),rSet.getString(2), rSet.getString(3));
		}else {
			return null;
		}
	} catch (SQLException e) {
		// TODO: handle exception
		return null;
	}
}
   
   public  static Course getFromDB(String name) {
	   ResultSet rSet =InitDB.getInitDb().getrSet("select*from course where name ='"+name+"'");
	   try {
		if (rSet.next()) {
			return new Course(rSet.getInt(1),rSet.getString(2), rSet.getString(3));
		}else {
			return null;
		}
	} catch (SQLException e) {
		// TODO: handle exception
		return null;
	}
}
   public int  insertToDB() {
	try {
		ResultSet rSet=InitDB.getInitDb().getrSet("select*from course where name ='"+this.name +"'and type ='"+this.type +"'");
		if (!rSet.next()) {
			String sql="insert into course (name,type) values('"+this.name+"','"+this.type+"')";
			InitDB.getInitDb().getStatement().executeUpdate(sql);
			rSet=InitDB.getInitDb().getrSet("select id from course where name ='"+this.name+"'and type ='"+this.type+"'");
			rSet.next();
			return rSet.getInt(1);
		}else {
			return rSet.getInt(1);
		}
	} catch (SQLException e) {
		// TODO: handle exception
		return -1;
	}
}
      public int updateToDB() {
		if (this.id!=-1) {
			ResultSet rSet=InitDB.getInitDb().getrSet("select*from course where name ='"+this.name+"'and type='"+this.type+"'");
			try {
				if (rSet.next()) {
					return -3;	
					}
				String sql = "update course set name='"+this.name+"',type='"+this.type+"'where id="+this.id;
				return InitDB.getInitDb().getStatement().executeUpdate(sql);
			} catch (SQLException e) {
				// TODO: handle exception
				return -1;
			}
		}else {
			return -2;
		}
	}
   
}

