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
 * Collection of utility methods for various stuff with swing applications.
 * @author Aurelien Ribon | http://www.aurelienribon.com
 */
public class DialogFX {
	/**
	 * Centers the dialog over the given parent component. Also, creates a
	 * semi-transparent panel behind the dialog to mask the parent content.
	 * The title of the dialog is displayed in a custom fashion over the dialog
	 * panel, and a rectangular shadow is placed behind the dialog.
	 */
	static void createDialogBackPanel(Dialog dialog, Component parent, String command) {
		DialogBackPanel newContentPane = new DialogBackPanel(dialog, dialog.getTitle(), command);
		newContentPane.create();
		dialog.setContentPane(newContentPane);
		dialog.setSize(GuiMain.frameRemembra.getSize());
		dialog.setLocation(GuiMain.frameRemembra.getLocationOnScreen());
		}

	/**
	 * Adds a glass layer to the dialog to intercept all key events. If the
	 * escpace key is pressed, the dialog is disposed (either with a fadeout
	 * animation, or directly).
	 */
	@SuppressWarnings("serial")
	static void addEscapeToCloseSupport(final JDialog dialog, final boolean fadeOnClose) {
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

	/**
	 * Creates an animation to fade the dialog opacity from 0 to 1.
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
		//timer.start();
		dialog.setVisible(true);
	}

	/**
	 * Creates an animation to fade the dialog opacity from 1 to 0.
	 */
	static void fadeOut(JDialog dialog) {
		final Timer timer = new Timer(10, null);
		timer.setRepeats(true);
		timer.addActionListener(new ActionListener() {
			private float opacity = 1;
			@Override public void actionPerformed(ActionEvent e) {
				opacity -= 0.15f;
				dialog.setOpacity(Math.max(opacity, 0));
				if (opacity <= 0) {
					timer.stop();
					dialog.dispose();
				}
			}
		});

		dialog.setOpacity(1);
		timer.start();
		dialog.setVisible(false);
	}

	@SuppressWarnings("serial")
	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	static class DialogBackPanel extends JPanel {
		private final Paint fill = new Color(0x000000, true);
		private final ImageIcon addImage = new ImageIcon(DialogFX.class.getResource("added.png"));
		private final ImageIcon deleteImage = new ImageIcon(DialogFX.class.getResource("delete.png"));
		private final ImageIcon editImage = new ImageIcon(DialogFX.class.getResource("edited.png"));
		private final ImageIcon saveImage = new ImageIcon(DialogFX.class.getResource("save.png"));
		private final JComponent cmp;
		private final JLabel titlelb;
		private final JLabel info = new JLabel("Hit 'ESC' to close the dialog");
		private final Dialog d;
		public DialogBackPanel(Dialog dialog, String t, String command){
			d = dialog;
			titlelb = new JLabel(t);
			this.cmp = (JComponent) d.getContentPane();
		}
		public void create() {
			
			
			setOpaque(false);
			setLayout(null);
			add(cmp);
			add(titlelb);
			add(info);

			cmp.setBorder(BorderFactory.createLineBorder(new Color(204,0,51), 5));
			titlelb.setFont(new Font("SquareFont", Font.PLAIN, 26));
			titlelb.setForeground(Color.WHITE);
			info.setForeground(Color.WHITE);
			//String s = dialog.getMyTitle();
			//System.out.print(s);
			//titlelb.setText("Task Added!");
			titlelb.setSize(titlelb.getPreferredSize());
			info.setSize(info.getPreferredSize());
			cmp.setSize(cmp.getPreferredSize());
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			int w = getWidth();
			int h = getHeight();

			int shadowX = -70;//31-50;
			int shadowY = -70;//501-25;
			cmp.setLocation(34, 501);
			titlelb.setLocation(34, 501-titlelb.getHeight());
			info.setLocation(34+cmp.getWidth()-info.getWidth(), 501-info.getHeight());

			Graphics2D gg = (Graphics2D) g.create();
			gg.setPaint(fill);
			gg.fillRect(0, 0, w, h);
			gg.drawImage(addImage.getImage(), shadowX, shadowY, GuiMain.frameRemembra.getSize().width+70, (int)GuiMain.frameRemembra.getSize().getHeight()+70 , null);
			gg.dispose();
		}
	}
}
