package com.avenqo.medusa.fe.selenium.test.util;

import org.openqa.selenium.WebElement;

public class ElementHelper {

	@SuppressWarnings({ "PMD.SystemPrintln" })
	public static void showDebugInfos(String tag, WebElement ele) {
		System.out.println("=====================");
		System.out.println(tag != null ? " " + tag + " " : "");
		System.out.println("=====================");

		System.out.println("TagName: " + ele.getTagName());
		System.out.println("Text: " + ele.getText());
		System.out.println("Classname: " + ele.getClass());
		System.out.println("Rect: " + ele.getRect());
		System.out.println("Size: " + ele.getSize());

		System.out.println("Displayed: " + ele.isDisplayed());
		System.out.println("Enabled: " + ele.isEnabled());
		System.out.println("Selected: " + ele.isSelected());

		System.out.println("Location: " + ele.getLocation());

	}
}