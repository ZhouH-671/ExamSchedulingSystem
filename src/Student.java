import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String name;
    private List<Subject> enrolledSubjects;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.enrolledSubjects = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getEnrolledSubjects() {
        return new ArrayList<>(enrolledSubjects);
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean enrollSubject(Subject subject) {
        if (subject == null) {
            System.out.println("错误！科目不能为空！");
            return false;
        }

        if (enrolledSubjects.contains(subject)) {
            System.out.println("提示：学生" + name + "已经选了科目" + subject.getName());
            return false;
        }

        enrolledSubjects.add(subject);
        System.out.println("成功：学生" + name + "选了科目" + subject.getName());
        return true;
    }

    public boolean dropSubject(Subject subject) {
        if (subject == null) {
            System.out.println("错误：退课科目不能为空!");
            return false;
        }

        boolean removed = enrolledSubjects.remove(subject);
        if (removed) {
            System.out.println("成功：学生" + name + "退选科目" + subject.getName());
        }
        else {
            System.out.println("提示：学生" + name + "未选择科目" + subject.getName());
        }
        return removed;
    }

    public boolean hasEnrolled(Subject subject) {
        return enrolledSubjects.contains(subject);
    }

    public int getEnrolledSubjectCount() {
        return enrolledSubjects.size();
    }

    public String getStudentInfo() {
        return "学号：" + studentId + "，姓名" + name;
    }

    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append("学号：").append(studentId)
                .append("，姓名：").append(name)
                .append("，已选科目数：").append(enrolledSubjects.size());
        if (!enrolledSubjects.isEmpty()) {
            info.append("，科目列表：");
            for (int i = 0; i < enrolledSubjects.size(); i++) {
                info.append(enrolledSubjects.get(i).getName());
                if (i < enrolledSubjects.size() - 1) {
                    info.append("，");
                }
            }
        }

        return info.toString();
    }

    public void displayInfo() {
        System.out.println(getStudentInfo());
    }

    public void displayDetailInfo() {
        System.out.println(getDetailedInfo());
    }

    @Override
    public String toString() {
        return getStudentInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Student student = (Student)obj;
        return studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }
}
