package classes;

public class MergeSort{
	private MergeSort() {}; // Bloqueia instâncias da classe.
	public static int contOp = 0; // Variável que conta o número de operações.
	private static final int INSERT_OFFSET = 15; // Offset de execuçãoo do insertsort
	
	public static Comparable[] mergeSort(Comparable[] src, int type) {
		/*
		 * Função principal merge
		 * type recebe um número inteiro representando o número da versão do MergeSort no pdf(1-4) que se quer testar.
		 */
		Comparable[] aux = src.clone();
		contOp = 0;
		switch(type) {
		case 1:
			mergeSort(src, aux, 0, src.length-1);
			return aux;
		case 2:
			mergeSortInsertSort(src, aux, 0, src.length-1);
			return aux;
		case 3:
			mergeSortTestIfOrdered(src, aux, 0, src.length-1);
			return aux;
		case 4:
			mergeSortNoCopy(src, aux, 0, src.length-1);
			return aux;
		}
		return null;
	}
	
	private static void mergeSort(Comparable[] src, Comparable[] dest, int left, int right) {
		// Função merge padrão
		int mid;
		if(left < right) {
			contOp++; // Incremento do contOp para comparação left<right
			mid = left+(right-left)/2;
			contOp++; // Incremento do contOp para atribuição à variável mid
			mergeSort(src, dest, left, mid);
			mergeSort(src, dest, mid+1, right);
			merge(src, dest, left, mid, right);
		}
	}

	private static void mergeSortNoCopy(Comparable[] src, Comparable[] dest, int left, int right){
		int mid;
		if(left < right){
			contOp++; // Incremento do contOp para comparação left<right
			mid = left + (right-left)/2;
			contOp++; // Incremento do contOp para atribuição à variável mid
			mergeSortNoCopy(dest, src, left, mid);
			mergeSortNoCopy(dest, src, mid+1, right);
			mergeNoCopy(src, dest, left, mid, right);
		}
	}

	private static void mergeSortTestIfOrdered(Comparable[] src, Comparable[] dest, int left, int right){
		int mid;
		if(left < right){
			contOp++; // Incremento do contOp para comparação left<right
			mid = left+(right-left)/2;
			contOp++; // Incremento do contOp para atribuição à variável mid
			
			mergeSortTestIfOrdered(src, dest, left, mid);
			mergeSortTestIfOrdered(src, dest, mid+1, right);
			if(less(src[mid], src[mid+1])){
				contOp++; // Incremento do contOp para a atribuição int i=left
				for(int i=left; i<=right; i++){
					contOp+=3; // Incremento para cada iteração desse for
					dest[i] = src[i];
				}
				return;
			}
			contOp++; // Incremento do contOp para a comparação src[mid] < src[mid+1]
			merge(src, dest, left, mid, right);
		}
	}
	
	private static void mergeSortInsertSort(Comparable[] src, Comparable[] dest, int left, int right) {
		if(right <= left + INSERT_OFFSET) {
			contOp++; // Incremento para a comparação right <= left + INSERT_OFFSET
			insertSort(dest, left, right);
			return;
		}
		int mid;
		mid = left+(right-left)/2;
		contOp++; // Incremento para a atribuição à variável mid
		mergeSortInsertSort(src, dest, left, mid);
		mergeSortInsertSort(src, dest, mid+1, right);
		merge(src, dest, left, mid, right);
	}

	private static void merge(Comparable[] src, Comparable[] dest, int left, int mid, int right) {
		// Função merge padrão, com cópia para o vetor merge
		int i=left, j=mid+1;
		contOp+=2; // Atribuição i=left, j=mid+1
		for(int k=left; k<=right; k++){
			if(countAndAssess(i>mid)){
				dest[k] = src[j++];
				contOp++;
			}
			else if(countAndAssess(j>right)){
				dest[k] = src[i++];
				contOp++;
			}
			else if(countAndAssess(less(src[j], src[i]))){ 
				dest[k] = src[j++];
				contOp++;
			}
			else{ 
				dest[k] = src[i++];
				contOp++;
			}
			/* Cada comparação passa pela função countAndAssess, que retorna o valor da expressão e incrementa uma operação 
			   de comparação. Apenas se a expressão for verdadeira, o contOp dessa expressão é incrementado. */
		}
		contOp++; // Atribuição k=left
		for(i=left; i<=right; i++) {
			src[i] = dest[i];
			contOp+=3;
		}
	}

	private static void mergeNoCopy(Comparable[] src, Comparable[] dest, int left, int mid, int right){
		// Função merge sem cópia para o vetor dest
		int i=left, j=mid+1;
		contOp+=2; // Atribuição i=left, j=mid+1
		for(int k=left; k<=right; k++){
			if(countAndAssess(i>mid)){
				dest[k] = src[j++];
				contOp++;
			}
			else if(countAndAssess(j>right)){
				dest[k] = src[i++];
				contOp++;
			}
			else if(countAndAssess(less(src[j], src[i]))){ 
				dest[k] = src[j++];
				contOp++;
			}
			else{ 
				dest[k] = src[i++];
				contOp++;
			}
			/* Cada comparação passa pela função countAndAssess, que retorna o valor da expressão e incrementa uma operação 
			   de comparação. Apenas se a expressão for verdadeira, o contOp dessa expressão é incrementado. */
		}
		contOp++; // Atribuição k=left
		
	}

	
	private static void insertSort(Comparable[] src, int left, int right) {
		contOp++; // Incremento de contOp para a atribuição i=left
		for(int i=left; i<=right; i++) {
			contOp+=3; // Incremento do contOp pelo for acima (i<=right e i++) e a inicialização int j=i
			for(int j=i; j>left && less(src[j], src[j-1]); j--) {
				Comparable temp = src[j];
				src[j] = src[j-1];
				src[j-1] = temp;
				contOp+=6; // Incremento somando as duas comparações do for, o decremento do j e a operação de troca, que faz 3 atribuições
			}
		}
	}
	
	/*
	 * Funções
	 * Auxiliares
	 */

	private static boolean countAndAssess(boolean expression){
		// Função auxiliar que simplesmente retorna o valor da expressão passada e incrementa contOp.
		contOp++;
		return expression;
	}

	private static boolean less(Comparable a, Comparable b) {
		return a.compareTo(b)<0; // Verifica se a<b;
	}
	
}
