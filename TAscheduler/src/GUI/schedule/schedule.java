package GUI.schedule;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class schedule {
	
	//private Table tableSetup;
	//TableEditor editor;
	private String header[] = {"QuarterYear", "Monday", "Tuesday", "Wednesday",
						"Thursday", "Friday"};
	
	private List<String> contentsList;
	private Text scheduleCells[];
	
	//Total of 150 cells
	final int SCHEDULE_COLUMNS = 6;
	final int SCHEDULE_ROWS =25;
	
	int ROW_SELECTED = 0;
	int COLUMN_SELECTED = 1;
	
	GridLayout layout;
	Composite comp;
	ScrolledComposite comps;

	/**
	 * Constructor intializes the all the components of the schedule tab
	 * 
	 * @param comps - the composite object into all the components will be placed in
	 */
	public schedule(ScrolledComposite comps)
	{
		this.comps = comps;
		
		//GridLayout
		layout = new GridLayout(SCHEDULE_COLUMNS,true);
		
		comp = new Composite(comps, SWT.NONE);
		comps.setContent(comp);
		
		//set Scrolled Composite attributes
		comps.setAlwaysShowScrollBars(true);
		comps.setExpandVertical(true);
		comps.setExpandHorizontal(true);
		
		
		layout.horizontalSpacing = 0;
		
		//Set the layout to the composite
		comp.setLayout(layout);
		
		//Test button
		/*
		final Button btn = new Button(comp, SWT.PUSH | SWT.BORDER);
		btn.setText("Test");
		btn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e)
			{
				addStudentToSchedule("Tony", "Tuesday", 9, 207);
			}
			
		});
		*/
		
		setupContentsV2();
		setupSchedule(comp);
		
	}
	
	/**
	 * Adds a student name to the schedule in a new line with the given day, the time and the
	 * room number the student will TA for. 
	 * 
	 * @param studentName - String object that contains the name of the student to place in the schedule
	 * @param day - String object that contains the week day in which the student will TA for.
	 * @param time - Integer in which specifies the day of the time, times include 8, 9, 10, 11, 12, 1, 2 and 3.
	 * @param room -Integer which specifies the room number. These include 203, 204, and 207. 
	 */
	public void addStudentToSchedule(String studentName, String day, int time, int room)
	{
		int index = 0;
		String dayLwr = day.toLowerCase();
		
		
		//The time determines the current row and its index
		switch(time){
			case 8:
				index = 6;
				break;
			case 9:
				index = 24;
				break;
			case 10:
				index = 42;
				break;
			case 11:
				index = 60;
				break;
			case 12:
				index = 78;
				break;
			case 1:
				index = 96;
				break;
			case 2:
				index = 114;
				break;
			case 3:
				index = 132;
				break;	
		}
		
		//The day determines the column 
		if(dayLwr.equalsIgnoreCase("Monday"))
			index += 1;
		else if(dayLwr.equalsIgnoreCase("Tuesday"))
			index += 2;
		else if(dayLwr.equalsIgnoreCase("Wednesday"))
			index += 3;
		else if(dayLwr.equalsIgnoreCase("Thursday"))
			index += 4;
		else if(dayLwr.equalsIgnoreCase("Friday"))
			index += 5;
		
		//The room determines the specific cell
		switch(room){
			case 203:
				index += 0;
				break;
			case 204:
				index += 6;
				break;
			case 207:
				index += 12;
				break;
		}
		
		//Get cells current text
		String cellCurrentText = scheduleCells[index].getText();
		System.out.println("Before" + cellCurrentText);
		
		//Append to it the student name in a new line
		cellCurrentText = cellCurrentText + studentName + "\n";
		
		System.out.println("After: " + cellCurrentText);
		
		//set the text to the cell
		scheduleCells[index].setText(cellCurrentText);
	
	}
	
	/**
	 * Places all the Text objects in the composite
	 * 
	 * @param comp -  the composite object onto which to place all the text objects onto.
	 */
	private void setupSchedule(final Composite comp)
	{
		//Initialize all cells of Text widgets
		scheduleCells = new Text[SCHEDULE_COLUMNS * SCHEDULE_ROWS];
		
		for(int i = 0; i < SCHEDULE_ROWS; i++)
		{
			
			for(int j = 0; j < SCHEDULE_COLUMNS; j++)
			{
				final Text newTextField = new Text(comp, SWT.BORDER | SWT.MULTI);
				final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
				
				newTextField.setLayoutData(gridData);
				newTextField.setText(contentsList.get(i * SCHEDULE_COLUMNS + j));

				scheduleCells[i * SCHEDULE_COLUMNS + j] = newTextField;
				
				
				newTextField.addModifyListener(new ModifyListener()
				{
					@Override
					public void modifyText(ModifyEvent arg0) {
						
						
						GC gc = new GC(newTextField);
						FontMetrics fm = gc.getFontMetrics();
						
						int height = fm.getHeight();
						
						gc.dispose();

						gridData.heightHint = newTextField.getLineCount() * height;
						newTextField.setLayoutData(gridData);
						
						//Refresh layout since text boxes got larger or smaller
						comp.layout();
						
						//compute the size of the composite and set scroll bar
						comps.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					}
				});
				
			}
		}
	}
	
	/**
	 * Initializes all the string objects that will go into each cell of the schedule
	 */
	public void setupContentsV2()
	{
		contentsList = new ArrayList<String>();
		String times[] = {"8:00", "9:00", "10:00", "11:00", "NOON", "1:00", "2:00", "3:00"};
		int timesIndex = 0;
		int timesCounter = 2;
		
		String rooms[] = {"203", "204", "207"};
		
		for(int i = 0; i < SCHEDULE_ROWS *  SCHEDULE_COLUMNS; i++)
		{
			if(i < 6)
			{
				contentsList.add(header[i]);
			}
			else if(i % 6 == 0 && timesCounter == 2)
			{
				contentsList.add(times[timesIndex]);
				timesIndex++;
				timesCounter = 0;
			}
			else if(i % 6 == 0)
			{
				contentsList.add(new String(" "));
				timesCounter++;
			}
			else
			{
				if(timesCounter == 0)
				{
					contentsList.add(rooms[0]);
				}
				else if(timesCounter == 1)
				{
					contentsList.add(rooms[1]);

				}
				else
				{
					contentsList.add(rooms[2]);
				}
			}
			
		}
		
		/*
		for(String str: contentsList)
		{
			System.out.println(str);
		}
		*/
	}

}
