public class Subject {
    private String subjectId;
    private String name;
    private int credit;

    public Subject(String subjectId, String name, int credit) {
        this.subjectId = subjectId;
        this.name = name;
        this.credit = credit;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getSubjectInfo() {
        return "科目ID：" + subjectId + "，名称：" + name + "，学分：" + credit;
    }

    public void displayInfo() {
        System.out.println(getSubjectInfo());
    }

    @Override
    public String toString() {
        return getSubjectInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Subject subject = (Subject)obj;
        return subjectId.equals(subject.subjectId);
    }

    @Override
    public int hashCode() {
        return subjectId.hashCode();
    }
}
