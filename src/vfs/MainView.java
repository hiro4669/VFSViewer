package vfs;

import java.awt.BorderLayout;
import java.io.FileFilter;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import unixv7.V7Driver;
import vfs.tree.VNode;
import vfs.tree.VNodeCreator;

public class MainView {
	
	private JFrame mainFrame;
	private VNode rootNode;
	private TreeView treeView;
	private TableView tableView;
	private DetailView detailView;
	private FileChooseView fileView;
	
	private V7Driver driver;
	private String diskPath;
	
	public static void main(String ...args) {
		MainView main = null;
		switch(args.length) {
		case 1: {
			main = new MainView(args[0]);
		}
		default: {
			main = new MainView("rp06.disk");
			break;
		}
		}
		
		
		main.init();
		main.process();
	}
	
	public MainView(String diskPath) {
		this.diskPath = diskPath;
	}
	
	private void initTreeView() {
		//rootNode = VNodeCreator.getInstance().createFromImage(driver);		
		treeView = new TreeView(this);
		//treeView.load(rootNode);
	}
	private void initTableView() {
		tableView = new TableView(this);
	}
	private void initDetailView() {
		detailView = new DetailView(this);
	}
	
	private void initDriver() {
		driver = new V7Driver(diskPath);
	}
	
	private void initFileView() {
		fileView = new FileChooseView(this);
	}
	
	public void init() {
		initFileView();
		//initDriver();
		initTableView();
		initDetailView();
		initTreeView();
	}
	
	public void process() {
		mainFrame = new JFrame("VFSViewer");
		mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout());
		mainFrame.getContentPane().add(fileView, BorderLayout.NORTH);		
		mainFrame.getContentPane().add(treeView, BorderLayout.WEST);
		mainFrame.getContentPane().add(tableView, BorderLayout.CENTER);
		mainFrame.getContentPane().add(detailView, BorderLayout.EAST);
		mainFrame.pack();
		mainFrame.setSize(1200, 800);		
		
		mainFrame.setVisible(true);
	}
	
	public void loadImage(String path) {
		this.diskPath = path;
		initDriver();
		rootNode = VNodeCreator.getInstance().createFromImage(driver);
		mainFrame.getContentPane().remove(treeView);
		treeView = new TreeView(this);		
		treeView.load(rootNode);
		mainFrame.getContentPane().add(treeView, BorderLayout.WEST);
		mainFrame.pack();
	}
	
	public void updateTable(List<VNode> newnodes) {
		tableView.updateModel(newnodes, driver);
	}
	
	public void updateDetail(VNode tnode) {
		detailView.inodeChanged(tnode, driver);
		detailView.dataChanged(tnode, driver);
		//String inodeInfo = driver.getInodeInfo(tnode.getAbsolutePath());
		//System.out.println(inodeInfo);
	}
	

}
