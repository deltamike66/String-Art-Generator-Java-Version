import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
public class StringArt {
	
	private File fileImage;
	private BufferedImage image;
	
	private JFrame frmStringArtBy;
	private JButton btn_loadImage;
	private JLabel lbl_pathFile;
	private JLabel lbl_image;
	private JScrollPane scrollPane;
	private JSpinner spinner_nails;
	private JSpinner spinner_lines;
	private JButton btn_generate;
	private JLabel lbl_art;
	private Art art;
	private JSlider slider_opacity;
	private JPanel panel_1;
	private JSlider slider_brightness;
	private JSlider slider_zoom;
	private JButton btnSave;
	private JLabel lblXOffset;
	private JLabel lblYOffset;
	private JLabel lblOpacity_1;
	private JLabel lblBrightness_1;
	private JLabel lblZoom_1;
	private JSlider sliderThickness;
	private JLabel lblTickness;
	private JComboBox<String> comboBoxShape;
	private JLabel lblNails;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StringArt window = new StringArt();
					window.frmStringArtBy.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StringArt() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStringArtBy = new JFrame();
		frmStringArtBy.setTitle("String Art by Deltamike");
		frmStringArtBy.setBounds(100, 100, 1310, 600);
		frmStringArtBy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btn_loadImage = new JButton("Load Image");
		btn_loadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					loadImage();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_loadImage.setBounds(12, 12, 117, 25);
		frmStringArtBy.getContentPane().setLayout(null);
		frmStringArtBy.getContentPane().add(btn_loadImage);
		
		lbl_pathFile = new JLabel("");
		lbl_pathFile.setBounds(135, 17, 359, 15);
		frmStringArtBy.getContentPane().add(lbl_pathFile);
		
