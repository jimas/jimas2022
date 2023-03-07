package cache;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liuqj
 */
public class CacheTest {

    @Test
    public void testLinkedHashMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(5, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                //当linkedHashMap 容量大于等于5的时候，再插入就移除旧的元素
                return this.size() >= 5;
            }

            @Override
            public boolean remove(Object key, Object value) {
                return super.remove(key, value);
            }
        };

        map.put("1", "aa");
        map.put("2", "bb");
        map.put("3", "cc");
        map.put("4", "dd");
        System.out.println("原始顺序：");
        print(map);
        map.get("2");
        System.out.println("最近访问了2");
        print(map);
        map.get("3");
        System.out.println("最近访问了3");
        print(map);
        System.out.println("加元素");
        map.put("5", "ee");
        print(map);

    }

    private void print(LinkedHashMap<String, String> map) {
        map.keySet().iterator().forEachRemaining(System.out::println);
    }

}
