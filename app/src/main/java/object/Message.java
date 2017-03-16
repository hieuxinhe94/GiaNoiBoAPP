package object;

public class Message {
    private String Id;
    private String SMS;
    private String Title;
    private String DayCreate;
    private String Image = "@drawable/logo";
    public Message() {
    }
    public Message(String SMS, String title, String dayCreate) {
        this.SMS = SMS;
        Title = title;
        DayCreate = dayCreate;
    }
    public Message(String id, String SMS, String title, String dayCreate) {
        Id = id;
        this.SMS = SMS;
        Title = title;
        DayCreate = dayCreate;
    }
    public String getId() {

        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getSMS() {
        return SMS;
    }
    public void setSMS(String SMS) {
        this.SMS = SMS;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getDayCreate() {
        return DayCreate;
    }
    public void setDayCreate(String dayCreate) {
        DayCreate = dayCreate;
    }
    public String getImage() {
        return Image;
    }
    public void setImage(String image) {
        Image = image;
    }
}