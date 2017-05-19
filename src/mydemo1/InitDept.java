package mydemo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import javax.imageio.IIOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import com.sun.org.apache.bcel.internal.classfile.Field;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;

public class InitDept{

	private Shell shell;
	private Text textDeptname;
	private Text textCourseName;
    private Department depart;
    private LinkedList<Department>departmentlist;
    private Combo comboCourseType;
	public InitDept() {
		super();
		shell=new Shell( SWT.SHELL_TRIM);
		shell.setText("\u8BBE\u7F6E\u4E13\u4E1A\u4E0E\u8BFE\u7A0B");
		shell.setSize(602, 416);
		shell.setLayout(new GridLayout(2, true));
		
		Label lblNewLabel = new Label(shell , SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("\u4E13\u4E1A\u540D\u79F0");
		
		textDeptname = new Text(shell, SWT.BORDER);
		textDeptname.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButtonadd = new Button(shell, SWT.NONE);
		btnNewButtonadd.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnNewButtonadd.setText("\u6DFB\u52A0");
		
		departmentlist=new LinkedList<Department>();
		btnNewButtonadd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String dn=textDeptname.getText().trim();
				if (dn!=null&&(!dn.equals(""))) {
					
					
					
				for (int i = 0; i < departmentlist.size(); i++) {
						if (departmentlist.get(i).getName().equals(dn)) {
						MessageDialog.openInformation(shell, "专业名称已存在", "该专业名称已存在\n请输入不同的专业名称！");
							return;
						}  
					}  
				
					System.out.println("i have no problem!");
				depart=new Department(textDeptname.getText());
					departmentlist.add(depart); 
					
				}else {
					MessageDialog.openInformation(shell, "专业名称为空", "专业名称为空\n请输入专业名称！");
				}
			}
		});
		
		Button btnNextDepart = new Button(shell, SWT.NONE);
		btnNextDepart.setText("\u4E0B\u4E00\u4E13\u4E1A");
		
		btnNextDepart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (depart!=null&&depart.getCoursesList().size()>0) {
					textDeptname.setText("");
					depart=null;
				}else {
					MessageDialog.openInformation(shell, "无课程", "当前专业还没有添加课程，或当前专业名称为空！");
				}
			}
		});
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		GridData gd_composite = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_composite.heightHint = 305;
		gd_composite.widthHint = 311;
		composite.setLayoutData(gd_composite);
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("\u8BFE\u7A0B\u540D");
		
		textCourseName = new Text(composite, SWT.BORDER);
		textCourseName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("\u8BFE\u7A0B\u7C7B\u578B");
		
		comboCourseType = new Combo(composite, SWT.NONE);
		comboCourseType.setItems(new String[] {"\u516C\u5171\u57FA\u7840\u8BFE", "\u4E13\u4E1A\u57FA\u7840\u8BFE", "\u4E13\u4E1A\u9009\u4FEE\u8BFE"});
		comboCourseType.setToolTipText("");
		comboCourseType.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		comboCourseType.setText("\uFF1F\r\n");
		new Label(composite, SWT.NONE);
		
		Button btnAddClass = new Button(composite, SWT.NONE);
		btnAddClass.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnAddClass.setText("\u6DFB\u52A0\u8BFE\u7A0B");
		
		btnAddClass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (depart==null) {
					MessageDialog.openInformation(shell, "未建立专业", "请先添加专业，然后再添加课程!");
					return;
				}
				String cn=textCourseName.getText().trim();
				String ct=comboCourseType.getText().trim();
				if (cn!=null&&(!ct.equals(""))&&ct!=null&&(!ct.equals(""))) {
					for (int i = 0; i < depart.getCoursesList().size(); i++) {
						if (depart.getCoursesList().get(i).getname().equals(cn)) {
							MessageDialog.openInformation(shell, "课程名重复", "已存在该课程名，请输入一个不同的课程名");
							return;
						}
					}
					Course course=new Course(textCourseName.getText().trim(),comboCourseType.getText().trim());
					depart.addCourse(course);
					textCourseName.setText("");
					comboCourseType.setText("");
				}
				
			}
		});
		
		Button btnSave= new Button(composite, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnSave.setText("\u4FDD\u5B58\u6570\u636E");
		
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (departmentlist.size()==0) {
					MessageDialog.openInformation(shell, "未建立专业", "还没有建立专业，不能保存！");
					return;
				}
				try {
					   FileOutputStream fis=new FileOutputStream(new File("deptList.obj"));
					ObjectOutputStream objectOutputStream=new ObjectOutputStream(fis);
					   objectOutputStream.writeObject(departmentlist);
						objectOutputStream.close();
						fis.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Button btnClose = new Button(composite, SWT.NONE);
		btnClose.setText("\u5173\u95ED\u7A97\u53E3");
		
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		
	}


	public Shell getShell() {
		return shell;
	}



}
