package mydemo1;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.mysql.jdbc.ResultSet;
import com.sun.org.apache.xml.internal.security.Init;
import com.sun.prism.paint.Color;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;

public class ScoreChart{

	private int cWidth;
	private int cHeight;
	private Shell shell;
	private Student student;
	private Canvas canvas;

	
	public ScoreChart(Student student) throws SQLException {
		super();
		this.student=student;
		shell=new Shell();
		shell.setText("\u6210\u7EE9\u76F4\u65B9\u56FE");
		shell.setSize(786, 546);
		shell.setLayout(new GridLayout(9, false));
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("\u4E13\u4E1A\uFF1A");
		
		Combo comboDepart = new Combo(shell, SWT.NONE);
		String sql="select*from department";
		ResultSet rSet=InitDB.getInitDb().getrSet(sql);
		try {
			while (rSet.next()) {
				comboDepart.add(rSet.getString(2));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		comboDepart.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("\u8BFE\u7A0B\uFF1A");
		
		Combo comboDept = new Combo(shell, SWT.NONE);
		String sql1="select* from course where course.id=student_course.courseID and student_course.studentID=student.id and student.departmentID="+rSet.getInt(1);
		ResultSet rSet2=InitDB.getInitDb().getrSet(sql1);
		try {
			while (rSet2.next()) {
				comboDept.add(rSet2.getString(2));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		GridData gd_combo_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_combo_1.widthHint = 254;
		comboDept.setLayoutData(gd_combo_1);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("\u5E74\u7EA7\uFF1A");
		
		Combo comboGrade = new Combo(shell, SWT.NONE);
		String sql2="select grade from student where student.id=student_course.studentID and student.departmentID="+rSet.getInt(1)+"and student_course.courseID="
				+rSet2.getInt(1);
		ResultSet rSet3=InitDB.getInitDb().getrSet(sql2);
		try {
			while (rSet2.next()) {
				comboGrade.add(rSet3.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		comboGrade.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("\u73ED\u7EA7\uFF1A");
		
		Combo comboClass = new Combo(shell, SWT.NONE);
		String sql3="select class from student where student.departmentID="+rSet.getInt(1)+"and student.id=student_course.studentID and student_course.courseID="
				+rSet2.getInt(1)+"and grade="+rSet3.getInt(1);
		ResultSet rSet4=InitDB.getInitDb().getrSet(sql3);
		try {
			while (rSet4.next()) {
				comboClass.add(rSet4.getInt(1)+"");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		GridData gd_comboClass = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_comboClass.widthHint = 71;
		comboClass.setLayoutData(gd_comboClass);
		comboClass.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (comboDept.getText()==null||"".equals(comboDept.getText())||comboDepart.getText()==null||"".equals(comboDepart.getText())
						||comboGrade.getText()==null||"".equals(comboGrade.getText())||comboClass.getText()==null||"".equals(comboClass.getText())) {
					MessageDialog.openError(shell, "数据不完整", "必须选择专业，课程，年级和班级");
				}else {
					try {
						ResultSet resultSet=InitDB.getInitDb().getrSet("select score from student,student_course where departmentID="+rSet.getInt(2)+"and grade="
								+Integer.parseInt(comboGrade.getText())+"and class="+Integer.parseInt(comboClass.getText())+"and courseID="+rSet2.getInt(2)
								+"and studentID=student.id");
						try {
							//for (int i = 0; i < frequency.length; i++) { frequency[i]=0}
							int total=0;
							while (resultSet.next()) {
								float score =resultSet.getFloat(1);
								total++;
								if (score<60) {
									//frequency[0]++;
								}if (score<70) {
									//frequency[1]++;
								}if (score<80) {
									//frequency[2]++;
								}else if (score<90) {
									//frequency[3]++;
								}else {
									//frequency[4]++;
								}
								canvas.redraw();
							}
						} catch (SQLException e1) {
							// TODO: handle exception
							e1.printStackTrace();
						}
					} catch (NumberFormatException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		canvas = new Canvas(shell, SWT.NONE);
		GridData gd_canvas = new GridData(SWT.CENTER, SWT.CENTER, false, true, 9, 1);
		gd_canvas.heightHint = 484;
		gd_canvas.widthHint = 750;
		canvas.setLayoutData(gd_canvas);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				cWidth=canvas.getClientArea().width;
				cHeight=canvas.getClientArea().height;
				drawChart(e.gc);
				shell.layout();
			}

			
		});

	}

	/**
	 * Create contents of the shell.
	 */
	private void drawChart(GC gc) {
		ArrayList<Course>courseList=student.getCourseList();
		float score;
		String name =null;
		int num = courseList.size();
		int rWidth =(int) ((cWidth/(num+1))*0.9);
		int rSpace=cWidth/(num+1)-rWidth;
		int x1=50+rSpace,y1=cHeight-50,rHeight=0;
		gc.drawLine(50, cHeight-50, cWidth, cHeight-50);
		gc.drawLine(50, 20, 50, cHeight-50);
		int perHeight=(cHeight-70)/10;
		for (int k=15;k<cHeight-50;k=k+perHeight) {
			int cy=110-((k-15)/perHeight+1)*10;
			String str=""+(cy<10?"   "+cy:(cy<100?""+cy:cy));
			gc.drawString(str, 20, k+5);
			gc.drawLine(45, k+11, 50, k+11);
		}
		for (int i=0;i<num;i++) {
			name =courseList.get(i).getname();
			score=courseList.get(i).getscore();
			rHeight=(int) ((cHeight-70)/100*score);
			y1=y1-rHeight;
			org.eclipse.swt.graphics.Color oldBgColor=gc.getBackground();
			gc.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLUE));
			gc.fillRectangle(x1, y1, rWidth, rHeight);
			gc.setBackground(oldBgColor);
			gc.drawString(name, (int) (x1-rSpace*0.5), cHeight-48+(i%3*15));
			x1=x1+rWidth+rSpace;
			y1=cHeight-50;
			
		}
	}

	public Shell getShell() {
		return shell;
	}
}
