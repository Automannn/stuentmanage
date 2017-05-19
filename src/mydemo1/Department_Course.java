package mydemo1;

public class Department_Course {
	private int id,departmentID,courseID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public Department_Course(int id, int departmentID, int courseID) {
		super();
		this.id = id;
		this.departmentID = departmentID;
		this.courseID = courseID;
	}

}
