package data;

import java.util.ArrayList;

public class Epic extends Task{

    private ArrayList<Integer> subTaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(Integer id, String name, String description) {
        super(id, name, description);
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

    public String toString() {
        return "ID=" + this.getId() +
                ", Наименование: " + this.getName() + '\'' +
                ", Описание: " + this.getDescription() + '\'' +
                ", Статус: " + this.getStatus() + ", ID подзадач: " + subTaskIds;
    }


}
