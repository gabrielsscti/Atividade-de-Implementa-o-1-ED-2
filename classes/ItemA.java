package classes;
import java.util.UUID;
import java.util.Random;

public class ItemA extends Item implements Comparable {

    private String key;
    private Double valor;

    public static Item[] generateRandomItem(int n){
        ItemA[] r = new ItemA[n];
        Random rand = new Random();
        for(int i=0; i<n; i++){
            r[i] = new ItemA();
            r[i].key = UUID.randomUUID().toString();
            r[i].valor = rand.nextDouble();
        }
        return toParent(r);
    }

    private static Item[] toParent(ItemA[] a){
        Item[] r = new Item[a.length];
		for(int i=0; i<a.length; i++) {
			r[i] = (Item)a[i];
		}
		return r;
    }

    @Override
    public int compareTo(Object o) {
        return key.compareTo(((ItemA)o).key);
    }

    @Override
    public String toString() {
        return "ItemA[" + this.key + "]: " + this.valor;
    }
    
}
