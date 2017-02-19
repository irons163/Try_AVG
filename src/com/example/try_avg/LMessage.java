package com.example.try_avg;

import android.view.KeyEvent;
import android.view.animation.Animation;

/**
 * Copyright 2008 - 2009
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
public class LMessage {

	private Animation animation;

	private LFont messageFont = LFont.getFont(LSystem.FONT_NAME, 20);

	private LColor fontColor = LColor.white;

	private long printTime, totalDuration;

	private int dx, dy, dw, dh;

	private IPrint print;

	public LMessage(int width, int height) {
		this(0, 0, width, height);
	}

	public LMessage(int x, int y, int width, int height) {
//		this((LImage) null, x, y, width, height);
	}

	public LMessage(String fileName, int x, int y) {
//		this(GraphicsUtils.loadImage(fileName), x, y);
	}

//	public LMessage(LImage formImage, int x, int y) {
////		this(formImage, x, y, formImage.getWidth(), formImage.getHeight());
//	}
//
//	public LMessage(LImage formImage, int x, int y, int width, int height) {
//		super(x, y, width, height);
//		this.animation = new Animation();
//		if (formImage == null) {
//			this.setBackground(new LImage(width, height, true));
//			this.setAlpha(0.3F);
//		} else {
//			this.setBackground(formImage);
//			if (width == -1) {
//				width = formImage.getWidth();
//			}
//			if (height == -1) {
//				height = formImage.getHeight();
//			}
//		}
//		this.print = new IPrint(getLocation(), width, height);
//		this.setTipIcon("system/images/creese.png");
//		this.totalDuration = 100;
//		this.customRendering = true;
//		this.setElastic(true);
//		this.setLocked(true);
//		this.setLayer(100);
//	}

	public void complete() {
		print.complete();
	}

	public void setLeftOffset(int left) {
		print.setLeftOffset(left);
	}

	public void setTopOffset(int top) {
		print.setTopOffset(top);
	}

	public int getLeftOffset() {
		return print.getLeftOffset();
	}

	public int getTopOffset() {
		return print.getTopOffset();
	}

	public int getMessageLength() {
		return print.getMessageLength();
	}

	public void setMessageLength(int messageLength) {
		print.setMessageLength(messageLength);
	}

	public void setDelay(long delay) {
		this.totalDuration = (delay < 1 ? 1 : delay);
	}

	public boolean isComplete() {
		return print.isComplete();
	}

	public void setPauseIconAnimationLocation(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setMessage(String context, boolean isComplete) {
		print.setMessage(context, isComplete);
	}

	public void setMessage(String context) {
		print.setMessage(context);
	}

	/**
	 * 处理点击事件（请重载实现）
	 * 
	 */
	public void doClick() {
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
		return "Message";
	}

}
