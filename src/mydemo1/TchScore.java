package mydemo1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

public class TchScore {
private Shell shell;
public User user;
private Label lblNewLabel;
	public TchScore(User user) {
		super();
		this.user=user;
		shell=new Shell();
		shell.setLayout(new GridLayout(1, false));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		
		mntmNewItem.setText("\u6210\u7EE9\u5F55\u5165");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem_1.setText("\u6210\u7EE9\u67E5\u8BE2");
		
		Menu menu_1 = new Menu(mntmNewItem_1);
		mntmNewItem_1.setMenu(menu_1);
		
		MenuItem mntmNewItem_6 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_6.setEnabled(false);
		mntmNewItem_6.setText("\u6D4F\u89C8 ");
		
		MenuItem menuItem = new MenuItem(menu_1, SWT.NONE);
		menuItem.setEnabled(false);
		menuItem.setText("\u6309\u5206\u6570\u67E5\u8BE2");
		
		MenuItem mntmNewItem_7 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_7.setEnabled(false);
		mntmNewItem_7.setText("\u6309\u5B66\u751F\u67E5\u8BE2");
		
		MenuItem mntmNewItem_2 = new MenuItem(menu, SWT.NONE);
		
		mntmNewItem_2.setText("\u6210\u7EE9\u7EDF\u8BA1");
		
		MenuItem mntmNewItem_3 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_3.setEnabled(false);
		mntmNewItem_3.setText("\u6210\u7EE9\u8F93\u51FA");
		
		MenuItem mntmNewItem_4 = new MenuItem(menu, SWT.NONE);
		
		mntmNewItem_4.setText("\u5E2E\u52A9");
		
		MenuItem mntmNewItem_5 = new MenuItem(menu, SWT.NONE);
		
		mntmNewItem_5.setText("\u9000\u51FA");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 24, SWT.NORMAL));
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, true, true, 1, 1));
		lblNewLabel_1.setText("\u5B66\u751F\u6210\u7EE9\u7BA1\u7406\u7CFB\u7EDF");
		
	    lblNewLabel = new Label(shell, SWT.NONE);
	    lblNewLabel.setText("欢迎"+Teacher.getFromDB(Integer.parseInt(user.getName())).getName()+"老师使用");
		lblNewLabel.setFont(SWTResourceManager.getFont("华文行楷", 34, SWT.NORMAL));
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			shell=new TeaherSoreEdit(Integer.parseInt(user.getName())).getShell();
			shell.open();
			}
		});
		mntmNewItem_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell.dispose();
			}
		});
		mntmNewItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				new BrowserHelp();
			}
		});
		mntmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell=new TchScoreQuery(Integer.parseInt(user.getName())).getShell();
				shell.open();
			}
		});
		
		createContents();
	}

	
	protected void createContents() {
		shell.setText("SWT Application");
		shell.setSize(662, 405);

	}


	public Shell getShell() {
		return shell;
	}
	
}
