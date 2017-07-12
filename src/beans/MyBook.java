package beans;

public class MyBook {
    private Integer id;

    private String bookname;

    private Integer userid;

    public MyBook(Integer id, String bookname, Integer userid) {
        this.id = id;
        this.bookname = bookname;
        this.userid = userid;
    }

    public MyBook() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname == null ? null : bookname.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "MyBook{" +
                "id=" + id +
                ", bookname='" + bookname + '\'' +
                ", userid=" + userid +
                '}';
    }
}