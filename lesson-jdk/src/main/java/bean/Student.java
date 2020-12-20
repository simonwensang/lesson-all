package bean;

/**
 * Created by sang on 2020/11/13.
 */
public class Student {

    private String name;
    // private Integer age ; ==null 初始值
    private int age ;

    private boolean sex;

    private void study(){
        System.out.println("private study()");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
