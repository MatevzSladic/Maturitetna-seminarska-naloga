import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui2 implements ActionListener {
	JFrame frame = new JFrame();
	JButton myButton = new JButton("NAZAJ V IGRO");
	JLabel label1 = new JLabel("");
	JLabel label2 = new JLabel("");
	JLabel informacije;
	JLabel informacije_1;
	JLabel informacije_2;

	public Gui2() {

		myButton.setBounds(100, 340, 200, 40);
		myButton.setFocusable(false);
		myButton.addActionListener(this);

		frame.setTitle("Navodila");
		frame.setSize(420, 420);
		frame.getContentPane().setLayout(null);
		frame.setVisible(false);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(myButton);

		label1.setIcon(new ImageIcon(
				"C:\\Users\\matev\\OneDrive - Šolski center Novo mesto\\Desktop\\Maturitetna\\leftClick.png"));
		label1.setBounds(0, 200, 64, 64);
		frame.getContentPane().add(label1);

		label2.setIcon(new ImageIcon(
				"C:\\Users\\matev\\OneDrive - Šolski center Novo mesto\\Desktop\\Maturitetna\\rightClick.png"));
		label2.setBounds(0, 270, 64, 64);
		frame.getContentPane().add(label2);

		informacije = new JLabel("<html>Z levim klikom na polje v mreži, ki se osvetli, "
				+ "se bo to odprlo in pokazala se bo vsebina skrita pod njim.</html>");
		informacije.setFont(new Font("Tahoma", Font.PLAIN, 12));
		informacije.setBounds(74, 200, 322, 46);
		frame.getContentPane().add(informacije);

		informacije_1 = new JLabel("<html>Z desnim klikom na polje v mreži, ki se osvetli, "
				+ "nanj postavimo zastavico. Tako poje je označeno in ga lahko odprete le, če s ponovnim "
				+ "desnim klikom prej odmaknete zastavico.</html>");
		informacije_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		informacije_1.setBounds(74, 270, 322, 64);
		frame.getContentPane().add(informacije_1);

		informacije_2 = new JLabel(
				"<html>Cilj igre je, v čim krajšem času odpreti vsa polja v mreži, ki ne vsebujejo bombe. "
						+ "Polja ki vsebujejo bombe je potrebno označiti z zastavico (desni klik). Število bomb v mreži,"
						+ " ki jih je potrebno označiti, je izpisano v levem zgornjem kotu."
						+ " Ob levem kliku na polje in njegovem odprtju se lahko pokaže ena izmed treh možnosti:"
						+ "<br>  1. Pokaže se bomba in igra se konča, saj ste odprli nedovoljeno polje."
						+ "<br>  2. Pokaže se prazno polje. To pomeni da v sosednjih 8 poljih ni nobene bombe."
						+ "<br>  3. Pokaže se polje s številko. Številka predstavlja število bomb v sosednjih 8 poljih."
						+ "<br>V primeru, da želite igro predčasno ponastaviti in začeti od začetka, kliknite v rumeno sliko smeškota.</html>");
		informacije_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		informacije_2.setVerticalAlignment(SwingConstants.TOP);
		informacije_2.setBounds(10, 0, 386, 189);
		frame.getContentPane().add(informacije_2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == myButton) {
			frame.setVisible(false);
		}

	}
}
