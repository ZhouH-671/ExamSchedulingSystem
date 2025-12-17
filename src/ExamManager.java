import java.util.ArrayList;
import java.util.List;

public class ExamManager {
    private List<Teacher> teachers;
    private List<Subject> subjects;
    private List<Classroom> classrooms;
    private List<ExamSchedule> examSchedules;

    public ExamManager() {
        teachers = new ArrayList<>();
        subjects = new ArrayList<>();
        classrooms = new ArrayList<>();
        examSchedules = new ArrayList<>();
        System.out.println("考试管理器已初始化完毕");
    }

    public boolean addTeacher(Teacher teacher) {
        if (teacher == null) {
            System.out.println("错误：教师不能为空!");
            return false;
        }

        for (Teacher t : teachers) {
            if (t.getTeacherId().equals(teacher.getTeacherId())) {
                System.out.println("错误：教师ID " + teacher.getTeacherId() + "已存在！");
                return false;
            }
        }

        teachers.add(teacher);
        System.out.println("成功添加教师：" + teacher.getName());
        return true;
    }

    public Teacher findTeacherById(String teacherId) {
        for (Teacher teacher : teachers) {
            if (teacher.getTeacherId().equals(teacherId)) {
                return teacher;
            }
        }
        System.out.println("提示：未找到ID为" + teacherId + "的教师");
        return null;
    }

    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers);
    }

    public boolean addSubject(Subject subject) {
        if (subject == null) {
            System.out.println("错误！科目不能为空！");
            return false;
        }

        for (Subject s : subjects) {
            if (s.getSubjectId().equals(subject.getSubjectId())) {
                System.out.println("错误！科目ID" + subject.getSubjectId() + "已存在！");
                return false;
            }
        }

        subjects.add(subject);
        System.out.println("成功添加科目：" + subject.getName());
        return true;
    }

    public Subject findSubjectById(String subjectId) {
        for (Subject subject : subjects) {
            if (subject.getSubjectId().equals(subjectId)) {
                return subject;
            }
        }
        System.out.println("提示：未找到ID为" + subjectId + "的科目");
        return null;
    }

    public List<Subject> getAllSubjects() {
        return new ArrayList<>(subjects);
    }

    public boolean addClassroom(Classroom classroom) {
        if (classroom == null) {
            System.out.println("错误：教室不能为空！");
            return false;
        }

        for (Classroom c : classrooms) {
            if (c.getRoomId().equals(classroom.getRoomId())) {
                System.out.println("错误：教室ID" + classroom.getRoomId() + "已存在！");
                return false;
            }
        }

        classrooms.add(classroom);
        System.out.println("成功添加教室：" + classroom.getLocation());
        return true;
    }

    public Classroom findClassroomById(String roomId) {
        for (Classroom classroom : classrooms) {
            if (classroom.getRoomId().equals(roomId)) {
                return classroom;
            }
        }
        System.out.println("提示：未找到ID为" + roomId + "的教室");
        return null;
    }

    public List<Classroom> getAllClassrooms() {
        return new ArrayList<>(classrooms);
    }

    public boolean scheduleExam(ExamSchedule exam) {
        if (exam == null) {
            System.out.println("错误：考试安排不能为空！");
            return false;
        }

        for (ExamSchedule e : examSchedules) {
            if (e.getExamId().equals(exam.getExamId())) {
                System.out.println("错误：考试安排ID" + exam.getExamId() + "已存在！");
                return false;
            }
        }

        if (!validateExam(exam)) {
            return false;
        }

        examSchedules.add(exam);
        System.out.println("成功安排考试：" + exam.getSubject().getName());
        return true;
    }

    private boolean validateExam(ExamSchedule exam) {
        if (exam.getSubject() == null) {
            System.out.println("错误：考试科目不能为空！");
            return false;
        }
        if (exam.getClassroom() == null) {
            System.out.println("错误：考试教室不能为空！");
            return false;
        }
        if (exam.getInvigilator() == null) {
            System.out.println("错误：监考教师不能为空！");
            return false;
        }
        return true;
    }

    public ExamSchedule findExamById(String examId) {
        for (ExamSchedule exam : examSchedules) {
            if (exam.getExamId().equals(examId)) {
                return exam;
            }
        }

        System.out.println("提示：未找到ID为" + examId + "的考试安排");
        return null;
    }

    public List<ExamSchedule> findExamsBySubject(Subject subject) {
        List<ExamSchedule> result = new ArrayList<>();
        for (ExamSchedule exam : examSchedules) {
            if (exam.getSubject().equals(subject)) {
                result.add(exam);
            }
        }
        return result;
    }

    public List<ExamSchedule> findExamsByTeacher(Teacher teacher) {
        List<ExamSchedule> result = new ArrayList<>();
        for (ExamSchedule exam : examSchedules) {
            if (exam.getInvigilator().equals(teacher)) {
                result.add(exam);
            }
        }
        return result;
    }

    public List<ExamSchedule> getAllExams() {
        return new ArrayList<>(examSchedules);
    }

    public boolean deleteExam(String examId) {
        ExamSchedule examToRemove = findExamById(examId);
        if (examToRemove != null) {
            examSchedules.remove(examToRemove);
            System.out.println("成功删除考试安排: " + examId);
            return true;
        }
        return false;
    }

    public int getTeacherCount() {
        return teachers.size();
    }

    public int getSubjectCount() {
        return subjects.size();
    }

    public int getClassroomCount() {
        return classrooms.size();
    }

    public int getExamScheduleCount() {
        return examSchedules.size();
    }

    public void displayStatistics() {
        System.out.println("=== 系统统计信息 ===");
        System.out.println("教师数量: " + getTeacherCount());
        System.out.println("科目数量: " + getSubjectCount());
        System.out.println("教室数量: " + getClassroomCount());
        System.out.println("考试安排数量: " + getExamScheduleCount());
        System.out.println("===================");
    }

    private FileDataLoader dataLoader = new FileDataLoader();

    public void loadFromFiles(String dataDir) {
        List<Teacher> loadedTeachers = dataLoader.loadTeachers(dataDir + "/teachers.txt");
        teachers.clear();
        teachers.addAll(loadedTeachers);
        System.out.println("已从数据库中加载 " + teachers.size() + " 名教师");

        List<Subject> loadedSubjects = dataLoader.loadSubjects(dataDir + "/subjects.txt");
        subjects.clear();
        subjects.addAll(loadedSubjects);
        System.out.println("已从数据库中加载 " + subjects.size() + " 门课程");

        List<Classroom> loadedClassrooms = dataLoader.loadClassrooms(dataDir + "/classrooms.txt");
        classrooms.clear();
        classrooms.addAll(loadedClassrooms);
        System.out.println("已从数据库中加载 " + classrooms.size() + " 间教室");

        List<ExamSchedule> loadedExams = dataLoader.loadExams(dataDir + "/exams.txt", this);
        examSchedules.clear();
        examSchedules.addAll(loadedExams);
        System.out.println("已从数据库中加载 " + examSchedules.size() + " 场考试安排");
    }
}
