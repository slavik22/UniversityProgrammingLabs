#include <iostream>
#include "DateTime.cpp"
#include "Graph.cpp"


void DateTimeTest(){
    DateTime d("27.09.2022 23:59:00");
    int ml = d + 60;
    DateTime d1(ml);

    cout << d1.toString() << endl;
    cout << d1.dayOfWeek() << endl;

}

void graphTest(){
    Graph g(4);

    g.addEdge(0,1);
    g.addEdge(2,3);

    g.print();

    cout  << "Is connected: " << g.isConnected() << endl;

    g.addEdge(1,2);

    cout << "Min path: " << g.minEdgeBFS(0,3);
}

int main() {
    DateTimeTest();
}
