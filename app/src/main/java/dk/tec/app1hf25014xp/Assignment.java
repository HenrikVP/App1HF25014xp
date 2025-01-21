package dk.tec.app1hf25014xp;

import java.time.LocalDateTime;
enum Status {Todo, Doing, Done;
        @Override
        public String toString() {
            switch (this) {
                case Todo: return "To Do";
                case Doing: return "In Progress";
                case Done: return "Completed";
                default: return super.toString();
            }
        }}

public class Assignment {
    private String title;
    private String description;
    private int reward;
    private LocalDateTime datetime;
    private Status status;
    private boolean paidOut;

    public boolean isPaidOut() {
        return paidOut;
    }

    public void setPaidOut(boolean paidOut) {
        this.paidOut = paidOut;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
