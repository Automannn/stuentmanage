package mydemo1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import com.sun.org.apache.bcel.internal.generic.NEW;

import jdk.internal.dynalink.beans.StaticClass;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;

public class BrowserHelp {
	private Text textUrl;
	private Tray tray;
    public Shell shell;
    
	public BrowserHelp() {
		
		super();
		shell=new Shell();
		shell.setLayout(new GridLayout(3, false));
		shell.setSize(845, 696);
		Display display=Display.getDefault();
		System.out.println("i have run3");
		createShell();
		shell.layout();
		shell.open();
		ToolTip tip =new ToolTip(shell, SWT.BALLOON|SWT.ICON_INFORMATION);
		tray=shell.getDisplay().getSystemTray();
		if (tray!=null) {
			final TrayItem item=new TrayItem(tray, SWT.None);
			item.setToolTipText("学生成绩管理系统帮助");
		//	item.setImage(new Image(shell.getDisplay(), "ions/icon.jpg"));
			item.setToolTip(tip);
			final org.eclipse.swt.widgets.Menu menu =new org.eclipse.swt.widgets.Menu(shell,SWT.POP_UP);
			final MenuItem showMenu=new MenuItem(menu, SWT.PUSH);
			showMenu.setText("隐藏&h");
			showMenu.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					boolean showFlag=shell.isVisible();
					shell.setVisible(showFlag);
					showMenu.setText(showFlag?"显示&w":"隐藏&h");
					tip.setText("学生成绩管理系统帮助的托盘图标");
					tip.setVisible(true);
				}
			});
			MenuItem exitMenu=new MenuItem(menu, SWT.PUSH);
			exitMenu.setText("退出&x");
			exitMenu.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shell.dispose();
				}
			});
			item.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
									
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					shell.setVisible(!shell.isVisible());	
					
				}
			});
			item.addMenuDetectListener(new MenuDetectListener() {
				
				@Override
				public void menuDetected(MenuDetectEvent arg0) {
					// TODO Auto-generated method stub
					menu.setVisible(true);
				}
			});
			shell.addShellListener(new ShellAdapter() {
				@Override
				public void shellClosed(ShellEvent e) {
					// TODO Auto-generated method stub
					super.shellClosed(e);
					e.doit=false;
					shell.setVisible(false);
					showMenu.setText("显示&w");
					tip.setText("学生成绩管理系统帮助的托盘图标");
					tip.setMessage("右键单击图标，可以选择菜单");
					tip.setVisible(true);
				}
				 @Override
				public void shellIconified(ShellEvent e) {
					// TODO Auto-generated method stub
					super.shellIconified(e);
					shellClosed(e);
				}
			});
		}else {
			tip.setText("该系统不支持托盘效果");
			tip.setLocation(400,400);
		}
		while (!shell.isDisposed()) {  
            if (!display.readAndDispatch()) {  
                display.sleep();  
            }  
        } 
		System.out.println("i have run2");
		
		
		
	}

	
	
	 
		
	

	public Shell getShell() {
		return shell;
	}
	
	public void createShell() {
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("\u5730\u5740");
		
		textUrl = new Text(shell, SWT.BORDER);
		GridData gd_textUrl = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUrl.widthHint = 616;
		textUrl.setLayoutData(gd_textUrl);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		
		btnNewButton.setText("\u8F6C\u5230");
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		
		ToolItem tltmNewItemBack = new ToolItem(toolBar, SWT.NONE);
		
		tltmNewItemBack.setText("\u540E\u9000");
		
		ToolItem tltmNewItemNext = new ToolItem(toolBar, SWT.NONE);
		
		tltmNewItemNext.setText("\u524D\u8FDB");
		
		ToolItem tltmNewItemStop = new ToolItem(toolBar, SWT.NONE);
		
		tltmNewItemStop.setText("\u505C\u6B62");
		
		ToolItem tltmNewItemRefresh = new ToolItem(toolBar, SWT.NONE);
		
		tltmNewItemRefresh.setText("\u5237\u65B0");
		
		ToolItem toolItem = new ToolItem(toolBar, SWT.SEPARATOR);
		toolItem.setWidth(80);
		
		ToolItem tltmNewItemBackHead = new ToolItem(toolBar, SWT.NONE);
		
		tltmNewItemBackHead.setText("\u56DE\u9996\u9875");
		new Label(shell, SWT.NONE);
		
		final Browser browser = new Browser(shell, SWT.NONE);
		GridData gd_browser = new GridData(SWT.LEFT, SWT.CENTER, true, true, 2, 2);
		gd_browser.heightHint = 402;
		gd_browser.widthHint = 748;
		browser.setLayoutData(gd_browser);
		new Label(shell, SWT.NONE);
		tltmNewItemBackHead.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.setUrl("htttp://www.baidu.com");
			}
		});
		tltmNewItemRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.refresh();
			}
		});
		tltmNewItemStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.stop();
			}
		});
		tltmNewItemNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.forward();
			}
		});
		btnNewButton.addListener(SWT.Selection, new Listener() 
        { 
            public void handleEvent(Event event) 
            {  
            	Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					browser.setUrl(textUrl.getText().trim()); 
					System.out.println("i have run1");
				}
			});
                
            } 
        }); 
        
		tltmNewItemBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.back();
			}
		});
	}
	
}

