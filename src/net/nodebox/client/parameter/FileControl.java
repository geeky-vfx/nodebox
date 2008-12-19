package net.nodebox.client.parameter;

import net.nodebox.client.FileUtils;
import net.nodebox.client.PlatformUtils;
import net.nodebox.node.Parameter;
import net.nodebox.node.ParameterDataListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileControl extends JComponent implements ParameterControl, ActionListener, ParameterDataListener {

    private Parameter parameter;
    private JTextField fileField;
    private JButton chooseButton;

    public FileControl(Parameter parameter) {
        this.parameter = parameter;
        setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        fileField = new JTextField();
        fileField.putClientProperty("Jcomponent.sizeVariant", "small");
        fileField.setPreferredSize(new Dimension(150, 19));
        fileField.setEditable(false);
        fileField.setFont(PlatformUtils.getSmallFont());
        chooseButton = new JButton("...");
        chooseButton.putClientProperty("JButton.buttonType", "gradient");
        chooseButton.setPreferredSize(new Dimension(30, 27));
        chooseButton.addActionListener(this);
        add(fileField);
        add(chooseButton);
        setValueForControl(parameter.getValue());
        parameter.addDataListener(this);
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setValueForControl(Object v) {
        fileField.setText(v.toString());
    }

    public String acceptedExtensions() {
        return "*";
    }

    public String acceptedDescription() {
        return "All files";
    }

    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        File f = FileUtils.showOpenDialog(frame, parameter.asString(), acceptedExtensions(), acceptedDescription());
        if (f != null) {
            parameter.setValue(f.getAbsolutePath());
        }
    }

    public void valueChanged(Parameter source, Object newValue) {
        setValueForControl(newValue);
    }
}
