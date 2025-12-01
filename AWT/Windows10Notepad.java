import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.io.*;

public class Windows10Notepad extends Frame implements ActionListener, WindowListener {
    // 菜单栏
    private MenuBar menuBar;
    private Menu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
    
    // 文件菜单项
    private MenuItem newItem, openItem, saveItem, saveAsItem, printItem, exitItem;
    
    // 编辑菜单项
    private MenuItem undoItem, redoItem, cutItem, copyItem, pasteItem, deleteItem;
    private MenuItem findItem, replaceItem, goToItem, selectAllItem, timeDateItem;
    
    // 格式菜单项
    private MenuItem wordWrapItem, fontItem;
    private CheckboxMenuItem statusBarItem;
    
    // 帮助菜单项
    private MenuItem aboutItem;
    
    // 文本编辑区域
    private TextArea textArea;
    
    // 状态栏
    private Label statusBar;
    
    // 文件相关变量
    private String currentFile = null;
    private boolean isModified = false;
    
    // 自动换行标志
    private boolean wordWrap = false;
    
    // 状态栏显示标志
    private boolean showStatusBar = true;
    
    // 构造函数
    public Windows10Notepad() {
        // 设置窗口标题
        setTitle("Untitled - Windows 10 Notepad");
        
        // 设置窗口大小和位置
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // 添加窗口监听器
        addWindowListener(this);
        
        // 创建菜单栏
        createMenuBar();
        
        // 创建文本编辑区域
        createTextArea();
        
        // 创建状态栏
        createStatusBar();
        
        // 设置布局管理器
        setLayout(new BorderLayout());
        
        // 添加组件到窗口
        add(textArea, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        
        // 设置窗口可见
        setVisible(true);
    }
    
    // 创建菜单栏
    private void createMenuBar() {
        menuBar = new MenuBar();
        
        // 文件菜单
        fileMenu = new Menu("File");
        newItem = new MenuItem("New\tCtrl+N");
        openItem = new MenuItem("Open...\tCtrl+O");
        saveItem = new MenuItem("Save\tCtrl+S");
        saveAsItem = new MenuItem("Save As...");
        printItem = new MenuItem("Print...\tCtrl+P");
        exitItem = new MenuItem("Exit");
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(printItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // 编辑菜单
        editMenu = new Menu("Edit");
        undoItem = new MenuItem("Undo\tCtrl+Z");
        redoItem = new MenuItem("Redo\tCtrl+Y");
        cutItem = new MenuItem("Cut\tCtrl+X");
        copyItem = new MenuItem("Copy\tCtrl+C");
        pasteItem = new MenuItem("Paste\tCtrl+V");
        deleteItem = new MenuItem("Delete\tDel");
        findItem = new MenuItem("Find...\tCtrl+F");
        replaceItem = new MenuItem("Replace...\tCtrl+H");
        goToItem = new MenuItem("Go To...");
        selectAllItem = new MenuItem("Select All\tCtrl+A");
        timeDateItem = new MenuItem("Time/Date\tF5");
        
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
        formatMenu = new Menu("Format");
        wordWrapItem = new MenuItem("Word Wrap");
        fontItem = new MenuItem("Font...");
        
        formatMenu.add(wordWrapItem);
        formatMenu.add(fontItem);
        
        // 查看菜单
        viewMenu = new Menu("View");
        statusBarItem = new CheckboxMenuItem("Status Bar", true);
        
        viewMenu.add(statusBarItem);
        
        // 帮助菜单
        helpMenu = new Menu("Help");
        aboutItem = new MenuItem("About Notepad");
        
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
        
        statusBarItem.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                showStatusBar = e.getStateChange() == ItemEvent.SELECTED;
                statusBar.setVisible(showStatusBar);
                validate();
            }
        });
        
        aboutItem.addActionListener(this);
        
