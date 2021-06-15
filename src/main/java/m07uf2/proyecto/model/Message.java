package m07uf2.proyecto.model;

public class Message {
    public String title;
    public String description;
    public String url;

    public Message(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public Message() {
    }

    public void setAllMessage(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
