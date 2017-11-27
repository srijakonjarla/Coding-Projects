// Srija Konjarla
// skonjarl
// pa3
// Sparse.java

import java.io.*;
import java.util.Scanner;

public class Sparse {
	public static void main (String[] args) throws IOException{
        Scanner in = null;
        PrintWriter out = null;
        Matrix A = null;
        Matrix B = null;
        int n = 0;
        int a = 0;
        int b = 0;
        int r = 0;
        int c = 0;
        double v = 0;

        if (args.length != 2) {
            System.err.println("Usage: Sparse infile outfile");
            System.exit(1);
        }

        in = new Scanner(new File(args[0]));
        out = new PrintWriter(new FileWriter(args[1]));

        while (in.hasNextInt()){
        	n = in.nextInt();
        	a = in.nextInt();
        	b = in.nextInt();

        	A = new Matrix(n);
        	B = new Matrix(n);

        	for (int i = 0; i < a; i++){
        		r = in.nextInt();
        		c = in.nextInt();
        		v = in.nextDouble();
        		A.changeEntry(r,c,v);
        	}

        	for (int i = 0; i < b; i++){
        		r = in.nextInt();
        		c = in.nextInt();
        		v = in.nextDouble();
        		B.changeEntry(r,c,v);
        	}
        }
        out.print("A has " + A.getNNZ() + " non-zero entries:\n" + A + "\n");
        out.print("B has " + B.getNNZ() + " non-zero entries:\n" + B+ "\n");

        out.print("(1.5)*A =\n" + A.scalarMult(1.5)+ "\n");

        out.print("A+B =\n" + A.add(B)+ "\n");

        out.print("A+A =\n" + A.add(A)+ "\n");

        out.print("B-A =\n" + B.sub(A)+ "\n");

        out.print("A-A =\n" + A.sub(A)+ "\n");

        out.print("Transpose(A) =\n" + A.transpose()+ "\n");

        out.print("A*B =\n" + A.mult(B)+ "\n");

        out.print("B*B =\n" + B.mult(B)+ "\n");

        in.close();
        out.close();
	}
}