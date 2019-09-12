package com.example.mytodoapp.pojo;

public class ListItems {
    private String todoitems;
    private String description;
    private Boolean isDone;

    public ListItems(String s, String d, Boolean b) {
        setTodoitems( s );
        setDescription( d );
        setDone( b );
    }

    public String getTodoitems() {
        return todoitems;
    }

    public void setTodoitems(String todoitems) {
        this.todoitems = todoitems;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}
