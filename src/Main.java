
public class Main implements Runnable {

	Gui gui = new Gui();
	Gui2 infoWindow = new Gui2();

	public static void main(String[] args) {
		new Thread(new Main()).start();
	}

	@Override
	public void run() {
		while (true) {
			gui.frame.repaint();
			if (gui.resetter == false) {
				gui.checkVictroyStatus();		
			}
			if (gui.info == true) {
				infoWindow.frame.setVisible(true);
				infoWindow.open = true;
				gui.info = false;
			}else if (gui.info = false && infoWindow.open == false) {
				infoWindow.frame.setVisible(false);
			}
		}
	}

}
