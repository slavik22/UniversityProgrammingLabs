#include <iostream>
#include <string>

using namespace std;

struct rule {  // формула підстановки
    char * from; // P
    char * to; // Q

    bool final; // заключительная

    rule() {
        this->from = NULL;
        this->to = NULL;
        this->final = false;
    }

    rule(char from[100], char to[100], bool final = false) {
        this->from = from;
        this->to = to;
        this->final = final;
    }
};
struct node {

    rule r;
    node* next;

    ~node() {
        delete next;
    }
};

node* rules_list();
void print_list(node* nd);

string nam(string s, node* r);

int main()
{
    char s[] = "Slava";

    node* r = rules_list();

    cout << nam(s,r);

}

node* rules_list() {

    node* t;
    
    char from[100];
    char to[100];

    bool final;
    
    static int count = 0;

    cout << ++count << " rule: ";
    
    if (cin >> from >> to >> final)
    {
        t = new node();

        t->r = rule(from, to,final);
        t->next = rules_list();

        return t;
    }
    else {
        return nullptr;
    }

}
void print_list(node* nd) {
   
    while (nd)
    {
        cout << nd->r.from << "-> " << nd->r.to;
        nd = nd->next;
    }

    cout << endl;
}

string nam(char s[100], node* r) {

    while (r)
    {
        int pos;
        
        if (strstr(s ,r->r.from)) {

            int l = strlen(r->r.from);

            s.erase(pos, l);
            s.insert(pos, r->r.to);

            return r->r.final ? s : nam(s, r);
        }
        else {
            return s;
        }

        r = r->next;
    }
    
    if (r && s.length() == 0) s.append(r->r.to);

};