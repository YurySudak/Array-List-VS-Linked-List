package zen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
    JFrame frame;
    JPanel dataPanel;
    JPanel controlPanel;
    JTextField textField;
    JRadioButton rb1, rb2;
    int rows = 3;
    int cols = 6;
    int gap = 5;
    String[][] data;
    List<MyObject> al;
    List<MyObject> ll;
    int max = 1000;
    final Font FONT = new Font("Segoe UI", Font.BOLD, 25);
    final Color GREEN = new Color(0, 200, 0);
    final Color BLUE = new Color(0, 0, 200);
    final Color GOLD = new Color(255, 215, 0);
    final Color RED = new Color(200, 0, 0);

    public static void main(String[] args) {
        Main main = new Main();
        main.makeControlPanel();
        main.makeData();
        main.makeDataPanel();
        main.makeFrame();
    }

    private void fillLists() {
        al = new ArrayList<>();
        ll = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i <= max; i++) {
            MyObject o = new MyObject(i+"", random.nextInt());
            al.add(o);
            ll.add(o);
        }
    }

    private void makeData() {
        fillLists();
        data = new String[rows][cols];
        data[0] = new String[]{format(max), "0", "0,25", "0,5", "0,75", "1"};
        data[1][0] = "Array List";
        data[2][0] = "Linked List";
        for (int i = 1; i < cols; i++) {
            data[1][i] = getData(i, al);
            data[2][i] = getData(i, ll);
        }
    }

    private String getData(int i, List<MyObject> list) {
        int n = (int) ((i - 1) * 0.25 * max);
        long start = System.nanoTime();
        if (rb1.isSelected()) {
            System.out.print("Removed " + list.remove(n) + " from " + list.getClass().getSimpleName());
            MyObject o = new MyObject("new "+n, new Random().nextInt());
            list.add(n, o);
            System.out.println(", then added " + o + " to that list");
        }
        if (rb2.isSelected()) {
            System.out.println("Got " + list.get(n) + " from " + list.getClass().getSimpleName());
        }
        long result = System.nanoTime() - start;
        return format(result);
    }

    private String format(long result) {
        String s = result + "";
        int l = s.length();
        StringBuilder sb = new StringBuilder();
        for (int i = l - 1; i >= 0; i--) {
            sb.insert(0, s.charAt(i));
            if ((l - i) % 3 == 0) sb.insert(0, " ");
        }
        return sb.toString();
    }

    private void makeFrame() {
        frame = new JFrame();
        frame.setSize(1000, 300);
        frame.setLocation(500, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Array List VS Linked List");
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(dataPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void makeControlPanel() {
        controlPanel = new JPanel(new FlowLayout());
        controlPanel.setOpaque(true);
        controlPanel.setBackground(Color.GRAY);

        rb1 = new JRadioButton("Remove and add");
        rb1.setSelected(true);
        rb1.setFont(FONT);
        rb1.setFocusable(false);
        rb2 = new JRadioButton("Get");
        rb2.setFont(FONT);
        rb2.setFocusable(false);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);


        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 50));
        textField.setFont(FONT);
        textField.setText("1000");

        JButton button = new JButton("Set N");
        button.setFocusable(false);
        button.setFont(FONT);
        button.setPreferredSize(new Dimension(100, 50));
        button.addActionListener(e -> action());

        controlPanel.add(rb1);
        controlPanel.add(rb2);
        controlPanel.add(textField);
        controlPanel.add(button);
    }

    private void action() {
        try {
            max = Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            textField.setText("1000");
            max = 1000;
        }
        System.out.println("max = " + max);
        makeData();
        frame.remove(dataPanel);
        makeDataPanel();
        frame.add(dataPanel);
        frame.setVisible(true);
    }

    private void makeDataPanel() {
        dataPanel = new JPanel(new GridLayout(rows, cols, gap, gap));
        dataPanel.setOpaque(true);
        dataPanel.setBackground(Color.GRAY);
        JLabel label;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                label = makeLabel(data[i][j], getColor(i, j));
                dataPanel.add(label);
            }
        }
    }

    private Color getColor(int i, int j) {
        if (j == 0) return GOLD;
        if (i == 1) {
            if (Integer.parseInt(data[1][j].replace(" ", "")) > Integer.parseInt(data[2][j].replace(" ", "")))
                return RED;
            else return GREEN;
        }
        if (i == 2) {
            if (Integer.parseInt(data[2][j].replace(" ", "")) > Integer.parseInt(data[1][j].replace(" ", "")))
                return RED;
            else return GREEN;
        }
        return BLUE;
    }

    private JLabel makeLabel(String s, Color color) {
        JLabel label;
        label = new JLabel(s);
        label.setFont(FONT);
        label.setForeground(color);
        label.setBackground(Color.DARK_GRAY);
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}

class MyObject {
    private String s;
    private Integer i;

    public MyObject(String s, Integer i) {
        this.s = s;
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "s='" + s + '\'' +
                ", i=" + i +
                '}';
    }
}