import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileDataLoader {
    public List<Teacher> loadTeachers(String filePath) {
        List<String> lines = readFile(filePath);
        List<Teacher> teachers = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(" ");
            if (parts.length >= 3) {
                String id = parts[0];
                String name = parts[1];
                String department = parts[2];
                Teacher teacher = new Teacher(id, name, department);
                teachers.add(teacher);
            }
        }

        return teachers;
    }

    public List<Subject> loadSubjects(String filePath) {
        List<String> lines = readFile(filePath);
        List<Subject> subjects = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(" ");
            if (parts.length >= 3) {
                String id = parts[0];
                String name = parts[1];
                int credit = Integer.parseInt(parts[2]);
                Subject subject = new Subject(id, name, credit);
                subjects.add(subject);
            }
        }
        return subjects;
    }

    public List<Classroom> loadClassrooms(String filePath) {
        List<String> lines = readFile(filePath);
        List<Classroom> classrooms = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(" ");
            if (parts.length >= 3) {
                String id = parts[0];
                String location = parts[1];
                int capacity = Integer.parseInt(parts[2]);
                Classroom classroom = new Classroom(id, location, capacity);
                classrooms.add(classroom);
            }
        }

        return classrooms;
    }

    public List<Student> loadStudents(String filePath, ExamManager examManager) {
        List<String> lines = readFile(filePath);
        List<Student> students = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(" ");
            if (parts.length >= 2) {
                String studentId = parts[0];
                String name = parts[1];
                Student student = new Student(studentId, name);

                for (int i = 2; i < parts.length; i++) {
                    String subjectId = parts[i];
                    Subject subject = examManager.findSubjectById(subjectId);
                    if (subject != null) {
                        student.enrollSubject(subject);
                    }
                }
                students.add(student);
            }
        }

        return students;
    }

    public List<ExamSchedule> loadExams(String filePath, ExamManager examManager) {
        List<String> lines = readFile(filePath);
        List<ExamSchedule> exams = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(" ");
            if (parts.length >= 6) {
                String examId = parts[0];
                String subjectId = parts[1];
                String timeStr = parts[2];
                String classroomId = parts[3];
                String teacherId = parts[4];
                int duration = Integer.parseInt(parts[5]);

                Subject subject = examManager.findSubjectById(subjectId);
                Classroom classroom = examManager.findClassroomById(classroomId);
                Teacher teacher = examManager.findTeacherById(teacherId);

                if (subject != null && classroom != null && teacher != null) {
                    LocalDateTime examTime = LocalDateTime.parse(timeStr);
                    ExamSchedule exam = new  ExamSchedule(examId, subject, examTime, classroom, teacher, duration);
                    exams.add(exam);
                }
            }
        }

        return exams;
    }

    public List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        }
        catch (Exception e) {
            // 保证文件一定存在
        }

        return lines;
    }
}
