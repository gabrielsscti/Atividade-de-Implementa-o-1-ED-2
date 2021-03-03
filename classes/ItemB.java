package classes;

import java.util.UUID;
import java.util.Random;

public class ItemB extends Item implements Comparable {

    private Double key;
    private String valor;

    public static Item[] generateRandomItem(int n){
        ItemB[] r = new ItemB[n];
        Random rand = new Random();
        for(int i=0; i<n; i++){
            r[i] = new ItemB();
            r[i].key = rand.nextDouble();
            r[i].valor = UUID.randomUUID().toString();
        }
        return toParent(r);
    }

    private static Item[] toParent(ItemB[] a){
        Item[] r = new Item[a.length];
		for(int i=0; i<a.length; i++) {
			r[i] = (Item)a[i];
		}
		return r;
    }

    @Override
    public int compareTo(Object o) {
        return (key.compareTo(((ItemB)o).key));
    }
    
    @Override
    public String toString() {
        return "ItemB[" + this.key + "]: " + this.valor;
    }
    
}
