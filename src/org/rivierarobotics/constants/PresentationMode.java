package org.rivierarobotics.constants;

import edu.wpi.first.wpilibj.Preferences;

public class PresentationMode {
	
	public static boolean inPresentationMode() {
		return Preferences.getInstance().getBoolean("presentation-mode", false);
	}

}
