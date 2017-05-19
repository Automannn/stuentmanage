package mydemo1;

import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;

public class GradeClass {
	private int id,departmentID,grade,cClass;

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

	public GradeClass(int departmentID, int grade) {
		super();
		this.departmentID = departmentID;
		this.grade = grade;
	}

	public GradeClass(int departmentID,int grade,int cClass) {
		super();
		this.cClass = cClass;
		this.departmentID=departmentID;
		this.grade=grade;
	}
	
	public int  insertToDB_grade() {
		try {
			ResultSet rSet=InitDB.getInitDb().getrSet("select*from department_grade_class where departmentID ='"+this.departmentID +"'and grade='"+this.grade +"'");
			if (!rSet.next()) {
				String sql="insert into department_grade_class(departmentID,grade) values('"+this.departmentID+"','"+this.grade+"')";
				InitDB.getInitDb().getStatement().executeUpdate(sql);
				rSet=InitDB.getInitDb().getrSet("select id from department_grade_class where departmentID ='"+this.departmentID+"'and grade ='"+this.grade+"'");
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
	
	public int  insertToDB_class() {
		try {
			ResultSet rSet=InitDB.getInitDb().getrSet("select*from department_grade_class where departmentID ='"+this.departmentID +"'and grade='"
		
					+this.grade +"'and class='"+this.cClass+"'");
			if (!rSet.next()) {
				String sql="insert into department_grade_class(departmentID,grade,class) values('"+this.departmentID+"','"+this.grade+"','"+this.cClass+"')";
				InitDB.getInitDb().getStatement().executeUpdate(sql);
				rSet=InitDB.getInitDb().getrSet("select id from department_grade_class where departmentID ='"+this.departmentID+"'and grade ='"+this.grade+"'and class='"+this.cClass+"'");
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
}
