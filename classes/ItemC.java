package classes;

import java.util.UUID;
import java.util.Random;

public class ItemC extends Item implements Comparable {

    private Integer key;
    private Integer[] valor;

    public static Item[] generateRandomItem(int n){
        ItemC[] r = new ItemC[n];
        Random rand = new Random();
        for(int i=0; i<n; i++){
            r[i] = new ItemC();
            r[i].key = rand.nextInt(n);
            r[i].valor = new Integer[30];
            for(int j=0; j<30; j++)
                r[i].valor[j] = rand.nextInt(n);
        }
        return toParent(r);
    }

    private static Item[] toParent(ItemC[] a){
        Item[] r = new Item[a.length];
		for(int i=0; i<a.length; i++) {
			r[i] = (Item)a[i];
		}
		return r;
    }

    @Override
    public int compareTo(Object o) {
        return (key.compareTo(((ItemC)o).key));
    }

    @Override
    public String toString() {
        return "ItemC[" + this.key + "]: " + this.valor;
    }
    

    
    
}
