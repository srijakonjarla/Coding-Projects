//-----------------------------------------------------------------------------
// Srija Konjarla
// skonjarl
// 12B
// August 17, 2017
// Dictionary ADT using hash tables
// Dictionary.c
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<string.h>
#include<assert.h>

#include "Dictionary.h"

const int tableSize = 101;

// Helper Functions
// rotate_left()
// rotate the bits in an unsigned int
unsigned int rotate_left(unsigned int value, int shift) {
    int sizeInBits = 8*sizeof(unsigned int);
    shift = shift & (sizeInBits - 1);
    if ( shift == 0 )
        return value;
    return (value << shift) | (value >> (sizeInBits - shift));
}
// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash(char* input) {
    unsigned int result = 0xBAE86554;
    while (*input) {
        result ^= *input++;
        result = rotate_left(result, 5);
    }
    return result;
}

// hash()
// turns a string into an int in the range 0 to tableSize-1
int hash(char* key){
    return pre_hash(key)%tableSize;
}

// private types and functions ------------------------------------------------

// NodeObj
typedef struct NodeObj {
    char* key;
    char* value;
    struct NodeObj* next;
} NodeObj;

typedef NodeObj* Node;

// Constructor
Node newNode(char* x, char* y) {
    Node N = malloc(sizeof(NodeObj));
    assert(N != NULL);
    N->key = calloc(strlen(x)+1, sizeof(char));
    strcpy(N->key, x);
    N->value = calloc(strlen(y)+1, sizeof(char));
    strcpy(N->value, y);
    N->next = NULL;
    return (N);
}

// Free Node
void freeNode(Node* pN) {
        Node N = *pN;
        free(N->key);
        free(N->value);
        free(N);
        free(*pN);
        *pN = NULL;
    }

typedef struct DictionaryObj {
    Node* T;
    int numItems;
} DictionaryObj;

Node findKey(Dictionary D, char* x){
   Node N = D->T[hash(x)];
   while(N != NULL && strcmp(N->key, x) != 0) {
       N = N->next;
   }
   return N;
}

void deleteAll(Node N){
       deleteAll(N->next);
       freeNode(&N);
   }

// Public Functions

// newDictionary()
// constructor for the Dictionary type
Dictionary newDictionary(void) {
    Dictionary D = malloc(sizeof(DictionaryObj));
    assert(D != NULL);
    D->T = calloc(tableSize+1, sizeof(Node*));
    D->numItems = 0;
    return D;
}

// freeDictionary()
// destructor for the Dictionary type
void freeDictionary(Dictionary* pD) {
    Dictionary D = *pD;   
    makeEmpty(D);
    free(*pD);
    *pD = NULL;
}

// isEmpty()
// returns 1 (true) if S is empty, 0 (false) otherwise
// pre: none
int isEmpty(Dictionary D){
    if( D == NULL){
	fprintf(stderr,"Dictionary Error: calling isEmpty() on NULL Dictionary\n");
	exit(EXIT_FAILURE);
    }
    if (D->numItems == 0){
        return 1;
    } else {
        return 0;
    }
}

// size()
// returns the number of (key, value) pairs in D
// pre: none
int size(Dictionary D){
    if(D == NULL){
        fprintf(stderr,"Dictionary Error: calling size() on NULL Dictionary\n");
	exit(EXIT_FAILURE);
    }
    return (D->numItems);
}

// lookup()
// returns the value v such that (k, v) is in D, or returns NULL if no 
// such value v exists.
// pre: none
char* lookup(Dictionary D, char* k){
    if(D == NULL){
       fprintf(stderr, "Dictionary Error: calling lookup() on Null Dictionary reference\n");
       exit(EXIT_FAILURE);
    }
    if(D->numItems == 0){
       fprintf(stderr, "Dictionary Error: calling lookup() on empty Dictionary\n");
       exit(EXIT_FAILURE);
    }
    Node N = findKey(D,k);
    if (N == NULL){
        return NULL;
    } else {
        return N->value;
    }
}

// insert()
// inserts new (key,value) pair into D
// pre: lookup(D, k)==NULL
void insert(Dictionary D, char* k, char* v){
    if(D == NULL){
        fprintf(stderr,"Dictionary Error: calling insert() on NULL Dictionary\n");
	exit(EXIT_FAILURE);
    }
    if(findKey(D,k) != NULL){
        fprintf(stderr,"Dictionary Error: cannot insert duplicate keys\n");
	exit(EXIT_FAILURE);
    }
    if (D->T[hash(k)] == NULL) { // list was initially empty
        D->T[hash(k)] = newNode(k, v);
        D->numItems++;
    } else {
        Node N = D->T[hash(k)];
        while (N->next != NULL) {
            N = N->next;
        }
        N->next = newNode(k, v);
        D->numItems++;
    }
}

// delete()
// deletes pair with the key k
// pre: lookup(D, k)!=NULL
void delete(Dictionary D, char* k){
    if (D == NULL) {
        fprintf(stderr, "Dictionary Error: calling delete() on NULL Dictionary\n");
        exit(EXIT_FAILURE);
    }
    if (D->numItems == 0) {
        fprintf(stderr, "Dictionary Error: calling delete() on empty Dictionary\n");
        exit(EXIT_FAILURE);
    }
    if (lookup(D, k) == NULL) {
        fprintf(stderr, "Dictionary Error: cannot delete non-existent key\n");
        exit(EXIT_FAILURE);
    } 
    Node N = findKey(D,k);
    if (D->numItems <= 1) { // if only item exists delete the head
        D->T[hash(k)] = NULL;
        freeNode(&N);
        D->numItems--;
    } else if (N == D->T[hash(k)] && findKey(D,k) != NULL){
        D->T[hash(k)] = N->next;
        freeNode(&N);
        D->numItems--;
    } else {
        Node P;
        for (P = D->T[hash(k)]; N->next != NULL && strcmp(N->next->key, k) != 0; P = P->next){
        }
        P->next = N->next;
        freeNode(&N);
        freeNode(&P);
        D->numItems--;
    }
}

// makeEmpty()
// re-sets D to the empty state.
// pre: none
void makeEmpty(Dictionary D){
    if( D == NULL){
	fprintf(stderr,"Dictionary Error: calling makeEmpty() on NULL Dictionary\n");
	exit(EXIT_FAILURE);
    }
    for(int i=0; i<tableSize; i++){
       deleteAll(D->T[i]);
       D->T[i] = NULL;
    }
    D->numItems = 0;
}

// printDictionary()
// pre: none
// prints a text representation of D to the file pointed to by out
void printDictionary(FILE* out, Dictionary D){
    Node N;
    if (D == NULL) {
        fprintf(stderr, "calling printDictionary() on NULL Dictionary\n");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < tableSize; i++){
        for (N = D->T[i]; N != NULL; N = N->next ){
            fprintf(out, "%s %s\n", N->key, N->value);
        }
    }
}
