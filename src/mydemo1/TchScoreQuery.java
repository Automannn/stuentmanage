package mydemo1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Tree;

import com.mysql.jdbc.ResultSet;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_ADDPeer;

import mydemo1.TeaherSoreEdit.ComparatorSort;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.CellEditor;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.swt.events.SelectionAdapter;

public class TchScoreQuery{
private Shell  shell;
private Text textcondi2;
private Text textcondi1;;
private Table table;
private ScoreTchData scoreTchData;
private TableViewer tableViewer;
private TreeViewer treeViewer;
private ComparatorSort mySorter=null;
private Combo comboCondition1;
private Combo comboCondition2;
private Button btnRadioButtonOr;
private Button btnRadioButtonAnd;



	public TchScoreQuery(int id) {
		super();
		scoreTchData=new ScoreTchData(Teacher.getFromDB(id));
		scoreTchData.setParent(null);
		scoreTchData.setChildren(scoreTchData.getCourseList().toArray(new CourseTreeNode[scoreTchData.getCourseList().size()]));
		shell=new Shell();
		shell.setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(7, false));
		GridData gd_composite = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_composite.widthHint = 622;
		composite.setLayoutData(gd_composite);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, false, 1, 1));
		lblNewLabel.setText("\u6210\u7EE9 ");
		
		comboCondition1 = new Combo(composite, SWT.NONE);
		comboCondition1.setItems(new String[] {"''", ">", "<", "="});
		comboCondition1.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		
		textcondi1 = new Text(composite, SWT.BORDER);
		textcondi1.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		
		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_group = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_group.widthHint = 75;
		gd_group.heightHint = 21;
		group.setLayoutData(gd_group);
		
		btnRadioButtonAnd = new Button(group, SWT.RADIO);
		btnRadioButtonAnd.setText("\u4E0E");
		
		btnRadioButtonOr = new Button(group, SWT.RADIO);
		btnRadioButtonOr.setText("\u6216");
		
		comboCondition2 = new Combo(composite, SWT.NONE);
		comboCondition2.setItems(new String[]{"''",">","<","="});
		comboCondition2.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		
		textcondi2 = new Text(composite, SWT.BORDER);
		textcondi2.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FilterLower filterLower=new FilterLower();
				FilterHigh filterHigh=new FilterHigh();
				FilterEquales fiq=new FilterEquales();
				Filter2Range fi2=new Filter2Range();
				float condi1=-1;
				float condi2=-1;
				String opr1=null;
				String opr2=null;
				try {
					if (textcondi1.getText()!=null&&!"".equals(textcondi1.getText())) {
						condi1=Float.parseFloat(textcondi1.getText());
					}
					if (textcondi2.getText()!=null&&!"".equals(textcondi2.getText())) {
						condi2=Float.parseFloat(textcondi2.getText());
					}
					
				} catch (NumberFormatException e2) {
					// TODO: handle exception
					MessageDialog.openError(shell, "必须输入数值", "必须在该比较符旁边的文本框中输入一个数值。");
				}
				if (comboCondition1.getText()!=null&&!"".equals(comboCondition1.getText())) {
					opr1=comboCondition1.getText().trim();
				}
				if (comboCondition2.getText()!=null&&!"".equals(comboCondition2.getText())) {
					opr2=comboCondition2.getText().trim();
				}
				if (condi1!=-1||condi2!=-1) {
					if (condi1>condi2) {
						float tmp=condi1;
						condi1=condi2;
						condi2=tmp;
						String string=opr1;
						opr1=opr2;
						opr2=string;
					}
					if (("".equals(opr2)||opr2==null)&&("".equals(opr1)||opr1==null)) {
						clearFilters();
					}else if ((!"".equals(opr2)&&opr2!=null)&&("".equals(opr1)||opr1==null)) {
						if (opr2.equals("=")) {
							fiq.setLimit(condi2);
							clearFilters();
							tableViewer.addFilter(fiq);
							tableViewer.refresh();
						}else if (opr2.equals(">")) {
							filterLower.setLimit(condi2);
							clearFilters();
							tableViewer.setFilters(filterLower);
							tableViewer.refresh();
						}else if (opr2.equals("<")) {
							filterHigh.setLimit(condi2);
							clearFilters();
							tableViewer.addFilter(filterHigh);
							tableViewer.refresh();
						}
					}else if (btnRadioButtonAnd.getSelection()) {
						if (">".equals(opr1)&&">".equals(opr2)) {
							clearFilters();
							filterLower.setLimit(condi2);
							tableViewer.addFilter(filterLower);
						}else if ("<".equals(opr1)&&"<".equals(opr2)) {
							clearFilters();
							filterHigh.setLimit(condi1);
							tableViewer.addFilter(filterHigh);
						}if (">".equals(opr1)&&"<".equals(opr2)) {
							clearFilters();
							filterLower.setLimit(condi1);
							tableViewer.addFilter(filterLower);
							filterHigh.setLimit(condi2);
							tableViewer.addFilter(filterHigh);
						}if ("<".equals(opr1)&&">".equals(opr2)) {
							clearFilters();
							filterLower.setLimit(1000);
							tableViewer.addFilter(filterLower);
							filterHigh.setLimit(-1000);
							tableViewer.addFilter(filterHigh);
						}
						tableViewer.refresh();
					}else if (btnRadioButtonOr.getSelection()) {
						if (">".equals(opr1)&&">".equals(opr2)) {
							clearFilters();
							filterLower.setLimit(condi1);
							tableViewer.addFilter(filterLower);
						}else if ("<".equals(opr1)&&"<".equals(opr2)) {
							clearFilters();
							filterHigh.setLimit(condi2);
							tableViewer.addFilter(filterHigh);
						}if (">".equals(opr1)&&"<".equals(opr2)) {
							clearFilters();
						}if ("<".equals(opr1)&&">".equals(opr2)) {
							clearFilters();
							fi2.setLimitLow(condi1);
							fi2.setLimitHigh(condi2);
							tableViewer.addFilter(fi2);
						}
						tableViewer.refresh();
					}
				}
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1);
		gd_btnNewButton.widthHint = 65;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("\u67E5\u627E");
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_composite_1 = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
		gd_composite_1.heightHint = 298;
		gd_composite_1.widthHint = 623;
		composite_1.setLayoutData(gd_composite_1);
		
		treeViewer = new TreeViewer(composite_1, SWT.BORDER);
		Tree tree_1 = treeViewer.getTree();
        mySorter=ComparatorSort.getSorter();
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
		treeViewer.setInput(scoreTchData);
		treeViewer.setLabelProvider(new ILabelProvider() {
			
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
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				if (tableViewer.getSorter()!=null)
				clearFilters();
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
							System.out.println("i have run");
							studentlist.add(new StdScore(Student.getFromDB(rSet.getInt(1))));
							tableViewer.setInput(studentlist);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
		treeViewer.expandAll();
		
		
		
		tableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(new StdScore[0]);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("\u5B66\u53F7");
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
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(112);
		tblclmnNewColumn_1.setText("\u59D3\u540D");
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
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(117);
		tblclmnNewColumn_2.setText("\u6210\u7EE9");
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
		
		
		org.eclipse.jface.viewers.CellEditor[] cellEditors =(org.eclipse.jface.viewers.CellEditor[]) new org.eclipse.jface.viewers.CellEditor[3];
		cellEditors[0]=null;
		cellEditors[1]=null;
		cellEditors[2]=null;
		tableViewer.setCellEditors((org.eclipse.jface.viewers.CellEditor[]) cellEditors);
		final String[] colNames=new String[]{"id","name","score"};
		tableViewer.setColumnProperties(colNames);
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
				if (colNames[2].equals(arg1)){
					return score1.getScorefromDB()+"";
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object arg0, String arg1) {
				// TODO Auto-generated method stub
				if (arg0 instanceof Studentscore&&colNames[2].equals(arg1)) {
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
					String score=stdScore.getScorefromDB()>0.5F?stdScore.getScorefromDB()+"":"";
					return score;
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
	protected void createContents() {
		shell.setText("\u6210\u7EE9\u6D4F\u89C8");
		shell.setSize(649, 410);

	}
	
	class FilterLower extends ViewerFilter{
		float limit;
		@Override
		public boolean select(Viewer arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			System.out.println("FilterLower");
			StdScore stdScore=(StdScore) arg2;
			if (stdScore.getScorefromDB()>limit) 
				return true;
				else 
			    return false;
		}
		public float getLimit() {
			return limit;
		}
		public void setLimit(float limit) {
			this.limit=limit;
		}
	}
	class FilterHigh extends ViewerFilter{
		float limit;
		@Override
		public boolean select(Viewer arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			StdScore stdScore=(StdScore) arg2;
			if (stdScore.getScorefromDB()<limit) 
				return true;
				else 
			    return false;
		}
		public float getLimit() {
			return limit;
		}
		public void setLimit(float limit) {
			this.limit=limit;
		}
	}
	class FilterEquales extends ViewerFilter{
		float limit;
		@Override
		public boolean select(Viewer arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			if (arg2 instanceof StdScore) {
				StdScore stdScore=(StdScore) arg2;
				if (Math.abs(stdScore.getScorefromDB()-limit)<0.01)
				return true;
				else 
			    return false;
			   }else
					return false;
				}
			
		public float getLimit() {
			return limit;
		}
		public void setLimit(float limit) {
			this.limit=limit;
		}
	}
	class Filter2Range extends ViewerFilter{
		float limitLow;
		float limitHigh;
		@Override
		public boolean select(Viewer arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			StdScore stdScore=(StdScore) arg2;
			if (stdScore.getScorefromDB()<limitLow) 
				return true;
				else if (stdScore.getScorefromDB()>limitHigh)
					return true;
				else
			    return false;
		}
		public float getlimitLow(){
			return limitLow;
		}
		public void setLimitLow(float limitLow) {
			this.limitLow=limitLow;
		}
		public float getlimitHigh(){
			return limitHigh;
		}
		public void setLimitHigh(float limitHigh) {
			this.limitHigh=limitHigh;
		}
	}
	
	public  void clearFilters() {
		ViewerFilter[] filters=tableViewer.getFilters();
		for (int i=0;i<filters.length;i++) {
			tableViewer.removeFilter(filters[i]);
		}
	}
	public Shell getShell() {
		return shell;
	}
		
}
