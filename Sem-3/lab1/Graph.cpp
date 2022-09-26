#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>

#include "list.cpp"

using namespace std;

class Graph{

public:
    Graph(int v) {
        n = v;
        array = new List<int> *[n];

        for (int i = 0; i < n; ++i) array[i] = nullptr;
    }

    void addVertex(int u){
        if(u >= n){
            cout << "Vertex is out of range";
            return;
        }
        if(array[u] == nullptr){
            array[u] = new List<int>();
        }
    }

    void addEdge(int u, int v) {
        addVertex(u);
        array[u]->push_back(v);

        addVertex(v);
        array[v]->push_back(u);

    }

    void removeVertex(int u) {
        if(u >= n){
            cout << "Vertex is out of range";
            return;
        }
        if(array[u] == nullptr){
            cout << "Vertex is not in graph";
            return;
        }

        List<int>* vertex = array[u];
        array[u] = nullptr;
        vertex->clear();

        for (int i = 0; i < n; ++i) {
            if(array[i] != nullptr){
                int index = array[i]->getIndex(u);
                if(index >= 0) array[i]->removeAt(index);
            }

        }
    }
    void removeEdge(int u, int v) {
        if(u >= n || v >= n){
            cout << "Vertex is out of range";
            return;
        }

        if(array[u] == nullptr || array[v] == nullptr){
            cout << "One of vertexes is not in graph";
            return;
        }

        int u_index = array[u]->getIndex(v);
        int v_index = array[v]->getIndex(u);

        if(v_index == -1 || u_index == 1) {
            cout << "No edge in graph";
            return;
        };

        array[u]->removeAt(u_index);
        array[v]->removeAt(v_index);

        if(array[u]->GetSize() == 0) removeVertex(u);
        if(array[v]->GetSize() == 0) removeVertex(v);
    }
    void print() {
        for(int i = 0; i < n; i++){
            if(array[i] != nullptr){
                cout<<i<<": ";
                array[i]->print();
            }
        }
        cout<<endl;
    }
    bool isConnected() {
        for (int i = 0; i < n; i++)
        {
            vector<bool> visited(n);

            DFS(i, visited);

            if (find(visited.begin(), visited.end(), false) != visited.end()) {
                return false;
            }

        }

        return true;
    }
    int minEdgeBFS(int u,int v)
    {
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

private:
    List<int>** array;
    int n;



    void DFS(int v, vector<bool> &visited) {
        visited[v] = true;

        List<int> *v_list = array[v];

        for (int i = 0; i < v_list->GetSize(); i++ )
        {
            int u = (*v_list)[i];

            if (!visited[u]) DFS(u, visited);
        }

    }

};

