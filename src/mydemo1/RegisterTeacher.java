package mydemo1;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

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

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class RegisterTeacher{
    private Shell shell;
    private Text textTchNum;
    private Text textTchName;
    private Text textTchAge;
    private Text textAddr;
    private Text textIntro;
	private Label lbTchImg;
	private Combo comboTchDepart;
	private Group group;
	private ImageRegistry imageRegistry;
	private boolean newIS;
	private ArrayList<Department>departmentlist;
	private Department depart;
	private Teacher currTeacher;
	
	public  RegisterTeacher() {
		super();
		shell=new Shell();
		shell.setLayout(new GridLayout(8, false));
		new Label(shell, SWT.NONE);
		imageRegistry=new ImageRegistry();
		newIS=true;
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
		lblNewLabel.setText("\u5DE5\u53F7");
		new Label(shell, SWT.NONE);
		
		textTchNum = new Text(shell, SWT.BORDER);
		textTchNum.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				String string=textTchNum.getText().trim();
				if (string!=null||!"".equals(string)&&string.matches("[0-9]++")) {
					try {
						org.eclipse.jface.resource.ImageDescriptor imageDescriptor=org.eclipse.jface.resource.ImageDescriptor.createFromURL(
								new URL("file:picTch/"+string+".jpg"));
						imageRegistry= new ImageRegistry();
						imageRegistry.put("std"+string, imageDescriptor);
						org.eclipse.jface.resource.ImageDescriptor imageDescriptor2= imageRegistry.getDescriptor("tch"+string);
						if (imageDescriptor2!=null) {
							lbTchImg.setImage(imageDescriptor.createImage());
						}
					} catch (MalformedURLException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		});
		GridData gd_textTchNum = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_textTchNum.widthHint = 98;
		textTchNum.setLayoutData(gd_textTchNum);
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
		
	    lbTchImg = new Label(composite, SWT.NONE);
		lbTchImg.setBounds(0, 10, 105, 135);
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
		
		textTchName = new Text(shell, SWT.BORDER);
		textTchName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(shell, SWT.NONE);
		
		Button btnNewButton = new Button(shell, SWT.ARROW);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				org.eclipse.swt.widgets.FileDialog fileDialog=new org.eclipse.swt.widgets.FileDialog(shell, SWT.SAVE);
			     fileDialog.setFilterExtensions(new String[]{"*.jpg","*.JPG"});
			     fileDialog.setFilterNames(new String[]{"jpeg文件(*.jpg)","JEPG文件(*.JPG)"});
			    String picName=fileDialog.open();
			    String saveName=textTchNum.getText().trim();
			    if (picName!=null&&!"".equals(picName)&&saveName!=null&&!"".equals(saveName)) {
					try {
						FileInputStream fileInputStream=new FileInputStream(picName);
						FileOutputStream  fileOutputStream=new FileOutputStream("PicTch/"+saveName+".jpg");
						int b=fileInputStream.read();
						while (b!=-1) {
							fileOutputStream.write(b);
							b=fileInputStream.read();
						}
						fileOutputStream.close();
						fileInputStream.close();
						lbTchImg.setImage(imageRegistry.getDescriptor("std"+saveName).createImage());
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
		lblNewLabel_3.setText("\u6027\u522B");
		new Label(shell, SWT.NONE);
		
		group = new Group(shell, SWT.NONE);
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_group = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_group.widthHint = 101;
		gd_group.heightHint = 14;
		group.setLayoutData(gd_group);
		
		Button btnRadioButton = new Button(group, SWT.RADIO);
		btnRadioButton.setText("\u7537");
		
		Button button_3 = new Button(group, SWT.RADIO);
		button_3.setText("\u5973");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_9 = new Label(shell, SWT.NONE);
		lblNewLabel_9.setText("\u5E74\u9F84");
		new Label(shell, SWT.NONE);
		
		textTchAge = new Text(shell, SWT.BORDER);
		textTchAge.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_6 = new Label(shell, SWT.NONE);
		lblNewLabel_6.setText("\u7B80\u4ECB");
		new Label(shell, SWT.NONE);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER);
		GridData gd_scrolledComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 3);
		gd_scrolledComposite.widthHint = 92;
		gd_scrolledComposite.heightHint = 118;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		textIntro = new Text(scrolledComposite, SWT.BORDER);
		scrolledComposite.setContent(textIntro);
		scrolledComposite.setMinSize(textIntro.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_7 = new Label(shell, SWT.NONE);
		lblNewLabel_7.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblNewLabel_7.setText("\u4E13\u4E1A");
		new Label(shell, SWT.NONE);
		
		comboTchDepart = new Combo(shell, SWT.NONE);
		ResultSet rSet1=InitDB.getInitDb().getrSet("select*from department");
		try {
			while (rSet1.next()) {
			comboTchDepart.add(rSet1.getString(2));
			}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		comboTchDepart.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_10 = new Label(shell, SWT.NONE);
		lblNewLabel_10.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblNewLabel_10.setText("\u5730\u5740");
		new Label(shell, SWT.NONE);
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite_1 = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_scrolledComposite_1.widthHint = 107;
		gd_scrolledComposite_1.heightHint = 0;
		scrolledComposite_1.setLayoutData(gd_scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		textAddr = new Text(scrolledComposite_1, SWT.BORDER);
		scrolledComposite_1.setContent(textAddr);
		scrolledComposite_1.setMinSize(textAddr.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
				saveCurrTeacher();
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
					saveCurrTeacher();
					textTchNum.setText("");
					textTchName.setText("");
					group.setText("");
					textTchAge.setText("");
					comboTchDepart.setText("");
					lbTchImg.setImage(null);
					textIntro.setText("");
				}
				if (!newIS) {
					MessageDialog.openConfirm(shell, "信息重复", "请输入新的信息");
					textTchNum.setText("");
					textTchName.setText("");
					group.setText("");
					textTchAge.setText("");
					comboTchDepart.setText("");
					lbTchImg.setImage(null);
					textIntro.setText("");
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
	
	protected void saveCurrTeacher() {
		if (!newIS) 
			return;
		
		try {
			int tchNum = Integer.parseInt(textTchNum.getText().trim());
			if (Teacher.getFromDB(tchNum)!=null) {
				MessageDialog.openConfirm(shell, "工号重复", "该工号教师数据已经存在！");
				return;
			}
			String tchName=textTchName.getText().trim();
			Control[]actorList=group.getChildren();
			int intActor=0;
			for (intActor = 0; intActor < actorList.length;intActor ++) {
				Button button=(Button) actorList[intActor];
				if (button.getSelection()) {
					break;
				}
			} 
			String sex;
			if (intActor==0)
				 sex="男";
			else
				sex = "女";
			updateDpart(comboTchDepart);
			int departmentID=depart.getId();
			int age=Integer.parseInt(textTchAge.getText().trim());
			String tchAddr=textAddr.getText().trim();
			String tchPicImg="picTch/"+tchNum+".jpg";
			String tchIntro=textIntro.getText()==null||textIntro.getText().equals("")?"":textIntro.getText();
			currTeacher=new Teacher(tchNum, tchName, sex, age, departmentID,tchAddr, tchPicImg, tchIntro);
			if (currTeacher!=null) {
				currTeacher.insertToDB();
				new UserSet().addUser(new User(currTeacher.getId()+"", "456", User.TEACHER));
			}
			currTeacher=null;
			newIS=false;
		} catch (NullPointerException e1) {
			MessageDialog.openError(shell, "数据不完整", "工号，姓名，专业，性别，年龄不能为空！");
		}catch (NumberFormatException e2) {
			MessageDialog.openError(shell, "数据不正确", "工号，年龄只能为整数！");
		}
		
	}

	protected void updateDpart(Combo combo) {
		int selIndex=combo.getSelectionIndex();
		if (selIndex>-1) {
			String selText=combo.getItem(selIndex);
			depart= Department.getFromDB(selText);
		    
		}
		
	}
	
	
	public Shell getShell() {
		return shell;
	}

	protected void createContents() {
		shell.setText("\u6559\u5E08\u6CE8\u518C ");
		shell.setSize(534, 501);

	}
}