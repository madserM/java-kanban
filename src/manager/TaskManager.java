package manager;

import java.util.ArrayList;
import java.util.HashMap;

import data.Epic;
import data.StatusList;
import data.SubTask;
import data.Task;

public class TaskManager {

    private HashMap<Integer, Task> taskList = new HashMap<>();//поменять на private
    private HashMap<Integer, SubTask> subTaskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();

    private int counter = 1;

    private int nextId() {
        int result = counter++;
        return result;
    }

    public Task createTask(Task newTask) {
        int newId = nextId();
        newTask.setId(newId);
        newTask.setStatus(StatusList.NEW);
        taskList.put(newId, newTask);
        return newTask;
    }

    public Epic createEpic(Epic newEpic) {
        int newId = nextId();
        newEpic.setId(newId);
        newEpic.setStatus(StatusList.NEW);
        epicList.put(newId, newEpic);
        return newEpic;
    }

    public SubTask createSubTask(SubTask newSubTask) {
        int newId = nextId();       // 1 генерируется newId
        newSubTask.setId(newId);// 2 проставляем  newId
        newSubTask.setStatus(StatusList.NEW);// 3 сетится статус NEW

        if (epicList.containsKey(newSubTask.getEpicId())) {
            Epic epic = epicList.get(newSubTask.getEpicId());
            ArrayList<Integer> currentSubTasks = epic.getSubTaskIds();
            currentSubTasks.add(newId);
            epic.setSubTaskIds(currentSubTasks);
            subTaskList.put(newId, newSubTask);
        } else {
            System.out.println("Эпик не найден! Создайте задачу заново, указав существующий epicId");
        }
        return newSubTask;
    }

    public void printAllTasks() {
        System.out.println("Список задач: ");
        int counter = 1;
        for (Integer number : taskList.keySet()) {
            System.out.println("----------");
            System.out.println("Задача " + counter + ": ");
            Task value = taskList.get(number);
            System.out.println(value);
            counter++;
        }
        System.out.println("**************************");
    }

    public void printAllEpics() {
        System.out.println("Список эпиков :");
        int counter = 1;
        for (Integer number : epicList.keySet()) {
            System.out.println("----------");
            System.out.println("Эпик " + counter + ": ");
            Task value = epicList.get(number);
            System.out.println(value);
            counter++;
        }
        System.out.println("**************************");
    }

    public void printAllSubTasks() {
        System.out.println("Список подзадач :");
        int counter = 1;
        for (Integer number : subTaskList.keySet()) {
            System.out.println("----------");
            System.out.println("Подзадача " + counter + ": ");
            SubTask value = subTaskList.get(number);
            System.out.println(value);
            counter++;
        }
        System.out.println("**************************");
    }

    public void printEpicsAndSubTAsks() {
        for (Integer number : epicList.keySet()) {
            int counter = 1;
            Epic epic = epicList.get(number);// цикл должен пройтись по ключам
            System.out.println("Id- " + epic.getId() + " | " + epic.getName() + " | " + epic.getDescription() + " | " +
                    "Статус-" + epic.getStatus());
            for (Integer subTaskNumber : subTaskList.keySet()) {
                SubTask subTask = subTaskList.get(subTaskNumber);
                if (subTask.getEpicId() == epic.getId()) {
                    System.out.println("Id- " + subTask.getId() + " | " + counter + " | " + subTask.getName() + " | " +
                            subTask.getDescription() + " | Статус- " + subTask.getStatus());
                    counter++;
                }
            }
            System.out.println("----------");
        }
    }

    public void printSubTAsksByEpicId(int id) {
        Epic epic = epicList.get(id);
        int counter = 1;
        for (Integer subTaskNumber : subTaskList.keySet()) {
            SubTask subTask = subTaskList.get(subTaskNumber);
            if (subTask.getEpicId() == epic.getId()) {
                System.out.println("Id- " + subTask.getId() + " | " + counter + " | " + subTask.getName() + " | " +
                        subTask.getDescription() + " | Статус- " + subTask.getStatus());
                counter++;
            }
        }
        System.out.println("----------");

    }


    public Task updateTask(Task updatedTask) {
        taskList.put(updatedTask.getId(), updatedTask);
        return updatedTask;
    }

    public Epic updateEpic(Epic updatedEpic) {
        for (Integer number : epicList.keySet()) {
            Epic epic = epicList.get(number);
            if (epic.getId() == updatedEpic.getId()) {
                updatedEpic.setSubTaskIds(epic.getSubTaskIds());
                updatedEpic.setId(epic.getId());
                updatedEpic.setStatus(epic.getStatus());
            }
            epicList.put(updatedEpic.getId(), updatedEpic);
        }
        return updatedEpic;
    }

    public SubTask updateSubTask(SubTask updatedSubTask) {
        subTaskList.put(updatedSubTask.getId(), updatedSubTask);
        updateEpicStatus(updatedSubTask.getEpicId());

        return updatedSubTask;
    }

    public void updateEpicStatus(int epicId) {
        int subtaskscount = 0;
        int inProgress = 0;
        int done = 0;
        Epic epic = epicList.get(epicId);
        if (epicList.containsKey(epicId)) {
            for (Integer subTaskNumber : subTaskList.keySet()) {
                SubTask subTask = subTaskList.get(subTaskNumber);
                if (subTask.getEpicId() == epicId) {
                    if (subTask.getStatus().equals(StatusList.IN_PROGRESS)) {
                        subtaskscount++;
                        inProgress++;
                    } else if (subTask.getStatus().equals(StatusList.DONE)) {
                        subtaskscount++;
                        done++;
                    } else {
                        subtaskscount++;
                    }
                }
            }
            if (subtaskscount > 0 && subtaskscount == done) {
                epic.setStatus(StatusList.DONE);
            } else if (subtaskscount > 0 && inProgress > 0) {
                epic.setStatus(StatusList.IN_PROGRESS);
            }
        }
    }

    public void removeAllTasks() {
        taskList.clear();
    }

    public void removeAllSubTasks() {
        subTaskList.clear();
    }

    public void removeAllEpics() {
        for (Integer subTaskNumber : subTaskList.keySet()) {
            SubTask subTask = subTaskList.get(subTaskNumber);
            subTask.setEpicId(0);  // Отчищаем связки подзадач от удаленных эпиков
        }
        epicList.clear();
    }

    public void getTaskById(Integer id) {
        if (taskList.containsKey(id)) {
            Task value = taskList.get(id);
            System.out.println(value);
        } else {
            System.out.println("Задача с данным ID отсутствует");
        }
    }

    public void getSubTaskById(Integer id) {
        if (subTaskList.containsKey(id)) {
            SubTask value = subTaskList.get(id);
            System.out.println(value);
        } else {
            System.out.println("Подзадача с данным ID отсутствует");
        }
    }

    public void getEpicById(Integer id) {
        if (epicList.containsKey(id)) {
            Epic value = epicList.get(id);
            System.out.println(value);
        } else {
            System.out.println("Эпик с данным ID отсутствует");
        }
    }

    public void removeTaskById(Integer id) {
        taskList.remove(id);
    }

    public void removeSubTaskById(Integer id) {
        subTaskList.remove(id);
    }

    public void removeEpicById(Integer id) {
        epicList.remove(id);
    }

}
