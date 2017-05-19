package mydemo1;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeNode;

import com.mysql.jdbc.ResultSet;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.swing.internal.plaf.basic.resources.basic;

public class ScoreTchData extends TreeNode{
	private Teacher teacher ;
	private ArrayList<CourseTreeNode>courseList=new ArrayList<>();
	private ArrayList<DepartmentTreeNode>departmetList=new ArrayList<>();
	private ArrayList<GradeClassTreeNode>gradeCllassList=new ArrayList<>();
	private ArrayList<StdScore>stdScoreList=new ArrayList<>();
	private TreeNode parent;
	private CourseTreeNode children[];

	public ScoreTchData(Teacher teacher) {
		super(null);
		System.out.println(departmetList.size());
		this.teacher=teacher;
		this.setCourseList(null);
		for (CourseTreeNode ctn:courseList) {
			System.out.println(courseList.size());
			this.setDepartmentList(ctn);
			System.out.println(departmetList.size());
			ctn.setParent(this);
			ctn.setChildren(departmetList.toArray(new DepartmentTreeNode[departmetList.size()]));
			for (DepartmentTreeNode dtn:departmetList) {
				System.out.println(departmetList.size());
				System.out.println(dtn.getDepartment().getId());
				this.setGradeCllassList(ctn,dtn);
				
				dtn.setParent(ctn);
				dtn.setChildren(gradeCllassList.toArray(new GradeClassTreeNode[gradeCllassList.size()]));
				for (GradeClassTreeNode gtn:gradeCllassList) {
					this.setStdScoreList(ctn);
					gtn.setParent(dtn);
					gtn.setChildren(stdScoreList.toArray(new StdScore[stdScoreList.size()]));
				}
				
			}
		}
	}

	public ArrayList<CourseTreeNode> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<CourseTreeNode> courseList) {
		this.courseList.clear();
	    String sql="select courseID from teacher_course where teacherID="+this.teacher.getId();
	    ResultSet rSet =InitDB.getInitDb().getrSet(sql);
	    try {
			while (rSet.next()) {
				this.courseList.add(new CourseTreeNode(Course.getFromDB(rSet.getInt(1))));
				System.out.println("i have run 1");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<DepartmentTreeNode> getDepartmetList() {
		return departmetList;
	}

	public void setDepartmentList(CourseTreeNode courseTreeNode) {
		this.departmetList.clear();
		int courseID=courseTreeNode.getCourse().getId();
		String sql="select departmentID from department_course where courseID="+courseID;
		ResultSet rSet=InitDB.getInitDb().getrSet(sql);
		try {
			while (rSet.next()) {
				this.departmetList.add(new DepartmentTreeNode(Department.getFromDB(rSet.getInt(1)))); 
				System.out.println("i have run 2");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<GradeClassTreeNode> getGradeCllassList() {
		return gradeCllassList;
	}

	public void setGradeCllassList(CourseTreeNode courseTreeNode,DepartmentTreeNode departmentTreeNode) {
		this.gradeCllassList.clear();
		int courseID =courseTreeNode.getCourse().getId();
		System.out.println("课程代码是："+courseID);
		int departmentID=departmentTreeNode.getDepartment().getId();
		System.out.println("专业代码是："+departmentID);
		String sql ="select grade,class from teacher_course where courseID='"+courseID+"'and departmentID='"+departmentID+"'and teacherID="+this.teacher.getId();
	    
		ResultSet rSet=InitDB.getInitDb().getrSet(sql);
		try {
			while (rSet.next()) {
				this.gradeCllassList.add(new GradeClassTreeNode(new GradeClass(departmentID, rSet.getInt(1),rSet.getInt(2))));
				System.out.println("i have run 3");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public ArrayList<StdScore> getStdScoreList() {
		return stdScoreList;
	}

	public void setStdScoreList(CourseTreeNode courseTreeNode) {
		this.stdScoreList.clear();
		int courseID =courseTreeNode.getCourse().getId();
		
		String sql ="select*from student_course where courseID="+courseID;
		ResultSet rSet=InitDB.getInitDb().getrSet(sql);
		try {
			while (rSet.next()) {
				stdScoreList.add(new StdScore(Student.getFromDB(rSet.getInt(2))));
				System.out.println("i have run 4");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TreeNode[] getChildren() {
		// TODO Auto-generated method stub
		return this.children;
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
		System.out.println("hasChildren:"+this.children!=null&&this.children.length>0?true:false);
		return this.children!=null&&this.children.length>0;
	}

	@Override
	public void setChildren(TreeNode[] children) {
		// TODO Auto-generated method stub
		this.children=(CourseTreeNode[]) children;
	}

	@Override
	public void setParent(TreeNode parent) {
		// TODO Auto-generated method stub
		this.parent=parent;
	}



}
