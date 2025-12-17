import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AdminWindow extends JFrame {
    private ExamManager examManager;
    private StudentManager studentManager;
    private JTabbedPane tabbedPane;

    // 学生管理相关
    private JTable studentTable;
    private DefaultTableModel studentTableModel;
    private JLabel studentCountLabel;  // ← 新增：学生计数标签

    public AdminWindow(ExamManager examManager, StudentManager studentManager) {
        this.examManager = examManager;
        this.studentManager = studentManager;

        initUI();
        loadAllData();
    }

    private void initUI() {
        setTitle("管理员系统 - 考试安排管理");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // 顶部信息栏
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel infoLabel = new JLabel("管理员系统 - 完整管理功能");
        infoLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        infoPanel.add(infoLabel);

        // 选项卡
        tabbedPane = new JTabbedPane();

        // 教师管理
        JPanel teacherPanel = createTeacherPanel();
        tabbedPane.addTab("教师管理", teacherPanel);

        // 学生管理
        JPanel studentPanel = createStudentPanel();
        tabbedPane.addTab("学生管理", studentPanel);

        // 科目管理
        JPanel subjectPanel = createSubjectPanel();
        tabbedPane.addTab("科目管理", subjectPanel);

        // 教室管理
        JPanel classroomPanel = createClassroomPanel();
        tabbedPane.addTab("教室管理", classroomPanel);

        // 考试安排
        JPanel examPanel = createExamPanel();
        tabbedPane.addTab("考试安排", examPanel);

        // 统计信息
        JPanel statsPanel = createStatsPanel();
        tabbedPane.addTab("系统统计", statsPanel);

        // 布局
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        // 添加窗口监听器，关闭时返回登录
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // 可以添加返回登录的逻辑，这里简单退出
                System.exit(0);
            }
        });
    }

    private void loadAllData() {
        // 空实现，或者添加一些日志
        System.out.println("管理员窗口：数据已加载");
    }

    // 教师管理面板
    private JPanel createTeacherPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 顶部按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("添加教师");
        JButton refreshButton = new JButton("刷新");

        addButton.addActionListener(e -> addTeacher());
        refreshButton.addActionListener(e -> refreshTeacherTable());

        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);

        // 教师表格
        String[] columnNames = {"教师ID", "姓名", "部门", "操作"};
        Object[][] data = getTeacherTableData();

        JTable teacherTable = new JTable(data, columnNames);
        teacherTable.setFillsViewportHeight(true);

        // 添加操作列
        teacherTable.getColumn("操作").setCellRenderer(new ButtonRenderer());
        teacherTable.getColumn("操作").setCellEditor(new ButtonEditor(new JCheckBox(), examManager, this));

        JScrollPane scrollPane = new JScrollPane(teacherTable);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private Object[][] getTeacherTableData() {
        java.util.List<Teacher> teachers = examManager.getAllTeachers();
        Object[][] data = new Object[teachers.size()][4];

        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            data[i][0] = teacher.getTeacherId();
            data[i][1] = teacher.getName();
            data[i][2] = teacher.getDepartment();
            data[i][3] = "删除";
        }

        return data;
    }

    private void refreshTeacherTable() {
        // 刷新教师表格数据
        // 这里需要重新加载表格，简化处理：显示消息
        JOptionPane.showMessageDialog(this, "刷新功能待实现", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addTeacher() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField deptField = new JTextField(10);

        panel.add(new JLabel("教师ID:"));
        panel.add(idField);
        panel.add(new JLabel("姓名:"));
        panel.add(nameField);
        panel.add(new JLabel("部门:"));
        panel.add(deptField);

        int result = JOptionPane.showConfirmDialog(this, panel, "添加教师",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String department = deptField.getText().trim();

            if (!id.isEmpty() && !name.isEmpty() && !department.isEmpty()) {
                Teacher teacher = new Teacher(id, name, department);
                boolean success = examManager.addTeacher(teacher);
                if (success) {
                    JOptionPane.showMessageDialog(this, "添加成功");
                } else {
                    JOptionPane.showMessageDialog(this, "添加失败（ID可能已存在）", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === 顶部按钮区域 ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton addButton = new JButton("添加学生");
        JButton deleteButton = new JButton("删除选中");
        JButton refreshButton = new JButton("刷新");
        JButton manageCoursesButton = new JButton("管理选课");

        // 设置按钮事件
        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteSelectedStudent());
        refreshButton.addActionListener(e -> refreshStudentTable());
        manageCoursesButton.addActionListener(e -> manageStudentCourses());

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(manageCoursesButton);

        // === 搜索区域 ===
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.add(new JLabel("搜索:"));

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("搜索");

        searchButton.addActionListener(e -> searchStudents(searchField.getText()));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // 组合按钮和搜索区域
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        // === 学生表格 ===
        String[] columnNames = {"选择", "学号", "姓名", "已选科目数", "已选科目", "操作"};
        studentTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; // 选择列
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 5; // 只有选择和操作列可编辑
            }
        };

        studentTable = new JTable(studentTableModel);
        studentTable.setRowHeight(30);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // 选择列
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(100); // 学号
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(100); // 姓名
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // 科目数
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(200); // 科目列表
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(150); // 操作

        // 操作列按钮
        studentTable.getColumn("操作").setCellRenderer(new StudentButtonRenderer());
        studentTable.getColumn("操作").setCellEditor(new StudentButtonEditor(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(studentTable);

        // === 底部信息 ===
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        studentCountLabel = new JLabel("学生总数: 0");
        infoPanel.add(studentCountLabel);

        // === 添加到主面板 ===
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);

        // 初始加载数据
        loadStudentData();
        updateStudentCount();

        return panel;
    }

    private void loadStudentData() {
        // 清空现有数据
        studentTableModel.setRowCount(0);

        // 获取所有学生
        List<Student> students = studentManager.getAllStudents();

        for (Student student : students) {
            Object[] row = new Object[6];
            row[0] = false; // 选择框
            row[1] = student.getStudentId();
            row[2] = student.getName();

            // 获取已选科目信息
            List<Subject> enrolledSubjects = student.getEnrolledSubjects();
            row[3] = enrolledSubjects.size(); // 科目数量

            // 科目名称列表
            StringBuilder subjectsStr = new StringBuilder();
            for (Subject subject : enrolledSubjects) {
                subjectsStr.append(subject.getName()).append(", ");
            }
            if (subjectsStr.length() > 0) {
                subjectsStr.setLength(subjectsStr.length() - 2); // 去掉最后的", "
            }
            row[4] = subjectsStr.toString();
            row[5] = "编辑|删除|选课"; // 操作按钮

            studentTableModel.addRow(row);
        }
    }

    private void updateStudentCount() {
        int count = studentTableModel.getRowCount();
        if (studentCountLabel != null) {
            studentCountLabel.setText("学生总数: " + count);
        }
    }

    private void addStudent() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);

        panel.add(new JLabel("学号:"));
        panel.add(idField);
        panel.add(new JLabel("姓名:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(this, panel, "添加学生",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String studentId = idField.getText().trim();
            String name = nameField.getText().trim();

            if (!studentId.isEmpty() && !name.isEmpty()) {
                Student student = new Student(studentId, name);
                boolean success = studentManager.addStudent(student);

                if (success) {
                    JOptionPane.showMessageDialog(this, "添加学生成功！");
                    loadStudentData();
                    updateStudentCount();
                } else {
                    JOptionPane.showMessageDialog(this, "添加失败！学号可能已存在。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteSelectedStudent() {
        List<String> selectedIds = new ArrayList<>();

        // 收集选中的学生
        for (int i = 0; i < studentTableModel.getRowCount(); i++) {
            Boolean selected = (Boolean) studentTableModel.getValueAt(i, 0);
            if (selected != null && selected) {
                String studentId = (String) studentTableModel.getValueAt(i, 1);
                selectedIds.add(studentId);
            }
        }

        if (selectedIds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的学生！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 确认删除
        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除选中的 " + selectedIds.size() + " 名学生吗？",
                "确认删除", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int deletedCount = 0;
            for (String studentId : selectedIds) {
                if (studentManager.deleteStudent(studentId)) {
                    deletedCount++;
                }
            }

            JOptionPane.showMessageDialog(this, "成功删除 " + deletedCount + " 名学生！");
            loadStudentData();
            updateStudentCount();
        }
    }

    private void refreshStudentTable() {
        loadStudentData();
        updateStudentCount();
        JOptionPane.showMessageDialog(this, "学生数据已刷新！", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadStudentData(); // 显示所有
            return;
        }

        keyword = keyword.trim().toLowerCase();

        // 清空现有数据
        studentTableModel.setRowCount(0);

        // 搜索匹配的学生
        List<Student> allStudents = studentManager.getAllStudents();
        for (Student student : allStudents) {
            if (student.getStudentId().toLowerCase().contains(keyword) ||
                    student.getName().toLowerCase().contains(keyword)) {

                Object[] row = new Object[6];
                row[0] = false;
                row[1] = student.getStudentId();
                row[2] = student.getName();

                List<Subject> enrolledSubjects = student.getEnrolledSubjects();
                row[3] = enrolledSubjects.size();

                StringBuilder subjectsStr = new StringBuilder();
                for (Subject subject : enrolledSubjects) {
                    subjectsStr.append(subject.getName()).append(", ");
                }
                if (subjectsStr.length() > 0) {
                    subjectsStr.setLength(subjectsStr.length() - 2);
                }
                row[4] = subjectsStr.toString();
                row[5] = "编辑|删除|选课";

                studentTableModel.addRow(row);
            }
        }

        updateStudentCount();
    }

    private void manageStudentCourses() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个学生！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentId = (String) studentTableModel.getValueAt(selectedRow, 1);
        String studentName = (String) studentTableModel.getValueAt(selectedRow, 2);

        // 打开选课管理对话框
        new StudentCourseDialog(this, studentId, studentName, examManager, studentManager);
    }



    // 简化其他面板创建...
    private JPanel createSubjectPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("科目管理功能（简化版）"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createClassroomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("教室管理功能（简化版）"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createExamPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("考试安排功能（简化版）"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int teacherCount = examManager.getTeacherCount();
        int subjectCount = examManager.getSubjectCount();
        int classroomCount = examManager.getClassroomCount();
        int examCount = examManager.getExamScheduleCount();
        int studentCount = studentManager.getStudentCount();

        panel.add(new JLabel("系统统计信息:"));
        panel.add(new JLabel("教师数量: " + teacherCount));
        panel.add(new JLabel("学生数量: " + studentCount));
        panel.add(new JLabel("科目数量: " + subjectCount));
        panel.add(new JLabel("教室数量: " + classroomCount));
        panel.add(new JLabel("考试安排数量: " + examCount));

        return panel;
    }

    // 内部类：按钮渲染器
    // 选课管理对话框
    class StudentCourseDialog extends JDialog {
        private String studentId;
        private ExamManager examManager;
        private StudentManager studentManager;

        private JList<String> enrolledList;
        private JList<String> availableList;
        private DefaultListModel<String> enrolledModel;
        private DefaultListModel<String> availableModel;

        public StudentCourseDialog(JFrame parent, String studentId, String studentName,
                                   ExamManager examManager, StudentManager studentManager) {
            super(parent, "管理选课 - " + studentName + " (" + studentId + ")", true);
            this.studentId = studentId;
            this.examManager = examManager;
            this.studentManager = studentManager;

            initUI();
            loadCourseData();

            setSize(600, 400);
            setLocationRelativeTo(parent);
            setVisible(true);
        }

        private void initUI() {
            setLayout(new BorderLayout(10, 10));
            JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // 已选课程面板
            JPanel enrolledPanel = new JPanel(new BorderLayout());
            enrolledPanel.setBorder(BorderFactory.createTitledBorder("已选课程"));

            enrolledModel = new DefaultListModel<>();
            enrolledList = new JList<>(enrolledModel);
            JScrollPane enrolledScroll = new JScrollPane(enrolledList);
            enrolledPanel.add(enrolledScroll, BorderLayout.CENTER);

            JButton dropButton = new JButton("退课");
            dropButton.addActionListener(e -> dropSelectedCourse());
            enrolledPanel.add(dropButton, BorderLayout.SOUTH);

            // 可选课程面板
            JPanel availablePanel = new JPanel(new BorderLayout());
            availablePanel.setBorder(BorderFactory.createTitledBorder("可选课程"));

            availableModel = new DefaultListModel<>();
            availableList = new JList<>(availableModel);
            JScrollPane availableScroll = new JScrollPane(availableList);
            availablePanel.add(availableScroll, BorderLayout.CENTER);

            JButton enrollButton = new JButton("选课");
            enrollButton.addActionListener(e -> enrollSelectedCourse());
            availablePanel.add(enrollButton, BorderLayout.SOUTH);

            // 左右布局
            JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
            listsPanel.add(enrolledPanel);
            listsPanel.add(availablePanel);

            // 底部按钮
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton closeButton = new JButton("关闭");
            closeButton.addActionListener(e -> dispose());
            buttonPanel.add(closeButton);

            contentPanel.add(listsPanel, BorderLayout.CENTER);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(contentPanel, BorderLayout.CENTER);
        }

        private void loadCourseData() {
            // 清空列表
            enrolledModel.clear();
            availableModel.clear();

            // 获取学生已选课程
            Student student = studentManager.findStudentById(studentId);
            if (student == null) return;

            List<Subject> enrolledSubjects = student.getEnrolledSubjects();
            List<Subject> allSubjects = examManager.getAllSubjects();

            // 添加已选课程
            for (Subject subject : enrolledSubjects) {
                enrolledModel.addElement(subject.getSubjectId() + " - " + subject.getName() + " (" + subject.getCredit() + "学分)");
            }

            // 添加可选课程（未选的）
            for (Subject subject : allSubjects) {
                if (!enrolledSubjects.contains(subject)) {
                    availableModel.addElement(subject.getSubjectId() + " - " + subject.getName() + " (" + subject.getCredit() + "学分)");
                }
            }
        }

        private void dropSelectedCourse() {
            int selectedIndex = enrolledList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "请先选择要退的课程！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selected = enrolledModel.get(selectedIndex);
            // 提取科目ID（格式：S001 - 课程名）
            String subjectId = selected.split(" - ")[0].trim();

            Subject subject = examManager.findSubjectById(subjectId);
            if (subject != null) {
                boolean success = studentManager.dropSubject(studentId, subject);
                if (success) {
                    JOptionPane.showMessageDialog(this, "退课成功！");
                    loadCourseData();
                } else {
                    JOptionPane.showMessageDialog(this, "退课失败！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void enrollSelectedCourse() {
            int selectedIndex = availableList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "请先选择要选的课程！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selected = availableModel.get(selectedIndex);
            // 提取科目ID（格式：S001 - 课程名）
            String subjectId = selected.split(" - ")[0].trim();

            Subject subject = examManager.findSubjectById(subjectId);
            if (subject != null) {
                boolean success = studentManager.enrollSubject(studentId, subject);
                if (success) {
                    JOptionPane.showMessageDialog(this, "选课成功！");
                    loadCourseData();
                } else {
                    JOptionPane.showMessageDialog(this, "选课失败！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // 学生表格按钮渲染器
    class StudentButtonRenderer extends JButton implements TableCellRenderer {
        public StudentButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // 学生表格按钮编辑器
    class StudentButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private AdminWindow adminWindow;
        private int currentRow;

        public StudentButtonEditor(JCheckBox checkBox, AdminWindow adminWindow) {
            super(checkBox);
            this.adminWindow = adminWindow;

            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            currentRow = row;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // 处理操作按钮点击
                String studentId = (String) adminWindow.studentTableModel.getValueAt(currentRow, 1);
                String studentName = (String) adminWindow.studentTableModel.getValueAt(currentRow, 2);

                // 弹出操作菜单
                String[] options = {"编辑信息", "删除学生", "管理选课"};
                int choice = JOptionPane.showOptionDialog(button,
                        "请选择操作:",
                        "学生操作 - " + studentName,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                switch (choice) {
                    case 0: // 编辑
                        editStudent(studentId);
                        break;
                    case 1: // 删除
                        deleteStudent(studentId);
                        break;
                    case 2: // 选课
                        new StudentCourseDialog(adminWindow, studentId, studentName, examManager, studentManager);
                        break;
                }
            }
            isPushed = false;
            return label;
        }

        private void editStudent(String studentId) {
            Student student = studentManager.findStudentById(studentId);
            if (student == null) return;

            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

            JTextField idField = new JTextField(studentId, 15);
            JTextField nameField = new JTextField(student.getName(), 15);

            idField.setEditable(false); // 学号不可修改

            panel.add(new JLabel("学号:"));
            panel.add(idField);
            panel.add(new JLabel("姓名:"));
            panel.add(nameField);

            int result = JOptionPane.showConfirmDialog(button, panel, "编辑学生",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String newName = nameField.getText().trim();
                if (!newName.isEmpty() && !newName.equals(student.getName())) {
                    // 更新学生姓名
                    if (studentManager.updateStudent(studentId, newName)) {
                        JOptionPane.showMessageDialog(button, "修改成功！");
                        adminWindow.loadStudentData();
                    } else {
                        JOptionPane.showMessageDialog(button, "修改失败！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        private void deleteStudent(String studentId) {
            int confirm = JOptionPane.showConfirmDialog(button,
                    "确定要删除学生 " + studentId + " 吗？",
                    "确认删除",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (studentManager.deleteStudent(studentId)) {
                    JOptionPane.showMessageDialog(button, "删除成功！");
                    adminWindow.loadStudentData();
                    adminWindow.updateStudentCount();
                } else {
                    JOptionPane.showMessageDialog(button, "删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // 内部类：按钮编辑器
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private ExamManager examManager;
        private AdminWindow adminWindow;

        public ButtonEditor(JCheckBox checkBox, ExamManager examManager, AdminWindow adminWindow) {
            super(checkBox);
            this.examManager = examManager;
            this.adminWindow = adminWindow;

            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // 处理删除操作
                JOptionPane.showMessageDialog(button, "删除功能待实现");
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}