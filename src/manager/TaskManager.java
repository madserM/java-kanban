package manager;

import java.util.ArrayList;
import java.util.HashMap;

import data.Epic;
import data.Status;
import data.SubTask;
import data.Task;

public class TaskManager {

    private HashMap<Integer, Task> taskList = new HashMap<>();
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
        newTask.setStatus(Status.NEW);
        taskList.put(newId, newTask);
        return newTask;
    }

    public Epic createEpic(Epic newEpic) {
        int newId = nextId();
        newEpic.setId(newId);
        newEpic.setStatus(Status.NEW);
        epicList.put(newId, newEpic);
        return newEpic;
    }

    public SubTask createSubTask(SubTask newSubTask) {
        int newId = nextId();       // 1 генерируется newId
        newSubTask.setId(newId);// 2 проставляем  newId
        newSubTask.setStatus(Status.NEW);// 3 сетится статус NEW
        Epic epic = epicList.get(newSubTask.getEpicId());
        if (epic != null) {
            ArrayList<Integer> currentSubTasks = epic.getSubTaskIds();
            currentSubTasks.add(newId);
            subTaskList.put(newId, newSubTask);
            updateEpicStatus(epic.getId());

        } else {
            System.out.println("Эпик не найден! Создайте задачу заново, указав существующий epicId");
        }
        return newSubTask;
    }

    public HashMap<Integer, Task> getAllTasks() {
        HashMap<Integer, Task> tasks = new HashMap<>();
        for (Integer number : taskList.keySet()) {
            Task value = taskList.get(number);
            tasks.put(value.getId(), value);
        }
        return tasks;
    }

    public HashMap<Integer, Epic> getAllEpics() {
        HashMap<Integer, Epic> epics = new HashMap<>();
        for (Integer number : epicList.keySet()) {
            Epic value = epicList.get(number);
            epics.put(value.getId(), value);
        }
        return epics;
    }

    public HashMap<Integer, SubTask> getAllSubTasks() {
        HashMap<Integer, SubTask> subTasks = new HashMap<>();
        for (Integer number : subTaskList.keySet()) {
            SubTask value = subTaskList.get(number);
            subTasks.put(value.getId(), value);
        }
        return subTasks;
    }

    public HashMap<Integer, SubTask> getSubTAsksByEpicId(int id) {
        HashMap<Integer, SubTask> subTasks = new HashMap<>();
        for (Integer number : subTaskList.keySet()) {
            SubTask value = subTaskList.get(number);
            if (value.getEpicId().equals(id)) {
                subTasks.put(value.getId(), value);
            }
        }
        return subTasks;
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

    public Task updateTask(Task updatedTask) {
        taskList.put(updatedTask.getId(), updatedTask);
        return updatedTask;
    }

    public Epic updateEpic(Epic updatedEpic) {
        Epic epic = epicList.get(updatedEpic.getId());
        updatedEpic.setSubTaskIds(epic.getSubTaskIds());
        updatedEpic.setId(epic.getId());
        updatedEpic.setStatus(epic.getStatus());
        epicList.put(updatedEpic.getId(), updatedEpic);
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
                    if (subTask.getStatus().equals(Status.IN_PROGRESS)) {
                        subtaskscount++;
                        inProgress++;
                    } else if (subTask.getStatus().equals(Status.DONE)) {
                        subtaskscount++;
                        done++;
                    } else {
                        subtaskscount++;
                    }
                }
            }
            if (subtaskscount > 0 && subtaskscount == done) {
                epic.setStatus(Status.DONE);
            } else if (subtaskscount > 0 && inProgress > 0) {
                epic.setStatus(Status.IN_PROGRESS);
            } else {
                epic.setStatus(Status.NEW);
            }
        }
    }

    public void removeAllTasks() {
        taskList.clear();
    }

    public void removeAllSubTasks() {
        subTaskList.clear();
        for (Integer number : epicList.keySet()) {
            Epic epic = epicList.get(number);
            epic.setSubTaskIds(null);
            epic.setStatus(Status.NEW);
        }
    }

    public void removeAllEpics() {
        epicList.clear();
        subTaskList.clear();
    }

    public Task getTaskById(Integer id) {
        Task value = taskList.get(id);
        return value;
    }

    public SubTask getSubTaskById(Integer id) {
        SubTask value = subTaskList.get(id);
        return value;
    }

    public Epic getEpicById(Integer id) {
        Epic value = epicList.get(id);
        return value;
    }

    public void removeTaskById(Integer id) {
        taskList.remove(id);
    }

    public void removeSubTaskById(Integer id) {
        SubTask value = subTaskList.get(id);
        Epic epic = epicList.get(value.getEpicId());
        if (epic != null) {
            ArrayList<Integer> currentSubTasks = epic.getSubTaskIds();
            for (Integer exp : currentSubTasks) {   //Перебираем эпики и убираем удаленную подзадачу из списка
                if (exp.equals(id)) {
                    currentSubTasks.remove(exp);
                    break;
                }
            }
            epic.setSubTaskIds(currentSubTasks);
        }
        subTaskList.remove(id);
        updateEpicStatus(value.getEpicId());
    }

    public void removeEpicById(Integer id) {
        HashMap<Integer, SubTask> subTasksToDelete = getSubTAsksByEpicId(id);
        for (Integer number : subTasksToDelete.keySet()) {
            SubTask subTask = subTaskList.get(number);
            subTaskList.remove(subTask.getId());
        }
        epicList.remove(id);
    }
}