import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 教师窗口 - 教师功能界面
 */
public class TeacherWindow extends JFrame {
    private String teacherId;
    private ExamManager examManager;
    private StudentManager studentManager;

    public TeacherWindow(String teacherId, ExamManager examManager, StudentManager studentManager) {
        this.teacherId = teacherId;
        this.examManager = examManager;
        this.studentManager = studentManager;

        initUI();
        loadTeacherData();
    }

    private void initUI() {
        setTitle("教师系统 - " + teacherId);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // 获取教师信息
        Teacher teacher = examManager.findTeacherById(teacherId);
        String teacherName = teacher != null ? teacher.getName() : "未知";
        String department = teacher != null ? teacher.getDepartment() : "未知";

        // 顶部信息栏
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel infoLabel = new JLabel("教师: " + teacherName + " (" + teacherId + ") - " + department);
        infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        infoPanel.add(infoLabel);

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("监考安排", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 考试列表面板（稍后填充）
        JPanel examListPanel = new JPanel();
        examListPanel.setLayout(new BoxLayout(examListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(examListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 布局
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadTeacherData() {
        Teacher teacher = examManager.findTeacherById(teacherId);
        if (teacher == null) {
            JOptionPane.showMessageDialog(this, "教师信息不存在！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 获取监考安排
        List<ExamSchedule> exams = examManager.findExamsByTeacher(teacher);

        // 清空并重新填充考试列表
        JPanel examListPanel = new JPanel();
        examListPanel.setLayout(new BoxLayout(examListPanel, BoxLayout.Y_AXIS));

        if (exams.isEmpty()) {
            JLabel noExamLabel = new JLabel("暂无监考安排");
            noExamLabel.setHorizontalAlignment(SwingConstants.CENTER);
            examListPanel.add(noExamLabel);
        } else {
            for (ExamSchedule exam : exams) {
                JPanel examPanel = createExamPanel(exam);
                examListPanel.add(examPanel);
                examListPanel.add(Box.createVerticalStrut(10));
            }
        }

        // 更新界面
        Container contentPane = getContentPane();
        for (Component comp : contentPane.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getComponentCount() > 0 && panel.getComponent(0) instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane) panel.getComponent(0);
                    scrollPane.setViewportView(examListPanel);
                    break;
                }
            }
        }

        revalidate();
        repaint();
    }

    private JPanel createExamPanel(ExamSchedule exam) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // 考试基本信息
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        JLabel subjectLabel = new JLabel("科目: " + exam.getSubject().getName());
        subjectLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));

        JLabel timeLabel = new JLabel("时间: " + exam.getExamTime().toLocalDate() + " " +
                exam.getExamTime().toLocalTime());

        JLabel classroomLabel = new JLabel("教室: " + exam.getClassroom().getLocation() +
                " (容量: " + exam.getClassroom().getCapacity() + "人)");

        JLabel durationLabel = new JLabel("时长: " + exam.getDuration() + "分钟");

        infoPanel.add(subjectLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(classroomLabel);
        infoPanel.add(durationLabel);

        panel.add(infoPanel, BorderLayout.CENTER);

        return panel;
    }
}