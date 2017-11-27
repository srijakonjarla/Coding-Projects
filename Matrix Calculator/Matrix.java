// Srija Konjarla
// skonjarl
// pa3
// Matrix.java

public class Matrix {

	// private inner class Entry
	private class Entry {
		int column;
		double value;

		Entry (int column, double value) {
			this.column = column;
			this.value = value;
		}

		// toString():  overrides Object's toString() method
        public String toString() {
            return ("("+column + ", " + value + ")" );
        }

        // equals(): overrides Object's equals() method
        public boolean equals(Object E) {
        	Entry entry = (Entry) E;
        	boolean eq;
        	if (this.column == entry.column && this.value == entry.value) {
        		eq = true;
        	} else {
        		eq = false;
        	}
        	return eq;
        }
	}

	// Fields for Matrix Class
	private int size;
	private List[] matrix;

	// Constructor
	// Makes a new n x n zero Matrix. pre: n>=1
	Matrix(int n) {
		if (n < 1){
			throw new RuntimeException ("Can not create a matrix with size less than 1");
		}
		size = n;
		matrix = new List[n];
		for (int i = 0; i < n; i++){
			matrix[i] = new List();
		}
	}

	// Access functions

	// Returns n, the number of rows and columns of this Matrix
	int getSize() {
		if (size == 0){
			throw new RuntimeException ("Can not call getSize() on empty matrix");
		}
		return this.size;
	} 

	// Returns the number of non-zero entries in this Matrix
	int getNNZ(){
		if (size == 0){
			throw new RuntimeException ("Can not call getNNZ() on empty matrix");
		}
		int nnz = 0;
		for (int i = 0; i < this.getSize(); i++){
			nnz += matrix[i].length();
		}
		return nnz;
	}

	// overrides Object's equals() method
	public boolean equals(Object x) {
		Matrix temp = (Matrix) x;
		boolean eq;
		if (temp.getSize() != this.getSize()) {
			eq = false;
		} else {
			eq = true;
		}

		if (eq == true){
			for(int i = 0; i < getSize(); i++){
				List R = this.matrix[i];
				List S = temp.matrix[i];
				if (R.length() != S.length()){
					eq = false;
					break;
				}
				if (R.equals(S)){
					eq = true;
				} else {
					eq = false;
					break;
				}
			}
		}
		return eq;
	}

	// Manipulation procedures
	
	// sets this Matrix to the zero state
	void makeZero() {
		if (this.matrix == null){
			throw new RuntimeException ("Can not call makeZero() on non-existant matrix");
		}
		for (int i = 0; i < this.getSize(); i++){
			matrix[i].clear();
		}
	}

	// returns a new Matrix having the same entries as this Matrix
	Matrix copy(){
		if (this.matrix == null) {
			throw new RuntimeException ("Can not call copy() on non-existant matrix");
		}
		Matrix copy = new Matrix(this.getSize());
		for (int i = 0; i < this.getSize(); i++){
			List R = matrix[i];
			for(R.moveFront(); R.index() >= 0; R.moveNext()) {
				Entry E = (Entry)R.get();
				Entry N = new Entry(E.column, E.value);
				copy.matrix[i].append(N);
			}
		}
		return copy;
	}
	
	// changes ith row, jth column of this Matrix to x
	// pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x) {
		if (this.matrix == null){
			throw new RuntimeException ("Can not call changeEntry() on non-existant matrix");
		}
		if (i < 1 || i > this.getSize() || j < 1 || j > this.getSize()){
			throw new RuntimeException ("Can not call changeEntry() on non-existant indices");
		}

		Entry E = new Entry(j, x);
		List R = matrix[i-1];

