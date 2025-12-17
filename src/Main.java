public class Main {

    public static void main(String[] args) {
        // 初始化
        ExamManager examManager = new ExamManager();
        StudentManager studentManager = new StudentManager();

        // 从数据文件中加载数据
        loadDataFromFiles(examManager, studentManager);

        // 启动用户界面
        ConsoleUI consoleUI = new ConsoleUI(examManager, studentManager);
        consoleUI.start();
    }

    private static void loadDataFromFiles(ExamManager examManager, StudentManager studentManager) {
        System.out.println("=== 开始从数据库中加载数据 ===");

        examManager.loadFromFiles("data");

        studentManager.loadFromFiles("data", examManager);

        System.out.println("=== 数据加载完成！===\n");
    }
}