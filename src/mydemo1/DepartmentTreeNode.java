package mydemo1;

import org.eclipse.jface.viewers.TreeNode;

public class DepartmentTreeNode extends TreeNode{
	private Department department;
	private CourseTreeNode parent;
	private GradeClassTreeNode[] children;
	

	public DepartmentTreeNode(Department value) {
		super(value);
		this.department =value;
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
		this.children=(GradeClassTreeNode[]) children;
	}


	@Override
	public void setParent(TreeNode parent) {
		// TODO Auto-generated method stub
		this.parent=(CourseTreeNode) parent;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.department.getName();
	}


	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
	

}
