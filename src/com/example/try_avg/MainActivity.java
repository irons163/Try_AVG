package com.example.try_avg;

import java.util.List;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	protected Command command;
	private boolean isSelectMessage, scrFlag, commandGo, running;
	LMessage message;
	SystemHandler handler;
	protected LSelect select;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.handler = LSystem.getSystemHandler();
	}
	
//	private void initAVG() {
//		this.initDesktop();
//		this.initMessageConfig(message);
//		this.initSelectConfig(select);
//		this.initCommandConfig(scriptName);
//
//	}
	
	
	public void initCommandConfig(final Command command){};
	public boolean nextScript(String message){return false;};
	protected Sprites sprites;
	
	public void initCommandConfig(String fileName) {
		if (fileName == null) {
			return;
		}
		Command.resetCache();
		if (command == null) {
			command = new Command(fileName);
		} else {
			command.formatCommand(fileName);
		}
		initCommandConfig(command);
		nextScript();
	}
	
	public synchronized void nextScript() {
		if (command != null && running) {
			for (; commandGo = command.next();) {
				String result = command.doExecute();
				if (result == null) {
					nextScript();
					break;
				}
				if (!nextScript(result)) {
					break;
				}
				List<?> commands = Command.splitToList(result, " ");
				int size = commands.size();
				String cmdFlag = (String) commands.get(0);

				String mesFlag = null, orderFlag = null, lastFlag = null;
				if (size == 2) {
					mesFlag = (String) commands.get(1);
				} else if (size == 3) {
					mesFlag = (String) commands.get(1);
					orderFlag = (String) commands.get(2);
				} else if (size == 4) {
					mesFlag = (String) commands.get(1);
					orderFlag = (String) commands.get(2);
					lastFlag = (String) commands.get(3);
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_WAIT)) {
					scrFlag = true;
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SNOW)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_RAIN)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_PETAL)) {
					if (sprites != null) {
						boolean flag = false;
						ISprite[] ss = sprites.getSprites();
						for (int i = 0; i < ss.length; i++) {
							ISprite s = ss[i];
							if (s instanceof FreedomEffect) {
								flag = true;
								break;
							}
						}
						if (!flag) {
							if (cmdFlag.equalsIgnoreCase(CommandType.L_SNOW)) {
								sprites.add(FreedomEffect.getSnowEffect());
							} else if (cmdFlag
									.equalsIgnoreCase(CommandType.L_RAIN)) {
								sprites.add(FreedomEffect.getRainEffect());
							} else if (cmdFlag
									.equalsIgnoreCase(CommandType.L_PETAL)) {
								sprites.add(FreedomEffect.getPetalEffect());
							}
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SNOWSTOP)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_RAINSTOP)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_PETALSTOP)) {
					if (sprites != null) {
						ISprite[] ss = sprites.getSprites();
						for (int i = 0; i < ss.length; i++) {
							ISprite s = ss[i];
							if (s instanceof FreedomEffect) {
								if (cmdFlag
										.equalsIgnoreCase(CommandType.L_SNOWSTOP)) {
									if (((FreedomEffect) s).getKernels()[0] instanceof SnowKernel) {
										sprites.remove(s);
									}
								} else if (cmdFlag
										.equalsIgnoreCase(CommandType.L_RAINSTOP)) {
									if (((FreedomEffect) s).getKernels()[0] instanceof RainKernel) {
										sprites.remove(s);
									}
								} else if (cmdFlag
										.equalsIgnoreCase(CommandType.L_PETALSTOP)) {
									if (((FreedomEffect) s).getKernels()[0] instanceof PetalKernel) {
										sprites.remove(s);
									}
								}

							}
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_PLAY)) {
					playtAssetsMusic(mesFlag, false);
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_PLAYLOOP)) {
					playtAssetsMusic(mesFlag, true);
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_PLAYSTOP)) {
					if (NumberUtils.isNan(mesFlag)) {
						stopAssetsMusic(Integer.parseInt(mesFlag));
					} else {
						stopAssetsMusic();
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_FADEOUT)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_FADEIN)) {
					scrFlag = true;
					LColor color = LColor.black;
					if (mesFlag.equalsIgnoreCase("red")) {
						color = LColor.red;
					} else if (mesFlag.equalsIgnoreCase("yellow")) {
						color = LColor.yellow;
					} else if (mesFlag.equalsIgnoreCase("white")) {
						color = LColor.white;
					} else if (mesFlag.equalsIgnoreCase("black")) {
						color = LColor.black;
					} else if (mesFlag.equalsIgnoreCase("cyan")) {
						color = LColor.cyan;
					} else if (mesFlag.equalsIgnoreCase("green")) {
						color = LColor.green;
					} else if (mesFlag.equalsIgnoreCase("orange")) {
						color = LColor.orange;
					} else if (mesFlag.equalsIgnoreCase("pink")) {
						color = LColor.pink;
					}
					if (sprites != null) {
						sprites.removeAll();
						if (cmdFlag.equalsIgnoreCase(CommandType.L_FADEIN)) {
							sprites.add(Fade.getInstance(Fade.TYPE_FADE_IN,
									color));
						} else {
							sprites.add(Fade.getInstance(Fade.TYPE_FADE_OUT,
									color));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SELLEN)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							select.setLeftOffset(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SELTOP)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							select.setTopOffset(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESLEN)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							message.setMessageLength(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESTOP)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							message.setTopOffset(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESLEFT)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							message.setLeftOffset(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESCOLOR)) {
					if (mesFlag != null) {
						if (mesFlag.equalsIgnoreCase("red")) {
							message.setFontColor(LColor.red);
						} else if (mesFlag.equalsIgnoreCase("yellow")) {
							message.setFontColor(LColor.yellow);
						} else if (mesFlag.equalsIgnoreCase("white")) {
							message.setFontColor(LColor.white);
						} else if (mesFlag.equalsIgnoreCase("black")) {
							message.setFontColor(LColor.black);
						} else if (mesFlag.equalsIgnoreCase("cyan")) {
							message.setFontColor(LColor.cyan);
						} else if (mesFlag.equalsIgnoreCase("green")) {
							message.setFontColor(LColor.green);
						} else if (mesFlag.equalsIgnoreCase("orange")) {
							message.setFontColor(LColor.orange);
						} else if (mesFlag.equalsIgnoreCase("pink")) {
							message.setFontColor(LColor.pink);
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MES)) {
					if (select.isVisible()) {
						select.setVisible(false);
					}
					scrFlag = true;
					String nMessage = mesFlag;
					message.setMessage(StringUtils.replace(nMessage, "&", " "));
//					message.setVisible(true);
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESSTOP)) {
					scrFlag = true;
//					message.setVisible(false);
					select.setVisible(false);
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SELECT)) {
//					selectMessage = mesFlag;
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SELECTS)) {
//					if (message.isVisible()) {
//						message.setVisible(false);
//					}
					select.setVisible(true);
					scrFlag = true;
					isSelectMessage = true;
					String[] selects = command.getReads();
//					select.setMessage(selectMessage, selects);
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SHAKE)) {
//					shakeNumber = Integer.valueOf(mesFlag).intValue();
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_CGWAIT)) {
					scrFlag = false;
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SLEEP)) {
//					sleep = Integer.valueOf(mesFlag).intValue();
//					sleepMax = Integer.valueOf(mesFlag).intValue();
					scrFlag = false;
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_FLASH)) {
					scrFlag = true;
					String[] colors = mesFlag.split(",");
//					if (color == null && colors != null && colors.length == 3) {
//						color = new LColor(Integer.valueOf(colors[0])
//								.intValue(), Integer.valueOf(colors[1])
//								.intValue(), Integer.valueOf(colors[2])
//								.intValue());
//						sleep = 20;
//						sleepMax = sleep;
//						scrFlag = false;
//					} else {
//						color = null;
//					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_GB)) {

//					if (mesFlag == null) {
//						return;
//					}
//					if (mesFlag.equalsIgnoreCase("none")) {
//						scrCG.noneBackgroundCG();
//					} else {
//						scrCG.setBackgroundCG(mesFlag);
//					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_CG)) {

//					if (mesFlag == null) {
//						return;
//					}
//					if (mesFlag.equalsIgnoreCase(CommandType.L_DEL)) {
//						if (orderFlag != null) {
//							scrCG.removeImage(orderFlag);
//						} else {
//							scrCG.clear();
//						}
//					} else if (lastFlag != null
//							&& CommandType.L_TO.equalsIgnoreCase(orderFlag)) {
//						Chara chara = scrCG.removeImage(mesFlag);
//						if (chara != null) {
//							int x = chara.getX();
//							int y = chara.getY();
//							chara = new Chara(lastFlag, 0, 0, getCurrentWidth());
//							chara.setMove(false);
//							chara.setX(x);
//							chara.setY(y);
//							scrCG.addChara(lastFlag, chara);
//						}
//					} else {
//						int x = 0, y = 0;
//						if (orderFlag != null) {
//							x = Integer.parseInt(orderFlag);
//						}
//						if (size >= 4) {
//							y = Integer.parseInt((String) commands.get(3));
//						}
//						scrCG.addImage(mesFlag, x, y, getCurrentWidth());
//					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_EXIT)) {
					scrFlag = true;
//					setFPS(LSystem.DEFAULT_MAX_FPS);
					running = false;
//					onExit();
					break;
				}
			}
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
	/**
	 * 播放Assets中的音频文件
	 * 
	 * @param file
	 * @param loop
	 */
	public void playtAssetsMusic(final String file, final boolean loop) {
		if (handler != null) {
			handler.getAssetsSound().playSound(file, loop);
		}
	}

	/**
	 * 设置Assets中的音频文件音量
	 * 
	 * @param vol
	 */
	public void resetAssetsMusic(final int vol) {
		if (handler != null) {
			handler.getAssetsSound().setSoundVolume(vol);
		}
	}

	/**
	 * 重置Assets中的音频文件
	 * 
	 */
	public void resetAssetsMusic() {
		if (handler != null) {
			handler.getAssetsSound().resetSound();
		}
	}

	/**
	 * 中断Assets中的音频文件
	 */
	public void stopAssetsMusic() {
		if (handler != null) {
			handler.getAssetsSound().stopSound();
		}
	}

	/**
	 * 中断Assets中指定索引的音频文件
	 */
	public void stopAssetsMusic(int index) {
		if (handler != null) {
			handler.getAssetsSound().stopSound(index);
		}
	}
}
