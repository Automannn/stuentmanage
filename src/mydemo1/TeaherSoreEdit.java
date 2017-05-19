package mydemo1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.mozilla.nsIBaseWindow;

import mydemo1.ScoreTchData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeColumn;

import com.mysql.jdbc.ResultSet;
import com.sun.org.apache.bcel.internal.generic.NEW;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeSelection;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.CellEditor;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class TeaherSoreEdit{
	
private Shell shell;
private Table table;
private ScoreTchData scoreTchData;
private TableViewer tableViewer;
private TreeViewer treeViewer;
private ComparatorSort mySorter=null;
	

	
	public TeaherSoreEdit(int id) {
		super();
		shell=new Shell();
		Teacher currTch=Teacher.getFromDB(id);
		

		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		scoreTchData=new ScoreTchData(currTch);  //实例化整颗树
		scoreTchData.setParent(null);
		scoreTchData.setChildren(scoreTchData.getCourseList().toArray(new CourseTreeNode[scoreTchData.getCourseList().size()]));//树的子类为课程节点
		
		mySorter=ComparatorSort.getSorter();//实例化排序器
		
		treeViewer = new TreeViewer(shell, SWT.BORDER);
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
		treeViewer.setInput(scoreTchData);  //为树形查看器设置输入数据集
		Tree tree = treeViewer.getTree();
		
		
		tableViewer = new TableViewer(shell, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.HIDE_SELECTION | SWT.MULTI);
		tableViewer.setContentProvider(new ArrayContentProvider());  //为表格查看器设置内容提供器
		tableViewer.setInput(new StdScore[0]);  //为表格查看器设置输入数据集
		table= tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("\u5B66\u53F7");
		
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(112);
		tblclmnNewColumn_1.setText("\u59D3\u540D");
		
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(117);
		tblclmnNewColumn_2.setText("\u6210\u7EE9");
		
		
		
		final String[] colNames=new String[]{"id","name","score"};//实例化列表名称属性
		tableViewer.setColumnProperties(colNames);//设置列表名称属性
		org.eclipse.jface.viewers.CellEditor[] cellEditors =new org.eclipse.jface.viewers.CellEditor[3];  //为实例化表格的内容编辑器
		cellEditors[0]=null;
		cellEditors[1]=null;
		cellEditors[2]=(org.eclipse.jface.viewers.CellEditor) new TextCellEditor(table);  //设置当前要修改的表格列
		tableViewer.setCellEditors((org.eclipse.jface.viewers.CellEditor[]) cellEditors);  //为表格设置内容编辑器
		
		
       treeViewer.setLabelProvider(new ILabelProvider() {  //为树形文件查看器设置 标签提供器
			
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
			public String getText(Object arg0) {
				// TODO Auto-generated method stub
				if (arg0 instanceof TreeNode) {
					((TreeNode)arg0).getValue().toString();
				}
				return arg0.toString();
			}
			
			@Override
			public Image getImage(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
        treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {  //为树形文件查看器设置内容监听器
			
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				StructuredSelection seleNode=(StructuredSelection) arg0.getSelection();
				Object seleObj=seleNode.getFirstElement();
				if (seleObj instanceof GradeClassTreeNode) {
					GradeClassTreeNode gcNode =(GradeClassTreeNode) seleObj;
					GradeClass gcObj=gcNode.getGradeClass();
					int grade=gcObj.getGrade();
					int classNo=gcObj.getcClass();
					DepartmentTreeNode departmentTreeNode=(DepartmentTreeNode) gcNode.getParent();
					int departmentID=departmentTreeNode.getDepartment().getId();
					CourseTreeNode ctn=(CourseTreeNode) departmentTreeNode.getParent();
					int courseID=ctn.getCourse().getId();
					ArrayList<StdScore>studentlist=new ArrayList<>();
					String sql="select student.id,student_course.id from student,student_course where departmentID='"+departmentID+"'and grade='"+grade+"'and class='"+classNo+"'and studentID=student.id and courseID="+courseID;
					ResultSet rSet=InitDB.getInitDb().getrSet(sql);
					try {
						studentlist.clear();
						while (rSet.next()) {
							System.out.println("i have run    hahahahha");
							studentlist.add(new StdScore(Student.getFromDB(rSet.getInt(1))));
							tableViewer.setInput(studentlist);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
		
		
		
        tblclmnNewColumn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				mySorter.setColum(1);
				tableViewer.setComparator(mySorter);
				tableViewer.refresh();
				ComparatorSort.setOrderID(-ComparatorSort.getOrderID());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
        tblclmnNewColumn_1.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stubmySorter.setColum(1);
				mySorter.setColum(2);
				tableViewer.setComparator(mySorter);
				tableViewer.refresh();
				ComparatorSort.setOrderName(-ComparatorSort.getOrderName());
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        tblclmnNewColumn_2.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				mySorter.setColum(3);
				tableViewer.setComparator(mySorter);
				tableViewer.refresh();
				ComparatorSort.setOrderScore(-ComparatorSort.getOrderScore());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		tableViewer.setCellModifier(new ICellModifier() {
			
			@Override
			public void modify(Object arg0, String arg1, Object arg2) {
				// TODO Auto-generated method stub
				TableItem tableItem=(TableItem) arg0;
				StdScore score2=(StdScore) tableItem.getData();
				if (colNames[2].equals(arg1)) {
					score2.setScore(Float.parseFloat((String) arg2));
					score2.updateToDB(score2.getStudent().getId());
				}
				tableViewer.update(score2, null);
			}
			
			@Override
			public Object getValue(Object arg0, String arg1) {
				// TODO Auto-generated method stub
				StdScore score1=(StdScore) arg0;
				if (colNames[2].equals(arg1)) {
					return score1.getScorefromDB()+"";
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object arg0, String arg1) {
				// TODO Auto-generated method stub
				if (arg0 instanceof StdScore&&colNames[2].equals(arg1)) {
					return true;
				}
				return false; 
				
			}
		});
		tableViewer.setLabelProvider(new ITableLabelProvider() {
			
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
				StdScore stdScore=(StdScore) arg0;
				if (arg1==0) {
					return stdScore.getStudent().getId()+"";
				}else if (arg1==1) {
					return stdScore.getStudent().getName();
				}
				if (arg1==2) {
					
					return stdScore.getScorefromDB()+"";
				}
				return null;
			}
			
			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		createContents();
		
		
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		shell.setText("\u6210\u7EE9\u767B\u5F55");
		shell.setSize(684, 432);

	}
  
	static class ComparatorSort extends ViewerComparator{
		private static int orderID=-1;
		private static int orderName=-1;
		private static int orderScore=-1;
		private int colum=0;
		private static final ComparatorSort sorter=new ComparatorSort();
		public ComparatorSort(){
			super();
		}
		
		public int getColum() {
			return colum;
		}

		public void setColum(int colum) {
			this.colum = colum;
		}

		public static int getOrderID() {
			return orderID;
		}
		public static void setOrderID(int orderID) {
			ComparatorSort.orderID = orderID;
		}
		public static int getOrderName() {
			return orderName;
		}
		public static void setOrderName(int orderName) {
			ComparatorSort.orderName = orderName;
		}
		public static int getOrderScore() {
			return orderScore;
		}
		public static void setOrderScore(int orderScore) {
			ComparatorSort.orderScore = orderScore;
		}
		public static ComparatorSort getSorter() {
			return sorter;
		}
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			// TODO Auto-generated method stub
			 StdScore sts1=(StdScore) e1;
			 StdScore sts2=(StdScore) e2;
			if (this.colum==1) {
				if (orderID>0) 
					return sts1.getStudent().getId()>sts2.getStudent().getId()?1:(sts1.getStudent().getId()==sts2.getStudent().getId()?0:-1);
				else if (orderID<0) 
					return sts1.getStudent().getId()<sts2.getStudent().getId()?1:(sts1.getStudent().getId()==sts2.getStudent().getId()?0:-1);
			}
			else if (this.colum==2) {
				if (orderName>0) 
				return sts1.getStudent().getName().compareTo(sts2.getStudent().getName());
				else if (orderName<0) 
					return sts2.getStudent().getName().compareTo(sts1.getStudent().getName());
			}
			else if (this.colum==3) {
				if (orderScore>0)
					return sts1.getScorefromDB()>sts2.getScorefromDB()?1:(sts1.getScorefromDB()==sts2.getScorefromDB()?0:-1);
				else if (orderScore<0)
					return sts1.getScorefromDB()<sts2.getScorefromDB()?1:(sts1.getScorefromDB()==sts2.getScorefromDB()?0:-1);
			}
			return super.compare(viewer, e1, e2);
		}
		
	}
	

	public Shell getShell() {
		return shell;
	}

	
	
}
