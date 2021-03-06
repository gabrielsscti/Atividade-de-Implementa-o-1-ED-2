package teste;

import classes.*;

import java.lang.Math;
import java.io.FileWriter;
import java.util.Arrays;


public class Main {

	public static void main(String[] args) {
		final int NUM_EXPERIMENTS = 6; // Número de experimentos para criar vetores de tamanho maior
		final int ROUNDS = 50; // Número de rodadas para cada experimento, será usado para tirar a média

		String[] file_names = {"ItemA_Results.csv", "ItemB_Results.csv", "ItemC_Results.csv"}; // Nome dos arquivos de saída
		for(int i=0; i<3; i++){
			FileWriter fw = null;
			try{
				// File Writer
				fw = new FileWriter(file_names[i]);
				fw.append("Tamanho de Vetor");
				fw.append(",");
				fw.append("Tempo de execução(s)(média de 50 execuções)");
				fw.append(",");
				fw.append("Número de operações(média de 50 execuções)");
				fw.append(",");
				fw.append("Tipo de Implementação MergeSort");
				fw.append("\n");
				System.out.println(file_names[i]);

				// Loop para cada experimento
				for(int j=1; j<=NUM_EXPERIMENTS; j++){
					int n = (int) Math.pow(10, j);

					Item[] itens;
					
					long startTime=0;

					System.out.printf("Experimento #%d\n", j);
					System.out.printf("Utilizando %d objetos aleatórios\n", n);
					double execTime = 0; // Variável que vai acumular o tempo de execução de cada sort para tirar a média
					long operationCount = 0; // Variável que vai acumular o número de operações parar tirar a média
					// Loop para cada round
					for(int k=0; k<ROUNDS; k++){
						// Gerando itens de diferentes tipos, como pedido no enunciado
						itens = getItens(i, n);

						System.out.printf("Rodada #%d\n", k);
						System.out.print("Vetor antes: ");
						printItemArray(itens, 3);
						startTime = System.nanoTime();
						Comparable[] aTemp = MergeSort.mergeSort(itens, 1);
						execTime += (System.nanoTime() - startTime)*Math.pow(10, -9);
						itens = comparableToItem(aTemp);

						System.out.println("Número de operações executadas: " + MergeSort.contOp);
						operationCount += MergeSort.contOp;
						System.out.print("Vetor depois: ");
						printItemArray(itens, 3);

						System.out.println();
					}
					insertCSV(fw, n, execTime/ROUNDS, operationCount/ROUNDS, "Clássica");
						
					execTime = 0;
					operationCount = 0;
					for(int k=0; k<ROUNDS; k++){
						itens = getItens(i, n);
						System.out.printf("Rodada #%d\n", k);
						System.out.print("Vetor antes: ");
						printItemArray(itens, 3);
						startTime = System.nanoTime();
						Comparable[] bTemp = MergeSort.mergeSort(itens, 2);
						execTime += (System.nanoTime() - startTime)*Math.pow(10, -9);
						itens = comparableToItem(bTemp);
						System.out.println("Número de operações executadas: " + MergeSort.contOp);
						operationCount += MergeSort.contOp;
						System.out.print("Vetor depois: ");
						printItemArray(itens, 3);
						System.out.println();
					}
					insertCSV(fw, n, execTime/ROUNDS, operationCount/ROUNDS, "Merge com Insert Sort");

					execTime = 0;
					operationCount = 0;
					for(int k=0; k<ROUNDS; k++){
						itens = getItens(i, n);

						System.out.printf("Rodada #%d\n", k);
						System.out.print("Vetor antes: ");
						printItemArray(itens, 3);
						startTime = System.nanoTime();
						Comparable[] cTemp = MergeSort.mergeSort(itens, 3);
						execTime += (System.nanoTime() - startTime)*Math.pow(10, -9);
						itens = comparableToItem(cTemp);
						System.out.println("Número de operações executadas: " + MergeSort.contOp);
						operationCount += MergeSort.contOp;
						System.out.print("Vetor depois: ");
						printItemArray(itens, 3);
						System.out.println();
					}
					insertCSV(fw, n, execTime/ROUNDS, operationCount/ROUNDS, "Merge testando se está ordenado");

					execTime = 0;
					operationCount = 0;
					for(int k=0; k<ROUNDS; k++){
						itens = getItens(i, n);
					
						System.out.printf("Rodada #%d\n", k);
						System.out.print("Vetor antes: ");
						printItemArray(itens, 3);
						startTime = System.nanoTime();
						Comparable[] dTemp = MergeSort.mergeSort(itens, 4);
						execTime += (System.nanoTime() - startTime)*Math.pow(10, -9);
						itens = comparableToItem(dTemp);
						System.out.println("Número de operações executadas: " + MergeSort.contOp);
						operationCount += MergeSort.contOp;
						System.out.print("Vetor depois: ");
						printItemArray(itens, 3);
					}
					insertCSV(fw, n, execTime/ROUNDS, operationCount/ROUNDS, "Merge sem cópia de vetores");
					
					execTime = 0;
					operationCount = 0;
					for(int k=0; k<ROUNDS; k++){
						itens = getItens(i, n);

						System.out.printf("Rodada #%d\n", k);
						System.out.println("Vetor antes: ");
						printItemArray(itens, 3);
						startTime = System.nanoTime();
						Arrays.sort(itens);
						execTime += (System.nanoTime() - startTime)*Math.pow(10, -9);
						itens = comparableToItem(itens);
						System.out.println("Número de operações executadas desconhecida.");
						System.out.println("Vetor depois: ");
						printItemArray(itens, 3);
						fw.flush();
						System.out.println();
					}
					insertCSV(fw, n, execTime/ROUNDS, (long)-1, "Merge da biblioteca java.utils.Arrays");
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

	private static Item[] getItens(int type, int n){
		switch(type){
			case 0:
				return ItemA.generateRandomItem(n);
			case 1:
				return ItemB.generateRandomItem(n);
			case 2:
				return ItemC.generateRandomItem(n);
		}
		return null;
	}

	private static void insertCSV(FileWriter fw, Integer tamVetor, Double tempExecucao, Long numOperacoes, String impMergeSort){
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
}
