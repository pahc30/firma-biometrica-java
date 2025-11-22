import java.applet.Applet;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Beans;
import gnu.io.*;
import com.topaz.sigplus.SigPlus;
import com.topaz.sigplus.SigPlusEvent0;
import com.topaz.sigplus.SigPlusListener;


public class SigPlusAppletDemo extends Applet {

	public void init() {
		// TODO Auto-generated method stub
		super.init();
	}



	public void start() {
		// TODO Auto-generated method stub
		super.start();
		
	}


	/**
	 * 
	 */
	SigPlus              sigObj = null;
	
	
	
	public SigPlusAppletDemo()
	{


	try
		{
		ClassLoader cl = (com.topaz.sigplus.SigPlus.class).getClassLoader();
  		sigObj = (SigPlus)Beans.instantiate( cl, "com.topaz.sigplus.SigPlus" );

		setLayout( new GridLayout( 1, 1 ) );
		add( sigObj );
	

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


		setSize( 500, 100 );
		show();

		sigObj.setTabletModel( "SignatureGemLCD1X5" ); //SPECIFY YOUR TABLETMODEL HERE    
		sigObj.setTabletComPort( "HID1" ); //SPECIFY YOU CONNECTION TYPE HERE
                //SEE TableModel_TabletComPort_options.txt FOR DETAILS ON SETTABLETMODEL() and SETTABLETCOMPORT() OPTIONS  

		sigObj.setTabletState( 1 );
		}
	catch ( Exception e )
		{
		return;
		}
	}	

        public void destroy()
        {
           if (sigObj != null)
           {
              sigObj.setTabletState(0);
              remove(sigObj);
           }
           sigObj = null;
           System.gc();
        }


}
