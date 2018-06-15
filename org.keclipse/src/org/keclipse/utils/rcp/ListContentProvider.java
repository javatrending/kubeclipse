package org.keclipse.utils.rcp;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ListContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		@SuppressWarnings("unchecked")
		List<Object> cp = (List<Object>) inputElement;
		return cp.toArray();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