		if (R.length() == 0){
			if (x != 0){
				R.append(E);
				return;
			} else {
				return;
			}
		} else if (R.length() > 0){
			if (x == 0){
				for(R.moveFront(); R.index() >= 0; R.moveNext()){
					Entry C = (Entry)R.get();
					if(C.column == j){
					    R.delete();
						return;
					}
				}
			} else {
				Entry F = (Entry)R.front();
				Entry B = (Entry)R.back();
				if (j < F.column){
					R.prepend(E);
					return;
				} else if (j > B.column){
					R.append(E);
					return;
				} else {
					for (R.moveFront(); R.index() >= 0; R.moveNext()){
						Entry C = (Entry)R.get();
						if (C.column == j){
							C.value = x;
							return;
						} else if(C.column > j){
							R.insertBefore(E);
							return;
						}
					}
				}
			}
		}
	}

	// returns a new Matrix that is the scalar product of this Matrix with x
	Matrix scalarMult(double x){
		Matrix sM = new Matrix(this.getSize());
		for (int i = 0; i < this.getSize(); i++){
			//changed
			List R = matrix[i];
			for (R.moveFront(); R.index() >= 0; R.moveNext()){
				Entry temp = (Entry)R.get();
				//Entry E = new Entry(R.column, temp.value * x);
				sM.changeEntry(i+1, temp.column, temp.value*x);
				//sM.R.append(E);
			}
		}
		return sM;
	}
	
	// returns a new Matrix that is the sum of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix add(Matrix M){
		Matrix A = new Matrix(this.getSize());
		if (M == null){
			throw new RuntimeException("Can not call add() on non-existant matrix");
		}
		if (this.getSize() != M.getSize()){
			throw new RuntimeException ("Can not call add() on matrices of different sizes");
		}
		for (int i = 0; i < this.getSize(); i++){
			List R1 = this.matrix[i];
			List R2 = M.matrix[i];
			List L = A.matrix[i];
			if ( this.equals(M)) {
				A = this.scalarMult(2.0);
			} else {
				List check = null;
				Entry E = null;
				R1.moveFront();
				R2.moveFront();
				int count = 1;
				while (R1.index() >= 0 && R2.index() >= 0){
					Entry N1 = null;
					Entry N2 = null;
					N1 = (Entry)R1.get();
					N2 = (Entry)R2.get();

					if (count == N1.column && count == N2.column){
						if (N1.value + N2.value != 0){
							E = new Entry(count, N1.value + N2.value);
							L.append(E);
						}
						count++;
						R1.moveNext();
						R2.moveNext();
					} else if (count != N1.column && count == N2.column){
						E = new Entry(count, N2.value);
						L.append(E);
						count++;
						R2.moveNext();
					} else if (count == N1.column && count != N2.column){
						E = new Entry(count, N1.value);
						L.append(E);
						count++;
						R1.moveNext();
					} else if (count != N1.column && count != N2.column){
						count++;
					}
				}
				Entry N3 = null;
				if (R1.index() == -1){
					for (;R2.index() >= 0; R2.moveNext()){
						N3 = (Entry)R2.get();
						E = new Entry(N3.column, N3.value);
						L.append(E);
					}
				} else if (R2.index() == -1){
					for (;R1.index() >= 0; R1.moveNext()){
						N3 = (Entry)R1.get();
						E = new Entry(N3.column, N3.value);
						L.append(E);
					}
				}
			}
		}
		return A;
	}

	// returns a new Matrix that is the difference of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix sub(Matrix M) {
		Matrix S = new Matrix(this.getSize());
		if (M == null){
			throw new RuntimeException("Can not call sub() on non-existant matrix");
		}
		if (this.getSize() != M.getSize()){
			throw new RuntimeException ("Can not call sub() on matrices of different sizes");
		}
		for (int i = 0; i < this.getSize(); i++){
			List R1 = this.matrix[i];
			List R2 = M.matrix[i];
			List L = S.matrix[i];
			if (this.equals(M)) {
				return S;
			} else {
				List check = null;
				Entry E = null;
				R1.moveFront();
				R2.moveFront();
				int count = 1;
				while (R1.index() >= 0 && R2.index() >= 0){
					Entry N1 = null;
					Entry N2 = null;
					N1 = (Entry)R1.get();
					N2 = (Entry)R2.get();

					if (count == N1.column && count == N2.column){
						if (N1.value - N2.value != 0){
							E = new Entry(count, N1.value - N2.value);
							L.append(E);
						}
						count++;
						R1.moveNext();
						R2.moveNext();
					} else if (count < N1.column && count == N2.column){
						E = new Entry(count, 0 - N2.value);
						L.append(E);
						count++;
						R2.moveNext();
					} else if (count == N1.column && count < N2.column){
						E = new Entry(count, N1.value);
						L.append(E);
						count++;
						R1.moveNext();
					} else if (count != N1.column && count != N2.column){
						count++;
					}
				}
				Entry N3 = null;
				if (R1.index() == -1){
					for (;R2.index() >= 0; R2.moveNext()){
						N3 = (Entry)R2.get();
						E = new Entry(N3.column, N3.value * -1);
						L.append(E);
					}
				} else if (R2.index() == -1){
					for (;R1.index() >= 0; R1.moveNext()){
						N3 = (Entry)R1.get();
						E = new Entry(N3.column, N3.value);
						L.append(E);
					}
				}
			}
		}
		return S;
	}

	// returns a new Matrix that is the transpose of this Matrix
	Matrix transpose(){
		Matrix T = new Matrix(this.getSize());
		for (int i = 0; i < this.getSize(); i++){
			List R = this.matrix[i];
			for (R.moveFront(); R.index() >= 0; R.moveNext()){
				Entry E = (Entry)R.get();
				T.changeEntry(E.column, i + 1, E.value);
			}
		}
		return T;
	}

	// returns a new Matrix that is the product of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix mult(Matrix M){
		if(this.getSize() != M.getSize()){
			throw new RuntimeException("can not call mult() matrices with different sizes");
		}
		Matrix U = new Matrix(this.getSize());
		Matrix T = M.transpose();
		double product = 0;
		for (int i = 0; i < this.getSize(); i++){
			List L = this.matrix[i];
			for (int j = 0; j < this.getSize(); j++){
				List R = T.matrix[j];
				product = dot(L, R);
				//System.out.println("dot: " + product);
				U.changeEntry(i + 1, j + 1, product);
			}
		}
		return U;
	}


	// Other functions

	// dot product function
	private static double dot(List P, List Q){
		double product = 0;
		P.moveFront();
		Q.moveFront();
		int count = 1;
		while (P.index() >=0 && Q.index() >= 0){
			Entry N1 = (Entry)P.get();
			Entry N2 = (Entry)Q.get();
			if (count == N1.column && count == N2.column){
				product += N1.value * N2.value;
				count++;
				P.moveNext();
				Q.moveNext();
			} else if (count == N2.column && count < N1.column){
				Q.moveNext();
				count++;
			} else if (count == N1.column  && count < N2.column){
				P.moveNext();
				count++;
			} else if (count != N1.column && count != N2.column) {
				count++;
			}
		}
		return product;
	}

	// overrides Object's toString() method
	public String toString(){
		String s = "";
		for (int i = 0; i < this.getSize(); i++){
			if (matrix[i].length() > 0) {
				s += i+1 + ": " + matrix[i]  +  "\n";
			}
		}
		return s;
	}
}