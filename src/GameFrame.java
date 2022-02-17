import javax.swing.JFrame;

public class GameFrame extends JFrame {
	GameFrame(){
		this.add(new GamePanel());
		this.setTitle("snake game");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

}
