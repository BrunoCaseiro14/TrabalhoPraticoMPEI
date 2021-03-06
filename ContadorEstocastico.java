public class ContadorEstocastico{
 	private double probAdd;         // Probabilidade para adicionar novo elemento
	private int n = 0;			    // Numero de elementos efetivos
	private int sum = 0;			// Numero de elementos provaveis

	// Caso nada seja especificado no construtor, executa o contador pela primeira forma
	public ContadorEstocastico(){
		this.probAdd = 0.5;			// Probabilidade inicial (sempre 50%)
	}

	// Caso seja indicado um numero qualquer no construtor, executa o contador pela segunda forma
	public ContadorEstocastico(int anotherWay){	
		this.probAdd = 1;			// Probabilidade inicial (2^-0 = 1)
	}
	
	public void primeiraSolucao(){
		if (Math.random() < probAdd) {
			n++;
		}
	}

	public void segundaSolucao(){
		if(probAdd >= Math.random()) {
			n++;
			probAdd = 1/(Math.pow(2,n));		// Formula (2^-n)
		}
	}

	public int getN() {
		return n;
	}

	public double getProbAdd() {
		return probAdd;
	}

	public String primeiraToString() {
		sum = 2 * n;
		return "Counter: " + n + "; Probability of adding next element: " + probAdd + "; Number of elements (approximately): " + sum;
	}

	public String segundaToString() {
		if(n == 0){
			sum = 0;
		}
		else if(n == 1){
			sum = 1;
		}
		else{
			for (int i = 0; i < n; i++) {
				sum += 2^i;
			}
		}
		return "Counter: " + n + "; Probability of adding next element: " + probAdd + "; Number of elements (approximately): " + sum;
	}
}