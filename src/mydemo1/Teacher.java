package mydemo1;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;

public class Teacher implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String sex;
	private int age;
	private int departmentID;
	private String address;
	private String pic;
	private String intro;
	public  Department department;
	private ArrayList<Course>courseList=new ArrayList<Course>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getDepartmentID() {
		return departmentID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public ArrayList<Course> getCourseList() {
		return courseList;
	}
	public Teacher(int id, String name, String tchSex, int age, int departmentID, String address, String pic,
			String intro) {
		super();
		this.id = id;
		this.name = name;
		this.sex = tchSex;
		this.age = age;
		this.departmentID = departmentID;
		this.address = address;
		this.pic = pic;
		this.intro = intro;
	}
	public void setDepartment(int departmentID) {
		String sql="select *from department where id="+departmentID;
		ResultSet rSet=InitDB.getInitDb().getrSet(sql);
		try {
			if (rSet.next()) {
				this.department=new Department(rSet.getInt(1),rSet.getString(2));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void getCoursesListFromDB() {
		   courseList.clear();
		   String sql="select courseID from department_course where departmentID="+this.departmentID;
		   ResultSet resultSet =InitDB.getInitDb().getrSet(sql);
		   try {
			while (resultSet.next()) {
			   courseList.add(Course.getFromDB(resultSet.getInt(1)));			
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		}
	
	public int  insertToDB() {
		try {
			ResultSet rSet=InitDB.getInitDb().getrSet("select*from teacher where name ='"+this.name +"'and id ='"+this.id +"'");
			if (!rSet.next()) {
				String sql="insert into teacher (id,name,sex,age,departmentID,address,pic) values('"+this.id+"','"+this.name+"','"+this.sex+"','"+this.age+"','"+this.departmentID+"','"+this.address+"','"+this.pic+"')";
				InitDB.getInitDb().getStatement().executeUpdate(sql);
				rSet=InitDB.getInitDb().getrSet("select id from teacher where name ='"+this.name+"'and id ='"+this.id+"'");
				rSet.next();
				return rSet.getInt(1);
			}else {
				return rSet.getInt(1);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
	}
	
	public static Teacher getFromDB(int id){
		   ResultSet rSet=InitDB.getInitDb().getrSet("select*from teacher where id="+id);
		   try {
			if (rSet.next()) {
				return new Teacher(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getInt(4), rSet.getInt(5), rSet.getString(6), rSet.getString(7), rSet.getString(8));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}	   
	      }
}
