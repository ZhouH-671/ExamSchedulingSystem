import java.util.ArrayList;
import java.util.List;

public class InMemoryStorage {
    private List<Teacher> teachers;
    private List<Student> students;
    private List<Subject> subjects;
    private List<Classroom> classrooms;
    private List<ExamSchedule> examSchedules;

    public InMemoryStorage() {
        teachers = new ArrayList<>();
        students = new ArrayList<>();
        subjects = new ArrayList<>();
        classrooms = new ArrayList<>();
        examSchedules = new ArrayList<>();
        System.out.println("系统内存已初始化完毕！");
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers);
    }

    public Teacher getTeacherById(String teacherId) {
        for (Teacher teacher : teachers) {
            if (teacher.getTeacherId().equals(teacherId)) {
                return teacher;
            }
        }
        return null;
    }

    public boolean removeTeacher(String teacherId) {
        return teachers.removeIf(teacher -> teacher.getTeacherId().equals(teacherId));
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student getStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public boolean removeStudent(String studentId) {
        return students.removeIf(student -> student.getStudentId().equals(studentId));
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public List<Subject> getAllSubjects() {
        return new ArrayList<>(subjects);
    }

    public Subject getSubjectById(String subjectId) {
        for (Subject subject : subjects) {
            if (subject.getSubjectId().equals(subjectId)) {
                return subject;
            }
        }
        return null;
    }

    public boolean removeSubject(String subjectId) {
        return subjects.removeIf(subject -> subject.getSubjectId().equals(subjectId));
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }

    public List<Classroom> getAllClassrooms() {
        return new ArrayList<>(classrooms);
    }

    public Classroom getClassroomById(String roomId) {
        for (Classroom classroom : classrooms) {
            if (classroom.getRoomId().equals(roomId)) {
                return classroom;
            }
        }
        return null;
    }

    public boolean removeClassroom(String roomId) {
        return classrooms.removeIf(classroom -> classroom.getRoomId().equals(roomId));
    }

    public void addExamSchedule(ExamSchedule exam) {
        examSchedules.add(exam);
    }

    public List<ExamSchedule> getAllExamSchedules() {
        return new ArrayList<>(examSchedules);
    }

    public ExamSchedule getExamScheduleById(String examId) {
        for (ExamSchedule exam : examSchedules) {
            if (exam.getExamId().equals(examId)) {
                return exam;
            }
        }
        return null;
    }

    public boolean removeExamSchedule(String examId) {
        return examSchedules.removeIf(exam -> exam.getExamId().equals(examId));
    }

    public void displayAllStatistics() {
        System.out.println("=== 存储数据统计 ===");
        System.out.println("教师数量: " + teachers.size());
        System.out.println("学生数量: " + students.size());
        System.out.println("科目数量: " + subjects.size());
        System.out.println("教室数量: " + classrooms.size());
        System.out.println("考试安排数量: " + examSchedules.size());
        System.out.println("===================");
    }

    public void clearAllData() {
        teachers.clear();
        students.clear();
        subjects.clear();
        classrooms.clear();
        examSchedules.clear();
        System.out.println("所有数据已清空！");
    }
}