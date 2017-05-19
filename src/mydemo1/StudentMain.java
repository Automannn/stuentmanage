package mydemo1;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mysql.jdbc.ResultSet;

import javafx.scene.control.TableView;
import mydemo1.TchScoreQuery.Filter2Range;
import mydemo1.TchScoreQuery.FilterEquales;
import mydemo1.TchScoreQuery.FilterHigh;
import mydemo1.TchScoreQuery.FilterLower;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StudentMain{
private Shell shell;
private Text textcondi1;
private Table table;
public Student currStudent;
private ArrayList<Studentscore>scoreList;
private Text textcondi2;
private TableViewer tableViewer;
private Combo comboCondition2;
private Combo comboCondition1;
public User user;
private Label lblNewLabelName;
	public StudentMain(User user) {
		super();
		this.user=user;
		this.currStudent=Student.getFromDB(Integer.parseInt(user.getName()));//得到当前的学生对象；
		shell =new  Shell();
		shell.setLayout(new GridLayout(9, false));
		scoreList=new ArrayList<>();	
		String sql="select*from student_course where studentID="+currStudent.getId();
		ResultSet rSet=InitDB.getInitDb().getrSet(sql);
		try {
			while (rSet.next()) {
				scoreList.add(Studentscore.getFromDB(currStudent.getId()));   //得到并且生成学生的分数列表
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.setEnabled(false);
		mntmNewItem.setText("\u6210\u7EE9\u6D4F\u89C8");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_1.setEnabled(false);
		mntmNewItem_1.setText("\u6309\u8BFE\u7A0B\u67E5\u627E");
		
		MenuItem mntmNewItem_2 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_2.setText("\u6309\u5206\u6570\u67E5\u627E");
		
		MenuItem mntmNewItem_3 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				new BrowserHelp();
			}
		});
		mntmNewItem_3.setText("\u5E2E\u52A9");
		
		MenuItem mntmNewItem_4 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell.dispose();
			}
		});
		mntmNewItem_4.setText("\u9000\u51FA");
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, false, 1, 1));
		lblNewLabel.setText("\u6210\u7EE9\uFF1A");
		
	    comboCondition1 = new Combo(shell, SWT.READ_ONLY);
		comboCondition1.setItems(new String[] {"''", ">", "<", "="});
		GridData gd_comboCondition1 = new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1);
		gd_comboCondition1.widthHint = 16;
		comboCondition1.setLayoutData(gd_comboCondition1);
		
		textcondi1 = new Text(shell, SWT.BORDER);
		GridData gd_textcondi1 = new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1);
		gd_textcondi1.widthHint = 93;
		textcondi1.setLayoutData(gd_textcondi1);
		new Label(shell, SWT.NONE);
		
		Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button btnRadioButtonAnd = new Button(group, SWT.RADIO);
		btnRadioButtonAnd.setText("\u4E0E");
		
		Button btnRadioButtonOr = new Button(group, SWT.RADIO);
		btnRadioButtonOr.setText("\u6216");
		
		comboCondition2 = new Combo(shell, SWT.READ_ONLY);
		comboCondition2.setItems(new String[] {"''", ">", "<", "="});
		GridData gd_comboCondition2 = new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1);
		gd_comboCondition2.widthHint = 16;
		comboCondition2.setLayoutData(gd_comboCondition2);
		
		textcondi2 = new Text(shell, SWT.BORDER);
		textcondi2.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FilterLower filterLower=new FilterLower();
				FilterHigh filterHigh=new FilterHigh();
				FilterEquales fiq=new FilterEquales();
				Filter2Range fi2=new Filter2Range();
				float condi1=-1;
				float condi2=-1;
				String opr1=null;
				String opr2=null;
				try {
					if (textcondi1.getText()!=null&&!"".equals(textcondi1.getText())) {
						condi1=Float.parseFloat(textcondi1.getText());
					}
					if (textcondi2.getText()!=null&&!"".equals(textcondi2.getText())) {
						condi2=Float.parseFloat(textcondi2.getText());
					}
					
				} catch (NumberFormatException e2) {
					// TODO: handle exception
					MessageDialog.openError(shell, "必须输入数值", "必须在该比较符旁边的文本框中输入一个数值。");
				}
				if (comboCondition1.getText()!=null&&!"".equals(comboCondition1.getText())) {
					opr1=comboCondition1.getText().trim();
				}
				if (comboCondition2.getText()!=null&&!"".equals(comboCondition2.getText())) {
					opr2=comboCondition2.getText().trim();
				}
				if (condi1!=-1||condi2!=-1) {
					if (condi1>condi2) {
						float tmp=condi1;
						condi1=condi2;
						condi2=tmp;
						String string=opr1;
						opr1=opr2;
						opr2=string;
					}
					if (("".equals(opr2)||opr2==null)&&("".equals(opr1)||opr1==null)) {
						clearFilters();
					}else if ((!"".equals(opr2)&&opr2!=null)&&("".equals(opr1)||opr1==null)) {
						if (opr2.equals("=")) {
							fiq.setLimit(condi2);
							clearFilters();
							tableViewer.addFilter(fiq);
							tableViewer.refresh();
						}else if (opr2.equals(">")) {
							filterLower.setLimit(condi2);
							clearFilters();
							tableViewer.setFilters(filterLower);
							tableViewer.refresh();
						}else if (opr2.equals("<")) {
							filterHigh.setLimit(condi2);
							clearFilters();
							tableViewer.addFilter(filterHigh);
							tableViewer.refresh();
						}
					}else if (btnRadioButtonAnd.getSelection()) {
						if (">".equals(opr1)&&">".equals(opr2)) {
							clearFilters();
							filterLower.setLimit(condi2);
							tableViewer.addFilter(filterLower);
						}else if ("<".equals(opr1)&&"<".equals(opr2)) {
							clearFilters();
							filterHigh.setLimit(condi1);
							tableViewer.addFilter(filterHigh);
						}if (">".equals(opr1)&&"<".equals(opr2)) {
							clearFilters();
							filterLower.setLimit(condi1);
							tableViewer.addFilter(filterLower);
							filterHigh.setLimit(condi2);
							tableViewer.addFilter(filterHigh);
						}if ("<".equals(opr1)&&">".equals(opr2)) {
							clearFilters();
							filterLower.setLimit(1000);
							tableViewer.addFilter(filterLower);
							filterHigh.setLimit(-1000);
							tableViewer.addFilter(filterHigh);
						}
						tableViewer.refresh();
					}else if (btnRadioButtonOr.getSelection()) {
						if (">".equals(opr1)&&">".equals(opr2)) {
							clearFilters();
							filterLower.setLimit(condi1);
							tableViewer.addFilter(filterLower);
						}else if ("<".equals(opr1)&&"<".equals(opr2)) {
							clearFilters();
							filterHigh.setLimit(condi2);
							tableViewer.addFilter(filterHigh);
						}if (">".equals(opr1)&&"<".equals(opr2)) {
							clearFilters();
						}if ("<".equals(opr1)&&">".equals(opr2)) {
							clearFilters();
							fi2.setLimitLow(condi1);
							fi2.setLimitHigh(condi2);
							tableViewer.addFilter(fi2);
						}
						tableViewer.refresh();
					}
				}
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1);
		gd_btnNewButton.widthHint = 69;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("\u67E5\u627E");
		
		lblNewLabelName = new Label(shell, SWT.NONE);
		lblNewLabelName.setText("欢迎"+currStudent.getName()+"同学登陆");
		lblNewLabelName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 17, SWT.NORMAL));
		lblNewLabelName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 9, 1));
		
		tableViewer=new TableViewer(shell,SWT.BORDER | SWT.FULL_SELECTION);
		table =tableViewer.getTable();
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(scoreList);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 9, 1);
		gd_table.heightHint = 262;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(165);
		tblclmnNewColumn.setText("\u8BFE\u7A0B\u540D");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(167);
		tblclmnNewColumn_1.setText("\u8BFE\u7A0B\u7C7B\u578B");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(159);
		tblclmnNewColumn_2.setText("\u6210\u7EE9 ");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(144);
		tblclmnNewColumn_3.setText("\u767B\u5206\u65F6\u95F4");
		tableViewer.setLabelProvider(new ITableLabelProvider() {
			
			@Override
			public void removeListener(ILabelProviderListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isLabelProperty(Object arg0, String arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addListener(ILabelProviderListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getColumnText(Object arg0, int arg1) {
				// TODO Auto-generated method stub			
				if (arg0 instanceof Studentscore) {
					Studentscore score=(Studentscore) arg0;
					if (arg1==0) {
						return Course.getFromDB(score.getCourseID()).getname();
					}else if (arg1==1) {
						return Course.getFromDB(score.getCourseID()).gettype();
					}else if (arg1==2) {
						return score.getScore()+"";
					}else if (arg1==3) {
						return score.getUpDatetime()+"";
					}
				}
				return null;
			}
			
			
			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
	
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		shell.setText("\u5B66\u751F\u6210\u7EE9\u7BA1\u7406\u7CFB\u7EDF--\u5B66\u751F\u5B50\u7CFB\u7EDF");
		shell.setSize(669, 418);

	}
	
	class FilterLower extends ViewerFilter{
		float limit;
		@Override
		public boolean select(Viewer arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			System.out.println("FilterLower");
			Studentscore stdScore=(Studentscore) arg2;
			if (stdScore.getScore()>limit) 
				return true;
				else 
			    return false;
		}
		public float getLimit() {
			return limit;
		}
		public void setLimit(float limit) {
			this.limit=limit;
		}
	}
	class FilterHigh extends ViewerFilter{
		float limit;
		@Override
		public boolean select(Viewer arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			Studentscore stdScore=(Studentscore) arg2;
			if (stdScore.getScore()<limit) 
				return true;
				else 
			    return false;
		}
		public float getLimit() {
			return limit;
		}
		public void setLimit(float limit) {
			this.limit=limit;
		}
	}
	class FilterEquales extends ViewerFilter{
		float limit;
		@Override
		public boolean select(Viewer arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			if (arg2 instanceof StdScore) {
				Studentscore stdScore=(Studentscore) arg2;
				if (Math.abs(stdScore.getScore()-limit)<0.01)
				return true;
				else 
			    return false;
			   }else
					return false;
				}
			
		public float getLimit() {
			return limit;
		}
		public void setLimit(float limit) {
			this.limit=limit;
		}
	}
	class Filter2Range extends ViewerFilter{
		float limitLow;
		float limitHigh;
		@Override
		public boolean select(Viewer arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			Studentscore stdScore=(Studentscore) arg2;
			if (stdScore.getScore()<limitLow) 
				return true;
				else if (stdScore.getScore()>limitHigh)
					return true;
				else
			    return false;
		}
		public float getlimitLow(){
			return limitLow;
		}
		public void setLimitLow(float limitLow) {
			this.limitLow=limitLow;
		}
		public float getlimitHigh(){
			return limitHigh;
		}
		public void setLimitHigh(float limitHigh) {
			this.limitHigh=limitHigh;
		}
	}

	public Shell getShell() {
		return shell;
	}

	public void clearFilters() {
		ViewerFilter[] filters=tableViewer.getFilters();
		for (int i=0;i<filters.length;i++) {
			tableViewer.removeFilter(filters[i]);
		}
	}
}
