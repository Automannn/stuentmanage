package mydemo1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;

public class TchscoreMana{
	private Shell shell;
	public User user;
	private Text textStdNum;
	private Text textStdName;
	private Text textClassName;
	private Text textScores;
	private Teacher currTeacher;
	private Combo comboTchCourse;
	private Combo comboStdList;
	private String currCourseName;

	
	public TchscoreMana(User user) {
		super();
		this.user=user;
		shell=new Shell(SWT.SHELL_TRIM);
		shell.setSize(613, 749);
		shell.setLayout(new GridLayout(2, false));
		
		int userID=Integer.parseInt(user.getName());
		LinkedList<Teacher>tchList=(LinkedList<Teacher>) readObjFile("tchList.obj");  //��ȡtchList.obj�ļ��еĽ�ʦ�б�,���ؽ�ʦ�б�
		for (Teacher tch:tchList) {
			if (tch.getId()==userID) {
				currTeacher=tch;
				break;
			}
		}
		System.out.println("��ǰ��ʦ�ǣ�"+currTeacher.getName());
		LinkedList<Student>stdList=(LinkedList<Student>) readObjFile("stdList.obj");//��ȡstdList.obj�ļ��е�ѧ���б�����ѧ���б�
		
		
		Composite composite = new Composite(shell, SWT.NONE);
		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.verticalSpacing = 30;
		gl_composite.marginTop = 60;
		composite.setLayout(gl_composite);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
		gd_composite.heightHint = 369;
		gd_composite.widthHint = 278;
		composite.setLayoutData(gd_composite);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("\u8BFE\u7A0B");
		
		comboTchCourse = new Combo(composite, SWT.READ_ONLY);
		comboTchCourse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (currTeacher!=null) {  //�жϵ�ǰ��ʦ�Ƿ����
			ArrayList<Course>courseList=currTeacher.getCourseList();  //ʵ����һ���γ��б���󣬲��õ���ǰ��ʦ�Ŀγ��б�
			for (Course course:courseList) {  //��������ʦ�Ŀγ��б�
				comboTchCourse.add(course.getname());  //������ʦ�Ŀγ��б���ӵ�����ѡ��
			}
		}
		comboTchCourse.addModifyListener(new ModifyListener() {
			
			

			@Override
			public void modifyText(ModifyEvent arg0) {
				currCourseName=comboTchCourse.getItem(comboTchCourse.getSelectionIndex());  //ʵ����һ����ǰ�γ̣���ѡ�еĿγ̸�����
				ArrayList<Course>courseList=null;  //ʵ����һ���տγ��б�
				comboStdList.removeAll();//�Ƴ�ѧ��ѡ���µ�����ѧ���б�
				textStdName.setText("");//��ʼ��
				textScores.setText("");//��ʼ��
				for (Student std:stdList) {  //����ѧ���б��е�ÿ��ѧ��
					courseList=std.getCourseList();  //�õ�ÿ��ѧ���Ŀγ��б�
					for (Course course:courseList) {//����ÿ��ѧ���Ŀγ��б�
						if (course.getname().equals(currCourseName)&&course.gettecherID()==currTeacher.getId()) {  //����γ̵��ο���ʦ���
							System.out.println("i have run");
							comboStdList.add(std.getId()+" " +std.getName());  //�õ���Щѧ���б�����ӵ�����ѡ��
							break;
						}
					}
				}
			}
		});
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("\u5B66\u751F");
		
		comboStdList = new Combo(composite, SWT.READ_ONLY);
		comboStdList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboStdList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String[] stdStr=comboStdList.getItem(comboStdList.getSelectionIndex()).split(" ");  //�Կո�Ϊ�磬�ָǰ��ѡѧ������Ϣ
				textStdNum.setText(stdStr[0]);  //�õ���ѧ����ѧ��
				textStdName.setText(stdStr[1]);  //�õ���ѧ��������
				textClassName.setText(currCourseName); //�õ���ѧ���ĵ�ǰ�γ�
				ArrayList<Course>courseList=null;  //ʵ����һ���տγ��б�
				boolean done=false;  //ʵ����һ���������ͱ�������ѭ����
				for (Student std:stdList) {  //����ѧ���б��µ�ÿ��ѧ��
					courseList=std.getCourseList();  //�õ�ÿ��ѧ���Ŀγ��б�
					for (Course course:courseList) {  //����ÿ��ѧ���γ��б��µ�ÿ���γ�
						if (std.getId()==Integer.parseInt(textStdNum.getText())&&course.getname().equals(textClassName.getText())) {  //����ѧ���ĺϷ���
							textScores.setText(""+course.getscore());  //�õ��ý�ʦΪ�ÿγ�����ķ���
							done=true; //����ֵΪ��
							break;
						}
					}
					if (done) {  //�����е�ѭ����ִ�н������˳�������ѭ���壻ʵ����ÿ��ִֻ��һ���ڲ�ѭ��
						break;
					}
				}
					
		}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			});
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		GridLayout gl_composite_1 = new GridLayout(2, false);
		gl_composite_1.verticalSpacing = 30;
		gl_composite_1.marginTop = 50;
		composite_1.setLayout(gl_composite_1);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.heightHint = 374;
		gd_composite_1.widthHint = 274;
		composite_1.setLayoutData(gd_composite_1);
		
		Label lblNewLabel_2 = new Label(composite_1, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("           \u5B66\u53F7\uFF1A");
		
		textStdNum = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		textStdNum.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_3 = new Label(composite_1, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("\u59D3\u540D\uFF1A");
		
		textStdName = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		textStdName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_4 = new Label(composite_1, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("\u8BFE\u7A0B\uFF1A");
		
		textClassName = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		textClassName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_5 = new Label(composite_1, SWT.NONE);
		lblNewLabel_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_5.setText("\u6210\u7EE9\uFF1A");
		
		textScores = new Text(composite_1, SWT.BORDER);
		textScores.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ArrayList<Course>courseList=null;  //ʵ����һ���տγ��б�
				boolean done=false;  //ʵ����һ���������ͱ�������ѭ����
				for (Student std:stdList) {  //����ѧ���б��е�ÿһ��
					courseList =std.getCourseList();  //�õ�ÿ��ѧ���Ŀγ��б�
					for (Course course:courseList) {  //�����ÿγ��б�
						if (std.getId()==Integer.parseInt(textStdNum.getText())&&course.getname().equals(textClassName.getText())) {  //����Ϸ���
							course.setscore(Float.parseFloat(textScores.getText()));  //Ϊ�ÿγ̴��
							done=true;  //����ֵΪ��
							break;
						}
					}
					if (done) {  //�˳�ѭ����
						break;
					}
				}
			}
		});
		btnNewButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton.setText("\u63D0\u4EA4");
		
		Button btnNewButton_1 = new Button(composite_1, SWT.NONE);
		btnNewButton_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton_1.setText("\u9000\u51FA");
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("stdList.obj"));
					oos.writeObject(stdList);
					oos.close();
				} catch (FileNotFoundException e1) {
					// TODO: handle exception
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				shell.dispose();
			}
			
		});
		
	}


	Object readObjFile(String file) {
		 Object object = null;
		 try {
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
			object=ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return object;		
	}


	public Shell getShell() {
		return shell;
	}

	
}
