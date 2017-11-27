// Srija Konjarla
// skonjarl
// pa3
// MatrixTest.java

public class MatrixTest {
	public static void main (String[] args){
		Matrix A = new Matrix(10);
        Matrix B = new Matrix(10);
        Matrix C;
        A.changeEntry(1,1,1);
      	A.changeEntry(1,3,-6);
      	A.changeEntry(1,2,3);
      	A.changeEntry(2,4,9);
      	A.changeEntry(2,2,2);
      	A.changeEntry(2,3,-4);
      	A.changeEntry(3,6,10);
      	A.changeEntry(3,2,92);
      	A.changeEntry(3,3,-32);
      	A.changeEntry(1,3,0);
      	A.changeEntry(1,4,0);
      	A.changeEntry(2,2,0);
      	A.changeEntry(3,3,0);
      	System.out.println(A);

      	B.changeEntry(1,7,-4.5);
      	B.changeEntry(1,2,8);
      	B.changeEntry(1,1,-2);
      	B.changeEntry(2,1,1);
      	B.changeEntry(2,8,-2);
      	B.changeEntry(2,2,2);
      	B.changeEntry(3,9,-2.5);
      	B.changeEntry(3,2,-3);
      	B.changeEntry(3,1,-1);
      	B.changeEntry(1,1,0);
      	B.changeEntry(1,3,0);
      	B.changeEntry(2,8,0);
      	B.changeEntry(3,9,0);
      	System.out.println(B);

      	if (A.getNNZ() != 9){
      		System.out.println(false);
      	} else {
      		System.out.println(true);
      	}

      	if (B.getNNZ() != 9){
      		System.out.println(false);
      	} else {
      		System.out.println(true);
      	}

      	System.out.println(A.equals(B));
      	System.out.println(A.equals(A));

      	C = A.scalarMult(-2);
      	System.out.println(C);
      	System.out.println(A.equals(C));
      	C = A.scalarMult(-0.5);
      	System.out.println(C);
      	System.out.println(A.equals(C));
      	System.out.println(C.equals(C));

      	C = A.mult(A);
      	System.out.println(A.equals(C));
      	System.out.println(C.equals(C));

      	System.out.println(B.sub(A));
      	System.out.println(A.sub(A));

      	A.makeZero();
      	System.out.println(A.getNNZ());
      	B.makeZero();
      	System.out.println(B.getNNZ());
    }
}