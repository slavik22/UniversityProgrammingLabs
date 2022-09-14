//введення, вивведення та спрощення виразу
#include <iostream>
using namespace std;

struct BTREE {
    int dat;
    BTREE *l, *r;
};

int Next = ' ';

void inputchar();
BTREE* inputexpr();
BTREE* expression();
int number();
BTREE* simpl(BTREE*);

BTREE* newnode(int, BTREE*, BTREE*);
void printexpr(BTREE*);
void deleteexpr(BTREE*);

int main() {
    BTREE *t, *p;
    t = inputexpr();
    
    cout << "Tree -> Expression: "; printexpr(t); cout << endl;
    
    p = simpl(t);
    
    cout << "Simple Expression: "; printexpr(p); cout << endl;
    
    return 0;
}

BTREE* inputexpr() {
    inputchar();
    return expression();
}

void inputchar() {
    if (Next == EOF) return;
    while ((Next = getchar()) == ' ' || Next == '\n');
}

BTREE* newnode(int v, BTREE* l, BTREE* r) {
    BTREE* p = new BTREE;
    p->dat = v; p->l = l; p->r = r;
    return p;
}

BTREE* expression() {
    BTREE* p;
    char nx;

    if (Next == '(') {
        inputchar();
        p = expression();
        
        if (strchr("+-*/", Next)) {
            nx = Next; 
            inputchar();
            p = newnode(nx, p, expression());
        }

        if (Next != ')') {
            cout << "ERROR" << endl; 
            return nullptr;
        }

        inputchar();
        return p;
    }
    if (strchr("xyz", Next)) {
        nx = Next; 
        inputchar();
        return newnode(nx, NULL, NULL);
    }
    else return newnode(-number(), NULL, NULL);
}

int number() {
    int v = 0;
    if (Next < '0' || Next > '9') {
        cout << "ERROR " << endl; 
        return -1;
    }

    if (Next >= '0' && Next <= '9') {
        v = v * 10 + (Next - '0');
    }
    
    inputchar();

    return v;
}

void printexpr(BTREE* p) {
    int v = p->dat;

    if (v <= 0) cout << -v;

    else if (strchr("+-*/", v)) {
        cout << '('; 
        printexpr(p->l);
        cout << (char)v; 
        printexpr(p->r); 
        cout << ')';
    }
    else cout << (char)v;
}

BTREE* simpl(BTREE* p) {
    BTREE *pl, *pr;
    
    if (!p) return p;
    if ((p->dat < 0) || (strchr("xyz", p->dat))) return p;
    
    pl = p->l = simpl(p->l);
    pr = p->r = simpl(p->r);

    switch (p->dat) {

    case '+': 
        if ((pl->dat < 0) && (pr->dat < 0)) { 
            p->dat = (pl->dat) + pr->dat;
            delete pl; delete pr;
            return p; 
        }

        if (pl->dat == 0) { delete pl; delete p; return pr; }
        if (pr->dat == 0) { delete pr; delete p; return pl; }
        return p;

    case '-': 

        if ((pl->dat < 0) && (pr->dat < 0)) {
            p->dat = (pl->dat) - pr->dat;
            delete pl; delete pr;
            return p;
        }

        if (pr->dat == 0) { delete pr; delete p; return pl; }
        return p;
    case '*': 

        if ((pl->dat < 0) && (pr->dat < 0)) {
            p->dat = -((pl->dat) * pr->dat);
            delete pl; delete pr;
            return p;
        }

        if ((pl->dat == 0) || (pr->dat == 0)) {
            p->dat = 0; p->l = p->r = NULL;
            deleteexpr(pl); deleteexpr(pr); 
            return p;
        }
        if (pl->dat == -1) { delete pl; delete p; return pr; }
        if (pr->dat == -1) { delete pr; delete p; return pl; }
        return p;

    case '/': 
        if ((pl->dat < 0) && (pr->dat < 0)) {
            p->dat = -((pl->dat) / pr->dat);
            delete pl; delete pr;
            return p;
        }

        if ((pl->dat == 0) || (pr->dat == -1)) {
            deleteexpr(pr); delete p; 
            return pl;
        }
    }
    return p;
}

void deleteexpr(BTREE* p) {
    if (!p) return;
    deleteexpr(p->l); deleteexpr(p->r);
    delete p;
}
