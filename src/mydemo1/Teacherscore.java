package mydemo1;

public class Teacherscore {
	private int id,teacherID,courseID,departmentID,grade,cClass;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
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

	public Teacherscore(int id, int teacherID, int courseID, int departmentID, int grade, int cClass) {
		super();
		this.id = id;
		this.teacherID = teacherID;
		this.courseID = courseID;
		this.departmentID = departmentID;
		this.grade = grade;
		this.cClass = cClass;
	}
	

}
