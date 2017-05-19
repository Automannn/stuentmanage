package mydemo1;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.widgets.Tree;

public class GradeClassTreeNode extends TreeNode{
    private GradeClass gradeClass;
    private DepartmentTreeNode parent;
    private StdScore[] children;
	public GradeClassTreeNode(GradeClass value) {
		super(value);
		this.gradeClass=value;
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
	public String toString() {
		// TODO Auto-generated method stub
		return this.gradeClass.getGrade()+"¼¶"+this.gradeClass.getcClass()+"°à";
	}
	@Override
	public boolean hasChildren() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setChildren(TreeNode[] children) {
		// TODO Auto-generated method stub
		this.children=(StdScore[]) children;
	}
	@Override
	public void setParent(TreeNode parent) {
		// TODO Auto-generated method stub
		this.parent=(DepartmentTreeNode) parent;
	}
	public GradeClass getGradeClass() {
		return gradeClass;
	}
	public void setGradeClass(GradeClass gradeClass) {
		this.gradeClass = gradeClass;
	}
	
}
