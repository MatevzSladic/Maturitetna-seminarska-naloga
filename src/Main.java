public class Main implements Runnable {

	Gui gui = new Gui();
	Gui2 infoWindow = new Gui2();

	public static void main(String[] args) {
		new Thread(new Main()).start(); // nit nam omogoča da program izvaja več delov naenkrat
	}
	
	@Override
	public void run() {
		while (true) { // neskončna zanka
			gui.frame.repaint();
			if (gui.resetter == false) {
				// preverjanje stanja igre zmaga/poraz
				gui.checkVictroyStatus(); 	
			}
			if (gui.info == true) {
				// odpiranje okna z informacijami ob kliku na vprašaj
				infoWindow.frame.setVisible(true); 
				infoWindow.open = true;
				gui.info = false;
			}
		}
	}
}
