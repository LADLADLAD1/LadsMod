package LadsMod.Util;

import java.awt.Color;
import java.util.regex.Pattern;

/**
 * Utility class for all things rendering in my mod.
 * @author SesuMoe
 */

public class ColorUtil {
	
	float[] color;

	
	public ColorUtil(float r, float g, float b, float a) {
		this.color = new float[] { r, g, b, a };
	}
	
	public ColorUtil(int color) {
		this(color, false);
	}
	
	public ColorUtil(int color, boolean fixTransparency) {
		if(fixTransparency) {
			if ((color & -67108864) == 0) {
	            color |= -16777216;
	        }
		}
		
		float a = (float) (color >> 24 & 255) / 255.0f;
        float r = (float) (color >> 16 & 255) / 255.0f;
        float g = (float) (color >> 8 & 255) / 255.0f;
        float b = (float) (color & 255) / 255.0f;
        
        this.color = new float[] { r, g, b, a };
	}
	
	public int toHex() {
		int rInt = Math.round(color[0] * 255.0f);
		int gInt = Math.round(color[1] * 255.0f);
		int bInt = Math.round(color[2] * 255.0f);
		int aInt = Math.round(color[3] * 255.0f);
		
		return (0xff000000 & (aInt << 24)) | (0xff0000 & (rInt << 16)) | (0xff00 & (gInt << 8)) | (0xff & bInt);
	}
	
	public float red() {
		return color[0];
	}
	
	public float green() {
		return color[1];
	}
	
	public float blue() {
		return color[2];
	}
	
	public float alpha() {
		return color[3];
	}

	public float[] getFloats() {
		return color;
	}
	
	public static int hex(float r, float g, float b, float a) {
		return new ColorUtil(r, g, b, a).toHex();
	}
	
	@Override
	public String toString() {
		return "[Color=" + red() + "," + green() + "," + blue() + "," + alpha() + "]";
	}

}