#include <iostream>
#include <algorithm>
#include <vector>
#include "list.cpp"
#include <map>
#include <queue>

using namespace std;

class Graph{
public:

    vector<List<int>*> array;

    Graph(map<int,int> edges);

    void addEdge(int u,int v);
    void addVertex(int u);

    void removeVertex(int u);
    void removeEdge(int u,int v);

    void print();

    bool isConnected();
    int minEdgeBFS(int u,int v);
private:
    void DFS(int v, vector<bool> &visited);

};

Graph::Graph(map<int, int> edges) {
    map<int, int>::iterator itr;
    for (itr = edges.begin(); itr != edges.end(); ++itr)
        addEdge(itr->first,itr->second);
}

void Graph::addEdge(int u, int v) {
    if(array[u] == nullptr)
        addVertex(u);
    if(array[v] == nullptr)
        addVertex(v);

    array[u]->push_back(v);
    array[v]->push_back(u);
}

void Graph::addVertex(int u) {
    if(array.at(u) == nullptr)
        array[u] = new List<int>();
    else
        cout << "Vertex is already in graph";
}

void Graph::removeVertex(int u) {
    List<int> *vertex = array[u];
    vertex->clear();

    array[u] = nullptr;
}

void Graph::removeEdge(int u, int v) {
    if(array[u] == nullptr || array[v] == nullptr){
        cout << "One of vertexes is not in graph";
        return;
    }

    int v_index = array[u]->getIndex(v);
    int u_index = array[v]->getIndex(u);

    if(v_index == -1 || u_index == 1) {
        cout << "No edge in graph";
        return;
    };

    array[u]->removeAt(v_index);
    array[v]->removeAt(u_index);
}

void Graph::print() {
    for(int i = 0; i < array.size(); i++){
        cout<<i<<"-->";
        array[i]->print();
    }
    cout<<endl;
}

void Graph::DFS(int v, vector<bool> &visited) {
    visited[v] = true;

    List<int> *v_list = array[v];

    for (int i = 0; i < v_list->GetSize(); i++ )
    {
        int u = (*v_list)[i];

        if (!visited[u]) DFS(u, visited);
    }

}

bool Graph::isConnected() {
    int n = array.size();

    for (int i = 0; i < n; i++)
    {
        vector<bool> visited(n);

        DFS( i, visited);

        if (find(visited.begin(), visited.end(), false) != visited.end()) {
            return false;
        }
    }

    return true;
}

int Graph::minEdgeBFS(int u,int v)
{
    int n = array.size();

    // visited[n] for keeping track of visited
    // node in BFS
    vector<bool> visited(n, 0);

    // Initialize distances as 0
    vector<int> distance(n, 0);

    // queue to do BFS.
    queue <int> Q;
    distance[u] = 0;

    Q.push(u);
    visited[u] = true;
    while (!Q.empty())
    {
        int x = Q.front();
        Q.pop();

        for (int i=0; i< array[x]->GetSize(); i++)
        {
            if (visited[ (*array[x])[i] ]) continue;

            // update distance for i
            distance[(*array[x])[i]] = distance[x] + 1;
            Q.push((*array[x])[i] );
            visited[(*array[x])[i]] = 1;
        }
    }
    return distance[v];
}


