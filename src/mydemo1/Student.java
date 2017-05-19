package mydemo1;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;

public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int departmentID;
	private int grade;
	private int cClass;
	private String pic;
	private String interested;
	private float score;
	private Department department;
	private ArrayList<Course>courseList=new ArrayList<>();
	public Student(int id, String name, int departmentID, int grade, int cClass, String pic, String interested) {
		super();
		this.id = id;
		this.name = name;
		this.departmentID = departmentID;
		this.grade = grade;
		this.cClass = cClass;
		this.pic = pic;
		this.interested = interested;
	}
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
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getcClass() {
		return cClass;
	}
	public void setcClass(int cClass) {
		this.cClass = cClass;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getInterested() {
		return interested;
	}
	public void setInterested(String interested) {
		this.interested = interested;
	}
	public ArrayList<Course> getCourseList() {
		return courseList;
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
	public void setCoursesListFromDB() {
		   courseList.clear();
		   String sql="syelect courseID from department_course where departmentID="+this.id;
		   ResultSet resultSet =InitDB.getInitDb().getrSet(sql);
		   try {
			while (resultSet.next()) {
			   courseList.add(Course.getFromDB(resultSet.getInt(1)));			
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		}

	public static Student getFromDB(int id){
		   ResultSet rSet=InitDB.getInitDb().getrSet("select*from student where id="+id);
		   try {
			if (rSet.next()) {
				return new Student(id, rSet.getString(2), rSet.getInt(3), rSet.getInt(4), rSet.getInt(5), rSet.getString(6), rSet.getString(7));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			return null;
		}	   
	      }
	public float getScoreFromDB(int id){
		   ResultSet rSet=InitDB.getInitDb().getrSet("select*from student_course where studentID="+id);
		   try {
			while(rSet.next()) {
				System.out.println("i have  run run run!");
				return rSet.getFloat(4);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return 0F;	   
	      }
	
	 public int  insertToDB() {
			try {
				ResultSet rSet=InitDB.getInitDb().getrSet("select*from student where name ='"+this.name +"'and id ='"+this.id +"'");
				System.out.println("i have run 1");
				if (!rSet.next()) {
					System.out.println("i have run 2");
					String sql="insert into student (id,name,departmentID,grade,class,pic,interested) values('"+this.id+"','"+this.name+"','"+this.departmentID+"','"+this.grade+"','"+this.cClass+"','"+this.pic+"','"+this.interested+"')";
					InitDB.getInitDb().getStatement().executeUpdate(sql);
					ResultSet rSet2=InitDB.getInitDb().getrSet("select id from student where name ='"+this.name+"'and id ='"+this.id+"'");
					rSet2.next();
					return rSet2.getInt(1);
				}else {
					return rSet.getInt(1);
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
				return -1;
			}
		}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	 
}

