package mydemo1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;

public class Userlogin{
	private Text textuser;
	private Text textpass;
	private Button btnNewButtonok;
	private Button btnNewButtoncancel;
	private Label lblNewLabeluser;
	private Label lblNewLabelpass;
	private Group group;
	private Button btnRadioButtonstd;
	private Button btnRadioButtontch;
	private Button btnRadioButtonadmin;
	private Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {  //入口函数，程序的起点
		try {
			Display display = Display.getDefault();
			Userlogin thisclass = new Userlogin(display);
			thisclass.shell.open();   //创建登陆的界面以及它对应的类  保持呈现
			while (!thisclass.shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Userlogin(Display display) {
		shell=new Shell(display, SWT.SHELL_TRIM);
		shell.setText("\u7528\u6237\u767B\u5F55");
		shell.setSize(765, 413);
		shell.setVisible(false);
		shell.setToolTipText("\u8BF7\u8F93\u5165\u7528\u6237\u540D\u548C\u5BC6\u7801");
		GridLayout gridLayout = new GridLayout(3, true);
		gridLayout.marginHeight = 3;
		gridLayout.verticalSpacing = 20;
		shell.setLayout(gridLayout);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("华文琥珀", 15, SWT.NORMAL));
		GridData gd_lblNewLabel = new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1);
		gd_lblNewLabel.widthHint = 307;
		gd_lblNewLabel.heightHint = 49;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("\u5B66\u751F\u6210\u7EE9\u7BA1\u7406\u7CFB\u7EDF\u7528\u6237\u767B\u5F55");
		
	    lblNewLabeluser = new Label(shell, SWT.NONE);
	    lblNewLabeluser.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabeluser.setText("\u8D26\u53F7\uFF1A");
		new Label(shell, SWT.NONE);
		
		textuser = new Text(shell, SWT.BORDER);
		GridData gd_textuser = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textuser.widthHint = 183;
		textuser.setLayoutData(gd_textuser);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
	    lblNewLabelpass = new Label(shell, SWT.NONE);
	    lblNewLabelpass.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabelpass.setText("\u5BC6\u7801\uFF1A");
		new Label(shell, SWT.NONE);
		
		textpass = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		textpass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textpass.setTextLimit(12);
		
		group = new Group(shell, SWT.NONE);
		group.setText("\u6211\u662F\u4E00\u540D");
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_group = new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1);
		gd_group.widthHint = 242;
		gd_group.heightHint = 57;
		group.setLayoutData(gd_group);
		
		btnRadioButtonstd = new Button(group, SWT.RADIO);
		btnRadioButtonstd.setText("\u5B66\u751F");
		
		btnRadioButtontch = new Button(group, SWT.RADIO);
		btnRadioButtontch.setText("\u8001\u5E08");
		
		btnRadioButtonadmin = new Button(group, SWT.RADIO);
		btnRadioButtonadmin.setText("\u7BA1\u7406\u5458");
		
	    btnNewButtoncancel = new Button(shell, SWT.NONE);
	    btnNewButtoncancel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
	    btnNewButtoncancel.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		shell.dispose();
	    	}
	    });
	    btnNewButtoncancel.setText("\u53D6\u6D88");
		new Label(shell, SWT.NONE);
		
		btnNewButtonok = new Button(shell, SWT.NONE);
		btnNewButtonok.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButtonok.setText("\u767B\u5F55");
		
        textuser.addModifyListener(new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent e) {
				String txt=textuser.getText()==null?"":textuser.getText().trim();
				if (!txt.matches("[a-zA-Z]*+[\\d]*[a-zA-Z]*+[\\d]*+")) {
					MessageBox messageBox=new MessageBox(shell);
					messageBox.setMessage("有非法字符！\n用户名只能由字母和数字组成。");
					messageBox.open();
					textuser.setText("");
				 }
			}
		});
        
        btnNewButtonok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Control[]actorList=group.getChildren();
				int intActor=0;
				for (intActor = 0; intActor < actorList.length;intActor ++) {
					Button button=(Button) actorList[intActor];
					if (button.getSelection()) {
						break;
					}
				}   //遍历每一个被选中的单选框组件
				User user=new User(textuser.getText().trim(), textpass.getText().trim(), intActor);  //生成一个用户对象实例
				Shell oldshell=null;
				if (new UserSet().isValid(user)) {  //判断用户的合法性
					oldshell=shell;
					if (user.getJob()==User.STUDENT) {
						shell=new StudentMain(user).getShell();
					}
				if (user.getJob()==User.TEACHER) {
						shell=new TchScore(user).getShell();
					}  
			    if (user.getJob()==User.ADMIN) {
						shell=new AdminMana(user).getShell();
					}
					oldshell.dispose();
					shell.open();  //根据用户身份进入不同的用户界面
				}
				else {
					textuser.setText("");
					textpass.setText("");
				}
			}
		});
	}
       

}
