import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXPanel;

public class PanelWithShadow extends JXPanel{

    private static final long serialVersionUID = 1L;

    public int pixels;

    public PanelWithShadow(int pix) {
        this.pixels = pix;
        Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels, pixels);
        this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));
        this.setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        int shade = 0;
        int topOpacity = 80;
        for (int i = 0; i < pixels; i++) {
            g.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
            g.drawRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1));
        }
    }
}