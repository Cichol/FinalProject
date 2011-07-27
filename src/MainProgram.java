import javax.swing.JFrame;


public class MainProgram 
{
	public static void main(String[] args)
	{
		
		JFrame frame = new JFrame("Pride of TsuiNation");
		frame.setSize(800,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Trollformer());
	}
}
