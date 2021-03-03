package teste;

import classes.*;

import java.util.Random;
import java.lang.Math;
import java.io.FileWriter;
import java.util.Arrays;


public class Main {

	public static void main(String[] args) {
		int numExperiments = 6;
		String[] file_names = {"ItemA_Results.csv", "ItemB_Results.csv", "ItemC_Results.csv"};
		for(int i=0; i<3; i++){
			FileWriter fw = null;
			try{
				fw = new FileWriter(file_names[i]);
				fw.append("Tamanho de Vetor");
				fw.append(",");
				fw.append("Tempo de execução(s)");
				fw.append(",");
				fw.append("Número de operações");
				fw.append(",");
				fw.append("Tipo de Implementação MergeSort");
				fw.append("\n");
				System.out.println(file_names[i]);
				for(int j=1; j<=numExperiments; j++){
					int n = (int) Math.pow(10, j);
					Item[] a=null, b=null, c=null, d=null, e=null;
					switch(i){
						case 0:
							a = ItemA.generateRandomItem(n);
							b = ItemA.generateRandomItem(n);
							c = ItemA.generateRandomItem(n);
							d = ItemA.generateRandomItem(n);
							e = ItemA.generateRandomItem(n);
							break;
						case 1:
							a = ItemB.generateRandomItem(n);
							b = ItemB.generateRandomItem(n);
							c = ItemB.generateRandomItem(n);
							d = ItemB.generateRandomItem(n);
							e = ItemB.generateRandomItem(n);
							break;
						case 2:
							a = ItemC.generateRandomItem(n);
							b = ItemC.generateRandomItem(n);
							c = ItemC.generateRandomItem(n);
							d = ItemC.generateRandomItem(n);
							e = ItemC.generateRandomItem(n);
							break;
					}
					long startTime=0;

					System.out.printf("Experimento #%d\n", j);
					System.out.printf("Utilizando %d objetos aleatórios\n", n);

					System.out.print("Vetor antes: ");
					printItemArray(a, 3);
					startTime = System.nanoTime();
					Comparable[] aTemp = MergeSort.mergeSort(a, 1);
					double execTime = (System.nanoTime() - startTime)*Math.pow(10, -9);
					System.out.printf("Tempo de execução do MergeSort simples: %.5fs\n", execTime);
					a = comparableToItem(aTemp);

					System.out.println("Número de operações executadas: " + MergeSort.contOp);
					System.out.print("Vetor depois: ");
					printItemArray(a, 3);
					insertCSV(fw, n, execTime, MergeSort.contOp, "Clássica");

					System.out.println();
					
					System.out.print("Vetor antes: ");
					printItemArray(b, 3);
					startTime = System.nanoTime();
					Comparable[] bTemp = MergeSort.mergeSort(b, 2);
					execTime = (System.nanoTime() - startTime)*Math.pow(10, -9);
					System.out.printf("Tempo de execução do MergeSort com InsertSort: %.5fs\n",execTime);
					b = comparableToItem(bTemp);
					System.out.println("Número de operações executadas: " + MergeSort.contOp);
					System.out.print("Vetor depois: ");
					printItemArray(b, 3);
					insertCSV(fw, n, execTime, MergeSort.contOp, "Merge com Insert Sort");

					System.out.println();

					System.out.print("Vetor antes: ");
					printItemArray(c, 3);
					startTime = System.nanoTime();
					Comparable[] cTemp = MergeSort.mergeSort(c, 3);
					execTime = (System.nanoTime() - startTime)*Math.pow(10, -9);
					System.out.printf("Tempo de execução do MergeSort testando se os subvetores já estão ordenados: %.5fs\n",execTime);
					c = comparableToItem(cTemp);
					System.out.println("Número de operações executadas: " + MergeSort.contOp);
					System.out.print("Vetor depois: ");
					printItemArray(c, 3);
					insertCSV(fw, n, execTime, MergeSort.contOp, "Merge verificando se subvetores estão ordenados");

					System.out.println();
					
					System.out.print("Vetor antes: ");
					printItemArray(d, 3);
					startTime = System.nanoTime();
					Comparable[] dTemp = MergeSort.mergeSort(d, 4);
					execTime = (System.nanoTime() - startTime)*Math.pow(10, -9);
					System.out.printf("Tempo de execução do MergeSort sem cópia de vetores: %.5fs\n", execTime);
					d = comparableToItem(dTemp);
					System.out.println("Número de operações executadas: " + MergeSort.contOp);
					System.out.print("Vetor depois: ");
					printItemArray(d, 3);
					insertCSV(fw, n, execTime, MergeSort.contOp, "Merge sem cópia de vetores");

					System.out.println("Vetor antes: ");
					printItemArray(e, 3);
					startTime = System.nanoTime();
					Arrays.sort(e);
					execTime = (System.nanoTime() - startTime)*Math.pow(10, -9);
					System.out.printf("Tempo de execução do MergeSort sem cópia de vetores: %.5fs\n", execTime);
					e = comparableToItem(e);
					System.out.println("Número de operações executadas desconhecida.");
					System.out.println("Vetor depois: ");
					printItemArray(e, 3);
					insertCSV(fw, n, execTime, -1, "Merge da biblioteca java.utils.Arrays");

					fw.flush();
					System.out.println();
				}
				fw.flush();
				fw.close();
			}catch(Exception e){
				System.err.println(e);
				try{
					fw.flush();
					fw.close();
				}catch(Exception f){
					System.err.println(f);
				}
			}
		}
	}

	private static void insertCSV(FileWriter fw, Integer tamVetor, Double tempExecucao, Integer numOperacoes, String impMergeSort){
		try{
			fw.write(tamVetor.toString());
			fw.write(",");
			fw.write(tempExecucao.toString());
			fw.write(",");
			fw.write(numOperacoes.toString());
			fw.write(",");
			fw.write(impMergeSort);
			fw.write("\n");
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	private static Item[] comparableToItem(Comparable[] a) {
		Item[] r = new Item[a.length];
		for(int i=0; i<a.length; i++) {
			r[i] = (Item)a[i];
		}
		return r;
	}
	
	public static void printItemArray(Item[] array, int limit) {
		int i=0;
		for(Item item : array) {
			System.out.print(item + "\n");
			i++;
			if(i>limit) {
				System.out.print("...");
				break;
			}
		}
		System.out.println();
	}
	
	public static void addRandomNumbers(Integer[] list, int n) {
		Random rand = new Random();
		rand.setSeed(System.nanoTime());
		for(int i=0; i<n; i++) 
			list[i] = rand.nextInt()%(n*5);
		
	}

}
