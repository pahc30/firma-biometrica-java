import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import com.topaz.sigplus.*;
import gnu.io.*;
import java.io.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;

public class SigPlusImgDemo extends Frame
   {
   SigPlus              sigObj = null;
   Thread               eventThread;

   public static void main( String Args[] )
	  {
	  SigPlusImgDemo demo = new SigPlusImgDemo();
	  demo.setSize(800,300);
	  demo.setVisible(true);
          demo.setBackground(Color.lightGray);
	  }

	public SigPlusImgDemo()
		{
                GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		setLayout(gbl);
		Panel controlPanel = new Panel();
		setConstraints(controlPanel, gbl, gc, 0, 0,
		GridBagConstraints.REMAINDER, 1, 0, 0,
		GridBagConstraints.CENTER,
		GridBagConstraints.NONE,0, 0, 0, 0);
		add(controlPanel, gc);



		controlPanel.add(connectionChoice);
		controlPanel.add(connectionTablet);
		
		Button startButton = new Button("START");
		controlPanel.add(startButton);

		Button stopButton = new Button("STOP");
		controlPanel.add(stopButton);
		
		Button clearButton = new Button("CLEAR");
		controlPanel.add(clearButton);

		Button savePngButton = new Button("SAVE SIG PNG");
		controlPanel.add(savePngButton);

		Button okButton = new Button("QUIT");
		controlPanel.add(okButton);

		initConnection();

		try
			{
			ClassLoader cl = (com.topaz.sigplus.SigPlus.class).getClassLoader();
	  		sigObj = (SigPlus)Beans.instantiate( cl, "com.topaz.sigplus.SigPlus" );




		setConstraints(sigObj, gbl, gc, 0, 1,
		GridBagConstraints.REMAINDER, 1, 1, 1,
		GridBagConstraints.CENTER,
		GridBagConstraints.BOTH, 5, 0, 5, 0);
		add(sigObj, gc);
		sigObj.setSize(100,100);
                sigObj.clearTablet();
		setTitle( "Demo SigPlus PNG Sig Image Application" );



	   okButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			    sigObj.setTabletState(0);
    		            System.exit(0);
		   }
	  });

	   startButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			    sigObj.setTabletState(0);
			    sigObj.setTabletState(1);
                            System.out.println(sigObj.getTabletState());
		   }
	  });

	  stopButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			    sigObj.setTabletState(0);
		   }
	  });

	  clearButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
                            System.out.println(sigObj.getKeyReceipt());
			    sigObj.clearTablet();
		   }
	  });

	  savePngButton.addActionListener(new ActionListener(){
	     public void actionPerformed(ActionEvent e){

                   try {

		   sigObj.setTabletState(0);
                   sigObj.setImageJustifyMode(5);
                   sigObj.setImagePenWidth(6);
                   sigObj.setImageXSize(1000);
                   sigObj.setImageYSize(350);
                   BufferedImage sigImage = sigObj.sigImage();
                   int w = sigImage.getWidth(null);
                   int h = sigImage.getHeight(null);
                   int[] pixels = new int[(w * h) * 2];

                   sigImage.setRGB(0, 0, 0, 0, pixels, 0, 0);
                   File outputfile = new File("c://sig.png");
                   ImageIO.write(sigImage, "png", outputfile);

                   }
                catch (Throwable th) {
                        th.printStackTrace();
                }


              }
	  });


	 connectionTablet.addItemListener(new ItemListener(){
		  public void itemStateChanged(ItemEvent e){
			    
                        if(connectionTablet.getSelectedItem() != "SignatureGemLCD4X3"){
                           sigObj.setTabletModel(connectionTablet.getSelectedItem());
                        }
                        else{
                           sigObj.setTabletModel("SignatureGemLCD4X3New"); //properly set up LCD4X3
                        }
                     
		  }
	  });


	 connectionChoice.addItemListener(new ItemListener(){
		  public void itemStateChanged(ItemEvent e){
			    
                        if(connectionChoice.getSelectedItem() != "HSB"){
  	                   sigObj.setTabletComPort(connectionChoice.getSelectedItem());
                        }
                        else{
                           sigObj.setTabletComPort("HID1"); //properly set up HSB tablet
                        }
                            
		  }
	  });

			addWindowListener( new WindowAdapter()
				{
				public void windowClosing( WindowEvent we )
					{
					sigObj.setTabletState( 0 );
					System.exit( 0 );
					}

				public void windowClosed( WindowEvent we )
					{
					System.exit( 0 );
					}
				} );

			sigObj.addSigPlusListener( new SigPlusListener()
				{
				public void handleTabletTimerEvent( SigPlusEvent0 evt )
					{
					}

				public void handleNewTabletData( SigPlusEvent0 evt )
					{
					}

				public void handleKeyPadData( SigPlusEvent0 evt )
					{
					}
				} );


                        setVisible(true);
                        
                        sigObj.setTabletModel("SignatureGem1X5");
                        sigObj.setTabletComPort("COM1");
 


			}
		catch ( Exception e )
			{
			return;
			}
			
		}




                TextField txtPath = new TextField("C:\\test.sig", 30);
      
                Choice connectionChoice = new Choice();   protected String[] connections = 
	        {
		   "COM1", 
		   "COM2", 
		   "COM3", 
		   "COM4",
                   "USB", 
		   "HSB",  
	        };


                Choice connectionTablet = new Choice();   protected String[] tablets = 
	        {
                   "SignatureGem1X5",
                   "SignatureGem4X5",
      		   "SignatureGemLCD1X5",
       		   "SignatureGemLCD4X3",
      		   "ClipGem",
      		   "ClipGemLGL", 
	        };


                private void initConnection()
	        {
		   for(int i = 0; i < connections.length; i++)
		   {
			connectionChoice.add(connections[i]);
		   }

		   for(int i = 0; i < tablets.length; i++)
		   {
			connectionTablet.add(tablets[i]);
		   }

	        }

                //Convenience method for GridBagLayout
	        private void setConstraints(
		Component comp,
		GridBagLayout gbl,
	    	GridBagConstraints gc,
	    	int gridx,
	    	int gridy,
	    	int gridwidth,
	    	int gridheight,
	    	int weightx,
	    	int weighty,
	    	int anchor,
	    	int fill,
	    	int top,
	    	int left,
	    	int bottom,
	    	int right)
	    	{
			gc.gridx = gridx;
			gc.gridy = gridy;
			gc.gridwidth = gridwidth;
			gc.gridheight = gridheight;
			gc.weightx = weightx;
			gc.weighty = weighty;
			gc.anchor = anchor;
			gc.fill = fill;
			gc.insets = new Insets(top, left, bottom, right);
			gbl.setConstraints(comp, gc);
	    	}
            }

