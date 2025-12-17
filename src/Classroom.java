public class Classroom {
    private String roomId;
    private String location;
    private int capacity;

    public Classroom(String roomId, String location, int capacity) {
        this.roomId = roomId;
        this.location = location;
        this.capacity = capacity;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean canAccommodate(int studentCount) {
        return studentCount <= capacity;
    }

    public String getClassroomInfo() {
        return "教室ID：" + roomId + "，位置：" + location + "，容量：" + capacity + "人";
    }

    public void displayInfo() {
        System.out.println(getClassroomInfo());
    }

    @Override
    public String toString() {
        return getClassroomInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Classroom classroom = (Classroom)obj;
        return roomId.equals(classroom.roomId);
    }

    @Override
    public int hashCode() {
        return roomId.hashCode();
    }
}
