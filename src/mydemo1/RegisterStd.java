package mydemo1;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import java.awt.FileDialog;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.mysql.jdbc.ResultSet;
import com.sun.javafx.iio.common.ImageDescriptor;
import com.sun.swing.internal.plaf.metal.resources.metal;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class RegisterStd{
    private Shell shell;
    private Text textStdNum;
    private Text textStdName;
	private Label lbStdPic;
	protected ImageRegistry imageRegistry;
	private Combo comboGrade;
	private Combo comboClass;
	private Department depart;
	private ArrayList<Department>departmentlist;
	private Combo comboDepartment;
	private Text textStdIn;
	private Student currStudent;
	private boolean newIS=true;
	
	public RegisterStd() {
		super();
		shell=new Shell();
		shell.setLayout(new GridLayout(8, false));
		new Label(shell, SWT.NONE);
		imageRegistry =new ImageRegistry();
		departmentlist=new ArrayList<>();
		
		ResultSet rSet=InitDB.getInitDb().getrSet("select *from department");
		try {
			while (rSet.next()) {
				departmentlist.add(new Department(rSet.getString(2)));				
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setText("\u5B66\u53F7");
		new Label(shell, SWT.NONE);
		
		textStdNum = new Text(shell, SWT.BORDER);
		textStdNum.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				String string=textStdNum.getText().trim();
				if (string!=null||!"".equals(string)&&string.matches("[0-9]++")) {
					try {
						org.eclipse.jface.resource.ImageDescriptor imageDescriptor=org.eclipse.jface.resource.ImageDescriptor.createFromURL(
								new URL("file:picStd/"+string+".jpg"));
						imageRegistry= new ImageRegistry();
						imageRegistry.put("std"+string, imageDescriptor);
						org.eclipse.jface.resource.ImageDescriptor imageDescriptor2= imageRegistry.getDescriptor("std"+string);
						if (imageDescriptor2!=null) {
							lbStdPic.setImage(imageDescriptor.createImage());
						}
					} catch (MalformedURLException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		});
		GridData gd_textStdNum = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_textStdNum.widthHint = 98;
		textStdNum.setLayoutData(gd_textStdNum);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("\u7167\u7247");
		new Label(shell, SWT.NONE);
		
		Composite composite = new Composite(shell, SWT.NONE);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 5);
		gd_composite.widthHint = 115;
		gd_composite.heightHint = 145;
		composite.setLayoutData(gd_composite);
		
		lbStdPic= new Label(composite, SWT.NONE);
		lbStdPic.setBounds(0, 10, 105, 135);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setText("\u59D3\u540D");
		new Label(shell, SWT.NONE);
		
		textStdName = new Text(shell, SWT.BORDER);
		textStdName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(shell, SWT.NONE);
		
		Button btnNewButton = new Button(shell, SWT.ARROW);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			   org.eclipse.swt.widgets.FileDialog fileDialog=new org.eclipse.swt.widgets.FileDialog(shell, SWT.SAVE);
			     fileDialog.setFilterExtensions(new String[]{"*.jpg","*.JPG"});
			     fileDialog.setFilterNames(new String[]{"jpeg文件(*.jpg)","JEPG文件(*.JPG)"});
			    String picName=fileDialog.open();
			    String saveName=textStdNum.getText().trim();
			    if (picName!=null&&!"".equals(picName)&&saveName!=null&&!"".equals(saveName)) {
					try {
						FileInputStream fileInputStream=new FileInputStream(picName);
						FileOutputStream  fileOutputStream=new FileOutputStream("PicStd/"+saveName+".jpg");
						int b=fileInputStream.read();
						while (b!=-1) {
							fileOutputStream.write(b);
							b=fileInputStream.read();
						}
						fileOutputStream.close();
						fileInputStream.close();
						lbStdPic.setImage(imageRegistry.getDescriptor("std"+saveName).createImage());
						shell.layout();
						} catch (FileNotFoundException e2) {
						// TODO: handle exception
					} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			}
		});
		btnNewButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnNewButton.setText("New Button");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setText("\u4E13\u4E1A");
		new Label(shell, SWT.NONE);
		
		comboDepartment = new Combo(shell, SWT.NONE);
		ResultSet rSet1=InitDB.getInitDb().getrSet("select*from department");
		try {
			while (rSet1.next()) {
			comboDepartment.add(rSet1.getString(2));
			}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		comboDepartment.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
					String thisDept=((Combo)e.getSource()).getText().trim();//获得当前专业对象的名称
					if (thisDept==null||"".equals(thisDept)) {
						MessageDialog.openConfirm(shell, "有错误.",   "请先选择专业");
						return;
					}
					comboGrade.removeAll();//初始化年级下拉框
					ResultSet resultSet=InitDB.getInitDb().getrSet("select grade from department_grade_class where departmentID="+Department.getFromDB(thisDept).getId());
					try {
						while (resultSet.next()) {
						comboGrade.add(resultSet.getInt(1)+"");//将专业下所有年级放入年级下拉框
						}
					} catch (SQLException e1) {
								e1.printStackTrace();
							}
					comboClass.removeAll();//初始化班级下拉框
			
			}
		});
		comboDepartment.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
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
		
		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setText("\u5E74\u7EA7");
		new Label(shell, SWT.NONE);
		
		comboGrade = new Combo(shell, SWT.NONE);
		comboGrade.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	   	comboGrade.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//为年级下拉框注册点击事件监听器
				comboClass.removeAll();//初始化班级下拉框
				String deptStr=comboDepartment.getText().trim();
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
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_6 = new Label(shell, SWT.NONE);
		lblNewLabel_6.setText("\u5B66\u4E60\u5174\u8DA3");
		new Label(shell, SWT.NONE);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER);
		GridData gd_scrolledComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 3);
		gd_scrolledComposite.widthHint = 92;
		gd_scrolledComposite.heightHint = 118;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		textStdIn = new Text(scrolledComposite, SWT.BORDER);
		scrolledComposite.setContent(textStdIn);
		scrolledComposite.setMinSize(textStdIn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_7 = new Label(shell, SWT.NONE);
		lblNewLabel_7.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblNewLabel_7.setText("\u73ED\u7EA7");
		new Label(shell, SWT.NONE);
		
		comboClass = new Combo(shell, SWT.NONE);
		comboClass.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
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
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveCurrStudent();
			}
		});
		button.setText("\u786E\u5B9A");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (newIS) {
					saveCurrStudent();
				}
				if (!newIS) {
					textStdNum.setText("");
					textStdName.setText("");
					comboDepartment.setText("");
					comboGrade.setText("");
					comboClass.setText("");
					lbStdPic.setImage(null);
					textStdIn.setText("");
				}
			}
		});
		button_1.setText("\u4E0B\u4E00\u4E2A ");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button button_2 = new Button(shell, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		button_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		button_2.setText("\u9000\u51FA");
		createContents();
	}

	
	protected void saveCurrStudent() {
		
		if (!newIS) 
		
			return;
		try {
			System.out.println(newIS);
			int stdNum = Integer.parseInt(textStdNum.getText().trim());
			if (Student.getFromDB(stdNum)!=null) {
				MessageDialog.openConfirm(shell, "学号重复", "该学号学生数据已经存在！");
				return;
			}
			String stdName=textStdName.getText().trim();
			updateDpart(comboDepartment);
			int departmentID=depart.getId();
			int grade=Integer.parseInt(comboGrade.getText().trim());
			int cClass=Integer.parseInt(comboClass.getText().trim());
			String stdPicImg="picStd/"+stdNum+".jpg";
			String stdInterest=textStdIn.getText()==null||textStdIn.getText().equals("")?"":textStdIn.getText();
			currStudent=new Student(stdNum, stdName, departmentID, grade, cClass, stdPicImg, stdInterest);
			if (currStudent!=null) {
				currStudent.insertToDB();
				new UserSet().addUser(new User(currStudent.getId()+"", "123", User.STUDENT));
			}
			currStudent=null;
			newIS=false;
		} catch (NullPointerException e) {
			e.printStackTrace();
			MessageDialog.openError(shell, "数据不完整", "学号，姓名，专业，年级和班级不能为空！");
		}catch (NumberFormatException e) {
			MessageDialog.openError(shell, "数据不正确", "学号，年级和班级只能为整数！");
		}
		
	}
	

	protected void updateDpart(Combo combo) {
		int selIndex=combo.getSelectionIndex();
		if (selIndex>-1) {
			String selText=combo.getItem(selIndex);
			depart=Department.getFromDB(selText);
		}
		
	}


	public Shell getShell() {
		return shell;
	}


	protected void createContents() {
		shell.setText("\u5B66\u751F\u6CE8\u518C ");
		shell.setSize(534, 501);

	}
	
}
