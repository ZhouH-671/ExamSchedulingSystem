import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private ExamManager examManager;
    private StudentManager studentManager;

    public LoginWindow(ExamManager examManager, StudentManager studentManager) {
        this.examManager = examManager;
        this.studentManager = studentManager;

        initUI();
    }

    private void initUI() {
        setTitle("考试安排系统 - 登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // 居中显示

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 标题
        JLabel titleLabel = new JLabel("考试安排系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton studentButton = new JButton("学生登录");
        JButton teacherButton = new JButton("教师登录");
        JButton adminButton = new JButton("管理员登录");

        // 设置按钮大小
        Dimension buttonSize = new Dimension(200, 50);
        studentButton.setPreferredSize(buttonSize);
        teacherButton.setPreferredSize(buttonSize);
        adminButton.setPreferredSize(buttonSize);

        // 按钮事件
        studentButton.addActionListener(e -> openStudentLogin());
        teacherButton.addActionListener(e -> openTeacherLogin());
        adminButton.addActionListener(e -> openAdminWindow());

        buttonPanel.add(studentButton);
        buttonPanel.add(teacherButton);
        buttonPanel.add(adminButton);

        // 添加到主面板
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void openStudentLogin() {
        String studentId = JOptionPane.showInputDialog(this, "请输入学号:", "学生登录", JOptionPane.PLAIN_MESSAGE);

        if (studentId != null && !studentId.trim().isEmpty()) {
            Student student = studentManager.findStudentById(studentId.trim());
            if (student != null) {
                // 打开学生窗口
                StudentWindow studentWindow = new StudentWindow(studentId.trim(), examManager, studentManager);
                studentWindow.setVisible(true);
                this.dispose(); // 关闭登录窗口
            }
            else {
                JOptionPane.showMessageDialog(this, "学号不存在！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openTeacherLogin() {
        String teacherId = JOptionPane.showInputDialog(this, "请输入教师ID:", "教师登录", JOptionPane.PLAIN_MESSAGE);

        if (teacherId != null && !teacherId.trim().isEmpty()) {
            Teacher teacher = examManager.findTeacherById(teacherId.trim());
            if (teacher != null) {
                // 打开教师窗口
                TeacherWindow teacherWindow = new TeacherWindow(teacherId.trim(), examManager, studentManager);
                teacherWindow.setVisible(true);
                this.dispose(); // 关闭登录窗口
            }
            else {
                JOptionPane.showMessageDialog(this, "教师ID不存在！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openAdminWindow() {
        // 简单管理员验证（实际项目中应该更严格）
        String username = JOptionPane.showInputDialog(this, "管理员账号:", "管理员登录", JOptionPane.PLAIN_MESSAGE);
        String password = JOptionPane.showInputDialog(this, "密码:", "管理员登录", JOptionPane.PLAIN_MESSAGE);

        if ("admin".equals(username) && "admin".equals(password)) {
            // 打开管理员窗口
            AdminWindow adminWindow = new AdminWindow(examManager, studentManager);
            adminWindow.setVisible(true);
            this.dispose(); // 关闭登录窗口
        }
        else {
            JOptionPane.showMessageDialog(this, "账号或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
