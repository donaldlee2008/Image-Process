//=========================================================================================
//
// Author : Daniel Sage, Biomedical Imaging Group (BIG), http://bigwww.epfl.ch/
//
//=========================================================================================

import ij.IJ;
import ij.plugin.PlugIn;

public class Skeletonize_ implements PlugIn {

	public void run(String args) {
		ImageAccess image = new ImageAccess();
		if (image.getMinimum() != 0) {
			IJ.error("Only binary image (0/255)");
			return;
		}
		if (image.getMaximum() != 255) {
			IJ.error("Only binary image (0/255)");
			return;
		}
		ImageAccess out = (new CodeMorphological()).skeletonize(image);
		out.show("Skeletonization of " + image.getTitle());
	}
}
