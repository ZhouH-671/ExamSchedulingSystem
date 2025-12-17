import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private ExamManager examManager;
    private StudentManager studentManager;
    private Scanner scanner;
    private DateTimeFormatter dateTimeFormatter;

    public ConsoleUI(ExamManager examManager, StudentManager studentManager) {
        this.examManager = examManager;
        this.studentManager = studentManager;
        this.scanner = new Scanner(System.in);
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("控制台界面已初始化完毕！");
    }

    public void start() {
        System.out.println("\n=== 欢迎来到小h开发的考试安排系统！现在是版本v1.0 ===");
        System.out.println("欢迎使用考试安排系统！开发者欢迎您在使用后提出宝贵的意见！您的支持是我前进的最大动力！");

        boolean isRunning = true;
        while (isRunning) {
            showMainMenu();
            int choice = getIntInput("请选择操作：");

            switch(choice) {
                case 1:
                    manageTeachers();
                    break;
                case 2:
                    manageSubjects();
                    break;
                case 3:
                    manageClassrooms();
                    break;
                case 4:
                    manageExams();
                    break;
                case 5:
                    manageStudents();
                    break;
                case 6:
                    viewAllData();
                    break;
                case 7:
                    examManager.displayStatistics();
                    studentManager.displayStatistics();
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("感谢使用，请多多支持！");
                    break;
                default:
                    System.out.println("无效选择，请重新选择！");
            }

            if (isRunning) {
                pressAnyKeyToContinue();
            }
        }

        scanner.close();
    }

    private void showMainMenu() {
        System.out.println("\n=== 主菜单 ===");
        System.out.println("1. 教师管理");
        System.out.println("2. 科目管理");
        System.out.println("3. 教室管理");
        System.out.println("4. 考试安排管理");
        System.out.println("5. 学生管理");
        System.out.println("6. 查看所有数据");
        System.out.println("7. 系统统计");
        System.out.println("0. 退出系统");
        System.out.println("=============");
    }

    private void manageTeachers() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== 教师管理 ===");
            System.out.println("1. 添加教师");
            System.out.println("2. 查看所有教师");
            System.out.println("3. 查找教师");
            System.out.println("4. 返回主菜单");

            int choice = getIntInput("请选择: ");

            switch (choice) {
                case 1:
                    addTeacher();
                    break;
                case 2:
                    viewAllTeachers();
                    break;
                case 3:
                    findTeacher();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("无效选择！");
            }
        }
    }

    private void addTeacher() {
        System.out.println("\n--- 添加教师 ---");
        String teacherId = getStringInput("教师ID: ");
        String name = getStringInput("姓名: ");
        String department = getStringInput("部门: ");

        Teacher teacher = new Teacher(teacherId, name, department);
        if (examManager.addTeacher(teacher)) {
            System.out.println("教师添加成功！");
        }
    }

    private void viewAllTeachers() {
        System.out.println("\n--- 所有教师列表 ---");
        List<Teacher> teachers = examManager.getAllTeachers();

        if (teachers.isEmpty()) {
            System.out.println("暂无教师信息");
        } else {
            for (int i = 0; i < teachers.size(); i++) {
                System.out.println((i + 1) + ". " + teachers.get(i).getTeacherInfo());
            }
        }
    }

    private void findTeacher() {
        String teacherId = getStringInput("请输入教师ID: ");
        Teacher teacher = examManager.findTeacherById(teacherId);

        if (teacher != null) {
            System.out.println("找到教师: " + teacher.getTeacherInfo());
        } else {
            System.out.println("未找到该教师");
        }
    }

    private void manageSubjects() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== 科目管理 ===");
            System.out.println("1. 添加科目");
            System.out.println("2. 查看所有科目");
            System.out.println("3. 返回主菜单");

            int choice = getIntInput("请选择: ");

            switch (choice) {
                case 1:
                    addSubject();
                    break;
                case 2:
                    viewAllSubjects();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("无效选择！");
            }
        }
    }

    private void addSubject() {
        System.out.println("\n--- 添加科目 ---");
        String subjectId = getStringInput("科目ID: ");
        String name = getStringInput("科目名称: ");
        int credit = getIntInput("学分: ");

        Subject subject = new Subject(subjectId, name, credit);
        if (examManager.addSubject(subject)) {
            System.out.println("科目添加成功！");
        }
    }

    private void viewAllSubjects() {
        System.out.println("\n--- 所有科目列表 ---");
        List<Subject> subjects = examManager.getAllSubjects();

        if (subjects.isEmpty()) {
            System.out.println("暂无科目信息");
        } else {
            for (int i = 0; i < subjects.size(); i++) {
                System.out.println((i + 1) + ". " + subjects.get(i).getSubjectInfo());
            }
        }
    }

    private void manageClassrooms() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== 教室管理 ===");
            System.out.println("1. 添加教室");
            System.out.println("2. 查看所有教室");
            System.out.println("3. 返回主菜单");

            int choice = getIntInput("请选择: ");

            switch (choice) {
                case 1:
                    addClassroom();
                    break;
                case 2:
                    viewAllClassrooms();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("无效选择！");
            }
        }
    }

    private void addClassroom() {
        System.out.println("\n--- 添加教室 ---");
        String roomId = getStringInput("教室ID: ");
        String location = getStringInput("位置: ");
        int capacity = getIntInput("容量: ");

        Classroom classroom = new Classroom(roomId, location, capacity);
        if (examManager.addClassroom(classroom)) {
            System.out.println("教室添加成功！");
        }
    }

    private void viewAllClassrooms() {
        System.out.println("\n--- 所有教室列表 ---");
        List<Classroom> classrooms = examManager.getAllClassrooms();

        if (classrooms.isEmpty()) {
            System.out.println("暂无教室信息");
        } else {
            for (int i = 0; i < classrooms.size(); i++) {
                System.out.println((i + 1) + ". " + classrooms.get(i).getClassroomInfo());
            }
        }
    }

    private void manageExams() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== 考试安排管理 ===");
            System.out.println("1. 安排考试");
            System.out.println("2. 查看所有考试安排");
            System.out.println("3. 按科目查看考试");
            System.out.println("4. 按教师查看监考安排");
            System.out.println("5. 删除考试安排");
            System.out.println("6. 返回主菜单");

            int choice = getIntInput("请选择: ");

            switch (choice) {
                case 1:
                    scheduleExam();
                    break;
                case 2:
                    viewAllExams();
                    break;
                case 3:
                    viewExamsBySubject();
                    break;
                case 4:
                    viewExamsByTeacher();
                    break;
                case 5:
                    deleteExam();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("无效选择！");
            }
        }
    }

    private void scheduleExam() {
        System.out.println("\n--- 安排考试 ---");

        // 显示可用资源
        System.out.println("可用科目:");
        viewAllSubjects();
        System.out.println("\n可用教室:");
        viewAllClassrooms();
        System.out.println("\n可用教师:");
        viewAllTeachers();

        // 获取输入
        String examId = getStringInput("考试安排ID: ");

        String subjectId = getStringInput("科目ID: ");
        Subject subject = examManager.findSubjectById(subjectId);
        if (subject == null) {
            System.out.println("错误：科目不存在！");
            return;
        }

        LocalDateTime examTime = getDateTimeInput("考试时间 (格式: yyyy-MM-dd HH:mm): ");
        if (examTime == null) return;

        String roomId = getStringInput("教室ID: ");
        Classroom classroom = examManager.findClassroomById(roomId);
        if (classroom == null) {
            System.out.println("错误：教室不存在！");
            return;
        }

        String teacherId = getStringInput("监考教师ID: ");
        Teacher teacher = examManager.findTeacherById(teacherId);
        if (teacher == null) {
            System.out.println("错误：教师不存在！");
            return;
        }

        int duration = getIntInput("考试时长(分钟): ");

        // 创建考试安排
        ExamSchedule exam = new ExamSchedule(examId, subject, examTime, classroom, teacher, duration);
        if (examManager.scheduleExam(exam)) {
            System.out.println("考试安排成功！");
        }
    }

    private void viewAllExams() {
        System.out.println("\n--- 所有考试安排 ---");
        List<ExamSchedule> exams = examManager.getAllExams();

        if (exams.isEmpty()) {
            System.out.println("暂无考试安排");
        } else {
            for (int i = 0; i < exams.size(); i++) {
                ExamSchedule exam = exams.get(i);
                System.out.println((i + 1) + ". " + exam.getExamInfo());
            }
        }
    }

    private void viewExamsBySubject() {
        String subjectId = getStringInput("请输入科目ID: ");
        Subject subject = examManager.findSubjectById(subjectId);

        if (subject != null) {
            List<ExamSchedule> exams = examManager.findExamsBySubject(subject);
            System.out.println("\n科目 \"" + subject.getName() + "\" 的考试安排:");

            if (exams.isEmpty()) {
                System.out.println("暂无考试安排");
            } else {
                for (int i = 0; i < exams.size(); i++) {
                    System.out.println((i + 1) + ". " + exams.get(i).getSimpleInfo());
                }
            }
        } else {
            System.out.println("科目不存在！");
        }
    }

    private void viewExamsByTeacher() {
        String teacherId = getStringInput("请输入教师ID: ");
        Teacher teacher = examManager.findTeacherById(teacherId);

        if (teacher != null) {
            List<ExamSchedule> exams = examManager.findExamsByTeacher(teacher);
            System.out.println("\n教师 \"" + teacher.getName() + "\" 的监考安排:");

            if (exams.isEmpty()) {
                System.out.println("暂无监考安排");
            } else {
                for (int i = 0; i < exams.size(); i++) {
                    System.out.println((i + 1) + ". " + exams.get(i).getSimpleInfo());
                }
            }
        } else {
            System.out.println("教师不存在！");
        }
    }

    private void deleteExam() {
        String examId = getStringInput("请输入要删除的考试安排ID: ");
        if (examManager.deleteExam(examId)) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败，考试安排不存在！");
        }
    }

    private void manageStudents() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== 学生管理 ===");
            System.out.println("1. 添加学生");
            System.out.println("2. 查看所有学生");
            System.out.println("3. 学生选课");
            System.out.println("4. 查看学生选课情况");
            System.out.println("5. 查看学生考试安排");
            System.out.println("6. 删除学生");
            System.out.println("7. 返回主菜单");

            int choice = getIntInput("请选择: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    manageStudentEnrollment();
                    break;
                case 4:
                    viewStudentSubjects();
                    break;
                case 5:
                    viewStudentExamSchedule();
                    break;
                case 6:
                    deleteStudent();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("无效选择！");
            }
        }
    }

    private void addStudent() {
        System.out.println("\n--- 添加学生 ---");
        String studentId = getStringInput("学号: ");
        String name = getStringInput("姓名: ");

        Student student = new Student(studentId, name);
        if (studentManager.addStudent(student)) {
            System.out.println("学生添加成功！");
        }
    }

    private void viewAllStudents() {
        System.out.println("\n--- 所有学生列表 ---");
        List<Student> students = studentManager.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("暂无学生信息");
        } else {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                System.out.println((i + 1) + ". " + student.getDetailedInfo());
            }
        }
    }

    private void manageStudentEnrollment() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== 学生选课管理 ===");
            System.out.println("1. 学生选课");
            System.out.println("2. 学生退课");
            System.out.println("3. 返回学生菜单");

            int choice = getIntInput("请选择: ");

            switch (choice) {
                case 1:
                    enrollStudentInSubject();
                    break;
                case 2:
                    dropStudentFromSubject();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("无效选择！");
            }
        }
    }

    private void enrollStudentInSubject() {
        System.out.println("\n--- 学生选课 ---");
        String studentId = getStringInput("学号: ");
        String subjectId = getStringInput("科目ID: ");

        Student student = studentManager.findStudentById(studentId);
        Subject subject = examManager.findSubjectById(subjectId);

        if (student == null) {
            System.out.println("错误：学生不存在！");
            return;
        }

        if (subject == null) {
            System.out.println("错误：科目不存在！");
            return;
        }

        if (studentManager.enrollSubject(studentId, subject)) {
            System.out.println("选课成功！");
        }
    }

    private void dropStudentFromSubject() {
        System.out.println("\n--- 学生退课 ---");
        String studentId = getStringInput("学号: ");
        String subjectId = getStringInput("科目ID: ");

        Student student = studentManager.findStudentById(studentId);
        Subject subject = examManager.findSubjectById(subjectId);

        if (student == null) {
            System.out.println("错误：学生不存在！");
            return;
        }

        if (subject == null) {
            System.out.println("错误：科目不存在！");
            return;
        }

        if (studentManager.dropSubject(studentId, subject)) {
            System.out.println("退课成功！");
        }
    }

    private void viewStudentSubjects() {
        String studentId = getStringInput("请输入学号: ");
        Student student = studentManager.findStudentById(studentId);

        if (student != null) {
            List<Subject> subjects = studentManager.getStudentSubjects(studentId);
            System.out.println("\n学生 \"" + student.getName() + "\" 的选课情况:");
            System.out.println("已选科目数: " + subjects.size());

            if (subjects.isEmpty()) {
                System.out.println("暂无选课");
            } else {
                for (int i = 0; i < subjects.size(); i++) {
                    System.out.println((i + 1) + ". " + subjects.get(i).getName() +
                            " (学分: " + subjects.get(i).getCredit() + ")");
                }
            }
        } else {
            System.out.println("学生不存在！");
        }
    }

    private void viewStudentExamSchedule() {
        String studentId = getStringInput("请输入学号: ");
        studentManager.displayStudentExamSchedule(studentId, examManager);
    }

    private void deleteStudent() {
        String studentId = getStringInput("请输入要删除的学号: ");
        if (studentManager.deleteStudent(studentId)) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败，学生不存在！");
        }
    }

    private void viewAllData() {
        System.out.println("\n=== 所有数据概览 ===");

        System.out.println("\n--- 教师信息 ---");
        viewAllTeachers();

        System.out.println("\n--- 科目信息 ---");
        viewAllSubjects();

        System.out.println("\n--- 教室信息 ---");
        viewAllClassrooms();

        System.out.println("\n--- 学生信息 ---");
        viewAllStudents();

        System.out.println("\n--- 考试安排 ---");
        viewAllExams();
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字！");
            }
        }
    }

    private LocalDateTime getDateTimeInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("时间不能为空！");
                    continue;
                }
                return LocalDateTime.parse(input, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("时间格式错误！请使用 yyyy-MM-dd HH:mm 格式，例如: 2024-01-15 09:00");
            }
        }
    }

    private void pressAnyKeyToContinue() {
        System.out.print("\n按Enter键继续...");
        scanner.nextLine();
    }
}