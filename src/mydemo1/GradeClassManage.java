package mydemo1;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolItem;

import com.mysql.jdbc.ResultSet;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

public class GradeClassManage{
	private static class ViewerLabelProvider extends LabelProvider {
		public Image getImage(Object element) {
			return super.getImage(element);
		}
		public String getText(Object element) {
			return super.getText(element);
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
    private  Shell shell;
    ArrayList<Department>departmentlist=new ArrayList<>();
	private Combo comboGrade;
	private Combo comboClass;
	private Combo comboDepartments;
	private GradeClass gradeClass;

	public GradeClassManage() {
		super();
		shell=new Shell();
		shell.setLayout(new GridLayout(5, false));
		
		ResultSet rSet=InitDB.getInitDb().getrSet("select*from department");//从数据库中得到所有的专业列表
		try {
			while (rSet.next()) {
				departmentlist.add(new Department(rSet.getInt(1),rSet.getString(2)));//初始化专业链表
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		
		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
              String currDepart=comboDepartments.getText().trim();
			  if (currDepart.equals("")||currDepart==null) {
			   MessageBox messageBox=	new MessageBox(shell);
			   messageBox.setMessage("没有专业\n 请先选择专业，然后再添加年级");
			   messageBox.open();
			}else {
				InputDialog intput=new InputDialog(shell, "设置年级", "请为该专业设置年级", null,null);
				intput.open();
				String newGrade=intput.getValue().trim();
				gradeClass=new GradeClass(Department.getFromDB(currDepart).getId(), Integer.parseInt(newGrade));
				gradeClass.insertToDB_grade();
			}
			}
		});
		tltmNewItem.setText("\u6DFB\u52A0\u5E74\u7EA7 ");
		
		ToolItem tltmNewItem_2 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			      String currDepart=comboDepartments.getText().trim();
			      String currGrade=comboGrade.getText().trim();
				  if (currDepart.equals("")||currDepart==null||currGrade.equals("")||currGrade==null) {
				   MessageBox messageBox=	new MessageBox(shell);
				   messageBox.setMessage("没有专业或年级\n 请先选择专业和年级后再添加年级！");
				   messageBox.open();
				}else {
					InputDialog intput=new InputDialog(shell, "设置班级", "请为该专业设置班级级", null,null);
					intput.open();
					String newcClass=intput.getValue().trim();
					gradeClass=new GradeClass(Department.getFromDB(currDepart).getId(), Integer.parseInt(currGrade),Integer.parseInt(newcClass));
					gradeClass.insertToDB_class();
				}
			}
		});
		tltmNewItem_2.setText("\u6DFB\u52A0\u73ED\u7EA7");
		
		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.SEPARATOR);
		
		ToolItem tltmNewItem_3 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		tltmNewItem_3.setText("\u9000\u51FA");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("\u4E13\u4E1A\u540D\uFF1A");
		
		ComboViewer comboViewer = new ComboViewer(shell, SWT.NONE);
		
		comboDepartments = comboViewer.getCombo();
		comboDepartments.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		comboViewer.setContentProvider(new ArrayContentProvider());
		comboViewer.setInput(departmentlist);//为combo组件设置查看器的内容提供器
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {//注册单击选择事件的监听器
				StructuredSelection seleDept=(StructuredSelection) arg0.getSelection();
				if (seleDept.getFirstElement() instanceof Department) {
					String thisDept=((Department)seleDept.getFirstElement()).getName();//获得当前专业对象的名称
					comboGrade.removeAll();//初始化年级下拉框
					ResultSet rSet=InitDB.getInitDb().getrSet("select grade from department_grade_class where departmentID="+Department.getFromDB(thisDept).getId());
					try {
						while (rSet.next()) {
							comboGrade.add(rSet.getInt(1)+"");//将专业下所有年级放入年级下拉框
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					comboClass.removeAll();//初始化班级下拉框
				}
				
			}
		});
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
			public String getText(Object arg0) {//实现了查看器的标签提供器中的gettext方法
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
		new Label(shell, SWT.NONE);
		
		
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_label.widthHint = 562;
		label.setLayoutData(gd_label);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("\u5E74\u7EA7 \uFF1A");
		
		
		comboGrade = new Combo(shell, SWT.NONE);
		comboGrade.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//为年级下拉框注册点击事件监听器
				comboClass.removeAll();//初始化班级下拉框
				String deptStr=comboDepartments.getText().trim();
				String gradeStr=((Combo)e.getSource()).getText().trim();
				if (deptStr==null||"".equals(deptStr)||gradeStr==null||"".equals(gradeStr)) {
					MessageDialog.openConfirm(shell, "有错误.",   "请先选择专业和年级，然后再添加班级。");
					return;
				}
			  ResultSet rSet =InitDB.getInitDb().getrSet("select class from department_grade_class where departmentID='"+Department.getFromDB(deptStr).getId()+"'and grade='"+Integer.parseInt(gradeStr)+"'");	
				try {
					while (rSet.next()) {
						comboClass.add(rSet.getInt(1)+"");
					}
					
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		});
		comboGrade.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("\u73ED\u7EA7 \uFF1A");
	
		comboClass = new Combo(shell, SWT.NONE);
		comboClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		shell.setText("\u73ED\u7EA7\u8BBE\u7F6E");
		shell.setSize(590, 368);

	}
	

	public Shell getShell() {
		return shell;
	}

	
}
