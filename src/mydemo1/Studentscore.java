package mydemo1;

import java.sql.Date;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;

public class Studentscore {
	private int id, studentID,courseID;
    private long score;
    private Date upDatetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public Date getUpDatetime() {
		return upDatetime;
	}
	public void setUpDatetime(Date upDatetime) {
		this.upDatetime = upDatetime;
	}
	public Studentscore(int studentID, int courseID, long score) {
		super();
		this.studentID = studentID;
		this.courseID = courseID;
		this.score = score;
	}
	public Studentscore(int id, int studentID, int courseID, long score) {
		super();
		this.id = id;
		this.studentID = studentID;
		this.courseID = courseID;
		this.score = score;
	}
	public Studentscore( int studentID, int courseID, long score, Date upDatetime) {
		super();
		this.studentID = studentID;
		this.courseID = courseID;
		this.score = score;
		this.upDatetime = upDatetime;
	}
	 public static Studentscore getFromDB(int id){
		   ResultSet rSet=InitDB.getInitDb().getrSet("select*from student_course where studentID="+id);
		   try {
			if (rSet.next()) {
				return new Studentscore(rSet.getInt(2), rSet.getInt(3), rSet.getLong(4),rSet.getDate(5));
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
