
import java.util.Map;


public class Cord<K,V> implements Map.Entry<K,V>{

    private final K key;
    private V value;

    public Cord(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V val) {
        V oldVal = value;
        value = val;
        return oldVal;
    }


    @Override
    public String toString(){
        return getKey() + " / " + getValue();
    }

}
