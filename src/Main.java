import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("小h开发的考试安排系统 v3.0");
        System.out.println("========================================\n");

        if (args.length > 0 && args[0].equals("--console")) {
            // 直接进入控制台模式
            startConsoleMode();
            return;
        }
        if (args.length > 0 && args[0].equals("--gui")) {
            // 直接进入GUI模式
            startGuiMode();
            return;
        }

        // 显示模式选择菜单
        showModeSelection();
    }

    private static void showModeSelection() {
        System.out.println("请选择启动模式：");
        System.out.println("1. 控制台模式（功能完整，适合管理员）");
        System.out.println("2. 图形界面模式（三视图，适合不同用户）");
        System.out.println("3. 退出系统");
        System.out.print("\n请选择 (1/2/3): ");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    startConsoleMode();
                    scanner.close();
                    return;
                case "2":
                    startGuiMode();
                    scanner.close();
                    return;
                case "3":
                    System.out.println("\n感谢使用，再见！");
                    scanner.close();
                    System.exit(0);
                    return;
                default:
                    System.out.print("输入无效，请选择 1、2 或 3: ");
            }
        }
    }

    private static void startConsoleMode() {
        System.out.println("\n=== 启动控制台模式 ===");
        System.out.println("提示：控制台模式包含完整的管理功能");
        System.out.println("可以使用所有增删改查操作\n");

        // 初始化系统组件
        ExamManager examManager = new ExamManager();
        StudentManager studentManager = new StudentManager();

        // 从数据文件中加载数据
        loadDataFromFiles(examManager, studentManager);

        // 启动控制台界面
        ConsoleUI consoleUI = new ConsoleUI(examManager, studentManager);
        consoleUI.start();
    }

    private static void startGuiMode() {
        System.out.println("\n=== 启动图形界面模式 ===");
        System.out.println("图形界面分为学生、教师、管理员三种视图");
        System.out.println("请根据身份选择登录方式\n");

        // 设置Swing外观为系统默认
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // 使用默认外观
        }

        // 初始化系统组件
        ExamManager examManager = new ExamManager();
        StudentManager studentManager = new StudentManager();

        // 从数据文件中加载数据
        loadDataFromFiles(examManager, studentManager);

        // 启动登录窗口
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow(examManager, studentManager);
            loginWindow.setVisible(true);
        });
    }

    private static void loadDataFromFiles(ExamManager examManager, StudentManager studentManager) {
        System.out.println("=== 开始从数据库中加载数据 ===");

        examManager.loadFromFiles("data");

        studentManager.loadFromFiles("data", examManager);

        System.out.println("=== 数据加载完成！===\n");
    }
}