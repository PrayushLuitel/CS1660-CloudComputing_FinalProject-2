import java.awt.Color;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import javax.swing.JPanel;

public class Final_project_GUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	String projectID = ""; //please add your project Id
	String bucketName = ""; //please add the name of the bucket where you store all of your outputs
	String objectName = "";
	static String Json_Name = "";//please add the name of your Json file here
	HashMap<String,String> invertedIndex = new HashMap<String,String>(); 
	HashMap<Integer,String> top_N = new HashMap<Integer,String>(); 
	boolean shakespeare = false;
	boolean tolstoy = false;
	boolean hugo = false;
	boolean allFiles = false;
	static GoogleCredentials credentials;
	final JButton btnNewButton = new JButton("Construct Inverted Indicies");
	final JButton btnChooseFiles = new JButton("Choose Files");
	final JButton btnLoadEngine= new JButton("Load Engine"); 
	final JFileChooser fc = new JFileChooser();
	File[] files;
	final JLabel listfile = new JLabel();
	final JLabel loaded = new JLabel("Engine was loaded");
	final JLabel also = new JLabel("&");
	final JLabel invIndex = new JLabel("Inverted indicies were constructed successfully!");
	final JLabel selection = new JLabel("Please Select Action");
	final JButton searchTerm =  new JButton("Search for Term");
	final JButton topN = new JButton ("Top-N");
	final JLabel lblNewLabel_1 = new JLabel("Load Search Engine");
	final JLabel ST = new JLabel("Enter your Search Term");
	final JLabel NV = new JLabel("Enter your N Value");
	final JButton search1 = new JButton("Search");
	final JButton search2 = new JButton("Search");
	final JTextField input1 = new JTextField();
	final JTextField input2 = new JTextField();
	final JLabel freq = new JLabel();
	final JLabel searched = new JLabel();
	final JLabel searchTime = new JLabel();
	JPanel panel = new JPanel();
	JTable N_out = new JTable();
	JTable Search_out = new JTable();
	final JButton back1 = new JButton();
	final JButton back2 = new JButton();
	
	public Final_project_GUI() throws HeadlessException {
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 20));
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MyName Search Engine");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 8));
		lblNewLabel.setBounds(0, 0, 89, 20);
		getContentPane().add(lblNewLabel);
		
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(227, 59, 240, 76);
		getContentPane().add(lblNewLabel_1);
		
		btnChooseFiles.setBounds(277, 151, 137, 29);
		btnChooseFiles.addActionListener(this);
		getContentPane().add(btnChooseFiles);
		
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton.setBounds(227, 244, 240, 55);
		btnNewButton.addActionListener(this);
		getContentPane().add(btnNewButton);
		 
        btnLoadEngine.setFont(new Font("Arial", Font.PLAIN, 16));
		btnLoadEngine.setBounds(227, 244, 240, 55);
		btnLoadEngine.addActionListener(this);
		getContentPane().add(btnLoadEngine);
		btnLoadEngine.setVisible(false);

		fc.setMultiSelectionEnabled(true);
		fc.setVisible(false);
		
		listfile.setFont(new Font("Arial", Font.PLAIN, 13));
		listfile.setBounds(227,197,189,42);
		getContentPane().add(listfile);
		listfile.setVisible(false);
		
		loaded.setFont(new Font("Arial", Font.BOLD, 26));
		loaded.setBounds(350, 50, 750, 100);
		getContentPane().add(loaded);
		loaded.setVisible(false);
		
		also.setFont(new Font("Arial", Font.BOLD, 26));
		also.setBounds(450, 75, 750, 100);
		getContentPane().add(also);
		also.setVisible(false);
		
		invIndex.setFont(new Font("Arial", Font.BOLD, 26));
		invIndex.setBounds(177, 100, 750, 100);
		getContentPane().add(invIndex);
		invIndex.setVisible(false);
		
		selection.setFont(new Font("Arial", Font.BOLD, 26));
		selection.setBounds(350,150, 750, 100);
		getContentPane().add(selection);
		selection.setVisible(false);
		
		searchTerm.setFont(new Font("Arial", Font.PLAIN, 16));
		searchTerm.setBounds(375, 250, 200, 50);
		searchTerm.addActionListener(this);
		getContentPane().add(searchTerm);
		searchTerm.setVisible(false);
		
		topN.setFont(new Font("Arial", Font.PLAIN, 16));
		topN.setBounds(375, 350, 200, 50);
		topN.addActionListener(this);
		getContentPane().add(topN);
		topN.setVisible(false);
		
		ST.setFont(new Font("Arial", Font.BOLD, 26));
		ST.setBounds(350, 50, 750, 100);
		getContentPane().add(ST);
		ST.setVisible(false);
		
		NV.setFont(new Font("Arial", Font.BOLD, 26));
		NV.setBounds(350, 50, 750, 100);
		getContentPane().add(NV);
		NV.setVisible(false);
		
		search1.setFont(new Font("Arial", Font.PLAIN, 16));
		search1.setBounds(375, 350, 200, 50);
		search1.addActionListener(this);
		getContentPane().add(search1);
		search1.setVisible(false);
		
		search2.setFont(new Font("Arial", Font.PLAIN, 16));
		search2.setBounds(375, 350, 200, 50);
		search2.addActionListener(this);
		getContentPane().add(search2);
		search2.setVisible(false);
		
		input1.setBounds(375, 250, 200, 50);
		input1.addActionListener(this);
		getContentPane().add(input1);
		input1.setVisible(false);
		
		input2.setBounds(375, 250, 200, 50);
		input2.addActionListener(this);
		getContentPane().add(input2);
		input2.setVisible(false);
		
		panel.setSize(736, 500);
		panel.setLocation(40, 109);
		panel.setBackground(Color.WHITE);
		
		getContentPane().add(panel);
		panel.setVisible(false);
		
		searched.setFont(new Font("Tahoma", Font.PLAIN, 12));
		searched.setBounds(50, 50, 240, 20);
		getContentPane().add(searched);
		
		searchTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
		searchTime.setBounds(50, 75, 240, 20);
		getContentPane().add(searchTime);
		
		freq.setFont(new Font("Tahoma", Font.PLAIN, 12));
		freq.setBounds(50, 80, 240, 20);
		freq.setText("Top-N Frequent Terms");
		getContentPane().add(freq);
		freq.setVisible(false);
		
		back1.setText("Return to Search");
		back1.setBounds(650,40, 240, 20);
		back1.addActionListener(this);
		getContentPane().add(back1);
		back1.setVisible(false);
		
		back2.setText("Return to Search");
		back2.setBounds(650,40, 240, 20);
		back2.addActionListener(this);
		getContentPane().add(back2);
		back2.setVisible(false);
	}
	public void actionPerformed(ActionEvent ae) {
        if ((JButton)ae.getSource() == btnNewButton) {
        }
        else if ((JButton)ae.getSource() == btnChooseFiles) {
            fc.setVisible(true);
        	fc.showOpenDialog(this);
            files = fc.getSelectedFiles();
            
            for (int i=0; i<files.length;i++) {
            	if(i==files.length-1) {
            		listfile.setText(listfile.getText() + files[i].getName() + " ");
            	}
            	else {
            		listfile.setText(listfile.getText() + files[i].getName() + ", ");
            	}
            	listfile.setVisible(true);
            }
            btnNewButton.setVisible(false);
            btnLoadEngine.setVisible(true);
        }
        else if ((JButton)ae.getSource() == btnLoadEngine) {
        	for(int i=0; i<files.length; i++) {
        		if(files[i].getName().contains("shakespeare")) {
        			shakespeare = true;
        		}
        		if(files[i].getName().contains("Hugo")) {
        			hugo = true;
        		}
        		if(files[i].getName().contains("Tolstoy")) {
        			tolstoy = true;
        		}
        	}
        	for(int j=0;j<7;j++) {
        	if (shakespeare && hugo && tolstoy) {//all 3 files were selected : please replace the object name with the ooutput folder from inverted index map reduce for all files
            		objectName = "all_files_invertedIndex_opt/part-r-0000" + j;
            }
        	else if (shakespeare && !hugo && !tolstoy) {// only shakespeare was selected: please replace the object name with the ooutput folder from inverted index map reduce for shakespeare
        		objectName = "shakespeare_inverted_index_opt/part-r-0000" + j;
        	}
        	else if (!shakespeare && hugo && !tolstoy) {// only hugo was selected: please replace the object name with the ooutput folder from inverted index map reduce for Hugo
        		objectName = "hugo_invertedindex_opt/part-r-0000" + j;
        	}
        	else if (!shakespeare && !hugo && tolstoy) {// only tolstoy was selected: please replace the object name with the ooutput folder from inverted index map reduce for Tolstoy
        		objectName = "tolstoy_invertedindex_opt/part-r-0000" + j;
        	}
        	else if(shakespeare && hugo && !tolstoy) {
        		objectName = "Hugo_Shakespeare_inverted_index_opt/part-r-0000"+j;//hugo+shakespeare was selected: please replace the object name with the ooutput folder from inverted index map reduce for Hugo and Shakespeare
        	}
        	else if(shakespeare && !hugo && tolstoy) {
        		objectName = "Shakespeare_Tolstoy_inverted_index_opt/part-r-0000"+j;//shakespeare+tolstoy was selected: please replace the object name with the ooutput folder from inverted index map reduce for Shakespeare and Tolstoy
        	}
        	else if(!shakespeare && hugo && tolstoy) {// hugo+tolstoy was selected: please replace the object name with the ooutput folder from inverted index map reduce for Hugo and Tolstoy
        		objectName = "Hugo_Tolstoy_inverted_index_opt/part-r-0000"+j;
        	}
        	Storage storage = StorageOptions.newBuilder().setProjectId(projectID).setCredentials(credentials).build().getService();
        	Blob blob = storage.get(bucketName,objectName);
        	String fileContent = new String(blob.getContent());
        	String[] lines = fileContent.split("\n");
        	for (String line:lines) {
        	String[] tokens = line.split("\t");
        	invertedIndex.put(tokens[0], tokens[1]);
        	}
        	}
        	listfile.setVisible(false);
        	btnChooseFiles.setVisible(false);
        	btnLoadEngine.setVisible(false);
        	lblNewLabel_1.setVisible(false);
        	loaded.setVisible(true);
        	also.setVisible(true);
        	invIndex.setVisible(true);
        	selection.setVisible(true);
        	searchTerm.setVisible(true);
        	topN.setVisible(true);
        }
        else if ((JButton)ae.getSource() == searchTerm) {
        	loaded.setVisible(false);
        	also.setVisible(false);
        	invIndex.setVisible(false);
        	selection.setVisible(false);
        	searchTerm.setVisible(false);
        	topN.setVisible(false);
        	ST.setVisible(true);
        	search1.setVisible(true);
        	input1.setVisible(true);
        }
        else if ((JButton)ae.getSource() == topN) {
        	loaded.setVisible(false);
        	also.setVisible(false);
        	invIndex.setVisible(false);
        	selection.setVisible(false);
        	searchTerm.setVisible(false);
        	topN.setVisible(false);
        	NV.setVisible(true);
        	search2.setVisible(true);
        	input2.setVisible(true);
        }
        else if ((JButton)ae.getSource() == search1) {
        	String searchVal = input1.getText();
        	
        	DefaultTableModel model = new DefaultTableModel(new Object[]{"Doc Folder", "Doc Name", "Frequencies"},0);
    		model.addRow(new Object[]{"Doc Folder", "Doc Name", "Frequencies"});
    		
    		if (invertedIndex.containsKey(searchVal.toLowerCase())) {
    			searched.setText("You searched for the term: " + searchVal);
    			panel.removeAll();
        		panel.add(Search_out);
        		ST.setVisible(false);
        		search1.setVisible(false);
        		input1.setVisible(false);
    			String temp = invertedIndex.get(searchVal.toLowerCase());
    			String[] allRecords = temp.toString().split(",");
    			long InitialTime = System.currentTimeMillis();
    			for (int i=0; i+2<=allRecords.length;i+=2) {
    				String[] values = allRecords[i].split(":");
    				model.addRow(new Object[] {values[0],values[1],values[2]});
    			}
    			long FinalTime = System.currentTimeMillis();
    			long totalTime = FinalTime-InitialTime;
    			searchTime.setText("Your search was executed in " + totalTime + " ms");
    			Search_out.setModel(model);
        		Search_out.getColumnModel().getColumn(0).setPreferredWidth(245);
        		Search_out.getColumnModel().getColumn(1).setPreferredWidth(245);
        		Search_out.getColumnModel().getColumn(2).setPreferredWidth(245);
        		panel.setVisible(true);
        		Search_out.setVisible(true);	
        		searched.setVisible(true);
        		searchTime.setVisible(true);
        		back1.setVisible(true);
    		}
    		

        }
        else if ((JButton)ae.getSource() == search2) {
        	String N_val = input2.getText();
        	int n_val = Integer.parseInt(N_val);
        	for(int j=0;j<7;j++) {// for each if statement please replace the object name if you renamed the output path as done before
            	if (shakespeare && hugo && tolstoy) {
                	objectName = "all_files_topN/part-r-0000" + j;
                }
            	else if (shakespeare && !hugo && !tolstoy) {
            		objectName = "shakespeare_top_N_opt/part-r-0000" + j;
            	}
            	else if (!shakespeare && hugo && !tolstoy) {
            		objectName = "hugo_top_N_opt/part-r-0000" + j;
            	}
            	else if (!shakespeare && !hugo && tolstoy) {
            		objectName = "tolstoy_top_N_opt/part-r-0000" + j;
            	}
            	else if(shakespeare && hugo && !tolstoy) {
            		objectName = "Hugo_Shakespeare_top_n_opt/part-r-0000"+j;
            	}
            	else if(shakespeare && !hugo && tolstoy) {
            		objectName = "Shakespeare_Tolstoy_top_n_opt/part-r-0000"+j;
            	}
            	else if(!shakespeare && hugo && tolstoy) {
            		objectName = "Hugo_Tolstoy_top_n_opt/part-r-0000"+j;
            	}
            	Storage storage = StorageOptions.newBuilder().setProjectId(projectID).setCredentials(credentials).build().getService();
            	Blob blob = storage.get(bucketName,objectName);
            	String fileContent = new String(blob.getContent());
            	String[] lines = fileContent.split("\n");
            	for (String line:lines) {
            	String[] tokens = line.split("\t");
				int total_freq = Integer.parseInt(tokens[0]);
            	top_N.put(total_freq,tokens[1]);
            	}
            	}
        		List<Integer> keys = top_N.entrySet().stream()
        				  .map(Map.Entry::getKey)
        				  .sorted(Comparator.reverseOrder())
        				  .limit(n_val)
        				  .collect(Collectors.toList());
        		NV.setVisible(false);
            	search2.setVisible(false);
            	input2.setVisible(false);
            	panel.removeAll();
            	panel.add(N_out);
        		
        		DefaultTableModel model = new DefaultTableModel(new Object[]{"Term", "TotalFrequency"},0);
        		model.addRow(new Object[]{"Term", "TotalFrequency"});
        		for (Integer key: keys) {
        			model.addRow(new Object[]{top_N.get(key),key});
        		}
        		N_out.setModel(model);
        		N_out.getColumnModel().getColumn(0).setPreferredWidth(368);
        		N_out.getColumnModel().getColumn(1).setPreferredWidth(368);
        		panel.setVisible(true);
        		N_out.setVisible(true);	
        		freq.setVisible(true);
        		back2.setVisible(true);
        }
        else if ((JButton)ae.getSource() == back1) {
        	panel.setVisible(false);
        	Search_out.setVisible(false);
        	searched.setVisible(false);
        	searchTime.setVisible(false);
        	back1.setVisible(false);
        	loaded.setVisible(true);
        	also.setVisible(true);
        	invIndex.setVisible(true);
        	selection.setVisible(true);
        	searchTerm.setVisible(true);
        	topN.setVisible(true);
        }
        
        else if ((JButton)ae.getSource() == back2) {
        	panel.setVisible(false);
        	N_out.setVisible(false);
        	freq.setVisible(false);
        	back2.setVisible(false);
        	loaded.setVisible(true);
        	also.setVisible(true);
        	invIndex.setVisible(true);
        	selection.setVisible(true);
        	searchTerm.setVisible(true);
        	topN.setVisible(true);
        	
        }
        
    }
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		credentials = GoogleCredentials.fromStream(new FileInputStream(s + "//" + Json_Name))
		        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		Final_project_GUI gui = new Final_project_GUI();
		gui.setVisible(true);
	}
}
