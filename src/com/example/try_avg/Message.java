package org.loon.framework.android.game.avg;

import java.util.HashMap;
import java.util.Map;

import org.loon.framework.android.game.LAGraphics;
import org.loon.framework.android.game.LAGraphicsUtils;
import org.loon.framework.android.game.LAImage;
import org.loon.framework.android.game.extend.MessageDialogSplit;

/**
 * 
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
 * @email ceponline@yahoo.com.cn
 * @version 0.1.0
 */
public class Message {

	public final static MessageDialog dialog = new MessageDialog();

	final static private Map lazyImages = new HashMap(10);

	final static private int space_width = 10;

	final static private int space_height = 10;

	final static private int space_size = 27;

	static{
		dialog.initialize();
	}
	
	public static void setWindowFrame(LAGraphics g, int x1, int y1, int x2,
			int y2) {
		LAImage frame = Message.loadFrameWindow(x2 - x1, y2 - y1);
		g.drawImage(frame, x1, y1);
	}

	public static void setWindowBuoyageMessage(LAGraphics g, int x, int y) {
		g.drawImage(Res.creeseIcon, x, y);
	}

	public final static LAImage loadFrameWindow(int width, int height) {
		String keyName = width + "|" + height;
		LAImage lazyImage = (LAImage) lazyImages.get(keyName);
		if (lazyImage == null) {
			int objWidth = 64;
			int objHeight = 64;
			int x1 = 128;
			int x2 = 192;
			int y1 = 0;
			int y2 = 64;
			LAImage image = null;
			LAImage messageImage = null;
			try {
				image = LAGraphicsUtils.drawClipImage(Res.windowsCanvas,
						objWidth, objHeight, x1, y1, x2, y2);
				messageImage = LAGraphicsUtils.drawClipImage(Res.windowsCanvas,
						128, 128, 0, 0, 128, 128);
			} catch (Exception e) {
				e.printStackTrace();
			}

			MessageDialogSplit mds = new MessageDialogSplit(image, space_width,
					space_height, space_size);
			mds.split();
			int doubleSpace = space_size * 2;
			if (width < doubleSpace) {
				width = doubleSpace + 5;
			}
			if (height < doubleSpace) {
				height = doubleSpace + 5;
			}
			lazyImage = LAImage.createImage(width - 10, height);
			LAGraphics graphics = lazyImage.getLAGraphics();
			graphics.setAlpha(0.5f);
			messageImage = LAGraphicsUtils.getResize(messageImage, width
					- space_width * 2, height - space_height);
			graphics.drawImage(messageImage, 5, 5);
			graphics.setAlpha(1.0f);
			graphics.drawImage(mds.getLeftUpImage(), 0, 0);
			graphics.drawImage(mds.getRightUpImage(),
					(width - space_size - space_width), 0);
			graphics
					.drawImage(mds.getLeftDownImage(), 0, (height - space_size));
			graphics.drawImage(mds.getRightDownImage(),
					(width - space_size - space_width), (height - space_size));

			int nWidth = width - doubleSpace;
			int nHeight = height - doubleSpace;
			graphics.drawImage(LAGraphicsUtils.getResize(mds.getUpImage(),
					nWidth, space_size), space_size, 0);
			graphics.drawImage(LAGraphicsUtils.getResize(mds.getDownImage(),
					nWidth, space_size), space_size, (height - space_size));
			graphics.drawImage(LAGraphicsUtils.getResize(mds.getLeftImage(),
					space_size, nHeight), 0, space_size);
			graphics.drawImage(LAGraphicsUtils.getResize(mds.getRightImage(),
					space_size, nHeight), (width - space_size - space_width),
					space_size);
			graphics.dispose();
			lazyImages.put(keyName, lazyImage);
		}
		return lazyImage;
	}
}
