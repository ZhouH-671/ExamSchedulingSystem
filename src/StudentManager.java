import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
        System.out.println("学生管理系统已初始化完毕！");
    }

    public boolean addStudent(Student student) {
        if (student == null) {
            System.out.println("错误:学生不能为空！");
            return false;
        }

        if (findStudentById(student.getStudentId()) != null) {
            System.out.println("错误：学号" + student.getStudentId() + "已存在！");
            return false;
        }

        students.add(student);
        System.out.println("成功添加学生" + student.getName());
        return true;
    }

    public Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> findStudentByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().equals(name)) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public boolean deleteStudent(String studentId) {
        Student studentToRemove = findStudentById(studentId);
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            System.out.println("成功删除学生：" + studentId);
            return true;
        }
        System.out.println("错误：找不到学号为" + studentId + "的学生");
        return false;
    }

    public boolean updateStudent(String studentId, String newName) {
        Student student = findStudentById(studentId);
        if (student != null) {
            String oldName = student.getName();
            student.setName(newName);
            System.out.println("成功更新学生的信息：" + oldName + "->" + newName);
            return true;
        }
        System.out.println("错误：找不到学号为" + studentId + "的学生");
        return false;
    }

    public boolean enrollSubject(String studentId, Subject subject) {
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("错误：找不到学号为" + studentId + "的学生");
            return false;
        }

        if (subject == null) {
            System.out.println("错误：科目不能为空！");
            return false;
        }

        return student.enrollSubject(subject);
    }

    public boolean dropSubject(String studentId, Subject subject) {
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("错误：找不到学号为" + studentId + "的学生");
            return false;
        }

        if (subject == null) {
            System.out.println("错误：科目不能为空！");
            return false;
        }

        return student.dropSubject(subject);
    }

    public List<Subject> getStudentSubjects(String studentId) {
        Student student = findStudentById(studentId);
        if (student != null) {
            return student.getEnrolledSubjects();
        }
        return new ArrayList<>();
    }

    public boolean hasStudentEnrolled(String studentId, Subject subject) {
        Student student = findStudentById(studentId);
        if (student != null) {
            return student.hasEnrolled(subject);
        }
        return false;
    }

    public List<ExamSchedule> getStudentExamSchedule(String studentId, ExamManager examManager) {
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("错误：找不到学号为" + studentId + "的学生");
            return new ArrayList<>();
        }

        List<ExamSchedule> studentExams = new ArrayList<>();
        List<Subject> enrolledSubjects = student.getEnrolledSubjects();

        for (ExamSchedule exam : examManager.getAllExams()) {
            if (enrolledSubjects.contains(exam.getSubject())) {
                studentExams.add(exam);
            }
        }

        return studentExams;
    }

    public void displayStudentExamSchedule(String studentId, ExamManager examManager) {
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("错误：找不到学号" + studentId + "的学生");
            return;
        }

        System.out.println("=== 学生考试安排 ===");
        System.out.println("学生：" + student.getName() + " (" + studentId + " )");
        System.out.println("已选科目数：" + student.getEnrolledSubjectCount());

        List<ExamSchedule> exams = getStudentExamSchedule(studentId, examManager);

        if (exams.isEmpty()) {
            System.out.println("暂无考试安排");
        }
        else {
            System.out.println("考试安排列表：");
            for (int i = 0; i < exams.size(); i++) {
                ExamSchedule exam = exams.get(i);
                System.out.println((i + 1) + ". " + exam.getSimpleInfo());
            }
        }
        System.out.println("================================");
    }

    public int getStudentCount() {
        return students.size();
    }

    public void displayStatistics() {
        System.out.println("=== 学生统计信息 ===");
        System.out.println("学生总数：" + getStudentCount());

        int totalEnrollments = 0;
        for (Student student : students) {
            totalEnrollments += student.getEnrolledSubjectCount();
        }

        double avgSubjects = students.isEmpty() ? 0 : (double) totalEnrollments / students.size();
        System.out.println("总选课人次：" + totalEnrollments);
        System.out.printf("平均选课人次：%.2f\n", avgSubjects);
        System.out.println("================================");
    }

    private FileDataLoader dataLoader = new FileDataLoader();

    public void loadFromFiles(String dataDir, ExamManager examManager) {
        List<Student> loadedStudents = dataLoader.loadStudents(
                dataDir + "/students.txt", examManager);
        students.clear();
        students.addAll(loadedStudents);
        System.out.println("已从数据库中加载 " + students.size() + " 名学生");
    }
}