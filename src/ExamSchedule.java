import java.time.LocalDateTime;

public class ExamSchedule {
    private String examId;
    private Subject subject;
    private LocalDateTime examTime;
    private Classroom classroom;
    private Teacher invigilator;
    private int duration;

    public ExamSchedule(String examId, Subject subject, LocalDateTime examTime,
                        Classroom classroom, Teacher invigilator, int duration) {
        this.examId = examId;
        this.subject = subject;
        this.examTime = examTime;
        this.classroom = classroom;
        this.invigilator = invigilator;
        this.duration = duration;
    }

    public String getExamId() {
        return examId;
    }

    public Subject getSubject() {
        return subject;
    }

    public LocalDateTime getExamTime() {
        return examTime;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Teacher getInvigilator() {
        return invigilator;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return examTime.plusMinutes(duration);
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setExamTime(LocalDateTime examTime) {
        this.examTime = examTime;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setInvigilator(Teacher invigilator) {
        this.invigilator = invigilator;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getExamInfo() {
        return "考试ID：" + examId +
                "，科目：" + subject.getName() +
                "，时间：" + examTime +
                "，教室：" + classroom.getLocation() +
                "，监考：" + invigilator.getName() +
                "，时长：" + duration + "分钟";
    }

    public String getSimpleInfo() {
        return subject.getName() + " - " +
                examTime.toLocalTime() + " - " +
                examTime.toLocalTime() + " - " +
                classroom.getLocation();
    }

    public void displayInfo() {
        System.out.println(getExamInfo());
    }

    public boolean isWithinTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return !examTime.isBefore(startTime) && !getEndTime().isAfter(endTime);
    }

    @Override
    public String toString() {
        return getSimpleInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ExamSchedule exam = (ExamSchedule) obj;
        return examId.equals(exam.examId);
    }

    @Override
    public int hashCode() {
        return examId.hashCode();
    }
}
