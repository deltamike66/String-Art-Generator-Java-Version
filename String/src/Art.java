import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Art {
	
	private BufferedImage art; // result
	private BufferedImage photo; // picture to elaborate
	private Graphics2D g2d_art;
	private Graphics2D g2d_photo;
	private int nails;
	private int lines;
	private int[] nailX;
	private int[] nailY;
	private int startNail;
	private int destNail;
	private float opacity;
	private int brightness;
	private int[] nail;
	private float thickness;


	public Art(int nails, int lines) {
		this.nails = nails;
		this.lines = lines;
		this.nailX = new int[this.nails];
		this.nailY = new int[this.nails];
		this.nail = new int[this.lines];
		this.startNail = 0;
		this.destNail = 0;
		this.opacity = 0.5f;
		this.brightness = 255;
		this.thickness = 1f;
		newArt();
	}
	
	public Art(int nails, int lines, float opacity, int brightness, float thickness) {
		this.nails = nails;
		this.lines = lines;
		this.nailX = new int[this.nails];
		this.nailY = new int[this.nails];
		this.nail = new int[this.lines];
		this.startNail = 0;
		this.destNail = 0;
		this.opacity = opacity;
		this.brightness = 255*brightness/100;
		this.thickness = thickness;
		newArt();
	}
	
	private void newArt() {
		this.art = new BufferedImage(510,510,1);
		this.g2d_art = this.art.createGraphics();
		this.g2d_art.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.g2d_art.setPaint(new Color(255,255,255));
    	this.g2d_art.fillRect(0, 0, art.getWidth(), art.getHeight());
    	this.g2d_art.setFont(new Font("Dialog", Font.PLAIN, 10));
    	
    	this.photo = new BufferedImage(510,510,1);
		this.g2d_photo = this.photo.createGraphics();
		this.g2d_photo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.g2d_photo.setPaint(new Color(255,255,255));
    	this.g2d_photo.fillRect(0, 0, photo.getWidth(), photo.getHeight());
	}

	public BufferedImage getImg() {
		return this.art;
	}

	public int getNails() {
		return nails;
	}
	
	public void setNails(int nails) {
		this.nails = nails;
		this.nailX = new int[this.nails];
		this.nailY = new int[this.nails];
	}
	
	public int getLines() {
		return lines;
	}
	
	public void setLines(int lines) {
		this.lines = lines;
		this.nail = new int[this.lines];
	}
	
	public void setOpacity(int value) {
		this.opacity = value/100.0f;
	}
	
	public void setBrightness(int value) {
		this.brightness = 255*value/100;   // 50:100 = x:255
	}
	
	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	public BufferedImage drawNails() {
    	double incDegree = 2 * Math.PI / nails;
		this.g2d_art.setColor(Color.BLUE);
		for (int i=0; i< this.nails; i++) {
			double x = 255+Math.cos(i*incDegree)*245;
			double y = 255+Math.sin(i*incDegree)*245;
			if (i%20 == 0) {
				this.g2d_art.drawString(Integer.toString(i), (int)(255+Math.cos(i*incDegree)*215), (int)(255+Math.sin(i*incDegree)*215));
			}
			this.g2d_art.drawOval((int)x, (int)y, 1, 1);
			this.nailX[i] = (int)x;
			this.nailY[i] = (int)y;
		}
		return this.art;
	}
	
	public BufferedImage generate(JLabel lbl, JScrollPane sp, int scrollX, int scrollY) {
		newArt(); // empty image
		drawNails();
		
		int visibleAreaX = sp.getViewport().getSize().width;
		int visibleAreaY = sp.getViewport().getSize().height;
		
		ImageIcon imageIcon= (ImageIcon) lbl.getIcon();
		BufferedImage image = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		imageIcon.paintIcon(null, g, 0, 0);
		g.dispose();
		
		int width = this.art.getWidth() > visibleAreaX ? visibleAreaX : this.art.getWidth();
		int height = this.art.getHeight() > visibleAreaY ? visibleAreaY : this.art.getHeight();
		
		int offsetX = (this.art.getWidth() - visibleAreaX) / 2;
		int offsetY = (this.art.getWidth() - visibleAreaY) / 2;
		
		int[][] result = new int[width][height];
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (scrollX + col < imageIcon.getIconWidth() && scrollY + row < imageIcon.getIconHeight()) {
					result[col][row] = image.getRGB(col, row);
					this.photo.setRGB(offsetX + col, offsetY + row, image.getRGB(scrollX + col, scrollY + row));
				}
			}
		}
		
		this.g2d_art = art.createGraphics();
		this.g2d_art.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.g2d_art.setPaint(new Color(255,255,255));
    		this.g2d_art.fillRect(0, 0, art.getWidth(), art.getHeight());
		
		double incDegree = 2 * Math.PI / nails;
		this.g2d_art.setColor(Color.BLUE);
		for (int i=0; i< this.nails; i++) {
			double x = 255+Math.cos(i*incDegree)*245;
			double y = 255+Math.sin(i*incDegree)*245;
			this.g2d_art.drawOval((int)x, (int)y, 1, 1);
			this.nailX[i] = (int)x;
			this.nailY[i] = (int)y;
		}
		
		this.startNail = 0;
		for (int l=0; l<this.lines; l++) {
			nail[l]=this.startNail;
			this.destNail = this.maxAvg(this.startNail);
			
			this.g2d_art.setColor(Color.BLACK); // Line to art
			Stroke strokePhoto = new BasicStroke(thickness);
			g2d_art.setStroke(strokePhoto);
			AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
			this.g2d_art.setComposite(alcom);
			
			this.g2d_art.drawLine(this.nailX[this.startNail], this.nailY[this.startNail], this.nailX[this.destNail], this.nailY[this.destNail]);
			g = (Graphics2D) this.photo.getGraphics();
			Stroke strokeArt = new BasicStroke(thickness+0.2f);
			g.setStroke(strokeArt);
			g.setColor(new Color(this.brightness,this.brightness,this.brightness)); // Line to delete the photo
			g.drawLine(this.nailX[this.startNail], this.nailY[this.startNail], this.nailX[this.destNail], this.nailY[this.destNail]);
			this.startNail = this.destNail;
		}
		
		return this.art;
	}
	
	public int[] getNail() {
		return nail;
	}

	private float avgLine(int x1, int y1, int x2, int y2) {
		int swap = 0;
		int DX = x2 - x1;
		int DY = y2 - y1;
		int D_Temp;
		float bright;
		int pixel = 2;

		if (Math.abs(DX) < Math.abs(DY)) {
			D_Temp = DX;
			DX = DY;
			DY = D_Temp;
			swap = 1;
		}

		int a = Math.abs(DY);
		int b = -Math.abs(DX);

		int x = x1;
		int y = y1;

		int d = 2 * a + b;

		int q = 1;
		int s = 1;
		if (x1 > x2) q = -1;
		if (y1 > y2) s = -1;
		bright = this.photo.getRGB(x, y);
		bright += this.photo.getRGB(x2, y2);
		for (int k = 0; k < -b; k+=1) {
			if (d > 0) {
				x= x + q; y= y + s;
				d= d + 2 * (a + b);
			}
			else {
				x = x + q;
				if (swap==1) { y = y + s; x = x - q; }
				d = d + 2 * a;
			}
			pixel++;
			bright += this.photo.getRGB(x, y);
		}
		return bright/pixel;
	}
	
	private int maxAvg(int sn){
		float avg = 0;
		float avgMin = 10000;
		int dest=sn;
		for (int i=0; i<nails; i++){
			int en = (sn+i)%nails;
			avg = avgLine(nailX[sn], nailY[sn], nailX[en], nailY[en]);
			if (avgMin > avg) {
				avgMin = avg;
				dest = (sn+i)%nails;
			}
		}
		return dest;
	}
}

