import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentWindow extends JFrame {
    private String studentId;
    private ExamManager examManager;
    private StudentManager studentManager;

    private JTabbedPane tabbedPane;
    private JPanel coursePanel;
    private JPanel examPanel;

    public StudentWindow(String studentId, ExamManager examManager, StudentManager studentManager) {
        this.studentId = studentId;
        this.examManager = examManager;
        this.studentManager = studentManager;

        initUI();
        loadStudentData();
    }

    private void initUI() {
        setTitle("学生系统 - " + studentId);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 获取学生信息
        Student student = studentManager.findStudentById(studentId);
        String studentName = student != null ? student.getName() : "未知";

        // 顶部信息栏
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel infoLabel = new JLabel("学生: " + studentName + " (" + studentId + ")");
        infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        infoPanel.add(infoLabel);

        // 选项卡
        tabbedPane = new JTabbedPane();

        // 选课面板
        coursePanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("我的选课", coursePanel);

        // 考试安排面板
        examPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("考试安排", examPanel);

        // 布局
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void loadStudentData() {
        loadCourseData();
        loadExamData();
    }

    private void loadCourseData() {
        coursePanel.removeAll();

        // 获取学生已选课程
        List<Subject> enrolledSubjects = studentManager.getStudentSubjects(studentId);
        List<Subject> allSubjects = examManager.getAllSubjects();

        // 已选课程面板
        JPanel enrolledPanel = new JPanel();
        enrolledPanel.setLayout(new BoxLayout(enrolledPanel, BoxLayout.Y_AXIS));
        enrolledPanel.setBorder(BorderFactory.createTitledBorder("已选课程"));

        if (enrolledSubjects.isEmpty()) {
            enrolledPanel.add(new JLabel("暂无选课"));
        } else {
            for (Subject subject : enrolledSubjects) {
                JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                subjectPanel.add(new JLabel(subject.getName() + " (" + subject.getSubjectId() + ") - " + subject.getCredit() + "学分"));

                JButton dropButton = new JButton("退课");
                dropButton.addActionListener(e -> dropSubject(subject));
                subjectPanel.add(dropButton);

                enrolledPanel.add(subjectPanel);
            }
        }

        // 可选课程面板
        JPanel availablePanel = new JPanel();
        availablePanel.setLayout(new BoxLayout(availablePanel, BoxLayout.Y_AXIS));
        availablePanel.setBorder(BorderFactory.createTitledBorder("可选课程"));

        // 找出未选的课程
        for (Subject subject : allSubjects) {
            if (!enrolledSubjects.contains(subject)) {
                JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                subjectPanel.add(new JLabel(subject.getName() + " (" + subject.getSubjectId() + ") - " + subject.getCredit() + "学分"));

                JButton enrollButton = new JButton("选课");
                enrollButton.addActionListener(e -> enrollSubject(subject));
                subjectPanel.add(enrollButton);

                availablePanel.add(subjectPanel);
            }
        }

        if (allSubjects.size() == enrolledSubjects.size()) {
            availablePanel.add(new JLabel("所有课程已选"));
        }

        // 滚动面板
        JScrollPane scrollPane = new JScrollPane();
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.add(enrolledPanel);
        containerPanel.add(Box.createVerticalStrut(20));
        containerPanel.add(availablePanel);

        scrollPane.setViewportView(containerPanel);
        coursePanel.add(scrollPane, BorderLayout.CENTER);

        coursePanel.revalidate();
        coursePanel.repaint();
    }

    private void loadExamData() {
        examPanel.removeAll();

        // 获取学生考试安排
        List<ExamSchedule> exams = studentManager.getStudentExamSchedule(studentId, examManager);

        JPanel examListPanel = new JPanel();
        examListPanel.setLayout(new BoxLayout(examListPanel, BoxLayout.Y_AXIS));

        if (exams.isEmpty()) {
            examListPanel.add(new JLabel("暂无考试安排"));
        } else {
            for (ExamSchedule exam : exams) {
                JPanel examPanelItem = new JPanel(new FlowLayout(FlowLayout.LEFT));

                String examInfo = exam.getSubject().getName() + " - " +
                        exam.getExamTime().toLocalDate() + " " +
                        exam.getExamTime().toLocalTime() + " - " +
                        exam.getClassroom().getLocation() + " - " +
                        "监考: " + exam.getInvigilator().getName();

                examPanelItem.add(new JLabel(examInfo));
                examListPanel.add(examPanelItem);
                examListPanel.add(Box.createVerticalStrut(5));
            }
        }

        JScrollPane scrollPane = new JScrollPane(examListPanel);
        examPanel.add(scrollPane, BorderLayout.CENTER);

        examPanel.revalidate();
        examPanel.repaint();
    }

    private void enrollSubject(Subject subject) {
        boolean success = studentManager.enrollSubject(studentId, subject);
        if (success) {
            JOptionPane.showMessageDialog(this, "选课成功: " + subject.getName());
            loadCourseData();
            loadExamData(); // 重新加载考试安排
        } else {
            JOptionPane.showMessageDialog(this, "选课失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dropSubject(Subject subject) {
        boolean success = studentManager.dropSubject(studentId, subject);
        if (success) {
            JOptionPane.showMessageDialog(this, "退课成功: " + subject.getName());
            loadCourseData();
            loadExamData(); // 重新加载考试安排
        } else {
            JOptionPane.showMessageDialog(this, "退课失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
