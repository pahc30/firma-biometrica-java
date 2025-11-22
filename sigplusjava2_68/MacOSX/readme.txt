SigPlus Java Setup for HSB signature pads for Macs
-----------------------------------------------------

For initial testing, reference this page:
http://support.apple.com/kb/ht1528


First enable root user, then log in as root on your Mac. Put together a folder on the desktop that includes all the files in the MacOSXSigUsb.zip included with this readme.


Open a terminal, and type in the following:


su (Enter)
cd Desktop (Enter)
cd MacOSXSigUsb (Enter) --this is the folder to the files, whatever you name it
chmod 777 Comp (Enter)
chmod 777 Test (Enter)
./Comp (Enter) -this compiles the SigPlusSimpleDemoHID.java file
./Test (Enter) - this runs SigPlusSimpleDemoHID


This brings up the SigPlusSimpleDemoHID window; sign and make sure it works as expected. The SigPlusSimpleDemoHID demo is set up for a Topaz T-LBK460-HSB or T-LBK462-HSB signature pad so if you are using a different model Topaz signature pad, please change the setTabletModel() function to reflect your model:
sigObj.setTabletModel( "SignatureGemLCD1X5" ); //change for your model

The argument options are listed in the SigPlusJava2_66.pdf file included with the package. Refer to p. 18, "General Proiperties".


This demo is for 64-bit Macs. There is a 32-bit version of the libSigUsb file included if required:
libSigUsb_32.zip
