import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PieceJOptionPane extends JOptionPane {
    private final JRadioButton queen;
    private final JRadioButton knight;
    private final JRadioButton bishop;
    private final JRadioButton rook;

    public PieceJOptionPane(String color) {
        queen = color.equals("white") ? 
            new JRadioButton("Queen", new ImageIcon(getClass().getResource("/assets/Chess_qlt60.png")))
            : new JRadioButton("Queen", new ImageIcon(getClass().getResource("/assets/Chess_qdt60.png")));
        knight = color.equals("white") ? 
            new JRadioButton("Knight", new ImageIcon(getClass().getResource("/assets/Chess_nlt60.png")))
            : new JRadioButton("Knight", new ImageIcon(getClass().getResource("/assets/Chess_ndt60.png")));
        bishop = color.equals("white") ? 
            new JRadioButton("Bishop", new ImageIcon(getClass().getResource("/assets/Chess_blt60.png")))
            : new JRadioButton("Bishop", new ImageIcon(getClass().getResource("/assets/Chess_bdt60.png")));
        rook = color.equals("white") ? 
            new JRadioButton("Rook", new ImageIcon(getClass().getResource("/assets/Chess_rlt60.png")))
            : new JRadioButton("Rook", new ImageIcon(getClass().getResource("/assets/Chess_rdt60.png")));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(queen);
        buttonGroup.add(knight);
        buttonGroup.add(bishop);
        buttonGroup.add(rook);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(queen);
        panel.add(knight);
        panel.add(bishop);
        panel.add(rook);

        setMessage(panel);
        setMessageType(PLAIN_MESSAGE);
        setOptions(new Object[]{"Select"});
    }

    public String showDialog(Component parent) {
        JDialog jdialog = createDialog(parent, "Choose a piece");
        jdialog.setVisible(true);

        if(queen.isSelected()) return "queen";
        if(knight.isSelected()) return "knight";
        if(bishop.isSelected()) return "bishop";
        if(rook.isSelected()) return "rook";

        // Default to queen
        return "queen";
    }
    
}
