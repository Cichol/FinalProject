import javax.swing.JFrame;


public class MainProgram 
{
	public static void main(String[] args)
	{
		
		JFrame frame = new JFrame("Pride of TsuiNation");
		frame.setSize(400,400);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Trollformer(frame));
		frame.setVisible(true);
	}
}
