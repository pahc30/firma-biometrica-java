Please refer to the TopazLicense.txt file for licensing and limited warranty information prior to using the SigPlus Java API. Use of the SigPlus Java API requires your agreement with these terms. 

For all apps, the SigPlus2_xx.jar and the RXTX.jar (varies from one operating system to another) must be added to the classpath. For Windows, use RXTXcomm.jar in the PATH. Additionally, the rxtxParallel.dll and rxtxSerial.dll should be copied into the /bin folder under Java if you are going to use 9-pin serial pads. Topaz has included the RXTX dependencies for various operating systems in the RXTX folder (rxtx-2.1-7-bins-r2.zip) in this download. For the latest version, however, please check the RXTX site:
http://users.frii.com/jarvi/rxtx/

When using Topaz HSB (HID USB) pads under Windows, copy the appropriate SigUsb.dll (32-bit for 32-bit JVM, or 64-bit for 64-bit JVM) to a location in the PATH on the target computer. See the SIGUSBDLL folder included. Additionally, the SigUsb.dll requires that the VC++ 2008 and 2010 runtimes be installed to the target computer. Bear in mind that the paths to these installers will certainly change over time. Ultimately, you can visit www.microsoft.com to locate the current 32-bit and 64-bit 2008 and 2010 VC++ redistributables available to you.

VC++ 32-bit versions (for 32-bit JVM):
2008 VC++ 32-bit redistributable:
http://www.microsoft.com/downloads/en/details.aspx?familyid=9B2DA534-3E03-4391-8A4D-074B9F2BC1BF&displaylang=en

2010 VC++ 64-bit redistributable:
http://www.microsoft.com/downloads/en/details.aspx?FamilyID=a7b7a05e-6de6-4d3a-a423-37bf0912db84&displaylang=en


VC++ 64-bit versions (for 64-bit JVM):
2008:
http://www.microsoft.com/downloads/en/details.aspx?familyid=BD2A6171-E2D6-4230-B809-9A8D7548C1B6&displaylang=en

2010:
http://www.microsoft.com/downloads/en/details.aspx?familyid=BD512D9E-43C8-4655-81BF-9350143D5867&displaylang=en


For using the HSB (HID USB) pads under Linux, please refer to the SigPlusLinuxHSB.zip file, which contains a demo and dependencies.