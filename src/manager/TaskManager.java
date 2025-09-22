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

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Integer number : taskList.keySet()) {
            Task value = taskList.get(number);
            tasks.add(value);
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

    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (Integer number : subTaskList.keySet()) {
            SubTask value = subTaskList.get(number);
            subTasks.add(value);
        }
        return subTasks;
    }

    public ArrayList<SubTask> getSubTAsksByEpicId(int id) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (Integer number : subTaskList.keySet()) {
            SubTask value = subTaskList.get(number);
            if (value.getEpicId().equals(id)) {
                subTasks.add(value);
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
        epic.setName(updatedEpic.getName());
        epic.setDescription(updatedEpic.getDescription());
        epicList.put(epic.getId(), epic);
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
        return taskList.get(id);
    }

    public SubTask getSubTaskById(Integer id) {
        return subTaskList.get(id);
    }

    public Epic getEpicById(Integer id) {
        return epicList.get(id);
    }

    public void removeTaskById(Integer id) {
        taskList.remove(id);
    }

    public void removeSubTaskById(Integer id) {
        SubTask value = subTaskList.get(id);
        Epic epic = epicList.get(value.getEpicId());
        if (epic != null) {
            ArrayList<Integer> currentSubTasks = epic.getSubTaskIds();
            currentSubTasks.remove(id);
            epic.setSubTaskIds(currentSubTasks);
        }
        subTaskList.remove(id);
        updateEpicStatus(value.getEpicId());
    }

    public void removeEpicById(Integer id) {
        ArrayList<SubTask> subTasksToDelete = getSubTAsksByEpicId(id);
        for (SubTask number : subTasksToDelete) {
            subTaskList.remove(number.getId());
        }
        epicList.remove(id);
    }
}