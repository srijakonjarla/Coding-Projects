//-----------------------------------------------------------------------------
// Srija Konjarla
// skonjarl
// 12B
// August 17, 2017
// Test file for the Dictionary ADT
// DictionaryTest.c
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include "Dictionary.h"

int main(int arc, char* argv[]) {
    Dictionary A = newDictionary();
    insert(A, "1", "dog");
    insert(A, "2", "cat");
    insert(A, "3", "mouse");
    insert(A, "4", "chicken");
    insert(A, "5", "elephant");
    printDictionary(stdout, A);
    insert(A, "6", "potato");
    int c = size(A);
    printf("%d\n", c);
    printDictionary(stdout, A);
    //insert(A,"3", "tomato");
    delete(A, "4");
    printDictionary(stdout, A);
    char* a = lookup(A, "2");
    printf("%s\n", a);
    //delete(A, "9");
    makeEmpty(A);
    int b = size(A);
    printf("%d\n", b);
    printDictionary(stdout, A);
    freeDictionary(&A);
    return (EXIT_SUCCESS);
}