		scrollPane = new JScrollPane(); 
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 49, 512, 512);
		scrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				updateLabel();
			}
		});
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				updateLabel();
			}
		});
		frmStringArtBy.getContentPane().add(scrollPane);
		
		lbl_image = new JLabel("");
		lbl_image.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				try {
					zoomImage(arg0.getWheelRotation());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(lbl_image);
		lbl_image.setAutoscrolls(true);
		lbl_image.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		lblNails = new JLabel("Nails");
		lblNails.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNails.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblNails.setFocusTraversalKeysEnabled(false);
		lblNails.setBounds(970, 17, 103, 15);
		frmStringArtBy.getContentPane().add(lblNails);
		
		spinner_nails = new JSpinner();
		spinner_nails.setFont(new Font("Dialog", Font.PLAIN, 12));
		spinner_nails.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateNails();
			}
		});
		spinner_nails.setModel(new SpinnerNumberModel(180, 4, 1000, 1));
		spinner_nails.setBounds(1078, 16, 76, 20);
		frmStringArtBy.getContentPane().add(spinner_nails);
		
		JLabel lblLines = new JLabel("Lines");
		lblLines.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLines.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblLines.setFocusTraversalKeysEnabled(false);
		lblLines.setBounds(1155, 18, 50, 15);
		frmStringArtBy.getContentPane().add(lblLines);
		
		spinner_lines = new JSpinner();
		spinner_lines.setFont(new Font("Dialog", Font.PLAIN, 12));
		spinner_lines.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateLines();
			}
		});
		spinner_lines.setModel(new SpinnerNumberModel(100,1,10000,100));
		spinner_lines.setBounds(1208, 16, 76, 20);
		frmStringArtBy.getContentPane().add(spinner_lines);
		
		btn_generate = new JButton("Generate Art");
		btn_generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateArt();
			}
		});
		btn_generate.setEnabled(false);
		btn_generate.setBounds(583, 408, 130, 25);
		frmStringArtBy.getContentPane().add(btn_generate);
		
		lbl_art = new JLabel("");
		lbl_art.setBackground(Color.WHITE);
		lbl_art.setBounds(774, 49, 510, 510);
		frmStringArtBy.getContentPane().add(lbl_art);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(691, 51, 76, 231);
		frmStringArtBy.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Line Opacity");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel.add(lblNewLabel);
		
		slider_opacity = new JSlider(SwingConstants.VERTICAL,0, 100, 50);
		slider_opacity.setEnabled(false);
		panel.add(slider_opacity);
		slider_opacity.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateOpacity();
			}
		});
		slider_opacity.setFont(new Font("Dialog", Font.PLAIN, 8));
		slider_opacity.setPaintLabels(true);
		slider_opacity.setPaintTicks(true);
		slider_opacity.setMinorTickSpacing(5);
		slider_opacity.setMajorTickSpacing(25);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(611, 51, 76, 231);
		frmStringArtBy.getContentPane().add(panel_1);
		
		JLabel lblB = new JLabel("Brightness");
		lblB.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_1.add(lblB);
		
		slider_brightness = new JSlider(SwingConstants.VERTICAL, 0, 100, 100);
		slider_brightness.setEnabled(false);
		slider_brightness.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateBrightness();
			}
		});
		slider_brightness.setPaintTicks(true);
		slider_brightness.setPaintLabels(true);
		slider_brightness.setMinorTickSpacing(5);
		slider_brightness.setMajorTickSpacing(25);
		slider_brightness.setFont(new Font("Dialog", Font.PLAIN, 8));
		panel_1.add(slider_brightness);
		slider_brightness.setFont(new Font("Dialog", Font.PLAIN, 8));
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1_1.setBounds(531, 51, 76, 231);
		frmStringArtBy.getContentPane().add(panel_1_1);
		
		JLabel lblZoom = new JLabel("   Zoom    ");
		lblZoom.setFont(new Font("Dialog", Font.PLAIN, 10));
		panel_1_1.add(lblZoom);
		
		slider_zoom = new JSlider(SwingConstants.VERTICAL, 50, 1000, 500);
		slider_zoom.setEnabled(false);
		panel_1_1.add(slider_zoom);
		slider_zoom.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					zoomImage(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		slider_zoom.setPaintTicks(true);
		slider_zoom.setMinorTickSpacing(50);
		slider_zoom.setMajorTickSpacing(100);
		slider_zoom.setFont(new Font("Dialog", Font.PLAIN, 8));
		
		btnSave = new JButton("Save File");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					saveFile();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSave.setEnabled(false);
		btnSave.setBounds(583, 533, 130, 25);
		frmStringArtBy.getContentPane().add(btnSave);
		
		lblXOffset = new JLabel("X offset:");
		lblXOffset.setBounds(583, 442, 140, 14);
		frmStringArtBy.getContentPane().add(lblXOffset);
		
		lblYOffset = new JLabel("Y offset:");
		lblYOffset.setBounds(583, 460, 140, 14);
		frmStringArtBy.getContentPane().add(lblYOffset);
		
		lblZoom_1 = new JLabel("Zoom:");
		lblZoom_1.setBounds(583, 478, 140, 14);
		frmStringArtBy.getContentPane().add(lblZoom_1);
		
		lblBrightness_1 = new JLabel("Brightness:");
		lblBrightness_1.setBounds(584, 496, 140, 14);
		frmStringArtBy.getContentPane().add(lblBrightness_1);
		
		lblOpacity_1 = new JLabel("Line opacity:");
		lblOpacity_1.setBounds(583, 514, 140, 14);
		frmStringArtBy.getContentPane().add(lblOpacity_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(532, 288, 234, 53);
		frmStringArtBy.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		sliderThickness = new JSlider(SwingConstants.HORIZONTAL, 0, 300, 100);
		sliderThickness.setMaximum(300);
		sliderThickness.setMinorTickSpacing(10);
		sliderThickness.setBounds(12, 22, 210, 27);
		sliderThickness.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateTickness();
			}
		});
		panel_2.add(sliderThickness);
		
		lblTickness = new JLabel("Thickness of line:");
		lblTickness.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblTickness.setBounds(18, 9, 192, 15);
		panel_2.add(lblTickness);
		
		comboBoxShape = new JComboBox<String>();
		comboBoxShape.addItem("Circle");
		comboBoxShape.addItem("Square");
		comboBoxShape.addItem("Grid");
		
		comboBoxShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeShape();
			}
		});
		comboBoxShape.setFont(new Font("Dialog", Font.PLAIN, 12));
		comboBoxShape.setBounds(822, 12, 136, 24);
		frmStringArtBy.getContentPane().add(comboBoxShape);
		
		JLabel lblShape = new JLabel("Shape");
		lblShape.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblShape.setFocusTraversalKeysEnabled(false);
		lblShape.setBounds(774, 17, 43, 15);
		frmStringArtBy.getContentPane().add(lblShape);
		drawNails();
	}
	
	/**
	 * open file chooser and choose file jpg or png
	 * @throws IOException 
	 */
	private void loadImage() throws IOException {
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics File Accepted: JPG, PNG", "jpg", "png", "jpeg");
		chooser.addChoosableFileFilter(filter);
		int returnVal = chooser.showOpenDialog(frmStringArtBy);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			 fileImage = chooser.getSelectedFile();
			 lbl_pathFile.setText(fileImage.toString());
			 insertImage();
			 spinner_nails.setEnabled(true);
			 spinner_lines.setEnabled(true);
			 btn_generate.setEnabled(true);
			 slider_brightness.setEnabled(true);
			 slider_opacity.setEnabled(true);
			 slider_zoom.setEnabled(true);
			 btnSave.setEnabled(true);
			 resetValue();
		}
	}
	
	/**
	 * insert image from file to label
	 * @throws IOException 
	 */
	private void insertImage() throws IOException {
		image = ImageIO.read(fileImage);
		lbl_image.setIcon(new ImageIcon(image));
	}
	
	private void zoomImage(int z) throws IOException {
		if (z != 0) {
			slider_zoom.setValue(slider_zoom.getValue()+z*10);
		}
		updateLabel();
		double value = slider_zoom.getValue()/500.0;
		int width = image.getWidth();
		int height = image.getHeight();
        Image newImg = image.getScaledInstance((int)(width*value), (int)(height*value),Image.SCALE_AREA_AVERAGING);
        lbl_image.setIcon( new ImageIcon(newImg) );
	}

	private void updateLabel() {
		int scrollX = scrollPane.getHorizontalScrollBar().getValue();
		int scrollY = scrollPane.getVerticalScrollBar().getValue();
		double zoom = slider_zoom.getValue()/500.0;
		int bright = slider_brightness.getValue();
		int opacity = slider_opacity.getValue();
		double thick = sliderThickness.getValue()/100.0;
		lblXOffset.setText("X Offset: " + scrollX);
		lblYOffset.setText("Y Offset: " + scrollY);
		lblZoom_1.setText("Zoom: " + zoom);
		lblBrightness_1.setText("Brightness: " + bright);
		lblOpacity_1.setText("Opacity: " + opacity);
		lblTickness.setText("Thickness of line: " + thick);
	}
	
	private void generateArt() {
		updateLabel();
		int scrollX = scrollPane.getHorizontalScrollBar().getValue();
		int scrollY = scrollPane.getVerticalScrollBar().getValue();
		lbl_art.setIcon(new ImageIcon(art.generate(lbl_image, scrollPane, scrollX, scrollY)));
	}
	
	private void drawNails() {
		art = new Art(comboBoxShape.getSelectedIndex(),
					 (int)spinner_nails.getValue(), 
					 (int)spinner_lines.getValue(),
					 slider_opacity.getValue()/100f,
					 slider_brightness.getValue(),
					 sliderThickness.getValue()/100f);
		lbl_art.setIcon(new ImageIcon(art.drawNails()));
	}
	
	private void updateNails() {
		art.setNails((int)spinner_nails.getValue());
		drawNails();
	}
	
	private void updateLines() {
		art.setLines((int)spinner_lines.getValue());
	}
	
	private void updateOpacity() {
		int value = slider_opacity.getValue();
		updateLabel();
		art.setOpacity(value);
	}
	
	private void updateBrightness() {
		int value = slider_brightness.getValue();
		updateLabel();
		art.setBrightness(value);
	}
	
	private void saveFile() throws FileNotFoundException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");
		int userSelection = fileChooser.showSaveDialog(frmStringArtBy);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			int scrollX = scrollPane.getHorizontalScrollBar().getValue();
			int scrollY = scrollPane.getVerticalScrollBar().getValue();
			double zoom = slider_zoom.getValue()/500.0;
			int bright = slider_brightness.getValue();
			int opacity = slider_opacity.getValue();
			float thick = sliderThickness.getValue()/100f;
		    
			File fileToSave = fileChooser.getSelectedFile();
		    int[] nail = new int[art.getLines()];
		    nail = art.getNail();
		    PrintWriter out = new PrintWriter(fileToSave);
		    
		    out.println(fileImage.toString());
		    out.println("X Offset: " + scrollX);
		    out.println("Y Offset: " + scrollY);
		    out.println("Zoom: " + zoom);
		    out.println("Brightness: " + bright);
		    out.println("Opacity: " + opacity);
		    out.println("Thickness: " + thick);
		    
		    for (int l=0;l<nail.length; l++) {
		    	if (l%20 == 0) {
		    		out.println("");
		    		out.print(l/20+1 + "[] -> ");
		    	}
		    	out.print(Integer.toString(nail[l]) + ", ");
		    }
		    out.close();
		}
	}
	
	private void updateTickness() {
		updateLabel();
		art.setThickness(sliderThickness.getValue()/100f);
	}
	
	private void resetValue() {
		scrollPane.getHorizontalScrollBar().setValue(0);
		scrollPane.getVerticalScrollBar().setValue(0);
		slider_zoom.setValue(500);
		slider_brightness.setValue(100);
		slider_opacity.setValue(50);
		sliderThickness.setValue(100);
		updateLabel();
	}
	
	private void changeShape() {
		switch (comboBoxShape.getSelectedIndex()) {
		case 0: // Circle
			lblNails.setText("Nails");
			spinner_nails.setValue(180);
			break;
		case 1:
			lblNails.setText("Nails on each side");
			spinner_nails.setValue(40);
			break;
		case 2:
			lblNails.setText("Nails on each side");
			spinner_nails.setValue(40);
			break;
		default:
			break;
		}
		drawNails();
	}
}
