package jeu;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import nebula.minigame.cropcircle.Cropcircle;
import nebula.minigame.cropcircle.MyLine;


public class ScoreManager{

	private String dataPath;
	private JTable scoreTable;
	private DefaultTableModel model;
	private static final long serialVersionUID = -3162930812692118827L;
	
	ScoreManager(){
		
		try {
			loadScore();
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Unable to load scores");
			String[] stab={"Nom","Score Général","Score SpaceInvader","Score Truc2", "Score Truc2"};
			String[] stab2={"1","1","1","1", "2"};

			String[][] re={stab2};
			model=new DefaultTableModel(re,stab);
			scoreTable=new JTable(model);
			String[] stab3={"2","2"};
			model.addRow(stab3);
			String[] stab4={"Score SpaceInvader","42"};
			addEntry("Gwenn", stab4);
		}
		
	}

	public void displayScore(JFrame parent){
		JPanel j=new JPanel(new BorderLayout());
		j.add(scoreTable.getTableHeader(),BorderLayout.PAGE_START);
		j.add(scoreTable, BorderLayout.CENTER);
		JOptionPane.showConfirmDialog(parent, j, "Score", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
	}
		
	
	public void addMultipleEntry(String name, ArrayList<String[]> values){
		
	}
	

	public void addEntry(String name, String[] values){
		
		int j=0;
		while( j<model.getRowCount()){
			if(model.getValueAt(j, 0).equals(name)){
				break;
			}
			j++;
		}
		
		if(j==model.getRowCount()){
			String[] s={""};
			model.addRow(s);
			model.setValueAt(name, j, 0);
		}
		
		for(int i=0; i<model.getColumnCount();i++){
			if(values[0].equals(model.getColumnName(i))){
				model.setValueAt(values[1], j, i);
			}
		}

	}
	
	private void save() throws ClassNotFoundException {
		String filename = dataPath;
		FileOutputStream fis = null;
		ObjectOutputStream oit = null;

		try {
			fis = new FileOutputStream(filename);
			oit = new ObjectOutputStream(fis);
			oit.writeObject(scoreTable);
			oit.flush();
			oit.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	private void loadScore() throws Exception{
		String filename = dataPath;
		FileInputStream fos = null;
		ObjectInputStream out = null;

		fos = new FileInputStream(filename);
		out = new ObjectInputStream(fos);
		scoreTable=(JTable)out.readObject();
		out.close();

	}
	
	
	
	
	public static void main(String[] args) throws SlickException {
		JFrame j=new JFrame("Score Test");
		j.setSize(800, 600);
		j.setVisible(true);
		new ScoreManager().displayScore(j);
		
	}
}
