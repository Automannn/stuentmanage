package mydemo1;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.ToolItem;

import com.mysql.jdbc.ResultSet;
import com.sun.net.ssl.HostnameVerifier;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class DepartmentManage{
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
   private  ArrayList<Department>departmentList=new ArrayList<>();
   private Combo comboDepartments;
   private ComboViewer comboViewer;
  

	public DepartmentManage() {
		super();
		this.shell=new Shell();
		shell.setLayout(new GridLayout(3, false));
		ResultSet rSet=InitDB.getInitDb().getrSet("select*from department");//获得数据库中专业表的结果集
		try {
			while (rSet.next()) {
				departmentList.add(new Department(rSet.getInt(1),rSet.getString(2)));//将得到的结果集放入专业列表中
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		GridData gd_toolBar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_toolBar.widthHint = 587;
		gd_toolBar.heightHint = 82;
		toolBar.setLayoutData(gd_toolBar);
		
		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			String strName=comboDepartments.getText().trim();
			boolean isNew =true;
			for (Department dept:departmentList) {
				if (strName.equals(dept.getName())) {
					isNew=false;
					break;
				}
			  }
			if (isNew) {
				System.out.println(isNew);
				Department newDept=new Department(strName);
			   	if (newDept.insertToDB()>0) {
					departmentList.add(newDept);
					System.out.println("i have run3");
					System.out.println(newDept.insertToDB());
					comboViewer.setInput(departmentList);
					comboDepartments.setText("");
					comboDepartments.setFocus();
				}
			}
			}
		});
		tltmNewItem.setText("\u6DFB\u52A0");
		
		ToolItem tltmNewItem_4 = new ToolItem(toolBar, SWT.SEPARATOR);
		tltmNewItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		tltmNewItem_4.setText("New Item");
		
		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String  strName=comboDepartments.getText().trim();
				InputDialog inputDialog=new InputDialog(shell, "修改专业名", "请修改专业名称！",strName, null);
				inputDialog.open();
				String newName=inputDialog.getValue();
				if (newName!=null&&!"".equals(newName)&&!strName.equals(newName)) {
					for (int i=0;i<departmentList.size();i++) {
						if (departmentList.get(i).getName().equals(strName)) {
							departmentList.get(i).setName(newName);
							if (departmentList.get(i).updateToDB()>0) {
								comboViewer.setInput(departmentList);
							}
						}
					}
				}
			}
		});
		tltmNewItem_1.setText("\u4FEE\u6539");
		
		ToolItem tltmNewItem_5 = new ToolItem(toolBar, SWT.SEPARATOR);
		tltmNewItem_5.setText("New Item");
		
		ToolItem tltmNewItem_2 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				comboDepartments.setVisibleItemCount(10);
				comboDepartments.pack();
			}
		});
		tltmNewItem_2.setText("\u6D4F\u89C8");
		
		ToolItem tltmNewItem_6 = new ToolItem(toolBar, SWT.SEPARATOR);
		tltmNewItem_6.setText("New Item");
		
		ToolItem tltmNewItem_3 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			shell.dispose();
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
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 121;
		gd_lblNewLabel.heightHint = 61;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("                \u4E13\u4E1A\u540D\uFF1A");
		new Label(shell, SWT.NONE);
		
	    comboViewer = new ComboViewer(shell, SWT.NONE);
		comboDepartments = comboViewer.getCombo();
		comboDepartments.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		comboViewer.setLabelProvider(new ILabelProvider(){

			@Override
			public void addListener(ILabelProviderListener arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean isLabelProperty(Object arg0, String arg1) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void removeListener(ILabelProviderListener arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Image getImage(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getText(Object arg0) {
				
				return ((Department)arg0).getName();
			}});
		
		comboViewer.setContentProvider(new ArrayContentProvider());
		comboViewer.setInput(departmentList);
		createContents();
	}
	
	protected void createContents() {
		shell.setText("SWT Application");
		shell.setSize(642, 423);

	}
	public Shell getShell() {
		return shell;
	}

}
