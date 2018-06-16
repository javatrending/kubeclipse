package org.keclipse.rcp.view.table;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.part.ViewPart;
import org.keclipse.Activator;
import org.keclipse.k8.access.KubeAccess;
import org.keclipse.rcp.ListContentProvider;
import org.keclipse.rcp.ListLabelProvider;
import org.keclipse.rcp.RCPUtils;

/**
 * The base class for the Table for all the k8 objects
 * 
 * @author vibhu.pratap
 *
 */
abstract public class AbstractTableView extends ViewPart {

	// The TableViewer behind the table view
	protected TableViewer _tableviewer;
	// Generic refresh action for making a call again and getting new objects
	private Action _refresh;

	/**
	 * Label Provider
	 * 
	 * @author vibhu.pratap
	 *
	 */
	public class K8LabelProvider extends ListLabelProvider {

		@Override
		public String getColumnText(Object element, int columnIndex) {
			Map<Integer, String> properties = getProperties(element);
			if (columnIndex > getTitles().length) {
				return "Unknown Column";
			} else {
				return properties.get(columnIndex);
			}
		}
	}

	/**
	 * Create the part control that will be created when the view gets activated
	 */
	public void createPartControl(Composite parent) {
		createViewer(parent);
		_tableviewer.setInput(getInput());
		makeActions();
		_refresh = new Action() {
			@Override
			public void run() {
				_tableviewer.setInput(getInput());
			}
		};
		_refresh.setImageDescriptor(Activator.getImageDescriptor("icons/page_refresh.gif"));
		_refresh.setText("Refresh");
		getViewSite().getActionBars().getToolBarManager().add(_refresh);

	}

	/**
	 * List of actions that can be provided for dropdown
	 * 
	 * @return : List of WorkbenchAction
	 */
	public Collection<IWorkbenchAction> getDropDownActions() {
		Collection<IWorkbenchAction> cacts = new ArrayList<IWorkbenchAction>();
		return cacts;
	}

	/**
	 * Get the properties of each row for each of the object that is passed in
	 * 
	 * @param element
	 *            : The object to be displayed in the row
	 * @return: Key Value of the properties here
	 */
	public abstract Map<Integer, String> getProperties(Object element);

	public AbstractTableView() {

	}

	/**
	 * Add actions to the MenuManager
	 */
	protected void makeActions() {

		MenuManager mmanager = new MenuManager(getClass().getSimpleName());
		mmanager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		Menu menu = mmanager.createContextMenu(_tableviewer.getControl());
		_tableviewer.getControl().setMenu(menu);
		getSite().registerContextMenu(mmanager, _tableviewer);

		for (IWorkbenchAction we : getDropDownActions()) {
			getViewSite().getActionBars().getMenuManager().add(we);
		}
	}

	/**
	 * Create the viewer in the parent composite
	 * 
	 * @param parent
	 */
	public void createViewer(Composite parent) {
		_tableviewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		createColumns(_tableviewer);
		_tableviewer.setContentProvider(new ListContentProvider());
		_tableviewer.setLabelProvider(new K8LabelProvider());

		_tableviewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				{
					ISelection selection = _tableviewer.getSelection();
					if (selection instanceof IStructuredSelection) {
						IStructuredSelection structuredSelection = (IStructuredSelection) selection;
						Object object = structuredSelection.getFirstElement();
						String details = getDetail(object);
						RCPUtils.openScrollableDialog(details, RCPUtils.getShell(),
								"Information => " + object.getClass().getSimpleName(), "Details");
					}
				}
			}
		});
	}

	// This will create the columns for the table
	private void createColumns(TableViewer viewer) {

		String[] titles = getTitles();
		List<String> wstrs = Arrays.asList(getWideTitles());
		for (int i = 0; i < titles.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(titles[i]);
			if (wstrs.contains(titles[i])) {
				column.getColumn().setWidth(getWideColumnLength());
			} else {
				column.getColumn().setWidth(100);
			}
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
		}
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	public String[] getTitles() {
		String[] titles = { "Name" };
		return titles;
	}

	public String[] getWideTitles() {
		String[] wide = { "Name" };
		return wide;
	}

	public abstract String getObjectName();

	public int getWideColumnLength() {
		return 250;
	}

	public void setFocus() {
		_tableviewer.getControl().setFocus();
	}

	public String getDetail(Object obj) {
		return obj.toString();
	}

	public KubeAccess getKubeAccess() {
		return new KubeAccess(RCPUtils.getKubeConfig());
	}

	/**
	 * Get the input object which will return the list of objects that need to be
	 * displayed in the table
	 * 
	 * @return : List of KubeObjects
	 */
	public Object getInput() {
		// Get the kubeAccess object
		KubeAccess ka = getKubeAccess();
		try {
			Method method = KubeAccess.class.getDeclaredMethod("get" + getObjectName() + "List");
			return method.invoke(ka);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}