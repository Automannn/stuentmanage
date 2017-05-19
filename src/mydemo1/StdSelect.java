package mydemo1;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.mysql.jdbc.ResultSet;
import com.sun.org.apache.xml.internal.security.Init;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class StdSelect{
	private class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		public String getColumnText(Object element, int columnIndex) {
			return element.toString();
		}
	}
	private static class ContentProvider_1 implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	private static class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
     private Shell shell;
	private Combo comboDepartments;
	private Combo comboCourse;
	private Combo comboTeacher;
	private Combo comboGrade;
	private Combo comboClass;
	private Department currDepart;
	public Teacher currTeacher;
	private ArrayList<Teacher>tchList;
	private Course currCourse;
	private Table tableUnselect;
	private Table tableSelected;
	public ArrayList<Student>stdListYes;
	public ArrayList<Student>stdListNo;
	private TableViewer tableViewer;
	private TableViewer tableViewer_1;

	public StdSelect() {
		super();
		shell=new  Shell();
		shell.setLayout(new GridLayout(7, false));
		tchList =new ArrayList<>();
		stdListYes=new ArrayList<>();
		stdListNo=new ArrayList<>();
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("\u4E13\u4E1A\uFF1A");
		
		comboDepartments = new Combo(shell, SWT.NONE);
		ResultSet rSet1=InitDB.getInitDb().getrSet("select*from department");
		try {
			while (rSet1.next()) {
				comboDepartments.add(rSet1.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		comboDepartments.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("\u8BFE\u7A0B\uFF1A");
		
		comboCourse = new Combo(shell, SWT.NONE);
		
		comboCourse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("\u6559\u5E08\uFF1A");
		
		comboTeacher = new Combo(shell, SWT.NONE);
		ResultSet resultSet12=InitDB.getInitDb().getrSet("select*from teacher");
		try {
			while (resultSet12.next()) {
				Teacher teacher=Teacher.getFromDB(resultSet12.getInt(1));
				teacher.getCoursesListFromDB();
				tchList.add(teacher);
				comboTeacher.add(teacher.getId()+"-"+teacher.getName());
			}
		} catch (SQLException e) {
			
		}
		comboTeacher.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!comboTeacher.equals("")) {
					int sidx=comboTeacher.getSelectionIndex();
					String sname=comboTeacher.getItem(sidx);
					currTeacher=Teacher.getFromDB(Integer.parseInt(sname.split("-")[0]));
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		comboTeacher.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("\u5E74\u7EA7\uFF1A");
		
		comboGrade = new Combo(shell, SWT.NONE);
		comboGrade.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String string=comboGrade.getText().trim();
				if (string!=null&&!"".equals(string)) {
					ResultSet resultSet=InitDB.getInitDb().getrSet("select*from department_grade_class where departmentID='"+currDepart.getId()+"'and grade="+Integer.parseInt(string));
					try {
						while (resultSet.next()) {
							comboClass.add(resultSet.getInt(4)+"");
						}
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		comboGrade.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_4 = new Label(shell, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("\u73ED\u7EA7\uFF1A");
		
		comboClass = new Combo(shell, SWT.NONE);
		
		comboClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1);
		gd_label.widthHint = 502;
		label.setLayoutData(gd_label);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setText("\u672A\u9009\u5B66\u751F\uFF1A");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_6 = new Label(shell, SWT.NONE);
		lblNewLabel_6.setText("\u5DF2\u9009\u5B66\u751F\uFF1A");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		tableViewer = new TableViewer(shell, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		tableUnselect = tableViewer.getTable();
		tableUnselect.setLinesVisible(true);
		tableUnselect.setHeaderVisible(true);
		GridData gd_tableUnselect = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3);
		gd_tableUnselect.widthHint = 281;
		tableUnselect.setLayoutData(gd_tableUnselect);
		
		TableColumn tblclmnNewColumn = new TableColumn(tableUnselect, SWT.NONE);
		tblclmnNewColumn.setWidth(146);
		tblclmnNewColumn.setText("\u5B66\u53F7");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(tableUnselect, SWT.NONE);
		tblclmnNewColumn_1.setWidth(135);
		tblclmnNewColumn_1.setText("\u59D3\u540D");
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		
		btnNewButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton.setText(">");
		
		tableViewer_1 = new TableViewer(shell, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.HIDE_SELECTION | SWT.MULTI);
		tableSelected = tableViewer_1.getTable();
		tableSelected.setLinesVisible(true);
		tableSelected.setHeaderVisible(true);
		tableSelected.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));
		
		TableColumn tableColumn = new TableColumn(tableSelected, SWT.NONE);
		tableColumn.setWidth(143);
		tableColumn.setText("\u5B66\u53F7");
		
		TableColumn tableColumn_1 = new TableColumn(tableSelected, SWT.NONE);
		tableColumn_1.setWidth(131);
		tableColumn_1.setText("\u59D3\u540D");
		tableViewer_1.setContentProvider(new ArrayContentProvider());
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		
		btnNewButton_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton_1.setText("<");
		new Label(shell, SWT.NONE);
		
		Button btnNewButton_2 = new Button(shell, SWT.NONE);
		
		btnNewButton_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		btnNewButton_2.setText("\u786E\u5B9A");
		new Label(shell, SWT.NONE);
		
		Button btnNewButton_3 = new Button(shell, SWT.NONE);
		
		btnNewButton_3.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		btnNewButton_3.setText("\u5173\u95ED");

		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (stdListNo.size()>0) {
					CheckboxTableViewer cvNo=new CheckboxTableViewer(tableUnselect);
					Object[] objNo=cvNo.getCheckedElements();
					for (Object obj:objNo) {
						stdListYes.add((Student) obj);
						stdListNo.remove((Student) obj);
					}
					tableViewer.refresh();
					tableViewer_1.refresh();
				}
			}
		});
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (stdListYes.size()>0) {
					CheckboxTableViewer cvYes=new CheckboxTableViewer(tableSelected);
					Object[] objYes=cvYes.getCheckedElements();
					for (Object obj:objYes) {
						stdListYes.remove(obj);
						stdListNo.add((Student) obj);
					}
					tableViewer.refresh();
					tableViewer_1.refresh();
				}
			}
		});
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveStdCourse();
				saveTchCourse();
			}
		});
		
		comboClass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboDepartments.getText()==null||"".equals(comboDepartments.getText())
						||comboCourse.getText()==null||comboCourse.getText().equals("")||comboTeacher.getText()==null||comboTeacher.getText().equals("")
						||comboGrade.getText().equals("")||comboGrade.getText()==null||comboClass.getText().equals("")||comboClass.getText()==null) {
					MessageDialog.openError(shell, "数据不完整", "必须选择专业，课程，教师，年级和班级！");
				}else {
					System.out.println("i have run 2");
                   ResultSet rSet=InitDB.getInitDb().getrSet("select*from student where departmentID='"+currDepart.getId()+"'and grade='"+Integer.parseInt(comboGrade.getText())+"'and class="+Integer.parseInt(comboClass.getText()));
			         try {
			        	 stdListNo.clear();
			        	 stdListYes.clear();
			        	 System.out.println("i have run 3");
			        	 while (rSet.next()) {
					     stdListNo.add(Student.getFromDB(rSet.getInt(1)));
					      }
					} catch (SQLException e2) {
						 tableViewer.setInput(stdListNo);
			        	 System.out.println("i have run 4");
			        	 tableViewer_1.setInput(stdListYes);
					}
			         
				}
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
				if (arg1==0) {
					return ((Student)(arg0)).getId()+"";
				}
				if (arg1==1) {
					return ((Student)(arg0)).getName();
				}
				return null;
			}
			
			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
      tableViewer_1.setLabelProvider(new ITableLabelProvider() {
			
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
				if (arg1==0) {
					return ((Student)(arg0)).getId()+"";
				}
				if (arg1==1) {
					return ((Student)(arg0)).getName();
				}
				return null;
			}
			
			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		comboDepartments.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String thisDepart=comboDepartments.getText().trim();
				if (thisDepart!=null&&!"".equals(thisDepart)) {
					currDepart =Department.getFromDB(thisDepart);
					ResultSet resultSet=InitDB.getInitDb().getrSet("select*from department_grade_class where departmentID="+currDepart.getId());
				    comboGrade.removeAll();
				    comboClass.removeAll();
				    try {
						while (resultSet.next()) {
							comboGrade.add(resultSet.getInt(3)+"");
						}
					} catch (SQLException e2) {
					    e2.printStackTrace();	
					}
				    comboCourse.removeAll();
				    resultSet=InitDB.getInitDb().getrSet("select course.*from course,department_course where departmentID="+currDepart.getId());
				    try {
						while (resultSet.next()) {
							comboCourse.add(resultSet.getString(2)+"-"+resultSet.getString(3));
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		
		comboCourse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!"".equals(comboCourse.getText().trim())&&comboCourse.getText()!=null) {
					String cName=comboCourse.getText();
					String[]cnt=cName.split("-");
					currCourse=Course.getFromDB(cnt[0], cnt[1]);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		createContents();
	}

	protected void saveTchCourse() {
		String sql="insert into teacher_course(teacherID,courseID,departmentID,grade,class)values("+currTeacher.getId()+","+currCourse.getId()+","+currDepart.getId()+","+Integer.parseInt(comboGrade.getText())+","+Integer.parseInt(comboClass.getText())+")";
		try {
			InitDB.getInitDb().getStatement().executeUpdate(sql);
			currDepart=null;
			currCourse=null;
			currTeacher=null;
			stdListYes.clear();
			stdListNo.clear();
			tableViewer.refresh();
			tableViewer_1.refresh();
			comboDepartments.setText("");
			comboCourse.setText("");
			comboTeacher.setText("");
			comboGrade.setText("");
			comboClass.setText("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	protected void saveStdCourse() {
		for (Student std:stdListYes) {
			String sql="insert into student_course(studentID,courseID,updatetime)values("+std.getId()+","+currCourse.getId()+",now())";
			try {
				InitDB.getInitDb().getStatement().executeUpdate(sql);
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
		
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		shell.setText("\u5B66\u751F\u5206\u6D3E");
		shell.setSize(639, 409);

	}

	public Shell getShell() {
		return shell;
	}

}
