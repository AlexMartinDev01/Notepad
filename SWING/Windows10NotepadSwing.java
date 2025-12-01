import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Windows10NotepadSwing extends JFrame implements ActionListener {
    // 菜单栏
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
    
    // 文件菜单项
    private JMenuItem newItem, openItem, saveItem, saveAsItem, printItem, exitItem;
    
    // 编辑菜单项
    private JMenuItem undoItem, redoItem, cutItem, copyItem, pasteItem, deleteItem;
    private JMenuItem findItem, replaceItem, goToItem, selectAllItem, timeDateItem;
    
    // 格式菜单项
    private JMenuItem wordWrapItem, fontItem;
    private JCheckBoxMenuItem statusBarItem;
    
    // 帮助菜单项
    private JMenuItem aboutItem;
    
    // 文本编辑区域
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private UndoManager undoManager;
    
    // 状态栏
    private JLabel statusBar;
    
    // 文件相关变量
    private String currentFile = null;
    private boolean isModified = false;
    
    // 自动换行标志
    private boolean wordWrap = false;
    
    // 状态栏显示标志
    private boolean showStatusBar = true;
    
    // 构造函数
    public Windows10NotepadSwing() {
        // 设置窗口标题
        setTitle("Untitled - Windows 10 Notepad");
        
        // 设置窗口大小和位置
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // 设置默认关闭操作
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
        
        // 创建菜单栏
        createMenuBar();
        
        // 创建文本编辑区域
        createTextArea();
        
        // 创建状态栏
        createStatusBar();
        
        // 设置布局管理器
        setLayout(new BorderLayout());
        
        // 添加组件到窗口
        add(scrollPane, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        
        // 设置窗口可见
        setVisible(true);
    }
    
    // 创建菜单栏
    private void createMenuBar() {
        menuBar = new JMenuBar();
        
        // 文件菜单
        fileMenu = new JMenu("File");
        newItem = new JMenuItem("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        openItem = new JMenuItem("Open...");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveItem = new JMenuItem("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAsItem = new JMenuItem("Save As...");
        printItem = new JMenuItem("Print...");
        printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        exitItem = new JMenuItem("Exit");
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(printItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // 编辑菜单
        editMenu = new JMenu("Edit");
        undoItem = new JMenuItem("Undo");
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        redoItem = new JMenuItem("Redo");
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        cutItem = new JMenuItem("Cut");
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        copyItem = new JMenuItem("Copy");
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        pasteItem = new JMenuItem("Paste");
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        deleteItem = new JMenuItem("Delete");
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        findItem = new JMenuItem("Find...");
        findItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        replaceItem = new JMenuItem("Replace...");
        replaceItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        goToItem = new JMenuItem("Go To...");
        selectAllItem = new JMenuItem("Select All");
        selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        timeDateItem = new JMenuItem("Time/Date");
        timeDateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(deleteItem);
        editMenu.addSeparator();
        editMenu.add(findItem);
        editMenu.add(replaceItem);
        editMenu.add(goToItem);
        editMenu.addSeparator();
        editMenu.add(selectAllItem);
        editMenu.add(timeDateItem);
        
        // 格式菜单
        formatMenu = new JMenu("Format");
        wordWrapItem = new JMenuItem("Word Wrap");
        fontItem = new JMenuItem("Font...");
        
        formatMenu.add(wordWrapItem);
        formatMenu.add(fontItem);
        
        // 查看菜单
        viewMenu = new JMenu("View");
        statusBarItem = new JCheckBoxMenuItem("Status Bar", true);
        
        viewMenu.add(statusBarItem);
        
        // 帮助菜单
        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About Notepad");
        
        helpMenu.add(aboutItem);
        
        // 添加菜单项监听器
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        saveAsItem.addActionListener(this);
        printItem.addActionListener(this);
        exitItem.addActionListener(this);
        
        undoItem.addActionListener(this);
        redoItem.addActionListener(this);
        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        deleteItem.addActionListener(this);
        findItem.addActionListener(this);
        replaceItem.addActionListener(this);
        goToItem.addActionListener(this);
        selectAllItem.addActionListener(this);
        timeDateItem.addActionListener(this);
        
        wordWrapItem.addActionListener(this);
        fontItem.addActionListener(this);
        
        statusBarItem.addActionListener(this);
        
        aboutItem.addActionListener(this);
        
        // 添加菜单到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        // 设置窗口菜单栏
        setJMenuBar(menuBar);
    }
    
    // 创建文本编辑区域
    private void createTextArea() {
        textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setLineWrap(false);
        textArea.setWrapStyleWord(true);
        
        // 初始化撤销管理器
        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        
        // 添加文档监听器，用于检测文本变化
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                isModified = true;
                updateTitle();
                updateStatusBar();
            }
            
            public void removeUpdate(DocumentEvent e) {
                isModified = true;
                updateTitle();
                updateStatusBar();
            }
            
            public void changedUpdate(DocumentEvent e) {
                updateStatusBar();
            }
        });
        
        // 添加光标监听器，用于更新状态栏
        textArea.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                updateStatusBar();
            }
        });
        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
    
    // 创建状态栏
    private void createStatusBar() {
        statusBar = new JLabel("Ln 1, Col 1    0 characters");
        statusBar.setFont(new Font("Arial", Font.PLAIN, 10));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    }
    
    // 更新状态栏
    private void updateStatusBar() {
        if (showStatusBar) {
            int caretPos = textArea.getCaretPosition();
            try {
                Element root = textArea.getDocument().getDefaultRootElement();
                int line = root.getElementIndex(caretPos) + 1;
                int column = caretPos - root.getElement(line - 1).getStartOffset() + 1;
                int chars = textArea.getText().length();
                
                statusBar.setText("Ln " + line + ", Col " + column + "    " + chars + " characters");
            } catch (Exception e) {
                statusBar.setText("Ln 1, Col 1    0 characters");
            }
        }
    }
    
    // 更新窗口标题
    private void updateTitle() {
        if (currentFile != null) {
            setTitle((isModified ? "*" : "") + new File(currentFile).getName() + " - Windows 10 Notepad");
        } else {
            setTitle((isModified ? "*" : "") + "Untitled - Windows 10 Notepad");
        }
    }
    
    // 文件新建功能
    private void fileNew() {
        if (isModified) {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to save changes?", "Windows 10 Notepad", 
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
            if (choice == JOptionPane.YES_OPTION) {
                if (!fileSave()) {
                    return;
                }
            }
        }
        textArea.setText("");
        currentFile = null;
        isModified = false;
        updateTitle();
        updateStatusBar();
    }
    
    // 文件打开功能
    private void fileOpen() {
        if (isModified) {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to save changes?", "Windows 10 Notepad", 
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
            if (choice == JOptionPane.YES_OPTION) {
                if (!fileSave()) {
                    return;
                }
            }
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(true);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                
                textArea.setText(content.toString());
                currentFile = file.getAbsolutePath();
                isModified = false;
                updateTitle();
                updateStatusBar();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(), 
                        "Windows 10 Notepad", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // 文件保存功能
    private boolean fileSave() {
        if (currentFile == null) {
            return fileSaveAs();
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                writer.write(textArea.getText());
                writer.close();
                
                isModified = false;
                updateTitle();
                return true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), 
                        "Windows 10 Notepad", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }
    
    // 文件另存为功能
    private boolean fileSaveAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(true);
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            
            // 确保文件扩展名为.txt
            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
                file = new File(filePath);
            }
            
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(textArea.getText());
                writer.close();
                
                currentFile = filePath;
                isModified = false;
                updateTitle();
                return true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), 
                        "Windows 10 Notepad", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }
    
    // 退出应用程序
    private void exitApplication() {
        if (isModified) {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to save changes?", "Windows 10 Notepad", 
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
            if (choice == JOptionPane.YES_OPTION) {
                if (!fileSave()) {
                    return;
                }
            }
        }
        dispose();
        System.exit(0);
    }
    
    // 显示关于对话框
    private void showAboutDialog() {
        JDialog aboutDialog = new JDialog(this, "About Notepad", true);
        aboutDialog.setLayout(new BorderLayout(10, 10));
        aboutDialog.setSize(300, 180);
        aboutDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Windows 10 Notepad", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel versionLabel = new JLabel("Version 1.0", SwingConstants.CENTER);
        JLabel copyrightLabel = new JLabel("Copyright (C) 2024", SwingConstants.CENTER);
        JLabel descriptionLabel = new JLabel("Pure Swing Implementation", SwingConstants.CENTER);
        
        panel.add(titleLabel);
        panel.add(versionLabel);
        panel.add(copyrightLabel);
        panel.add(descriptionLabel);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aboutDialog.dispose();
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        
        aboutDialog.add(panel, BorderLayout.CENTER);
        aboutDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        aboutDialog.setVisible(true);
    }
    
    // 实现ActionListener接口
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        // 文件菜单处理
        if (source == newItem) {
            fileNew();
        } else if (source == openItem) {
            fileOpen();
        } else if (source == saveItem) {
            fileSave();
        } else if (source == saveAsItem) {
            fileSaveAs();
        } else if (source == printItem) {
            JOptionPane.showMessageDialog(this, "Print functionality not implemented yet.", 
                    "Windows 10 Notepad", JOptionPane.INFORMATION_MESSAGE);
        } else if (source == exitItem) {
            exitApplication();
        }
        
        // 编辑菜单处理
        else if (source == undoItem) {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        } else if (source == redoItem) {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        } else if (source == cutItem) {
            textArea.cut();
        } else if (source == copyItem) {
            textArea.copy();
        } else if (source == pasteItem) {
            textArea.paste();
        } else if (source == deleteItem) {
            textArea.replaceSelection("");
        } else if (source == findItem) {
            JOptionPane.showMessageDialog(this, "Find functionality not implemented yet.", 
                    "Windows 10 Notepad", JOptionPane.INFORMATION_MESSAGE);
        } else if (source == replaceItem) {
            JOptionPane.showMessageDialog(this, "Replace functionality not implemented yet.", 
                    "Windows 10 Notepad", JOptionPane.INFORMATION_MESSAGE);
        } else if (source == goToItem) {
            JOptionPane.showMessageDialog(this, "Go To functionality not implemented yet.", 
                    "Windows 10 Notepad", JOptionPane.INFORMATION_MESSAGE);
        } else if (source == selectAllItem) {
            textArea.selectAll();
        } else if (source == timeDateItem) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeDate = sdf.format(new Date());
            textArea.insert(timeDate, textArea.getCaretPosition());
        }
        
        // 格式菜单处理
        else if (source == wordWrapItem) {
            wordWrap = !wordWrap;
            textArea.setLineWrap(wordWrap);
            scrollPane.setHorizontalScrollBarPolicy(wordWrap ? JScrollPane.HORIZONTAL_SCROLLBAR_NEVER : JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        } else if (source == fontItem) {
            Font currentFont = textArea.getFont();
            Font newFont = JFontChooser.showDialog(this, "Font", currentFont);
            if (newFont != null) {
                textArea.setFont(newFont);
            }
        }
        
        // 查看菜单处理
        else if (source == statusBarItem) {
            showStatusBar = statusBarItem.isSelected();
            statusBar.setVisible(showStatusBar);
            validate();
        }
        
        // 帮助菜单处理
        else if (source == aboutItem) {
            showAboutDialog();
        }
    }
    
    // 自定义字体选择器对话框
    static class JFontChooser {
        public static Font showDialog(Component parent, String title, Font initialFont) {
            final JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), title, Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setLayout(new BorderLayout(10, 10));
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(parent);
            
            // 字体列表
            String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            JList<String> fontList = new JList<>(fonts);
            fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontList.setSelectedValue(initialFont.getFamily(), true);
            JScrollPane fontScrollPane = new JScrollPane(fontList);
            
            // 样式列表
            String[] styles = {"Plain", "Bold", "Italic", "Bold Italic"};
            JList<String> styleList = new JList<>(styles);
            styleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            int styleIndex = initialFont.getStyle();
            if (styleIndex == Font.PLAIN) styleList.setSelectedIndex(0);
            else if (styleIndex == Font.BOLD) styleList.setSelectedIndex(1);
            else if (styleIndex == Font.ITALIC) styleList.setSelectedIndex(2);
            else if (styleIndex == (Font.BOLD | Font.ITALIC)) styleList.setSelectedIndex(3);
            JScrollPane styleScrollPane = new JScrollPane(styleList);
            
            // 大小列表
            Integer[] sizes = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
            JList<Integer> sizeList = new JList<>(sizes);
            sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            sizeList.setSelectedValue(initialFont.getSize(), true);
            JScrollPane sizeScrollPane = new JScrollPane(sizeList);
            
            // 预览标签
            final JLabel previewLabel = new JLabel("AaBbYyZz 123", SwingConstants.CENTER);
            previewLabel.setFont(initialFont);
            previewLabel.setBorder(BorderFactory.createEtchedBorder());
            
            // 字体变化监听器
            ListSelectionListener fontChangeListener = new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        String fontName = fontList.getSelectedValue();
                        int style = styleList.getSelectedIndex();
                        if (style == 1) style = Font.BOLD;
                        else if (style == 2) style = Font.ITALIC;
                        else if (style == 3) style = Font.BOLD | Font.ITALIC;
                        else style = Font.PLAIN;
                        int size = sizeList.getSelectedValue();
                        previewLabel.setFont(new Font(fontName, style, size));
                    }
                }
            };
            
            fontList.addListSelectionListener(fontChangeListener);
            styleList.addListSelectionListener(fontChangeListener);
            sizeList.addListSelectionListener(fontChangeListener);
            
            // 控制面板
            JPanel controlPanel = new JPanel(new GridLayout(1, 3, 10, 10));
            controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
            controlPanel.add(new JLabel("Font:"));
            controlPanel.add(new JLabel("Style:"));
            controlPanel.add(new JLabel("Size:"));
            
            // 列表面板
            JPanel listPanel = new JPanel(new GridLayout(1, 3, 10, 10));
            listPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            listPanel.add(fontScrollPane);
            listPanel.add(styleScrollPane);
            listPanel.add(sizeScrollPane);
            
            // 预览面板
            JPanel previewPanel = new JPanel(new BorderLayout());
            previewPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            previewPanel.add(new JLabel("Preview:"), BorderLayout.NORTH);
            previewPanel.add(previewLabel, BorderLayout.CENTER);
            
            // 按钮面板
            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("OK");
            JButton cancelButton = new JButton("Cancel");
            
            final Font[] result = new Font[1];
            
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String fontName = fontList.getSelectedValue();
                    int style = styleList.getSelectedIndex();
                    if (style == 1) style = Font.BOLD;
                    else if (style == 2) style = Font.ITALIC;
                    else if (style == 3) style = Font.BOLD | Font.ITALIC;
                    else style = Font.PLAIN;
                    int size = sizeList.getSelectedValue();
                    result[0] = new Font(fontName, style, size);
                    dialog.dispose();
                }
            });
            
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    result[0] = null;
                    dialog.dispose();
                }
            });
            
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            
            // 添加组件到对话框
            dialog.add(controlPanel, BorderLayout.NORTH);
            dialog.add(listPanel, BorderLayout.CENTER);
            dialog.add(previewPanel, BorderLayout.SOUTH);
            dialog.add(buttonPanel, BorderLayout.PAGE_END);
            
            dialog.setVisible(true);
            return result[0];
        }
    }
    
    // 主方法
    public static void main(String[] args) {
        // 设置系统外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Windows10NotepadSwing();
            }
        });
    }
}