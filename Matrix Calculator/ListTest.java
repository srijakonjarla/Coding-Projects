// Srija Konjarla
// skonjarl
// pa3
// ListTest.java

public class ListTest {
	public static void main(String[] args){
		List A = new List();
		List B = new List();
		List C = new List();
      
      	for(int i=1; i<=10; i++){
      		A.append(i*2);
         	B.append(i*3);
         	C.prepend(i*2);
      	}
      	System.out.println(A);
      	System.out.println(B);
      	System.out.println(C);
      
      	for(A.moveFront(); A.index() >= 0; A.moveNext()){
         	System.out.print(A.get()+" ");
      	}
      	System.out.println("\n");
      	for(B.moveBack(); B.index() >= 0; B.movePrev()){
         	System.out.print(B.get()+" ");
      	}
      	System.out.println("\n");
      	for(C.moveBack(); C.index() >= 0; C.movePrev()){
         	System.out.print(C.get()+" ");
      	}
      	System.out.println("\n");
      
      	System.out.println(A.equals(B));
      	System.out.println(A.equals(A));
      	System.out.println(A.equals(C));
      	System.out.println(B.equals(B));
      	System.out.println(C.equals(C));
      
      	A.moveFront();
      	for(int i=0; i<2; i++) A.moveNext();
      	A.insertBefore(0);                  
      	for(int i=0; i<6; i++) A.moveNext();
      	A.insertAfter(0);
      	for(int i=0; i<5; i++) A.movePrev();
        B.moveFront();
        System.out.println(B.get());
        C.moveBack();
        System.out.println(C.get());
        System.out.println(C.front());
        System.out.println(C.back());
      	C.delete();
      	C.deleteBack();
      	C.deleteFront();
      	System.out.println(C);
      	System.out.println(C.length());
      	C.clear();
     	System.out.println(C.length());
	}
}