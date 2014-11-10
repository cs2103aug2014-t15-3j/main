import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

/*
 * Class that creates a image backpanel for a dialog
 */
public class DialogBackPanel extends JPanel {
	private final Paint fill = new Color(0x000000, true);
	private final ImageIcon shadowImage = new ImageIcon(DialogFX.class.getResource("dialogShadow.png"));
	private final ImageIcon addImage = new ImageIcon(DialogFX.class.getResource("added.png"));
	private final ImageIcon deleteImage = new ImageIcon(DialogFX.class.getResource("delete.png"));
	private final ImageIcon editImage = new ImageIcon(DialogFX.class.getResource("edited.png"));
	private final ImageIcon saveImage = new ImageIcon(DialogFX.class.getResource("save.png"));
	private ImageIcon Image = new ImageIcon();
	private final JComponent cmp;
	private final Dialog d;

	public DialogBackPanel(Dialog dialog, String command){
		d = dialog;
		cmp = (JComponent) d.getContentPane();
		switch (command){
		case "add":
			Image = addImage;
			break;
		case "delete":
			Image = deleteImage;
			break;
		case "edit":
			Image = editImage;
			break;
		case "save":
			Image = saveImage;
			break;
		default:
			Image = shadowImage;
		}
	}

	//@author A0116160W
	/**
	 * Add the component to BackPanel to display dialog
	 * 
	 */
	public void addComponent() {

		setOpaque(false);
		setLayout(null);
		add(cmp);
		
		if (Image != saveImage){
			cmp.setSize(300, 450);
			cmp.setOpaque(true);
			cmp.setBackground(new Color(0x000000, true));
		} else{
			//Because do not need a display box. But created 
			//inorder for escape key to work 
			cmp.setSize(0,0);
		}
	}

	//@author A0116160W
	/**
	 * To set the background as the Image and cover it in shadow
	 * as well as position the text component onto it.
	 * 
	 * @param graphic
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int w = getWidth();
		int h = getHeight();

		int imageX = 0;
		int imageY = 320;
		cmp.setLocation(120, 200);


		Graphics2D gg = (Graphics2D) g.create();
		gg.setPaint(fill);
		gg.fillRect(0, 0, w, h);
		gg.drawImage(shadowImage.getImage(), -70, -70, GuiMain.frameRemembra.getSize().width+80, 
				(int)GuiMain.frameRemembra.getSize().getHeight()+80 , null);
		gg.drawImage(Image.getImage(), imageX, imageY, 806, 306 , null);
		gg.dispose();
	}

}
