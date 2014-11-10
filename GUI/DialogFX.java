import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.LayerUI;

/**
 * 
 * Provides effects for dialog box like fadeIn, fadeOut and create a transparent backpanel.
 */
public class DialogFX {
	
	//@author A0116160W
	/**
	 * Creates a semi-transparent panel behind the dialog to mask the parent content.
	 * Creates the backpanel with the appropriate image.
	 * 
	 * @param Dialog
	 * @param Parent window
	 * @param command to detect which backpanel to create
	 * 
	 */
	static void createDialogBackPanel(Dialog dialog, Component parent, String command) {
		DialogBackPanel newContentPane = new DialogBackPanel(dialog, command);
		newContentPane.addComponent();
		dialog.setContentPane(newContentPane);
		dialog.setSize(GuiMain.frameRemembra.getSize());
		dialog.setLocation(GuiMain.frameRemembra.getLocationOnScreen());
	}

	//@author A0116160W
	/**
	 * Adds a glass layer to the dialog to intercept all key events. If the
	 * escape key is pressed, the dialog is disposed (either with a fadeout
	 * animation, or directly).
	 * 
	 * @param Dialog
	 * @param boolean
	 */
	@SuppressWarnings("serial")
	static void addEscapeToCloseSupport(JDialog dialog, boolean fadeOnClose) {
		LayerUI<Container> layerUI = new LayerUI<Container>() {
			private boolean closing = false;

			@Override
			public void installUI(JComponent c) {
				super.installUI(c);
				((JLayer) c).setLayerEventMask(AWTEvent.KEY_EVENT_MASK);
			}

			@Override
			public void uninstallUI(JComponent c) {
				super.uninstallUI(c);
				((JLayer) c).setLayerEventMask(0);
			}

			@Override
			public void eventDispatched(AWTEvent e, JLayer<? extends Container> l) {
				if (e instanceof KeyEvent && ((KeyEvent) e).getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (closing) {
						return;
					}
					closing = true;
					if (fadeOnClose) {
						fadeOut(dialog);
					}else {
						dialog.dispose();
					}
				}
			}
		};

		JLayer<Container> layer = new JLayer<>(dialog.getContentPane(), layerUI);
		dialog.setContentPane(layer);
	}

	//@author A0116160W
	/**
	 * Creates an animation to fade the dialog opacity from 0 to 1.
	 * 
	 * @param Dialog
	 */
	static void fadeIn(JDialog dialog) {
		final Timer timer = new Timer(0, null);
		timer.setRepeats(true);
		timer.addActionListener(new ActionListener() {
			private float opacity = 0;
			@Override public void actionPerformed(ActionEvent e) {
				opacity += 0.25f;
				dialog.setOpacity(Math.min(opacity, 1));
				if (opacity >= 1) {
					timer.stop();
				}
			}
		});

		dialog.setOpacity(0);

		dialog.setVisible(true);
	}

	//@author A0116160W
	/**
	 * Creates an animation to fade the dialog opacity from 1 to 0.
	 * 
	 * @param Dialog
	 */
	static void fadeOut(JDialog dialog) {
		final Timer timer = new Timer(10, null);
		timer.setRepeats(true);
		timer.addActionListener(new ActionListener() {
			private float opacity = 1;
			@Override public void actionPerformed(ActionEvent e) {
				opacity -= 0.25f;
				dialog.setOpacity(Math.max(opacity, 0));
				if (opacity <= 0) {
					timer.stop();
					dialog.setVisible(false);
				}
			}
		});

		dialog.setOpacity(1);
		timer.start();

	}
}
