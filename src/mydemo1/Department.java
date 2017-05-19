package mydemo1;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;

import com.mysql.jdbc.ResultSet;

public class Department implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private LinkedList<Course> coursesList=new LinkedList<>();
	private int id;
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Course> getCoursesList() {
		return coursesList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Department(int id,String name) {
		super();
		this.name = name;
		this.id=id;
	}
	public void addCourse(Course course) {
		coursesList.add(course);
	}                     
	 public static Department getFromDB(String name){
		   ResultSet rSet=InitDB.getInitDb().getrSet("select*from department where name='"+name+"'");
		   try {
			if (rSet.next()) {
				return new Department(rSet.getInt(1),rSet.getString(2));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			return null;
		}	   
	      }
	   public  static Department getFromDB(int id,String name) {
		   ResultSet rSet =InitDB.getInitDb().getrSet("select*from department where id ='"+id+"'and name ='"+name+"'");
		   try {
			if (rSet.next()) {
				return new Department(rSet.getInt(1),rSet.getString(2));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			return null;
		}
	}
	   
	   public  static Department getFromDB(int id) {
		   ResultSet rSet =InitDB.getInitDb().getrSet("select*from department where id ="+id);
		   try {
			if (rSet.next()) {
				return new Department(rSet.getInt(1),rSet.getString(2));
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
				ResultSet rSet=InitDB.getInitDb().getrSet("select*from department where name ='"+this.name+"'");
				System.out.println("i have run1");
				if (!rSet.next()) {
					System.out.println("i have run2");
					String sql="insert into department (name) values('"+this.name+"')";
					InitDB.getInitDb().getStatement().executeUpdate(sql);
					rSet=InitDB.getInitDb().getrSet("select id from department where name ='"+this.name+"'");
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
	   
	
	public void getCoursesListFromDB() {
	   coursesList.clear();
	   String sql="select courseID from department_course where departmentID="+this.id;
	   ResultSet resultSet =InitDB.getInitDb().getrSet(sql);
	   try {
		while (resultSet.next()) {
		   coursesList.add(Course.getFromDB(resultSet.getInt(1)));			
		}
	} catch (SQLException e) {
		// TODO: handle exception
	}
	}
	
	 public int updateToDB() {
			if (this.id!=-1) {
				ResultSet rSet=InitDB.getInitDb().getrSet("select*from department where id ='"+this.id+"'and name='"+this.name+"'");
				try {
					if (rSet.next()) {
						return -3;	
						}
					String sql = "update department  set name='"+this.name+"'where id="+this.id;
					return InitDB.getInitDb().getStatement().executeUpdate(sql);
				} catch (SQLException e) {
					// TODO: handle exception
					return -1;
				}
			}else {
				return -2;
			}
		}
	   
	

	public Department(String name) {
		super();
		this.name = name;
	}

}
