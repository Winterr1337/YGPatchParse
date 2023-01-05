package ygpatchparse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;


public class PatchParser {

	public static void main(String[] args) throws FileNotFoundException {
		Log.Info("Yogurting Patch Parser - Created by Winter.");
		File folder = new File("new");
		if (!folder.exists()) {
			new File("new").mkdirs();
			Log.Error("The \"new\" folder does not exist! Creating one for you...");
			Log.Warn("To generate a patchinfo.dat file, please put the .cab archives into the newly created \"new\" folder, and start this program again.");
			System.exit(0);
		}
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.toLowerCase().endsWith(".cab");
            }
        };

        String[] pathnames = folder.list(filter);

        Arrays.sort(pathnames);

        if (pathnames.length <= 0) {
        	Log.Error("No files have been detected inside \"new\" folder.");
        	System.exit(0);
        }
        Log.Info("Detected " + pathnames.length + " cab files.");
        Log.Info("Do you want to continue? (y/n)");
        
        Scanner in = new Scanner(System.in);
        
        char choice = in.next().toLowerCase().charAt(0);
        
        switch (choice) {
        case 'y':
        	try {
        		in.close();
				parseFile(pathnames);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	break;
        case 'n':
        	Log.Info("Aborting...");
        	System.exit(0);
        }
	}
	
	
	
	
	
	protected static void parseFile(String[] pathnames) throws IOException {
		OutputStream os = null;
        File result = new File("patchinfo.dat");

		if (result.createNewFile()) {
		os = new FileOutputStream(result, true);
    	for (String file : pathnames) {
    		File i = new File("new/" + file);
    		Log.Info("processing " + file + "...");
int name = Integer.parseInt(getBaseName(i.getName()));
os.write(int2bytes(name));
os.write(int2bytes((int) i.length()));
os.write(int2bytes(0));
    	}
    	if (result.length() <= 0) {
    		result.delete();
    		Log.Warn("");
    	}
		os.close();
		Log.Info("Parsing complete!");
		Log.Info("patchinfo.dat saved to " + result.getAbsolutePath());
		
		System.exit(0);
	} else {
		Log.Warn("patchinfo.dat already exists.");
		System.exit(0);
	}
	}
		
	
	
	
	public static String getBaseName(String fileName) {
	    int index = fileName.lastIndexOf('.');
	    if (index == -1) {
	        return fileName;
	    } else {
	        return fileName.substring(0, index);
	    }
	}
	
	
    static byte[] int2bytes(int i) {
        byte[] result = new byte[4];

        result[3] = (byte) (i >> 24);
        result[2] = (byte) (i >> 16);
        result[1] = (byte) (i >> 8);
        result[0] = (byte) (i);

        return result;
      }
    
    static byte[] long2bytes(long i) {
        byte[] result = new byte[8];

        
        result[7] = (byte) (i >> 56);
        result[6] = (byte) (i >> 48);
        result[5] = (byte) (i >> 40);
        result[4] = (byte) (i >> 32);
        result[3] = (byte) (i >> 24);
        result[2] = (byte) (i >> 16);
        result[1] = (byte) (i >> 8);
        result[0] = (byte) (i);

        return result;
      }
    
	
}
