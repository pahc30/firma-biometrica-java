import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import com.topaz.sigplus.*;

import gnu.io.*;
import java.io.*;

public class SigPlusSimpleDemoHID extends Frame
   {
   SigPlus              sigObj = null;

   public static void main( String Args[] )
	  {
	  SigPlusSimpleDemoHID demo = new SigPlusSimpleDemoHID();
	  }

	public SigPlusSimpleDemoHID()
		{

		try
			{
			ClassLoader cl = (com.topaz.sigplus.SigPlus.class).getClassLoader();
	  		sigObj = (SigPlus)Beans.instantiate( cl, "com.topaz.sigplus.SigPlus" );

			setLayout( new GridLayout( 1, 1 ) );
			add( sigObj );
			pack();
			setTitle( "DemoSigPlus" );

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


			setSize( 640, 256 );
			setVisible(true);

			sigObj.setTabletModel( "SignatureGemLCD1X5" );
			sigObj.setTabletComPort( "HID1" );
  	                //SEE TableModel_TabletComPort_options.txt FOR DETAILS ON SETTABLETMODEL() and SETTABLETCOMPORT() OPTIONS

			sigObj.setTabletState( 1 );
			}
		catch ( Exception e )
			{
			return;
			}
		}	

   }