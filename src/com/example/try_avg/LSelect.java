package org.loon.framework.android.game.core.graphics.window;

import java.util.List;

import org.loon.framework.android.game.core.LSystem;
import org.loon.framework.android.game.core.graphics.LColor;
import org.loon.framework.android.game.core.graphics.LContainer;
import org.loon.framework.android.game.core.graphics.LFont;
import org.loon.framework.android.game.core.graphics.LImage;
import org.loon.framework.android.game.core.graphics.device.LGraphics;
import org.loon.framework.android.game.core.timer.LTimer;
import org.loon.framework.android.game.utils.GraphicsUtils;

import android.view.KeyEvent;

/**
 * Copyright 2008 - 2010
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 */
public class LSelect {

	private LFont messageFont = LFont.getFont(LSystem.FONT_NAME, 20);

	private LColor fontColor = LColor.white;

	private int left, top, type, nTop;

	private int sizeFont, doubleSizeFont, tmpOffset, messageLeft, nLeft,
			messageTop, selectSize, selectFlag;

	private float autoAlpha;

	private LTimer delay;

	private String[] selects;

	private String message, result;

	private LImage cursor, buoyage;

	private boolean isAutoAlpha, isSelect;
	private int width, height;
	
	double dx, dy;
	
	public LSelect(int x, int y, int width, int height) {
		this.setLocation(x, y);
		this.width = width;
		this.height = height;

		if (this.width == 0) {
			this.width = 10;
		}
		if (this.height == 0) {
			this.height = 10;
		}
	}
	
	public void setLocation(double dx, double dy) {
//		if (this.getX() != dx || this.getY() != dy) {
//			super.setLocation(dx, dy);
//		}
		this.dx = dx;
		this.dy = dy;
	}

	public LSelect(String fileName) {
		this(fileName, 0, 0);
	}

	public LSelect(String fileName, int x, int y) {
		this(GraphicsUtils.loadImage(fileName), x, y);
	}

	public LSelect(LImage formImage) {
		this(formImage, 0, 0);
	}

	public LSelect(LImage formImage, int x, int y) {
		this(formImage, x, y, formImage.getWidth(), formImage.getHeight());
	}

	public LSelect(LImage formImage, int x, int y, int width, int height) {
		super(x, y, width, height);
		if (formImage == null) {
			this.setBackground(new LImage(width, height, true));
			this.setAlpha(0.3F);
		} else {
			this.setBackground(formImage);
		}
//		this.customRendering = true;
		this.selectFlag = 1;
		this.tmpOffset = -(width / 10);
		this.delay = new LTimer(150);
		this.autoAlpha = 0.25F;
		this.isAutoAlpha = true;
		this.setCursor("system/images/creese.png");

	}

	public void setLeftOffset(int left) {
		this.left = left;
	}

	public void setTopOffset(int top) {
		this.top = top;
	}

	public int getLeftOffset() {
		return left;
	}

	public int getTopOffset() {
		return top;
	}

	private static String[] getListToStrings(List<String> list) {
		if (list == null || list.size() == 0)
			return null;
		String[] result = new String[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (String) list.get(i);
		}
		return result;
	}

	public void setMessage(String message, List<String> list) {
		setMessage(message, getListToStrings(list));
	}

	public void setMessage(String[] selects) {
		setMessage(null, selects);
	}

	public void setMessage(String message, String[] selects) {
		this.message = message;
		this.selects = selects;
		this.selectSize = selects.length;
		if (doubleSizeFont == 0) {
			doubleSizeFont = 20;
		}
	}

	private boolean onClick;

	/**
	 * 处理点击事件（请重载实现）
	 * 
	 */
	public void doClick() {

	}

	public boolean isClick() {
		return onClick;
	}

	public LColor getFontColor() {
		return fontColor;
	}

	public void setFontColor(LColor fontColor) {
		this.fontColor = fontColor;
	}

	public LFont getMessageFont() {
		return messageFont;
	}

	public void setMessageFont(LFont messageFont) {
		this.messageFont = messageFont;
	}


	public String getUIName() {
		return "Select";
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	protected boolean visible = true;
	
	public void setVisible(boolean visible) {
		if (this.visible == visible) {
			return;
		}
		this.visible = visible;
//		this.desktop.setComponentStat(this, this.visible);
	}
}
