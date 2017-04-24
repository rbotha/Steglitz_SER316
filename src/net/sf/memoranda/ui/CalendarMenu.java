/**
 * 
 */
package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import net.sf.memoranda.EventsManager;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;

/**
 * Apr 4, 2017
 * @author Jesus Rodriguez Jr
 *
 */
public class CalendarMenu extends JPopupMenu{
	
	//Set Parent
	private WorkPanel _parentPanel;
	
	//dialogs
	ContactsAddDialog dialog;
	
	//menus for popup menu
	//Create New menu items
	JMenuItem newEvent;
	JMenuItem newTask;
	JMenuItem newContact;
	JMenuItem newSticker;
	//GoTo Menu items
	JMenuItem taskMenu;
	JMenuItem agendaMenu;
	JMenuItem eventMenu;
	JMenuItem noteMenu;
	JMenuItem resourceMenu;
	JMenuItem contactsMenu;
	//View Menut Items
	//JMenuItem viewWeekly;
	//JMenuItem viewDaily;
	
	//SubMenu
	JMenu createNew;
	JMenu goTo;
	//JMenu view;
	
	//Single parameter Constructor
	public CalendarMenu(WorkPanel parentPanel) {
		
		//Get WorkPanel
		_parentPanel = parentPanel;
		
		//Create SubMenus and Items
		goTo = new JMenu(Local.getString("Go To"));
		createNew = new JMenu(Local.getString("New"));
		//view = new JMenu(Local.getString("View"));
		newEvent = new JMenuItem(Local.getString("Event"));
		newTask = new JMenuItem(Local.getString("Task"));
		newContact = new JMenuItem(Local.getString("Contact"));
		newSticker = new JMenuItem(Local.getString("Sticker"));
		agendaMenu = new JMenuItem(Local.getString("Go to Agenda"));
		eventMenu = new JMenuItem(Local.getString("Go to Event"));
		taskMenu = new JMenuItem(Local.getString("Go to Tasks"));
		noteMenu = new JMenuItem(Local.getString("Go to Notes"));
		resourceMenu = new JMenuItem(Local.getString("Go to Resources"));
		contactsMenu = new JMenuItem(Local.getString("Go to Contacts"));
		
		//View Menu Items Still need to be implemented in US-#17
		//viewWeekly = new JMenuItem(Local.getString("Set Day View"));
		//viewDaily = new JMenuItem(Local.getString("Set to Week View"));
		
		//Add to New 
		createNew.add(newEvent);
		createNew.add(newTask);
		createNew.add(newContact);
		createNew.add(newSticker);
		
		//Add to GOTO menu
		goTo.add(agendaMenu);
		goTo.add(eventMenu);
		goTo.add(taskMenu);
		goTo.add(noteMenu);
		goTo.add(resourceMenu);
		goTo.add(contactsMenu);
		
		//Add to VIEW
		//view.add(viewDaily);
		//view.add(viewWeekly);
		
		//Add items to menu
		add(createNew);//add events menu
		add(goTo);//add agenda menu
		//add(view);//add view menu
		
		
		//Create New Event Action Listener
		//Mouse Listener for the creation of a new event
		newEvent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {

            	if(e.getButton() == java.awt.event.MouseEvent.BUTTON1)
                _parentPanel.dailyItemsPanel.eventsPanel.ppNewEvent_actionPerformed(null);
                
            }
        });
		//Mouse Listener for the creation of a new Task
		newTask.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {

            	if(e.getButton() == java.awt.event.MouseEvent.BUTTON1){
                _parentPanel.dailyItemsPanel.tasksPanel.ppNewTask_actionPerformed(null);
            	}
            }
        });
		//Mouse Listener for the creation of a new Task
		newContact.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				
				if(e.getButton() == java.awt.event.MouseEvent.BUTTON1){
					Contacts temp = _parentPanel.dailyItemsPanel.contactsPanel;
					temp.dialog.Invoke(temp.getList());
				}
			}
		});
		//Mouse Listener for the creation of a new Sticker
		newSticker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {

            	if(e.getButton() == java.awt.event.MouseEvent.BUTTON1){

				StickerDialog dlg = new StickerDialog(App.getFrame());
				Dimension frmSize = App.getFrame().getSize();
				dlg.setSize(new Dimension(300,380));
				Point loc = App.getFrame().getLocation();
				dlg.setLocation(
						(frmSize.width - dlg.getSize().width) / 2 + loc.x,
						(frmSize.height - dlg.getSize().height) / 2
						+ loc.y);
				dlg.setVisible(true);
				if (!dlg.CANCELLED) {
					String txt = dlg.getStickerText();
					int sP = dlg.getPriority();
					txt = txt.replaceAll("\\n", "<br>");
                    txt = "<div style=\"background-color:"+dlg.getStickerColor()+";font-size:"+dlg.getStickerTextSize()+";color:"+dlg.getStickerTextColor()+"; \">"+txt+"</div>";
					EventsManager.createSticker(txt, sP);
					CurrentStorage.get().storeEventsManager();
				}
				_parentPanel.dailyItemsPanel.agendaPanel.refresh(CurrentDate.get());
				System.out.println("I added a sticker");
            	}
            }
        });
		//Mouse Listener... goto agenda tab
		agendaMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                _parentPanel.agendaB_actionPerformed(null);
            }
        });
		//Mouse Listener... goto event tab
		eventMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                _parentPanel.eventsB_actionPerformed(null);
            }
        });
		//Mouse Listener... goto task tab
		taskMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                _parentPanel.tasksB_actionPerformed(null);
            }
        });
		//Mouse Listener... goto note menu
		noteMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                _parentPanel.notesB_actionPerformed(null);
            }
        });
		//Mouse Listerner... goto resource tab
		resourceMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                _parentPanel.filesB_actionPerformed(null);
            }
        });
		//Mouse Listener... goto contacts tab
		contactsMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                _parentPanel.contactsB_actionPerformed(null);
            }
        });
	}
	//get parent
	void setParentPanel(WorkPanel p){
		  _parentPanel = p;
	  }

}
