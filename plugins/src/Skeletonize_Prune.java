//=========================================================================================
//
// Author : Daniel Sage
//
//=========================================================================================

import ij.IJ;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

public class Skeletonize_Prune implements PlugIn {

	public void run(String args) {
		ImageAccess image = new ImageAccess();
		GenericDialog dialog = new GenericDialog("Skeletonization and pruning");
		dialog.addNumericField("Pruning length", 3, 0);
		dialog.showDialog();
		if(dialog.wasCanceled())
			return;
		int m = (int)dialog.getNextNumber();
		if (image.getMinimum() != 0) {
			IJ.error("Only binary image (0/255)");
			return;
		}
		if (image.getMaximum() != 255) {
			IJ.error("Only binary image (0/255)");
			return;
		}
		ImageAccess out = (new CodeMorphological()).skeletonizeAndPrune(image, m);
		out.show("Skeletonization and Pruning of " + image.getTitle());
	}
}
