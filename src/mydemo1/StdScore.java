package mydemo1;

import java.sql.SQLException;

import org.eclipse.jface.viewers.TreeNode;

import com.mysql.jdbc.ResultSet;

public class StdScore extends TreeNode{
	private Student student;
	private GradeClassTreeNode parent;
	private float score;

	public StdScore(Student value) {
		super(value);
		this.student=value;
	}

	@Override
	public TreeNode[] getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return this.parent;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean hasChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setChildren(TreeNode[] children) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void setParent(TreeNode parent) {
		// TODO Auto-generated method stub
		this.parent=(GradeClassTreeNode) parent;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
  
	
public float getScorefromDB() {
		String sql="select score from student_course where studentID="+this.getStudent().getId();
		ResultSet rSet=InitDB.getInitDb().getrSet(sql);
		try {
			while (rSet.next()) {
				this.score=rSet.getInt(1);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	
		return score;
	}

public  static Student getFromDB(int id) {
	   ResultSet rSet =InitDB.getInitDb().getrSet("select*from student where id ="+id);
	   try {
		if (rSet.next()) {
			return new Student(rSet.getInt(1), rSet.getString(2), rSet.getInt(3), rSet.getInt(4), rSet.getInt(5), rSet.getString(6), rSet.getString(7));
		}else {
			return null;
		}
	} catch (SQLException e) {
		// TODO: handle exception
		return null;
	}
}

@Override
public boolean equals(Object object) {
	// TODO Auto-generated method stub
	return super.equals(object);
}

@Override
public int hashCode() {
	// TODO Auto-generated method stub
	return super.hashCode();
}

public int updateToDB(int studentID) {
	if (!"".equals(this.score)&&this.score>=0) {
		ResultSet rSet=InitDB.getInitDb().getrSet("select*from student_course where score ="+this.score);
		try {
			while (rSet.next()) {
				return -3;
			}
			String sql = "update student_course  set score="+this.score;
			return InitDB.getInitDb().getStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO: handle exception
			return -1;
		}
	}else {
		return -2;
	}
}

  public void setScore(float score) {
	this.score=score;
}
}
