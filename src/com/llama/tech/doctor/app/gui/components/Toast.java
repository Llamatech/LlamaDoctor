/*
 * Toast.java
 * This file is part of LlamaDoctor
 * 
 * Copyright (C) 2015 - schnie
 * Taken from https://github.com/schnie/android-toasts-for-swing/blob/master/Toast.java
 *
 * LlamaDoctor is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * LlamaDoctor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LlamaDoctor. If not, see <http://www.gnu.org/licenses/>.
 */


package com.llama.tech.doctor.app.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Toast extends JDialog {
	private static final long serialVersionUID = -1602907470843951525L;

	public enum Style {
		NORMAL, SUCCESS, ERROR
	};

	public static final int LENGTH_SHORT = 3000;
	public static final int LENGTH_LONG = 6000;
	public static final Color ERROR_RED = new Color(121, 0, 0);
	public static final Color SUCCESS_GREEN = new Color(22, 127, 57);
	public static final Color NORMAL_BLACK = new Color(0, 0, 0);
	private final float MAX_OPACITY = 0.8f;
	private final float OPACITY_INCREMENT = 0.05f;
	private final int FADE_REFRESH_RATE = 20;
	private final int WINDOW_RADIUS = 15;
	private final int CHARACTER_LENGTH_MULTIPLIER = 7;
	private final int DISTANCE_FROM_PARENT_TOP = 100;
	private JFrame mOwner;
	private String mText;
	private int mDuration;
	private Color mBackgroundColor = Color.BLACK;
	private Color mForegroundColor = Color.WHITE;
	private Font font;

	public Toast(JFrame owner) {
		super(owner);
		mOwner = owner;
	}

	private void createGUI() {
		setLayout(new GridBagLayout());
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setShape(new RoundRectangle2D.Double(0, 0, getWidth(),
						getHeight(), WINDOW_RADIUS, WINDOW_RADIUS));
			}
		});
		setAlwaysOnTop(true);
		setUndecorated(true);
		setFocusableWindowState(false);
		setModalityType(ModalityType.MODELESS);
		setSize(mText.length() * CHARACTER_LENGTH_MULTIPLIER, 25);
		getContentPane().setBackground(mBackgroundColor);
		JLabel label = new JLabel(mText);
		label.setForeground(mForegroundColor);
		
		if(font != null)
		{
			label.setFont(font);
		}
		
		add(label);
	}

	public void fadeIn() {
		final Timer timer = new Timer(FADE_REFRESH_RATE, null);
		timer.setRepeats(true);
		timer.addActionListener(new ActionListener() {
			private float opacity = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity += OPACITY_INCREMENT;
				setOpacity(Math.min(opacity, MAX_OPACITY));
				if (opacity >= MAX_OPACITY) {
					timer.stop();
				}
			}
		});
		setOpacity(0);
		timer.start();
		setLocation(getToastLocation());
		setVisible(true);
	}

	public void fadeOut() {
		final Timer timer = new Timer(FADE_REFRESH_RATE, null);
		timer.setRepeats(true);
		timer.addActionListener(new ActionListener() {
			private float opacity = MAX_OPACITY;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity -= OPACITY_INCREMENT;
				setOpacity(Math.max(opacity, 0));
				if (opacity <= 0) {
					timer.stop();
					setVisible(false);
					dispose();
				}
			}
		});
		setOpacity(MAX_OPACITY);
		timer.start();
	}

	private Point getToastLocation() {
		Point ownerLoc = mOwner.getLocation();
		int x = (int) (ownerLoc.getX() + ((mOwner.getWidth() - this.getWidth()) / 2));
		int y = (int) (ownerLoc.getY() + (mOwner.getHeight() - DISTANCE_FROM_PARENT_TOP*2));
		return new Point(x, y);
	}

	public void setText(String text) {
		mText = text;
	}

	public void setDuration(int duration) {
		mDuration = duration;
	}

	@Override
	public void setBackground(Color backgroundColor) {
		mBackgroundColor = backgroundColor;
	}

	@Override
	public void setForeground(Color foregroundColor) {
		mForegroundColor = foregroundColor;
	}
	
	public static Toast makeText(JFrame owner, String text) 
	{
		return makeText(owner, text, LENGTH_SHORT, null);
	}

	public static Toast makeText(JFrame owner, String text, Font font) {
		return makeText(owner, text, LENGTH_SHORT, font);
	}

	public static Toast makeText(JFrame owner, String text, Style style, Font font) {
		return makeText(owner, text, LENGTH_SHORT, style, font);
	}

	public static Toast makeText(JFrame owner, String text, int duration, Font font) {
		return makeText(owner, text, duration, Style.NORMAL, font);
	}

	public static Toast makeText(JFrame owner, String text, int duration,
			Style style, Font font) {
		Toast toast = new Toast(owner);
		toast.mText = text;
		toast.mDuration = duration;
		if (style == Style.SUCCESS)
			toast.mBackgroundColor = SUCCESS_GREEN;
		if (style == Style.ERROR)
			toast.mBackgroundColor = ERROR_RED;
		if (style == Style.NORMAL)
			toast.mBackgroundColor = NORMAL_BLACK;
		if(font != null)
		{
			toast.font = font;
		}
		return toast;
	}

	public void display() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					createGUI();
					fadeIn();
					Thread.sleep(mDuration);
					fadeOut();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();
	}
}