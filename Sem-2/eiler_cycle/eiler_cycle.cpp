#include <iostream>
#include "Stack.h"

using namespace std;

struct node {
    int d;
    node* nx;
};

node** build_graph(int& n);
void add(node*& gr, int v);
void del(node*& l, int v);

bool if_eiler(node** gr, int n);
void build_eiler_cycle(node** gr);

int main()
{
    int n;

    node** gr = build_graph(n);

    if (if_eiler(gr, n)) build_eiler_cycle(gr);

}

node** build_graph(int &n) {
    FILE* fi = fopen("graph.txt", "r");

    fscanf(fi, "%d", &n);
    node** gr = new node* [n];

    for (int i = 0; i < n; i++) gr[i] = NULL;

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {

            int v; fscanf(fi, "%d", &v);
           if(v) add(gr[i], j);
        }
    }

    return gr;
}
void add(node* &l, int v) {
    if (!l)
    {
        l = new node; l->d = v; l->nx = NULL;
        return;
    }   


    node* t = l;
    while (t->nx) t = t->nx;

    node* n = new node;
    n->d = v; n->nx = NULL;

    t->nx = n;

}
void del(node* &l, int v) {
    if (l->d == v) {
        node* t = l;
        l = l->nx;
        delete t;
        return;
    }

    node* temp = l;
    while (temp) {

        if (temp->nx && temp->nx->d == v) {
            node* t = l->nx;

            temp->nx = temp->nx->nx;
            delete t;
        }

        temp = temp->nx;
    }
}

bool if_eiler(node** gr, int n) {
    for (int i = 0; i < n; i++)
    {
        int count = 0;
        node* temp = gr[i];

        while (temp)
        {
            count++;
            temp = temp->nx;
        }

        if (count % 2 != 0) return false;
    }

    return true;
}

void build_eiler_cycle(node** gr) {
    FILE* fo = fopen("eiler_cycle.txt", "w");

    int v = 1;
    
    Stack st; st.push(v);

    while (st.getHead()) {
        int v = st.getHead()->p;

        if (!gr[v]) {
            st.pop();
            fprintf(fo, "%d ", v);
        }
        else {
            int u = gr[v]->d; 
            st.push(u);

            del(gr[v], u); del(gr[u], v);
        }
    }
}