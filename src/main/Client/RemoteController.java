package main.Client;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class RemoteController extends JFrame implements MouseListener,MouseMotionListener, WindowListener {

	protected boolean wasReleased = false;
	protected boolean wasPressed = false;
	protected boolean wasClicked = false ; 
	protected Timer tim1;
	protected Timer tim2;
	private Client client;
	private JLabel screenImage;
	;
	
	
	public RemoteController(byte[] byteimg,Client c){
		super("Remote server");
		client = c;
		//setSize(800,600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addMouseListener(this);
		
		screenImage = new JLabel();
	   addWindowListener(this);
		updateScreen(byteimg);
        add(screenImage);
        pack();
	}
	
	
	public RemoteController() {
		super("Remote server");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addMouseListener(this);
		setVisible(true);
	    addWindowListener(this);
        pack();
	}


	public void updateScreen(byte[] byteimg) {
		//convert byte array back to BufferedImage
		InputStream in = new ByteArrayInputStream(byteimg);
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		double oriHeight = bimg.getHeight();
		double oriWidth = bimg.getWidth();
		Dimension local = Toolkit.getDefaultToolkit().getScreenSize();
		int modHeight, modWidth;
		double factor = oriHeight/oriWidth;
		modWidth = 1024;
		modHeight = (int)Math.round(factor * modWidth);
		BufferedImage scaledImage = new BufferedImage(
				modWidth, modHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(bimg, 0, 0, modWidth, modHeight, null);

			// clean up

			graphics2D.dispose();
			setSize(modWidth,modHeight);

		screenImage.setIcon(new javax.swing.ImageIcon(scaledImage));
		if(!isVisible()){
			setUndecorated(true);
			setVisible(true);
		}
			
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(wasClicked){
			tim1.stop();
			tim2.stop();
			wasClicked = false ;
			double x = e.getPoint().x;
			double y = e.getPoint().y;
			x = x / ((double)getWidth()/100) ;
			y = y / ((double)getHeight()/100) ;
			double[] pourcentFenetre = new double[]{x/100,y/100};
			Asker ask = new Asker(null, client, Worker.MOUSE_CLICKED, false, pourcentFenetre);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		wasReleased = false;
		final MouseEvent e1 = e;
		// Pour éviter d'envoyer 2 requetes très rapproché si c'est un clic !
		tim1 = new Timer(400,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(wasReleased){
					wasReleased = false;
					wasPressed = false;
					wasClicked = true;
					mouseClicked(e1);
					wasClicked = false;
					System.out.println("click");
				}else{
					System.out.println("press");
					wasPressed = true;
					double x = e1.getPoint().x;
					double y = e1.getPoint().y;
					x = x / ((double)getWidth()/100) ;
					y = y / ((double)getHeight()/100) ;
					double[] pourcentFenetre = new double[]{x/100,y/100};
					Asker ask = new Asker(null, client, Worker.MOUSE_PRESSED, false, pourcentFenetre);
				}
				tim1.stop();
			}
		});
		tim1.start();
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		wasReleased = true;
		final MouseEvent e1 = e;
		// Pour éviter d'envoyer 2 requetes très rapproché si c'est un clic !
		tim2 = new Timer(400,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(wasPressed){
					System.out.println("released");
					double x = e1.getPoint().x;
					double y = e1.getPoint().y;
					x = x / ((double)getWidth()/100) ;
					y = y / ((double)getHeight()/100) ;
					double[] pourcentFenetre = new double[]{x/100,y/100};
					Asker ask = new Asker(null, client, Worker.MOUSE_RELEASED, false, pourcentFenetre);
				}
				//wasPressed = false;
				tim2.stop();
			}
		});
		tim2.start();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {

		
	}

	public static void main(String[] args){
		Robot bot;
		try {
			bot = new Robot();
			RemoteController rmc = new RemoteController();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent arg0) {
		Asker ask = new Asker(null, client, Worker.STOP_USER_CONTROL, false);
	}


	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}




}
