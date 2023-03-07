package cache;

import com.jimas.RandomArray;
import org.junit.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CacheSoftReferenceTest {
    @Test
    public void testSoft() {
        //缓存
        HashMap<Integer, SoftReferencePerson> map = new HashMap<>();
        ReferenceQueue<Person> queue = new ReferenceQueue<>();
        int i = 0;
        while (i < 100000000) {
            Person person = new Person(RandomArray.randomNum(30) + "", RandomArray.randomNum(90));
            map.put(i, new SoftReferencePerson(person, queue, i));
            SoftReferencePerson pollRef = (SoftReferencePerson) queue.poll();
            //找出被软引用回收的对象
            if (pollRef != null) {
                System.out.println("回收" + pollRef.key);
                //以key为标志，从map中移除
                map.remove(pollRef.key);
                System.out.println(i + "新一轮================================================");
                Iterator<Map.Entry<Integer, SoftReferencePerson>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, SoftReferencePerson> entry = iterator.next();
                    if ((int) entry.getKey() == pollRef.key) {
                        System.out.println("见鬼了");
                    }
                }
                System.out.println(i + "新一轮================================================");

            }
            i++;
        }
        System.out.println("done");
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class SoftReferencePerson extends SoftReference<Person> {
    public int key;

    //ReferenceQueue 用来存储封装待回收Reference对象的
    //当Person 对象被回收后，SoftReferencePerson对象会加入queue中
    public SoftReferencePerson(Person referent, ReferenceQueue<? super Person> q, int key) {
        super(referent, q);
        this.key = key;
    }
}