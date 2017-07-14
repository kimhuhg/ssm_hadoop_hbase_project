package beans;

import java.io.Serializable;
import java.util.List;

public class MyUser implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private List<MyBook> books;

    public MyUser(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public MyUser() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public List<MyBook> getBooks() {
        return books;
    }

    public void setBooks(List<MyBook> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MyUser)
            if(((MyUser) obj).getId()==this.getId()
                    &&((MyUser) obj).getUsername().equals(this.getUsername())
                    &&((MyUser) obj).getPassword().equals(this.getPassword())
                    &&((MyUser) obj).getBooks().equals(this.getBooks()))
               return true;
            else
                return false;
        return false;
    }
}