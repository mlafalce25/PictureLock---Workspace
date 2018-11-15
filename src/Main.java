import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;    

public class Main extends JFrame implements MouseListener{

	private static final long serialVersionUID = 1L;
	private BufferedImage image;	
	static ArrayList<Point> points = new ArrayList<>();//attempted points that the user has entered
	static ArrayList<Point> lock = new ArrayList<>();//actual points that unlock the screen
	Boolean passwordCreated = false;//manages state change between creating password and trying to log in

	
	// Constructor to setup the GUI
	public Main() {
		
		//instantiates the GUI components
		JLabel contentPanel = new JLabel();
		JPanel coursePanel = new JPanel();	
		JLabel pictureLabel = new JLabel();
		JButton button = new JButton("Save Password");
		
		
		
		pictureLabel.setLayout(new FlowLayout(FlowLayout.CENTER));
		pictureLabel.addMouseListener(this);
		addMouseListener(this);
		
		
		try
		{
			image = ImageIO.read(new File("/Users/Matt/Documents/workspace/PictureLock/src/seabear2.jpeg")); //reads in the image from memory
			pictureLabel.add(new JLabel(new ImageIcon(image))); //adds the image to the label
			contentPanel.setText("To create password, CLICK at least 4 points on the PICTURE. Then click 'Save Password'.");//adds the message to the panel
		
		}
		catch(IOException ioe)
		{
			System.out.println("Unable to fetch image.");
			ioe.printStackTrace();
		}
		
		//button controls the state change from the password creation to the password entering states
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				passwordCreated = true;
				eventCount = 0;
				System.out.println("Button Clicked");
				contentPanel.setText("Enter password to unlock screen.");//changes the message to the panel
				button.setVisible(false);

				
			}
		});		
		
		
		contentPanel.setSize(new Dimension(900, 50));//dimensions of the message panel at the bottom of the page
		pictureLabel.setPreferredSize(new Dimension(786, 440)); // dimensions of picture
		
		
		//compiling the components together
		pictureLabel.add(button);
		coursePanel.add(button);
		coursePanel.add(pictureLabel);
		coursePanel.add(contentPanel);

		getContentPane().add(coursePanel);//adds the components to the view
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//defines how to close the operation  
		setTitle("Create Password");//sets view title 
		setSize(900,550); //sets view size
		setVisible(true); //makes view visible to the user

				
	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {


		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main(); 
			}
		});

	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked");
	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void mousePressed(MouseEvent e) {
	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	int eventCount = 0;
	int correctPoints = 0;
	@Override
	public void mouseReleased(MouseEvent e) {

		if(passwordCreated == true) {//if true allows user to store attempted clicks and then see if they entered the correct login
			if(eventCount<lock.size()) {
				eventCount++;
				System.out.println(eventCount);
				points.add(e.getPoint());
				if(eventCount == lock.size()) {
					System.out.println("__________________________________________");
					System.out.println("Comparison Starting");
					System.out.println("__________________________________________\n");

					//comparison to correct lock combination
					for(int x=0;x<lock.size();x++) {
						System.out.println("Check "+ x);

						if(points.get(x).getX()>(lock.get(x).getX()-30) && points.get(x).getX()<(lock.get(x).getX()+30)) {
							System.out.println("	X is right");

							if(points.get(x).getY()>(lock.get(x).getY()-30) && points.get(x).getY()<(lock.get(x).getY()+30)) {
								System.out.println("	Y is right");

								correctPoints++;	
								System.out.println("Point "+x+" is correct");
								System.out.println("__________________________________________\n");

							}

						}

						if(correctPoints == lock.size()) {//if all points are correct, will display a new image
							System.out.println("You did it!");
							//change picture
							try
							{
								image = ImageIO.read(new File("/Users/Matt/Documents/workspace/PictureLock/src/trump.jpeg"));

								setContentPane(new JLabel(new ImageIcon(image)));
								
							}
							catch(IOException ioe)
							{
								System.out.println("Unable to fetch image.");
								ioe.printStackTrace();
							}

							setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
							setTitle("Success Screen"); 
							setSize(900,550);  
							setVisible(true);  
						}

					}

					correctPoints = 0;
					eventCount = 0;
					System.out.println("Comparison Ending");
					System.out.println("__________________________________________\n");

				}
			}
		}
		else {

				System.out.println("Point "+eventCount+" saved.");
				System.out.println(e.getPoint());
				System.out.println(e.getSource());
				eventCount++;
				lock.add(e.getPoint());			
				System.out.println();

		}
	}


	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------


	@Override
	public void mouseEntered(MouseEvent e) {

	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------



	@Override
	public void mouseExited(MouseEvent e) {

	}
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------


}
