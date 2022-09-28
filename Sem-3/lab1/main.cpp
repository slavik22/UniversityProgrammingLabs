#include <iostream>
#include <vector>
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
    Graph<char> gr;

    gr.addVertex('a');
    gr.addVertex('b');
    gr.addVertex('c');
    gr.addVertex('d');

    gr.addEdge(0,3,'1');
    gr.addEdge(1,2,'1');
    gr.addEdge(2,3,'3');


    cout << gr.minEdgeBFS(0,3);

    gr.print();
}

int main() {
    DateTimeTest();
    graphTest();
}
