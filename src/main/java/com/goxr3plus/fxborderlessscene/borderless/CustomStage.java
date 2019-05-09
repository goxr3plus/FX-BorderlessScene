package com.goxr3plus.fxborderlessscene.borderless;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomStage extends Stage {
	public CustomStage() {}
	
	public CustomStage(StageStyle style) {
		super(style);
	}
	
	public BorderlessScene craftBorderlessScene(Parent sceneRoot) {
		return new BorderlessScene(this, getStyle(), sceneRoot);
	}
	
	public void showAndAdjust() {
		if(isShowing())
			return;
		show();
		makeMinimizableOnIconClick();
	}

	@SuppressWarnings("restriction")
	private void makeMinimizableOnIconClick() {
		long lhwnd = com.sun.glass.ui.Window.getWindows().get(0).getNativeWindow();
        Pointer lpVoid = new Pointer(lhwnd);
        WinDef.HWND hwnd = new WinDef.HWND(lpVoid);
        final User32 user32 = User32.INSTANCE;
        int oldStyle = user32.GetWindowLong(hwnd, WinUser.GWL_STYLE);
        int newStyle = oldStyle | 0x00020000;//WS_MINIMIZEBOX
        user32.SetWindowLong(hwnd, WinUser.GWL_STYLE, newStyle);		
	}
}
