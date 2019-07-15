package swtDemo;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class SWTSimple extends ApplicationWindow{

	public SWTSimple() {
		super(null);

	}
	
	protected Control createContents(Composite parent) {
	
		parent.setLayout(new FillLayout());
		
		GridLayout mainWindowGrid= new GridLayout(2,true);
		Composite mainComposite =new Composite(parent, SWT.NONE);
		mainComposite.setLayout(mainWindowGrid);
		
		Menu Headermenu =new Menu(getShell(),SWT.BAR);
		final MenuItem file=new MenuItem(Headermenu,SWT.CASCADE);
		file.setText("File");
		 MenuManager menuManager =new MenuManager();
		 MenuManager fileMenu =new MenuManager("&File");
		 menuManager.add(fileMenu);
		 final Menu menu = menuManager.createContextMenu();
		 
	//	getShell().setMenuBar(menuManager);
		getShell().setText("Jface Homework log");
		parent.setSize(600,250);
		return parent;		
	}

	public static void main(String[] args) {
		SWTSimple swts=new SWTSimple();
		swts.setBlockOnOpen(true);
		swts.open();
		Display.getCurrent().dispose();
	}

}