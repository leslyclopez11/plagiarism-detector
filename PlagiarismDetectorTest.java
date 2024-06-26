

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class PlagiarismDetectorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
    	String directory = "C:\\Users\\Lesly\\CIS730\\homework\\homework_5\\plagiarism-detector\\docs";

    	Map<String, Integer> map = null;

    	// run the method
        try {
        	long start = System.currentTimeMillis();
    	    map = PlagiarismDetector.detectPlagiarism(directory, 4, 5);
    	    long end = System.currentTimeMillis();
    	    System.out.println("Took " + (end-start) + "ms to run");
        }
        catch (IllegalArgumentException e) {
        	e.printStackTrace();
        	fail("ERROR! Could not read corpus of documents: be sure that /" + directory + " is a subdirectory of where you started Java, or is a subdirectory of the root of your IDE's project" );
        }
        catch (Exception e) { // oops, got an exception
        	e.printStackTrace();
            fail("INCORRECT OUTPUT: detectPlagiarism throws " + e.toString() + " when windowSize = 4 and threshold = 5");
        }
        
        // make sure the method didn't return null
        if (map == null) {
            fail("INCORRECT OUTPUT: Map returned by detectPlagiarism is null when windowSize = 4 and threshold = 5");
        }
        
    	Set<Map.Entry<String, Integer>> entries = map.entrySet();
    	System.out.println("Here are the values in the Map:");
    	for (Map.Entry<String, Integer> entry : entries) {
    		System.out.println(entry.getKey() + ": " + entry.getValue());
    	}

    	// check that output has right number of elements
        if (entries.size() != 10) {
            fail("INCORRECT OUTPUT: Map returned by detectPlagiarism has incorrect number of entries; should return Map with 10 elements when windowSize = 4 and threshold = 5");
        }
        
        // check that the collection has the right elements in the right order
        int count = 0;
    	for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            if (key == null) {
                fail("INCORRECT OUTPUT: Key in Map returned by detectPlagiarism is null when windowSize = 4 and threshold = 5");
            }

            int value = entry.getValue();

    		if (count == 0) { // bwa242.txt-sra42.txt: 10
                if (key.contains("bwa242.txt") == false || key.contains("sra42.txt") == false || value != 10) {
                    fail("INCORRECT OUTPUT: incorrect key/value pair in Map; first match should be bwa242.txt-sra42.txt with value 10 when windowSize = 4 and theshold = 5");
                }
            }
            else if (count == 1 || count == 2) { // bwa242.txt-bwa249.txt: 8, bwa0.txt-bwa242.txt: 8
            	if (value != 8) {
                	fail("INCORRECT OUTPUT: incorrect value in Map; second and third matches should have value 8 when windowSize = 4 and theshold = 5");
            	}
            	if ((key.contains("bwa242.txt") && key.contains("bwa249.txt")) || (key.contains("bwa242.txt") && key.contains("bwa0.txt"))) {
            		// okay
            	}
            	else {
                	fail("INCORRECT OUTPUT: incorrect key/value pair in Map; second and third matches should be bwa242.txt-bwa249.txt and bwa0.txt-bwa242.txt (in some order) with value 8 when windowSize = 4 and theshold = 5");
                }   
            }
            else if (count == 3 || count == 4 || count == 5) { // edo20.txt-edo26.txt: 7 ; bwa132.txt-bwa137.txt: 7 ; bwa132.txt-bwa133.txt: 7
            	if (value != 7) {
                	fail("INCORRECT OUTPUT: incorrect value in Map; fourth, fifth, and sixth matches should have value 7 when windowSize = 4 and theshold = 5");
            	}
            	if (key.contains("edo20.txt") && key.contains("edo26.txt")) { }
            	else if (key.contains("bwa132.txt") && key.contains("bwa137.txt")) { }
            	else if (key.contains("bwa132.txt") && key.contains("bwa133.txt")) { } 
            	else {
                	fail("INCORRECT OUTPUT: incorrect key/value pair in Map; key [" + key + "] with value 7 is incorrect when windowSize = 4 and theshold = 5");
            	}
            }
            else if (count >= 6) { 
            	/*
					bwa233.txt-bwa242.txt: 6
					bwa242.txt-ecu201.txt: 6
					sra126.txt-sra42.txt: 6
					bwa0.txt-bwa132.txt: 6
            	 */
            	if (value != 6) {
                	fail("INCORRECT OUTPUT: incorrect value in Map; seventh through tenth matches should have value 6 when windowSize = 4 and theshold = 5");
            	}
            	if (key.contains("bwa233.txt") && key.contains("bwa242.txt")) { } 
            	else if (key.contains("bwa242.txt") && key.contains("ecu201.txt")) { } 
            	else if (key.contains("sra126.txt") && key.contains("sra42.txt")) { } 
            	else if (key.contains("bwa0.txt") && key.contains("bwa132.txt")) { } 
            	else {
            		fail("INCORRECT OUTPUT: incorrect key/value pair in Map; key [" + key + "] with value 6 is incorrect when windowSize = 4 and theshold = 5");
                }   
  
            }
            count++;
        }
        //System.out.println("Output is correct!");
    }

}
