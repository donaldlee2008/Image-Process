//=========================================================================================
//
// Author : Daniel Sage
//
//=========================================================================================

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;

public class Erode_Bug implements PlugIn {

	public void run(String args) {
		ImagePlus imp = WindowManager.getCurrentImage();
		int type = imp.getType();
		if (type != ImagePlus.GRAY8 && type != ImagePlus.GRAY32 && type != ImagePlus.COLOR_RGB && type!= ImagePlus.GRAY16) {
			IJ.error("Only process the 8-bits, 16-bits, RGB, or 32-bits.");
			return;
		}
		ImageAccess input[] = null;
		if (type == ImagePlus.COLOR_RGB) {
			input = ImageAccess.RGB.create(imp);
		}
		else {
			input = new ImageAccess[1];
			input[0] = new ImageAccess(imp);
		}
		ImageAccess out[] = new ImageAccess[input.length];
		for(int i=0; i<input.length; i++)
			out[i] = (new CodeMorphological()).erodeBug(input[i]);
		if (out.length == 3)
			new  ImageAccess.Display(true, out[0], out[1], out[2], "Erode 3x3 of " + imp.getTitle());
		else
			new ImageAccess.Display(out, "Erode 3x3 of " + imp.getTitle());
			
	}
}
