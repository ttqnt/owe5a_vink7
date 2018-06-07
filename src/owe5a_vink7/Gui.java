package owe5a_vink7;

/**
 *
 * @author karin
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class Gui extends JFrame implements ActionListener{
    private JTextField textf;
    private JButton browseButton, analyseButton;
    private JLabel label1, label2;
    private JPanel panel;
    private Graphics g;
    private JTextArea area;
    private JFileChooser fileChooser;

			
    //constructor:
    public Gui(){
	super("Protein Visualisation");
	setLayout(new FlowLayout());
		
	label2 = new JLabel("Bestand");
	add(label2);
		
	textf = new JTextField(35);
	add(textf);
		
	browseButton = new JButton("Browse");
	add(browseButton);
        browseButton.addActionListener(this);
		
	analyseButton = new JButton("Visualiseer");
	add(analyseButton);
        analyseButton.addActionListener(this);
		
	label1 = new JLabel("Informatie");
	add(label1);

	area = new JTextArea(10,50);
	area.setLineWrap(true);
	area.setWrapStyleWord(true);
	area.setEditable(false);
	add( area);
	JScrollPane scroll = new JScrollPane ( area );
	scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	add (scroll);
	    
        panel = new JPanel(){
            public void paintComponent(Graphics g) {
                g.setColor(Color.lightGray);
                g.fillRect(0, 0, 500, 150);
                g.setColor(Color.black);
                g.fillRect(25, 65, 25, 25);
                g.setColor(Color.black);
                g.fillRect(25, 65, 25, 25);
                g.setColor(Color.blue);
                g.fillRect(25, 90, 25, 25);
                g.setColor(Color.red);
                g.fillRect(25, 115, 25, 25);
		
                g.setColor(Color.black);
                g.drawString("Neutraal", 55, 82);
                g.drawString("Hydrofoob", 55, 107);
                g.drawString("Hydrofiel", 55, 132);
            }
        };
        panel.setPreferredSize(new Dimension(500, 150));
        add(panel);  
    }
	
    public void actionPerformed(ActionEvent event){
        Object buttonPressed = event.getSource();
	String input = area.getText();
	File selectedFile;
	int reply;
	BufferedReader infile;
		
        //Door de Analyse knop aan te klikken wordt een object van de SeqAnalysis klasse aangemaakt en de analyse van de input-sequentie gevisualiseerd in de Gui
        if (analyseButton == buttonPressed){ 
            if (input.length() <= 0){
		JOptionPane.showMessageDialog(null, "Bestand is leeg");
            } else try {
                SeqAnalysis analyse = new SeqAnalysis(input);
		Graphics g = panel.getGraphics();
		drawVisualisation(g, analyse);
            } catch (InvalidSeqException e) {
		JOptionPane.showMessageDialog(null, "Corrupte sequentie");
            }
            //Door de browseknop aan te klikken wordt filechooser geopent, de gekozen file geopent, gelezen en in de textArea geprint
        } else if (browseButton == buttonPressed){
            fileChooser = new JFileChooser();
            reply = fileChooser.showOpenDialog(this);
            String line;
            if(reply == JFileChooser.APPROVE_OPTION){
                selectedFile = fileChooser.getSelectedFile();
		textf.setText(selectedFile.getAbsolutePath());
            } 
            try {
                infile = new BufferedReader(new FileReader(textf.getText()));
		while ((line = infile.readLine()) != null){
                    area.setText(line);
		}
		infile.close();
            }catch (FileNotFoundException e) {
		JOptionPane.showMessageDialog(null, "Bestand niet gevonden");
            } catch (IOException e) {
		JOptionPane.showMessageDialog(null, "Kan bestand niet lezen");
            }catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Onbekende fout");
            }
        }
    }
	
    //tekent de visualisatie van de sequentie-analyse
    public void drawVisualisation(Graphics g, SeqAnalysis s){
        area.setText("Het totaal aantal aminozuren: "+ s.getTotal() + "\nAlle aminozuren zijn juist.");
	g.setColor(Color.black);
	g.fillRect(25, 20, 450, 36);
	ArrayList<Integer> hydrofoob = s.getPhilic();
        for(Integer i : hydrofoob ){
            g.setColor(Color.blue);
            g.fillRect(25+1+i*(450/s.getTotal()), 20, 450/s.getTotal(), 36);
        }
	ArrayList<Integer> hydrofiel = s.getPhobic();
        for(Integer i : hydrofiel ){
            g.setColor(Color.red);
            g.fillRect(25+1+i*(450/s.getTotal()), 20, 450/s.getTotal(), 36);
        }
    }
}

