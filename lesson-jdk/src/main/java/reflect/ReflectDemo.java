package reflect;

import bean.Student;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by sang on 2020/11/13.
 */
public class ReflectDemo {

    public static void main(String[] args) {
      //  Student student = new Student();
       // Class studentClass =  Student.class;
        URLClassLoader classLoader = new URLClassLoader(new URL[]{});
        Class studentClass = null;
        Student student = null ;
        try {
            studentClass = classLoader.loadClass("bean.Student");//resolve =false
            student = (Student)studentClass.newInstance();//link + init
        } catch (Exception e) {
            e.printStackTrace();
        }
         Field[] fields = studentClass.getDeclaredFields();
        for (Field f :fields ){
            f.setAccessible(true);

            try {

                if(f.getName().equals("name")){
                    f.set(student,"tony");
                }
                System.out.println(f.get(student));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Method[] methods = studentClass.getDeclaredMethods();
        for (Method m :methods ){
            //m.setAccessible(true);
            System.out.println(m.getName());
            if(m.getName().equals("study")){
                m.setAccessible(true);
                try {
                    m.invoke(student,null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