        // 添加菜单到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        // 设置窗口菜单栏
        setMenuBar(menuBar);
    }
    
    // 创建文本编辑区域
    private void createTextArea() {
        textArea = new TextArea("", 0, 0, TextArea.SCROLLBARS_BOTH);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.addTextListener(new TextListener() {
            public void textValueChanged(TextEvent e) {
                isModified = true;
                updateStatusBar();
            }
        });
        textArea.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                updateStatusBar();
            }
        });
    }
    
    // 创建状态栏
    private void createStatusBar() {
        statusBar = new Label("Ln 1, Col 1    0 characters", Label.LEFT);
        statusBar.setFont(new Font("Arial", Font.PLAIN, 10));
        statusBar.setForeground(Color.GRAY);
    }
    
    // 更新状态栏
    private void updateStatusBar() {
        if (showStatusBar) {
            int caretPos = textArea.getCaretPosition();
            String text = textArea.getText();
            int chars = text.length();
            
            // 简单的行号和列号计算
            int line = 1;
            int column = 1;
            for (int i = 0; i < caretPos && i < text.length(); i++) {
                if (text.charAt(i) == '\n') {
                    line++;
                    column = 1;
                } else {
                    column++;
                }
            }
            
            statusBar.setText("Ln " + line + ", Col " + column + "    " + chars + " characters");
        }
    }
    
    // 更新窗口标题
    private void updateTitle() {
        if (currentFile != null) {
            setTitle((isModified ? "*" : "") + currentFile + " - Windows 10 Notepad");
        } else {
            setTitle((isModified ? "*" : "") + "Untitled - Windows 10 Notepad");
        }
    }
    
    // 文件新建功能
    private void fileNew() {
        if (isModified) {
            int choice = MessageBox.showConfirmDialog(this, "Do you want to save changes?", "Windows 10 Notepad", 
                    MessageBox.YES_NO_CANCEL_OPTION, MessageBox.QUESTION_MESSAGE);
            if (choice == MessageBox.CANCEL_OPTION) {
                return;
            }
            if (choice == MessageBox.YES_OPTION) {
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
            int choice = MessageBox.showConfirmDialog(this, "Do you want to save changes?", "Windows 10 Notepad", 
                    MessageBox.YES_NO_CANCEL_OPTION, MessageBox.QUESTION_MESSAGE);
            if (choice == MessageBox.CANCEL_OPTION) {
                return;
            }
            if (choice == MessageBox.YES_OPTION) {
                if (!fileSave()) {
                    return;
                }
            }
        }
        
        FileDialog fileDialog = new FileDialog(this, "Open", FileDialog.LOAD);
        fileDialog.setFile("*.txt");
        fileDialog.setVisible(true);
        
        String fileName = fileDialog.getFile();
        String dirName = fileDialog.getDirectory();
        
        if (fileName != null && dirName != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(dirName + fileName))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
                currentFile = dirName + fileName;
                isModified = false;
                updateTitle();
                updateStatusBar();
            } catch (IOException e) {
                MessageBox.showMessageDialog(this, "Error opening file: " + e.getMessage(), 
                        "Windows 10 Notepad", MessageBox.ERROR_MESSAGE);
            }
        }
    }
    
    // 文件保存功能
    private boolean fileSave() {
        if (currentFile == null) {
            return fileSaveAs();
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                writer.write(textArea.getText());
                isModified = false;
                updateTitle();
                return true;
            } catch (IOException e) {
                MessageBox.showMessageDialog(this, "Error saving file: " + e.getMessage(), 
                        "Windows 10 Notepad", MessageBox.ERROR_MESSAGE);
                return false;
            }
        }
    }
    
    // 文件另存为功能
    private boolean fileSaveAs() {
        FileDialog fileDialog = new FileDialog(this, "Save As", FileDialog.SAVE);
        fileDialog.setFile("*.txt");
        fileDialog.setVisible(true);
        
        String fileName = fileDialog.getFile();
        String dirName = fileDialog.getDirectory();
        
        if (fileName != null && dirName != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dirName + fileName))) {
                writer.write(textArea.getText());
                currentFile = dirName + fileName;
                isModified = false;
                updateTitle();
                return true;
            } catch (IOException e) {
                MessageBox.showMessageDialog(this, "Error saving file: " + e.getMessage(), 
                        "Windows 10 Notepad", MessageBox.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }
    
    // 显示关于对话框
    private void showAboutDialog() {
        Dialog aboutDialog = new Dialog(this, "About Notepad", true);
        aboutDialog.setLayout(new BorderLayout());
        aboutDialog.setSize(300, 150);
        aboutDialog.setLocationRelativeTo(this);
        
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(4, 1));
        
        Label titleLabel = new Label("Windows 10 Notepad", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        Label versionLabel = new Label("Version 1.0", Label.CENTER);
        Label copyrightLabel = new Label("Copyright (C) 2024", Label.CENTER);
        
        Button okButton = new Button("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aboutDialog.dispose();
            }
        });
        
        panel.add(titleLabel);
        panel.add(versionLabel);
        panel.add(copyrightLabel);
        
        aboutDialog.add(panel, BorderLayout.CENTER);
        aboutDialog.add(okButton, BorderLayout.SOUTH);
        
        aboutDialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                aboutDialog.dispose();
            }
        });
        
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
            MessageBox.showMessageDialog(this, "Print functionality not implemented yet.", 
                    "Windows 10 Notepad", MessageBox.INFORMATION_MESSAGE);
        } else if (source == exitItem) {
            windowClosing(null);
        }
        
        // 编辑菜单处理
        else if (source == undoItem) {
            // AWT TextArea不支持撤销功能
            MessageBox.showMessageDialog(this, "Undo functionality not supported in AWT TextArea.", 
                    "Windows 10 Notepad", MessageBox.INFORMATION_MESSAGE);
        } else if (source == redoItem) {
            // AWT TextArea不支持重做功能
            MessageBox.showMessageDialog(this, "Redo functionality not supported in AWT TextArea.", 
                    "Windows 10 Notepad", MessageBox.INFORMATION_MESSAGE);
        } else if (source == cutItem) {
            // 实现剪切功能
            String selectedText = textArea.getSelectedText();
            if (selectedText != null) {
                // 将选中的文本复制到剪贴板
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(selectedText), null);
                // 删除选中的文本
                textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
            }
        } else if (source == copyItem) {
            // 实现复制功能
            String selectedText = textArea.getSelectedText();
            if (selectedText != null) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(selectedText), null);
            }
        } else if (source == pasteItem) {
            // 实现粘贴功能
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    String text = (String) contents.getTransferData(DataFlavor.stringFlavor);
                    textArea.replaceRange(text, textArea.getSelectionStart(), textArea.getSelectionEnd());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (source == deleteItem) {
            textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
        } else if (source == findItem) {
            MessageBox.showMessageDialog(this, "Find functionality not implemented yet.", 
                    "Windows 10 Notepad", MessageBox.INFORMATION_MESSAGE);
        } else if (source == replaceItem) {
            MessageBox.showMessageDialog(this, "Replace functionality not implemented yet.", 
                    "Windows 10 Notepad", MessageBox.INFORMATION_MESSAGE);
        } else if (source == goToItem) {
            MessageBox.showMessageDialog(this, "Go To functionality not implemented yet.", 
                    "Windows 10 Notepad", MessageBox.INFORMATION_MESSAGE);
        } else if (source == selectAllItem) {
            textArea.selectAll();
        } else if (source == timeDateItem) {
            java.util.Date now = new java.util.Date();
            textArea.insert(now.toString(), textArea.getCaretPosition());
        }
        
        // 格式菜单处理
        else if (source == wordWrapItem) {
            wordWrap = !wordWrap;
            // AWT TextArea不直接支持自动换行，需要自定义实现
            MessageBox.showMessageDialog(this, "Word Wrap functionality not fully supported in AWT TextArea.", 
                    "Windows 10 Notepad", MessageBox.INFORMATION_MESSAGE);
        } else if (source == fontItem) {
            MessageBox.showMessageDialog(this, "Font functionality not implemented yet.", 
                    "Windows 10 Notepad", MessageBox.INFORMATION_MESSAGE);
        }
        
        // 帮助菜单处理
        else if (source == aboutItem) {
            showAboutDialog();
        }
    }
    
    // 实现WindowListener接口
    public void windowOpened(WindowEvent e) {}
    
    public void windowClosing(WindowEvent e) {
        if (isModified) {
            int choice = MessageBox.showConfirmDialog(this, "Do you want to save changes?", "Windows 10 Notepad", 
                    MessageBox.YES_NO_CANCEL_OPTION, MessageBox.QUESTION_MESSAGE);
            if (choice == MessageBox.CANCEL_OPTION) {
                return;
            }
            if (choice == MessageBox.YES_OPTION) {
                if (!fileSave()) {
                    return;
                }
            }
        }
        dispose();
        System.exit(0);
    }
    
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    
    // 自定义消息框类
    static class MessageBox {
        public static final int YES_OPTION = 0;
        public static final int NO_OPTION = 1;
        public static final int CANCEL_OPTION = 2;
        
        public static final int YES_NO_CANCEL_OPTION = 0;
        
        public static final int ERROR_MESSAGE = 0;
        public static final int INFORMATION_MESSAGE = 1;
        public static final int WARNING_MESSAGE = 2;
        public static final int QUESTION_MESSAGE = 3;
        
        public static void showMessageDialog(Component parent, String message, String title, int messageType) {
            Dialog dialog = new Dialog((Frame)parent, title, true);
            dialog.setLayout(new BorderLayout(10, 10));
            dialog.setSize(300, 150);
            dialog.setLocationRelativeTo(parent);
            
            Label messageLabel = new Label(message, Label.CENTER);
            dialog.add(messageLabel, BorderLayout.CENTER);
            
            Panel buttonPanel = new Panel();
            Button okButton = new Button("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            buttonPanel.add(okButton);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            
            dialog.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dialog.dispose();
                }
            });
            
            dialog.setVisible(true);
        }
        
        public static int showConfirmDialog(Component parent, String message, String title, int optionType, int messageType) {
            Dialog dialog = new Dialog((Frame)parent, title, true);
            dialog.setLayout(new BorderLayout(10, 10));
            dialog.setSize(350, 180);
            dialog.setLocationRelativeTo(parent);
            
            Label messageLabel = new Label(message, Label.CENTER);
            dialog.add(messageLabel, BorderLayout.CENTER);
            
            Panel buttonPanel = new Panel();
            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");
            Button cancelButton = new Button("Cancel");
            
            final int[] result = {CANCEL_OPTION};
            
            yesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    result[0] = YES_OPTION;
                    dialog.dispose();
                }
            });
            
            noButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    result[0] = NO_OPTION;
                    dialog.dispose();
                }
            });
            
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    result[0] = CANCEL_OPTION;
                    dialog.dispose();
                }
            });
            
            buttonPanel.add(yesButton);
            buttonPanel.add(noButton);
            buttonPanel.add(cancelButton);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            
            dialog.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    result[0] = CANCEL_OPTION;
                    dialog.dispose();
                }
            });
            
            dialog.setVisible(true);
            return result[0];
        }
    }
    
    // 主方法
    public static void main(String[] args) {
        new Windows10Notepad();
    }
}