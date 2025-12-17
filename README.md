# 关于《学生选课与考试管理系统》开发的说明

24371313 周鸿 

作为我的《Java编程思想》的结课大作业，这是一个学生选课管理和考试安排的系统，基于Java3.24版本实现。这个项目能够满足的需求包括**教师信息管理，学生信息管理，学生选课管理，考试安排管理**等。

整个项目经过了3次迭代。第一版实现了一个基础的信息管理系统，已经具备全部的功能。第一版的项目结构如下：

```text
examSchedulingSystem/     # 项目文件夹
├── src/                   # 所有源代码
│   ├── Main.java
│   ├── Teacher.java
│   ├── Student.java
│   ├── Subject.java
│   ├── Classroom.java
│   ├── ExamSchedule.java
│   ├── ExamManager.java
│   ├── StudentManager.java
│   ├── InMemoryStorage.java
│   └── ConsoleUI.java
└── README.txt            # 简单说明
```

