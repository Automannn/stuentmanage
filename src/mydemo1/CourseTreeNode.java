package mydemo1;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeNode;

public class CourseTreeNode extends TreeNode{
    private Course course;
    private TreeNode parent;
    private DepartmentTreeNode[] children;
    public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
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
		return this.children!=null&&this.children.length>0;
	}
	@Override
	public void setChildren(TreeNode[] children) {
		// TODO Auto-generated method stub
		this.children=(DepartmentTreeNode[]) children;
	}
	@Override
	public void setParent(TreeNode parent) {
		// TODO Auto-generated method stub
		this.parent=(TreeNode) parent;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.course.getname();
	}
	public CourseTreeNode(Course value) {
		super(value);
		this.course=value;
	}

}
