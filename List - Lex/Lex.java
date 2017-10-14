//-----------------------------------------------------------------------------
// Srija Konjarla
// skonjarl
// pa1
// Class to Sort an array in alphabetical order
// Lex.java
//-----------------------------------------------------------------------------

import java.util.Scanner;
import java.io.*;

public class Lex {

    public static List insertionSort(List L, String[] T) {
        int n = T.length;
        int i;
        int j;

        if (n == 0) {
            throw new RuntimeException("EmptyArray");
        }
        L.append(0);
            for (i = 1; i < n; i++) {
            L.moveFront();
            while (L.index() >= 0) {
                if (T[i].compareTo(T[L.get()]) == 0) {
                    L.insertAfter(i);
                    L.moveBack();
                }
                if (T[i].compareTo(T[L.get()]) < 0) {
                    L.insertBefore(i);
                    L.moveBack();

                }
                L.moveNext();
            }
            if (L.index() < 0 && T[i].compareTo(T[L.back()]) > 0) {
                L.append(i);
            }
        }
        return L;
    }

    public static void main(String[] args) throws IOException {

        // Variable
        Scanner in = null;
        PrintWriter out = null;
        String line = "";
        String[] token = null;
        List list = null;
        String temp;
        int i, j, lineNumber = 0;

        // Usage Message
        if (args.length != 2) {
            System.err.println("Usage: Lex infile outfile");
            System.exit(1);
        }

        // Initialize in and out variables
        in = new Scanner(new File(args[0]));
        out = new PrintWriter(new FileWriter(args[1]));

        // Count lines and populate array
        while (in.hasNextLine()) {
            lineNumber++;
            line += in.nextLine() + " ";
        }

        // Add elements into array
        in = new Scanner(new File(args[0]));
        token = new String[lineNumber];

        for (i = 0; i < token.length; i++) {
            token[i] = in.nextLine();
        }

        // Create new list
        list = new List();
        list = insertionSort(list, token);

        // Print the list
        list.moveFront();
        while (list.index() >= 0) {
            out.println(token[list.get()]);
            list.moveNext();
        }

        in.close();
        out.close();
    }
}
