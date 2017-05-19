package mydemo1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeColumn;

import com.mysql.jdbc.ResultSet;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.swt.widgets.Composite;

import java.sql.SQLException;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TchScoreStat{
	private static class ViewerLabelProvider extends LabelProvider {
		public Image getImage(Object element) {
			return super.getImage(element);
		}
		public String getText(Object element) {
			return super.getText(element);
		}
	}
	private static class TreeContentProvider implements ITreeContentProvider {
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}
		public Object[] getChildren(Object parentElement) {
			return new Object[] { "item_0", "item_1", "item_2" };
		}
		public Object getParent(Object element) {
			return null;
		}
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}
	}
private  Shell shell;
private ScoreTchData scoreTchData;
	public TchScoreStat() {
		super();
		shell=new Shell();
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		scoreTchData=new ScoreTchData(null);
		scoreTchData.setParent(null);
		scoreTchData.setChildren(scoreTchData.getCourseList().toArray(new CourseTreeNode[scoreTchData.getCourseList().size()]));
		
		TreeViewer treeViewer = new TreeViewer(shell, SWT.BORDER);
		treeViewer.setInput(scoreTchData);
		Tree tree = treeViewer.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		
		
		TreeColumn trclmnNewColumn_1 = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn_1.setWidth(100);
		
		TreeColumn trclmnNewColumn = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn.setWidth(100);
		trclmnNewColumn.setText("\u4EBA\u6570");
		
		TreeColumn trclmnNewColumn_2 = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn_2.setWidth(100);
		trclmnNewColumn_2.setText("\u5E73\u5747\u5206");
		
		TreeColumn trclmnNewColumn_3 = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn_3.setWidth(100);
		trclmnNewColumn_3.setText("\u6700\u9AD8\u5206");
		
		TreeColumn trclmnNewColumn_5 = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn_5.setWidth(100);
		trclmnNewColumn_5.setText("\u6700\u4F4E\u5206");
		
		TreeColumn trclmnNewColumn_4 = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn_4.setWidth(100);
		trclmnNewColumn_4.setText("\u53CA\u683C\u4EBA\u6570");
		treeViewer.setLabelProvider(new ITableLabelProvider() {
			private int sumpeople;
			private float sumscore;
			private float maxscore;
			private float minscore;
			private int validpeople;
			void stataCourse(CourseTreeNode node){
				   sumpeople=0;
				   sumscore=0.0f;
				   maxscore=-1;
				   minscore=1000;
				   validpeople=0;
				   int courseID=node.getCourse().getId();
				   String sql="select COUNT(DISTINCT stuent.id),SUM(student_course.score),MAX(student_course.score),"
				   		+ "MIN(student_course.score)"+"COUNT(student_course.score>=60) from student,student_course "
				   				+ "where courseID="+courseID+"ande studentID=student.id";
				   ResultSet rSet=InitDB.getInitDb().getrSet(sql);
				   try {
					if (rSet.next()) {
						this.sumpeople=rSet.getInt(1);
						this.sumscore=rSet.getFloat(2);
						this.maxscore=rSet.getFloat(3);
						this.minscore=rSet.getFloat(4);
					}
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				   sql="select COUNT(DISTINCT student.id) from student,student_course where courseID="+courseID+"and studentID=student.id and sudent_course.score>=60";
				   rSet=InitDB.getInitDb().getrSet(sql);
				   try {
					this.validpeople=rSet.getInt(1);
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			   }
			
			void stataDepartment(DepartmentTreeNode node){
				   sumpeople=0;
				   sumscore=0.0f;
				   maxscore=-1;
				   minscore=1000;
				   validpeople=0;
				  // for (int i = 0; i < frequency.length; i++) {frequency[i]=0;}
				   int courseID=((CourseTreeNode)node.getParent()).getCourse().getId();
				   int departmentID=node .getDepartment().getId();
				   String sql="select COUNT(DISTINCT stuent.id),SUM(student_course.score),MAX(student_course.score),"
				   		+ "MIN(student_course.score)"+"COUNT(student_course.score>=60) from student,student_course "
				   				+ "where departmentID="+departmentID+"and courseID="+courseID+"and studentID=student.id";
				   ResultSet rSet=InitDB.getInitDb().getrSet(sql);
				   try {
					if (rSet.next()) {
						this.sumpeople=rSet.getInt(1);
						this.sumscore=rSet.getFloat(2);
						this.maxscore=rSet.getFloat(3);
						this.minscore=rSet.getFloat(4);
					}
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				   sql="select COUNT(DISTINCT student.id) from student,student_course where departmentID="+departmentID+"+and courseID="+courseID+"and studentID=student.id and sudent_course.score>=60";
				   rSet=InitDB.getInitDb().getrSet(sql);
				   try {
					this.validpeople=rSet.getInt(1);
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			   }
			
			void stataGradeClass(GradeClassTreeNode node){
				   sumpeople=0;
				   sumscore=0.0f;
				   maxscore=-1;
				   minscore=1000;
				   validpeople=0;
				  // for (int i = 0; i < frequency.length; i++) {frequency[i]=0;}
				   int courseID=((CourseTreeNode)node.getParent()).getCourse().getId();
				   int departmentID=((DepartmentTreeNode)node.getParent()).getDepartment().getId();
				   int cClass=node.getGradeClass().getcClass();
				   int gradeID=node.getGradeClass().getGrade();
				   String sql="select COUNT(DISTINCT stuent.id),SUM(student_course.score),MAX(student_course.score),"
				   		+ "MIN(student_course.score)"+"COUNT(student_course.score>=60) from student,student_course "
				   				+ "where departmentID="+departmentID+"and courseID="+courseID+"and grade ="+gradeID+"and class ="+cClass+"and studentID=student.id";
				   ResultSet rSet=InitDB.getInitDb().getrSet(sql);
				   try {
					if (rSet.next()) {
						this.sumpeople=rSet.getInt(1);
						this.sumscore=rSet.getFloat(2);
						this.maxscore=rSet.getFloat(3);
						this.minscore=rSet.getFloat(4);
					}
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				   sql="select COUNT(DISTINCT student.id) from student,student_course where departmentID="+departmentID+"+and courseID="+courseID+"and grade="+gradeID+
						   "and class="+cClass+"and studentID=student.id and sudent_course.score>=60";
				   rSet=InitDB.getInitDb().getrSet(sql);
				   try {
					this.validpeople=rSet.getInt(1);
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			   }
			
			
			@Override
			public void removeListener(ILabelProviderListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isLabelProperty(Object arg0, String arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addListener(ILabelProviderListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getColumnText(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				if (arg0 instanceof TreeNode) {
					TreeNode node=(TreeNode) arg0;
					if (arg1==0) {
						return node.getValue().toString();
					}else if (arg1==1) {
						if (node instanceof CourseTreeNode) {
							stataCourse((CourseTreeNode)node);
						}else if (node instanceof DepartmentTreeNode) {
							stataDepartment((DepartmentTreeNode)node);
						}else if (node instanceof GradeClassTreeNode) {
							stataGradeClass((GradeClassTreeNode)node);
						}
						return this.sumpeople+"";
					}else if (arg1==2) {
						if (node instanceof CourseTreeNode) {
							stataCourse((CourseTreeNode)node);
						}else if (node instanceof DepartmentTreeNode) {
							stataDepartment((DepartmentTreeNode)node);
						}else if (node instanceof GradeClassTreeNode) {
							stataGradeClass((GradeClassTreeNode)node);
						}
						return  this.sumscore/sumpeople+"";
					}else if (arg1==3) {
						if (node instanceof CourseTreeNode) {
							stataCourse((CourseTreeNode)node);
						}else if (node instanceof DepartmentTreeNode) {
							stataDepartment((DepartmentTreeNode)node);
						}else if (node instanceof GradeClassTreeNode) {
							stataGradeClass((GradeClassTreeNode)node);
						}
						return  this.maxscore+"";
					}else if (arg1==4) {
						if (node instanceof CourseTreeNode) {
							stataCourse((CourseTreeNode)node);
						}else if (node instanceof DepartmentTreeNode) {
							stataDepartment((DepartmentTreeNode)node);
						}else if (node instanceof GradeClassTreeNode) {
							stataGradeClass((GradeClassTreeNode)node);
						}
						return  this.minscore+"";
					}else if (arg1==5) {
						if (node instanceof CourseTreeNode) {
							stataCourse((CourseTreeNode)node);
						}else if (node instanceof DepartmentTreeNode) {
							stataDepartment((DepartmentTreeNode)node);
						}else if (node instanceof GradeClassTreeNode) {
							stataGradeClass((GradeClassTreeNode)node);
						}
						return  this.validpeople+"";
					}
				}
				return null;
			}
			
			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		treeViewer.setContentProvider(new TreeNodeContentProvider(){
			@Override
			public Object[] getChildren(Object parentElement) {
				// TODO Auto-generated method stub
				return ((TreeNode)parentElement).getChildren();
			}
			@Override
			public Object[] getElements(Object inputElement) {
				// TODO Auto-generated method stub
				return ((TreeNode)inputElement).getChildren();
			}
			@Override
			public Object getParent(Object element) {
				// TODO Auto-generated method stub
				return ((TreeNode)element).getParent();
			}
			@Override
			public boolean hasChildren(Object element) {
				// TODO Auto-generated method stub
				return ((TreeNode)element).hasChildren();
			}
			
		});
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		shell.setText("\u6210\u7EE9\u7EDF\u8BA1");
		shell.setSize(622, 434);

	}
   

}
