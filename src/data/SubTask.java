package data;

public class SubTask extends Task {
    private Integer epicId;

    public SubTask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId=epicId;
    }

    public SubTask(Integer id, String name, String description, StatusList status, Integer epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "ID=" + this.getId() +
                ", Наименование: " + this.getName() + '\'' +
                ", Описание: " + this.getDescription() + '\'' +
                ", Статус: " + this.getStatus() + ", Эпик ID: " + epicId;
    }
}

