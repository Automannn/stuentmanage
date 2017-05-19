package mydemo1;


import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.widgets.ToolItem;

import com.mysql.jdbc.ResultSet;
import com.sun.org.apache.bcel.internal.generic.NEW;

import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CourseManage{
	private static class ViewerLabelProvider_1 extends LabelProvider {
		public Image getImage(Object element) {
			return super.getImage(element);
		}
		public String getText(Object element) {
			return super.getText(element);
		}
	}
	private static class ViewerLabelProvider extends LabelProvider {
		public Image getImage(Object element) {
			return super.getImage(element);
		}
		public String getText(Object element) {
			return super.getText(element);
		}
	}
    private  Shell shell;
	private ArrayList<Department>departmentlist=new ArrayList<>();
	private List listDepartments;
	private Department currDepartment;
	private Combo comboCourse;
	private ComboViewer comboViewer;
	private Combo comboType;
    
    
	public CourseManage() {
		super();
		shell =new Shell();
		shell.setLayout(new GridLayout(4, false));
		
		ResultSet rSet=InitDB.getInitDb().getrSet("select*from department");
		try {
			while (rSet.next()) {
				departmentlist.add(new Department(rSet.getInt(1),rSet.getString(2)));
				
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		
		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		
		tltmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currDepartment=departmentlist.get(listDepartments.getSelectionIndex());
				String currType=comboType.getText().trim();
				String currCourse=comboCourse.getText().trim();
				if (currCourse.equals("")||currType.equals("")||currCourse==null||currType==null) {
					MessageBox messageBox=new MessageBox(shell);
					messageBox.setMessage("请先选择课程类型和创建课程再添加！");
					messageBox.open();
				}else {
					Course newCourse=new Course(currCourse, currType);
					newCourse.insertToDB();
					ResultSet resultSet=InitDB.getInitDb().getrSet("select*from department_course where courseID='"+Course.getFromDB(currCourse).getId()+"'and departmentID="+currDepartment.getId());
					try {
						if (!resultSet.next()) {
							String sql="insert into department_course(departmentID,courseID) values("+currDepartment.getId()+","+Course.getFromDB(currCourse).getId()+")";
							try {
								InitDB.getInitDb().getStatement().executeUpdate(sql);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			}
		});
		tltmNewItem.setText("\u6DFB\u52A0");
		
		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String oldCourse=comboCourse.getText().trim();
				if (oldCourse==null||oldCourse.equals("")) {
					MessageBox messageBox=new MessageBox(shell);
					messageBox.setMessage("请先选择要修改的课程名称！");
					messageBox.open();
				}else {
					InputDialog inputDialog=new InputDialog(shell, "修改课程名", "请修改课程名称！",oldCourse, null);
					inputDialog.open();
					String newCourse=inputDialog.getValue().trim();
					Course currCourse=new Course(Course.getFromDB(oldCourse).getId(),newCourse, comboType.getText().trim());
					currCourse.updateToDB();
				}
			}
		});
		tltmNewItem_1.setText("\u4FEE\u6539 ");
		
		ToolItem tltmNewItem_2 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			  MessageBox messageBox =new MessageBox(shell);
			  messageBox.setMessage("本功能尚未开放");
			  messageBox.open();
			}
		});
		tltmNewItem_2.setText("\u67E5\u8BE2");
		
		ToolItem tltmNewItem_3 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		tltmNewItem_3.setText("\u9000\u51FA");
		new Label(shell, SWT.NONE);
		
		ListViewer listViewer = new ListViewer(shell, SWT.BORDER | SWT.V_SCROLL);
		listDepartments = listViewer.getList();
		GridData gd_list = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 2);
		gd_list.widthHint = 139;
		gd_list.heightHint = 333;
		listDepartments.setLayoutData(gd_list);
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setInput(departmentlist);
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				currDepartment=departmentlist.get(listDepartments.getSelectionIndex());
				currDepartment.getCoursesListFromDB();
				comboCourse.removeAll();
				comboViewer.setInput(currDepartment.getCoursesList());
				
			}
		});
		listViewer.setLabelProvider(new ILabelProvider() {
			
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
				return ((Department)arg0).getName();
			}
			
			@Override
			public Image getImage(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("\u8BFE\u7A0B\u7C7B\u578B\uFF1A");
		
		comboType = new Combo(shell, SWT.NONE);
		comboType.setItems(new String[] {"\u516C\u5171\u57FA\u7840\u8BFE", "\u4E13\u4E1A\u57FA\u7840\u8BFE", "\u4E13\u4E1A\u9009\u4FEE\u8BFE"});
		comboType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		lblNewLabel_1.setText("\u8BFE\u7A0B\u540D\u79F0\uFF1A");
		
		comboViewer = new ComboViewer(shell, SWT.NONE);
		comboCourse = comboViewer.getCombo();
		comboCourse.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		comboViewer.setLabelProvider(new ILabelProvider() {
			
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
				return ((Course)arg0).getname();
			}
			
			@Override
			public Image getImage(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		comboViewer.setContentProvider(new ArrayContentProvider());
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				comboType.removeAll();
				String currType=Course.getFromDB(comboCourse.getSelectionIndex()).gettype();
				comboType.setText(currType);		
			}
		});
		createContents();
	}

	protected void createContents() {
		shell.setText("\u8BFE\u7A0B\u8BBE\u7F6E");
		shell.setSize(576, 416);

	}

	public Shell getShell() {
		return shell;
	}

	
}
