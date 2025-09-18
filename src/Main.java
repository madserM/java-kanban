import data.Epic;
import data.StatusList;
import data.SubTask;
import data.Task;
import manager.TaskManager;

public class Main {
    public static void main(String[] args) {

    TaskManager manager = new TaskManager();



    System.out.println();
    System.out.println("$$$$$$$$$$ Тест 1 - создание 3 задач: ");
    Task newTask1 = new Task("Задача 1", "Написать задачу в практикум");
    Task newTask2 = new Task("Задача 2", "Проверить на месте ли задача");
    Task newTask3 = new Task("Задача 3", "Отметить, что задача на месте");
    manager.createTask(newTask1);
    manager.createTask(newTask2);
    manager.createTask(newTask3);
    manager.printAllTasks();

    System.out.println();
    System.out.println("$$$$$$$$$$ Тест 2 - создание 2 эпиков: ");
    Epic newEpic1 = new Epic("Эпик 1", "Строительство дома");
    Epic newEpic2 =  new Epic("Эпик 2", "Изучение Java");
    manager.createEpic(newEpic1);
    manager.createEpic(newEpic2);
    manager.printAllEpics();


    //Создаем 2 подзадачи для Эпик 1
    System.out.println();
    System.out.println("$$$$$$$$$$ Тест 3 - создание подзадач: ");
    SubTask newSubTask1 = new SubTask("Фундамент","Залить фундамент", 4);
    SubTask newSubTask2 = new SubTask("Стены","Построить стены", 4);
    manager.createSubTask(newSubTask1);
    manager.createSubTask(newSubTask2);

    //Создаем 1 подзадачу для Эпик 2
    SubTask newSubTask3 = new SubTask("Выбор курсов","Выбрать подходящие курсы", 5);
    manager.createSubTask(newSubTask3);

    manager.printEpicsAndSubTAsks();

    //Создаем подзадачу с несуществующим эпиком
    System.out.println();
    System.out.println("$$$$$$$$$$Тест 4 - Создаем подзадачу с не существующим эпиком: ");
    newSubTask3 = new SubTask("Subtask3","Subtask3 for Epic X", 99);
    manager.createSubTask(newSubTask3);

    System.out.println();
    System.out.println("$$$$$$$$$$Тест 5 - Обновляем задачу: ");
    //Обновляем задачу 1
      newTask1 = new Task(1,"Задача 1", "Написать задачу в практикум и оставить комментарий",
              StatusList.IN_PROGRESS);
    manager.updateTask(newTask1);
    manager.getTaskById(1);

    System.out.println();
    System.out.println("$$$$$$$$$$Тест 6 - Обновляем подзадачу и эпик: ");
     // Обновляем 1 эпик
    newEpic1 = new Epic(4,"Эпик 1_1", "Строительство дачи");
    manager.updateEpic(newEpic1);
    manager.printEpicsAndSubTAsks();

    System.out.println();
    System.out.println("$$$$$$$$$$Тест 7 - Обновляем подзадачу первого Эпика и меняем его статус: ");
    newSubTask1 = new SubTask(6, "Фундамент","Залить фундамент", StatusList.IN_PROGRESS, 4);
    manager.updateSubTask(newSubTask1);
    manager.getEpicById(4);
    manager.printSubTAsksByEpicId(4);

    System.out.println();
    System.out.println("$$$$$$$$$$Тест 8 - Завершаем подзадачу 2 эпика и сам Эпик:");
    newSubTask2 = new SubTask(8, "Выбор курсов","Выбрать подходящие курсы", StatusList.DONE, 5);
    manager.updateSubTask(newSubTask2);
    manager.getEpicById(5);
    manager.printSubTAsksByEpicId(5);

    System.out.println("Тесты завершены!!!");





    }
}
