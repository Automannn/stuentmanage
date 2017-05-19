package mydemo1;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;

import java.sql.SQLException;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mysql.jdbc.ResultSet;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.internal.win32.TCHITTESTINFO;

public class AdminMana{
       private Shell shell;
       public User user;

	public AdminMana(User user) {
		super();
		this.user=user;
		shell=new Shell();
		shell.setLayout(new GridLayout(1, false));
		new Label(shell, SWT.NONE);
		
		ToolBar toolBar_1 = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		GridData gd_toolBar_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_toolBar_1.widthHint = 554;
		toolBar_1.setLayoutData(gd_toolBar_1);
		
		ToolItem tltmNewItem_5 = new ToolItem(toolBar_1, SWT.NONE);
		tltmNewItem_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
						
						shell=new RegisterStd().getShell();
						shell.open();
						
			}
		});
		tltmNewItem_5.setText("\u5B66\u751F\u6CE8\u518C");
		
		ToolItem tltmNewItem_6 = new ToolItem(toolBar_1, SWT.NONE);
		tltmNewItem_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell oldShell=shell;
				oldShell.close();
				shell=new RegisterTeacher().getShell();
				shell.open();
			}
		});
		tltmNewItem_6.setText("\u6559\u5E08\u6CE8\u518C");
		
		ToolItem tltmNewItem_7 = new ToolItem(toolBar_1, SWT.NONE);
		tltmNewItem_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell oldShell=shell;
				oldShell.close();
				shell=new StdSelect().getShell();
				shell.open();
			}
		});
		tltmNewItem_7.setText("\u73ED\u7EA7\u6392\u8BFE");
		
		ToolItem tltmNewItem_8 = new ToolItem(toolBar_1, SWT.NONE);
		tltmNewItem_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
                       	Display.getDefault().syncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								new BrowserHelp();
							}
						});	
			}
		});
		tltmNewItem_8.setText("\u7CFB\u7EDF\u5E2E\u52A9");
		
		ToolItem tltmNewItem_9 = new ToolItem(toolBar_1, SWT.NONE);
		tltmNewItem_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();			}
		});
		tltmNewItem_9.setText("\u9000\u51FA\u7CFB\u7EDF");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
		GridData gd_lblNewLabel = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 313;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("\u6B22\u8FCE\u4F7F\u7528\u5B66\u751F\u6210\u7EE9\u7BA1\u7406\u7CFB\u7EDF");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 27, SWT.NORMAL));
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("\u6559\u52A1\u7BA1\u7406");
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem.setText("\u7CFB\u7EDF\u8BBE\u7F6E");
		
		Menu menu_1 = new Menu(mntmNewItem);
		mntmNewItem.setMenu(menu_1);
		
		MenuItem mntmNewItem_5 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_5.setText("\u4E13\u4E1A\u8BBE\u7F6E");
		mntmNewItem_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

				shell=new DepartmentManage().getShell();
				shell.open();
			}
		});
		
		MenuItem mntmNewItem_6 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell=new GradeClassManage().getShell();
				shell.open();
			}
		});
		mntmNewItem_6.setText("\u73ED\u7EA7\u8BBE\u7F6E");
		
		MenuItem mntmNewItem_7 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell=new CourseManage().getShell();
				shell.open();
			}
		});
		mntmNewItem_7.setText("\u8BFE\u7A0B\u8BBE\u7F6E");
		
		MenuItem mntmNewItem_8 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
			
		});
		mntmNewItem_8.setText("\u9000\u51FA(&x)");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem_1.setText("\u7528\u6237\u6CE8\u518C");
		
		Menu menu_2 = new Menu(mntmNewItem_1);
		mntmNewItem_1.setMenu(menu_2);
		
		MenuItem mntmNewItem_17 = new MenuItem(menu_2, SWT.NONE);
		mntmNewItem_17.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				shell=new RegisterStd().getShell();
				shell.open();
			}
		});
		mntmNewItem_17.setText("\u5B66\u751F\u6CE8\u518C");
		
		MenuItem mntmNewItem_9 = new MenuItem(menu_2, SWT.CASCADE);
		mntmNewItem_9.setText("\u5B66\u751F\u67E5\u8BE2");
		
		Menu menu_7 = new Menu(mntmNewItem_9);
		mntmNewItem_9.setMenu(menu_7);
		
		MenuItem mntmNewItem_28 = new MenuItem(menu_7, SWT.NONE);
		mntmNewItem_28.setEnabled(false);
		mntmNewItem_28.setText("\u6309\u5B66\u53F7");
		
		MenuItem mntmNewItem_29 = new MenuItem(menu_7, SWT.NONE);
		mntmNewItem_29.setEnabled(false);
		mntmNewItem_29.setText("\u6309\u73ED\u7EA7 ");
		
		MenuItem mntmNewItem_30 = new MenuItem(menu_7, SWT.NONE);
		mntmNewItem_30.setEnabled(false);
		mntmNewItem_30.setText("\u6309\u6210\u7EE9");
		
		MenuItem mntmNewItem_10 = new MenuItem(menu_2, SWT.NONE);
		mntmNewItem_10.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				shell=new RegisterTeacher().getShell();
				shell.open();
			}
		});
		mntmNewItem_10.setText("\u6559\u5E08\u6CE8\u518C");
		
		MenuItem mntmNewItem_11 = new MenuItem(menu_2, SWT.CASCADE);
		mntmNewItem_11.setText("\u6559\u5E08\u67E5\u8BE2");
		
		Menu menu_3 = new Menu(mntmNewItem_11);
		mntmNewItem_11.setMenu(menu_3);
		
		MenuItem mntmNewItem_13 = new MenuItem(menu_3, SWT.NONE);
		mntmNewItem_13.setEnabled(false);
		mntmNewItem_13.setText("\u6309\u5DE5\u53F7 ");
		
		MenuItem mntmNewItem_14 = new MenuItem(menu_3, SWT.NONE);
		mntmNewItem_14.setEnabled(false);
		mntmNewItem_14.setText("\u6309\u59D3\u540D ");
		
		MenuItem mntmNewItem_15 = new MenuItem(menu_3, SWT.NONE);
		mntmNewItem_15.setEnabled(false);
		mntmNewItem_15.setText("\u6309\u4E13\u4E1A");
		
		MenuItem mntmNewItem_16 = new MenuItem(menu_3, SWT.NONE);
		mntmNewItem_16.setEnabled(false);
		mntmNewItem_16.setText("\u6309\u8BFE\u7A0B");
		
		MenuItem mntmNewItem_12 = new MenuItem(menu_2, SWT.BAR);
		mntmNewItem_12.setText("New Item");
		
		MenuItem mntmNewItem_2 = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem_2.setText("\u9009\u8BFE\u6392\u8BFE");
		
		Menu menu_4 = new Menu(mntmNewItem_2);
		mntmNewItem_2.setMenu(menu_4);
		
		MenuItem mntmNewItem_18 = new MenuItem(menu_4, SWT.NONE);
		mntmNewItem_18.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				shell=new StdSelect().getShell();
				shell.open();
			}
			
		});
		mntmNewItem_18.setText("\u7BA1\u7406\u5458\u6392\u8BFE");
		
		MenuItem mntmNewItem_19 = new MenuItem(menu_4, SWT.NONE);
		mntmNewItem_19.setEnabled(false);
		mntmNewItem_19.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				
				shell.open();
			}
			
		});
		mntmNewItem_19.setText("\u5B66\u751F\u9009\u8BFE");
		
		MenuItem mntmNewItem_3 = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem_3.setText("\u7CFB\u7EDF\u7BA1\u7406 ");
		
		Menu menu_5 = new Menu(mntmNewItem_3);
		mntmNewItem_3.setMenu(menu_5);
		
		MenuItem mntmNewItem_21 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_21.setEnabled(false);
		mntmNewItem_21.setText("\u4FEE\u6539\u5BC6\u7801");
		
		MenuItem mntmNewItem_20 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_20.setEnabled(false);
		mntmNewItem_20.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog inputDialog=new InputDialog(shell, "请输入信息", "请输入要删除的学生学号", null, null);
			    String str=inputDialog.getValue();
			    ResultSet rSet=InitDB.getInitDb().getrSet("select*from student where id="+Integer.parseInt(str));
			    try {
					while (rSet.next()) {
						String sql="delete *from stuent where id="+Integer.parseInt(str);
						InitDB.getInitDb().getStatement().executeUpdate(sql);
						return;
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			    
			    ResultSet resultSet=InitDB.getInitDb().getrSet("select*from student_course where studentID="+Integer.parseInt(str));
			    try {
					while (resultSet.next()) {
						String sql="delete *from stuent_course where studentID="+Integer.parseInt(str);
						InitDB.getInitDb().getStatement().executeUpdate(sql);
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		mntmNewItem_20.setText("\u5B66\u751F\u9500\u6237");
		
		MenuItem mntmNewItem_22 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_22.setEnabled(false);
		mntmNewItem_22.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog inputDialog=new InputDialog(shell, "请输入信息", "请输入要删除的教师工号", null, null);
			    String str=inputDialog.getValue();
			    ResultSet rSet=InitDB.getInitDb().getrSet("select*from teacher where id="+Integer.parseInt(str));
			    try {
					while (rSet.next()) {
						String sql="delete *from teacher where id="+Integer.parseInt(str);
						InitDB.getInitDb().getStatement().executeUpdate(sql);
						return;
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			    
			    ResultSet rSet1=InitDB.getInitDb().getrSet("select*from teacher_course where teacherID="+Integer.parseInt(str));
			    try {
					while (rSet1.next()) {
						String sql="delete *from teacher_course where teacherID="+Integer.parseInt(str);
						InitDB.getInitDb().getStatement().executeUpdate(sql);
						return;
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		});
		mntmNewItem_22.setText("\u6559\u5E08\u9500\u6237");
		
		MenuItem mntmNewItem_23 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_23.setEnabled(false);
		mntmNewItem_23.setText("\u6570\u636E\u5907\u4EFD");
		
		MenuItem mntmNewItem_24 = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem_24.setEnabled(false);
		mntmNewItem_24.setText("\u6570\u636E\u6062\u590D");
		
		MenuItem mntmNewItem_4 = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem_4.setText("\u5E2E\u52A9");
		
		Menu menu_6 = new Menu(mntmNewItem_4);
		mntmNewItem_4.setMenu(menu_6);
		
		MenuItem mntmNewItem_25 = new MenuItem(menu_6, SWT.NONE);
		mntmNewItem_25.setEnabled(false);
		mntmNewItem_25.setText("\u5E2E\u52A9\u5185\u5BB9");
		
		MenuItem mntmNewItem_26 = new MenuItem(menu_6, SWT.NONE);
		mntmNewItem_26.setEnabled(false);
		mntmNewItem_26.setText("\u4F7F\u7528\u624B\u518C");
		
		MenuItem mntmNewItem_27 = new MenuItem(menu_6, SWT.NONE);
		mntmNewItem_27.setEnabled(false);
		mntmNewItem_27.setText("\u5173\u4E8E...");
		createContents();
	}

	protected void createContents() {
		shell.setText("\u5B66\u751F\u6210\u7EE9\u7BA1\u7406\u7CFB\u7EDF");
		shell.setSize(577, 398);

	}

	public Shell getShell() {
		return shell;
	}
}
