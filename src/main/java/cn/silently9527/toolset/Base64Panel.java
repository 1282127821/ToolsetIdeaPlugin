package cn.silently9527.toolset;

import cn.silently9527.component.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.JBColor;
import com.intellij.util.Base64;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class Base64Panel extends JPanel {

    private TextEditor sourceTextEditor = new TextEditor(8, 20);
    private TextEditor targetTextEditor = new TextEditor(8, 20);
    private JLabel exceptionMessageLabel = new JLabel();


    public Base64Panel(Project project) {
        super(new BorderLayout(0, 10));
        this.setBorder(JBUI.Borders.empty(10));

        this.add(createSourceTextEditor(), BorderLayout.NORTH);
        this.add(createTargetTextEditor(), BorderLayout.CENTER);
        this.add(createConvertButton(), BorderLayout.SOUTH);
    }

    private Component createConvertButton() {
        JButton encodeButton = new JButton("编码");
        encodeButton.addActionListener(encodeButtonListener());

        JButton decodeButton = new JButton("解码");
        decodeButton.addActionListener(decodeButtonListener());

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(encodeButton);
        horizontalBox.add(Box.createHorizontalStrut(10));
        horizontalBox.add(decodeButton);
        horizontalBox.add(Box.createHorizontalStrut(10));
        horizontalBox.add(exceptionMessageLabel);
        return horizontalBox;
    }

    private JComponent createSourceTextEditor() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("输入源:"), BorderLayout.NORTH);
        panel.add(this.sourceTextEditor, BorderLayout.CENTER);
        return panel;
    }

    private JComponent createTargetTextEditor() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("结果:"), BorderLayout.NORTH);
        panel.add(this.targetTextEditor, BorderLayout.CENTER);
        return panel;
    }


    @NotNull
    private ActionListener encodeButtonListener() {
        return e -> {
            exceptionMessageLabel.setText("");
            exceptionMessageLabel.setForeground(JBColor.RED);

            String sourceText = this.sourceTextEditor.getTextValue();
            if (StringUtil.isEmpty(sourceText)) {
                exceptionMessageLabel.setText("输入源必填");
                return;
            }

            String result = Base64.encode(sourceText.getBytes(StandardCharsets.UTF_8));
            targetTextEditor.setTextValue(result);

            exceptionMessageLabel.setForeground(JBColor.GREEN);
            exceptionMessageLabel.setText("编码完成");
        };
    }

    @NotNull
    private ActionListener decodeButtonListener() {
        return e -> {
            exceptionMessageLabel.setText("");
            exceptionMessageLabel.setForeground(JBColor.RED);

            String sourceText = this.sourceTextEditor.getTextValue();
            if (StringUtil.isEmpty(sourceText)) {
                exceptionMessageLabel.setText("输入源必填");
                return;
            }

            String result = new String(Base64.decode(sourceText), StandardCharsets.UTF_8);
            this.targetTextEditor.setTextValue(result);

            exceptionMessageLabel.setForeground(JBColor.GREEN);
            exceptionMessageLabel.setText("解码完成");
        };
    }

}
