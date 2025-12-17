public class Teacher {
    //教师属性
    private String teacherId;
    private String name;
    private String department;

    public Teacher(String teacherId, String name, String department) {
        this.teacherId = teacherId;
        this.name = name;
        this.department = department;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTeacherInfo() {
        return "教师ID：" + teacherId + "，姓名：" + name + "，学院：" + department;
    }

    public void displayInfo() {
        System.out.println(getTeacherInfo());
    }

    @Override
    public String toString() {
        return getTeacherInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Teacher teacher = (Teacher)obj;
        return teacherId.equals(teacher.teacherId);
    }

    @Override
    public int hashCode() {
        return teacherId.hashCode();
    }
}
