package org.cytoscape.view.presentation.property;

import org.cytoscape.model.CyTableEntry;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualLexiconNode;
import org.cytoscape.view.model.VisualProperty;

public class VisualPropertyUtil {

	public static boolean isChildOf(final VisualProperty<?> parent, final VisualProperty<?> vp,
			final VisualLexicon lexicon) {
		
		if(vp == null)
			throw new NullPointerException("Visual Property is null.");
		
		if(lexicon == null)
			throw new NullPointerException("Lexicon is null.");
		
		if(lexicon.getAllVisualProperties().contains(vp) == false)
			throw new IllegalArgumentException("No such Visual Porperty in the lexicon: " + vp.getDisplayName());
		
		
		VisualLexiconNode node = lexicon.getVisualLexiconNode(vp);
		
		// This is a root
		if(node.getParent() == null)
			return false;

		if (vp == parent || node.getParent().getVisualProperty() == parent)
			return true;

		
		while (node.getParent() != null) {
			node = node.getParent();
			if (node.getVisualProperty() == parent)
				return true;
		}

		return false;
	}
	
	public static String getGraphObjectType(final VisualProperty<?> vp, final VisualLexicon lexicon) {
		if(isChildOf(TwoDVisualLexicon.NODE, vp, lexicon))
			return CyTableEntry.NODE;
		else if(isChildOf(TwoDVisualLexicon.EDGE, vp, lexicon))
			return CyTableEntry.EDGE;
		else if(isChildOf(TwoDVisualLexicon.NETWORK, vp, lexicon))
			return CyTableEntry.NETWORK;
		else
			throw new IllegalStateException("Could not find a category for Visual Property: " + vp.getDisplayName()); 
	}
}