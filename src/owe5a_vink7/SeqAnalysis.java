package owe5a_vink7;

/**
 *
 * @author karin
 */
import java.util.ArrayList;

public class SeqAnalysis extends Exception {
    static final char[] HYDROPHILIC = {'R', 'K', 'N', 'H', 'P'};
    static final char[] HYDROPHOBIC = {'L', 'I', 'F', 'W', 'V', 'M'};
    private String protSeq;

    SeqAnalysis(String input) throws InvalidSeqException{
	checkSequence(input);
    }
	
    //controleert of de invoer een geldige eiwitsequentie is en gooit anders een exception op
    private void checkSequence (String input) throws InvalidSeqException{
	String seq = input.toUpperCase();	
	if(seq.matches("[ACDEFGHIKLMNPQRSTVWY]+")){
            protSeq = seq;
        } else {
            throw new InvalidSeqException();
	}
    }
	
    //retourneert aantal eiwitten in de sequentie
    public int getTotal(){
	return protSeq.length();
    }
	
    //retourneert Arraylist met locaties van hydrofiele aminozuren in de sequentie
    public ArrayList getPhilic(){
	ArrayList <Integer> philic = new ArrayList<Integer>();
		
	for(int a = 0; a < protSeq.length(); a++){
            for(int i = 0; i < HYDROPHILIC.length; i++){
		if (HYDROPHILIC[i] == (protSeq.charAt(a) )) {
                    philic.add(a);
                }
            }
	}
	return philic;
    }
	
    //retourneert Arraylist met locaties van hydrofobe aminozuren in de sequentie
    public ArrayList getPhobic(){
	ArrayList <Integer> phobic = new ArrayList<Integer>();

	for(int a = 0; a < protSeq.length(); a++){
            for(int i = 0; i < HYDROPHOBIC.length; i++){
                if (HYDROPHOBIC[i] == (protSeq.charAt(a) )) {
                    phobic.add(a);
                }
            }
	}
	return phobic;
    }	
}
