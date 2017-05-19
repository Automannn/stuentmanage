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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.sun.corba.se.spi.orb.StringPair;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StudentSelect {
    private Shell shell;
    private LinkedList<Teacher>tchList;
    private Teacher currTeacher;
    private LinkedList<Student>stdList;
	private Combo comboDeptSele;
	private Combo comboTchSele;
	private Composite compositeYesSeleList;
	private Composite compositeNoSeleList;
	private Button btnAdd;
	private Button btnDele;
	public StudentSelect() {
		super();
		shell=new Shell( SWT.SHELL_TRIM);
		shell.setSize(625, 477);
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.marginBottom = 30;
		gridLayout.marginTop = 30;
		gridLayout.marginRight = 30;
		gridLayout.marginLeft = 30;
		shell.setLayout(gridLayout);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 204;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("\u9009\u62E9\u6559\u5E08\uFF1A");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_1.widthHint = 206;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		lblNewLabel_1.setText("\u9009\u62E9\u8BFE\u7A0B\uFF1A");
		
		comboTchSele = new Combo(shell, SWT.NONE);
		comboTchSele.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addTchComboTtem();         //��ȡtchList.obj�ļ�����ȡ����ӵ�����ѡ��
		comboTchSele.addModifyListener(new ModifyListener() {		
			@Override   //����ѡ��ĵ������
			public void modifyText(ModifyEvent arg0) {
				if (!comboTchSele.getText().equals("")) {   //�ж�����ѡ���Ƿ񱻱༭
					int sidx=comboTchSele.getSelectionIndex();  //�õ�����ѡ�������
					String[] sName=comboTchSele.getItem(sidx).split(" ");  //�õ���ѡ���������Ϣ�����Կո�ָ�Ϊ�ַ���
					for (Teacher tch:tchList) {
						if (tch.getName().equals(sName[1])) {  //�ж���ѡ��ʦ�ĺϷ���
							currTeacher=tch;   //ʵ����һ����ǰ��ʦ����
							initCurrDeptList();  //��ʼ���γ�ѡ���б��
							addDeptComboTchItem();  //��ȡ��ǰ��ʦ�����пγ��б�����ӵ�������ѡ��
							break;
						}
					}
				}
				
			}

			
		});
		
		initStdList();  //��ʼ��ѧ���б�
		comboDeptSele = new Combo(shell, SWT.NONE);
		comboDeptSele.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		comboDeptSele.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (!comboTchSele.getText().trim().equals("")&&!comboDeptSele.getText().trim().equals("")&&comboDeptSele.getItemCount()>0) {   //�жϵ�ǰ��ʦ�Ϳγ��Ƿ�ѡ��
					int cidx=comboDeptSele.getSelectionIndex();   //�õ��γ������������
					String cName=comboDeptSele.getItem(cidx);    //�õ���ѡ��Ŀγ�
				    clearComposite(compositeNoSeleList);   //��ʼ��δѡѧ���б�
					clearComposite(compositeYesSeleList);   //��ʼ����ѡѧ���б�
					addStdNoSele(cName);  //���δѡ��γ̵�ѧ��
				}
				
			}
		});
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_label.widthHint = 532;
		label.setLayoutData(gd_label);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 2);
		gd_composite.heightHint = 267;
		gd_composite.widthHint = 537;
		composite.setLayoutData(gd_composite);
		
		Label lblNewLabel_4 = new Label(composite, SWT.NONE);
		GridData gd_lblNewLabel_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_4.widthHint = 247;
		lblNewLabel_4.setLayoutData(gd_lblNewLabel_4);
		lblNewLabel_4.setText("\u672A\u9009\u5B66\u751F");
		new Label(composite, SWT.NONE);
		
		Label lblNewLabel_5 = new Label(composite, SWT.NONE);
		GridData gd_lblNewLabel_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_5.widthHint = 237;
		lblNewLabel_5.setLayoutData(gd_lblNewLabel_5);
		lblNewLabel_5.setText("\u5DF2\u9009\u5B66\u751F");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scrolledComposite.widthHint = 213;
		gd_scrolledComposite.heightHint = 200;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		compositeNoSeleList = new Composite(scrolledComposite, SWT.NONE);
		compositeNoSeleList.setLayout(new FillLayout(SWT.VERTICAL));
		scrolledComposite.setContent(compositeNoSeleList);
		scrolledComposite.setMinSize(compositeNoSeleList.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.VERTICAL));
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.widthHint = 34;
		gd_composite_1.heightHint = 224;
		composite_1.setLayoutData(gd_composite_1);
		
		btnAdd = new Button(composite_1, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Control[] controlsL=compositeNoSeleList.getChildren();
				for (Control c:controlsL) {
					Button button=(Button) c;
					if (button.getSelection()) {			
						new Button(compositeYesSeleList, SWT.CHECK).setText(button.getText());
						button.dispose();
					}
				}
				compositeYesSeleList.layout();
				compositeNoSeleList.layout();
			}
		});
		btnAdd.setText("->");
		
		btnDele = new Button(composite_1, SWT.NONE);
		btnDele.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Control[] controls=compositeYesSeleList.getChildren();
				for (Control c:controls) {
					Button button= (Button) c;
					if (button.getSelection()) {
						new Button(compositeNoSeleList, SWT.CHECK).setText(button.getText());
						button.dispose();
					}
				}
				compositeYesSeleList.layout();
				compositeNoSeleList.layout();
			}
		});
		btnDele.setText("<-");
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scrolledComposite_1.heightHint = 182;
		gd_scrolledComposite_1.widthHint = 175;
		scrolledComposite_1.setLayoutData(gd_scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		compositeYesSeleList = new Composite(scrolledComposite_1, SWT.NONE);
		compositeYesSeleList.setLayout(new FillLayout(SWT.VERTICAL));
		scrolledComposite_1.setContent(compositeYesSeleList);
		scrolledComposite_1.setMinSize(compositeYesSeleList.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			    modifyStdCourse();  //Ϊ��ѡ��Ŀγ�ָ��ѧ������д��γ̣�����û�б���  ע�⣡��
				clearComposite(compositeNoSeleList);  //����Ϊ��ʼ������
				clearComposite(compositeYesSeleList);
				comboDeptSele.setText("");
				compositeNoSeleList.layout();
			}
		});
		btnNewButton_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton_1.setText("\u786E\u5B9A");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveStdList();//����γ���ѡ���ѧ����������Ϣд��ѧ���б�����ļ�
				shell.dispose();
			}

			
		});
		btnNewButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton.setText("\u5173\u95ED");
		
	}
	   void addDeptComboTchItem(){
		     try { 
		    	ArrayList<Course>currcourses=new ArrayList<>();
		    	currcourses=currTeacher.getCourseList();
		    	 for (Course currcourse:currcourses) {
					comboDeptSele.add(currcourse.getname());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}   	   
	   }	

	protected void addStdMessage() {
	try {
		ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("stdList.obj"));
		stdList=(LinkedList<Student>) objectInputStream.readObject();
		for (Student std:stdList) {
			comboDeptSele.add(std.getName());
		}
		objectInputStream.close();
	} catch (FileNotFoundException e) {
		// TODO: handle exception
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	void addTchComboTtem(){
		try {
			ObjectInputStream oiStream=new ObjectInputStream(new FileInputStream("tchList.obj"));
			tchList=(LinkedList<Teacher>) oiStream.readObject();
			for (Teacher tch:tchList) {
				comboTchSele.add(tch.getId()+" "+tch.getName());
			}
			oiStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	  void initStdList(){
		  try {
			ObjectInputStream objectInputStream =new ObjectInputStream(new FileInputStream("stdList.obj"));
			stdList=(LinkedList<Student>) objectInputStream.readObject();
			objectInputStream.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
	  }
	   void clearComposite(Composite composite){
		   Control[] controls=composite.getChildren();
		   for (int i = 0; i < controls.length; i++) {
			controls[i].dispose();
		}	   
	   }
	    void addStdNoSele(String cName){	    	
	    	ArrayList<Course>courseList=null;
	    	for (Student std:stdList) {    //����ѧ���б��е�ÿһ��ѧ��
				courseList =std.getCourseList();   //�õ�ÿһ��ѧ���Ŀγ��б�
				for (Course thisCourse:courseList) {   //����ÿ���γ��б�
					if (thisCourse.getname().equals(cName)&&thisCourse.gettecherID()==-1) {   //����ѡ��������ڲ����Ŀγ���ͬ�������ο���ʦʱ������
						new Button(compositeNoSeleList, SWT.CHECK).setText(std.getName());
						compositeNoSeleList.layout();
						break;
					  }
					}
				}
			}
	    void modifyStdCourse(){
	         ArrayList<Course> courseList;//ʵ����һ���γ��б�
	         String currCourseName=comboDeptSele.getText().trim(); //�õ���ѡ��Ŀγ̲�ʵ����һ����ǰ�γ�
	         Control[] controlsL=compositeYesSeleList.getChildren(); //ʵ����һ���ӿؼ����󼯺�
	         boolean thisDone=false;  
	         for (Control c:controlsL) {  //����ÿ���ӿռ�
				String stdName= ((Button)c).getText(); //�õ�ÿ���ӿؼ�������
				for (Student std:stdList) {  //����ѧ���б�
					if (std.getName().equals(stdName)) { //����ѧ���ĺϷ���
						courseList=std.getCourseList(); //�õ���ǰѧ���Ŀγ��б�
						for (Course course:courseList) {  //�����ÿγ��б�
							if (course.getname().equals(currCourseName)){  //����γ̵ĺϷ���
								course.settecherID(currTeacher.getId());  //���õ�ǰ�γ���ʦ
								thisDone=true; 
								break;
							}
						}
						if (thisDone) {  //�˳���ѭ����
							break;
						}
					}
					thisDone=false;//��ʼ������ֵ���Ա�����һ��ѭ��
				}
			}
	    
	    }
	    
	    void initCurrDeptList() {
	    	
				int cout=comboDeptSele.getItemCount();
			    System.out.println("������"+cout);
				comboDeptSele.removeAll();
	    	  }
	    private void saveStdList() {
			try {
				ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("stdList.obj"));
				objectOutputStream.writeObject(stdList);
			} catch (FileNotFoundException e) {
				// TODO: handle exception
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	
			
		
	    	
	public Shell getShell() {
		return shell;
	}

}
