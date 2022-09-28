#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>

#include "list.cpp"

using namespace std;

template<class T>
class Graph{

private:
    template<class T1>
    class Vertex{
    public:
        T1 data;
        List<T1> edges;

        Vertex(T1 data){
            this->data = data;
        }

        void print(){
            cout << "Vertex data: " << data << endl;
            edges.print();
        }
    };

    vector<Vertex<T>> array;


public:

    void addVertex(T data){
        array.emplace_back(data);
    }
    void addEdge(int u, int v, T data) {
        if(u >= array.size() || v >= array.size())
        {
            cout << "One of vertexes of edge is not in graph";
            return;
        }

        array[u].edges.push_back(v,data);
        array[v].edges.push_back(u,data);
    }

    void removeVertex(int u) {
        if(u >= array.size()){
            cout << "Vertex is out of range";
            return;
        }

        array.erase(array.begin() + u);

        for (int i = 0; i < array.size(); ++i) {
            for (int j = 0; j < array[i].edges.GetSize(); ++j) {

                int t = array[i].edges.getVertex(j);
                if(t == u)
                    array[i].edges.removeAt(j);
                else if(t > u)
                    array[i].edges.changeVertex(j);
            }

        }
    }
    void removeEdge(int u, int v) {
        if(u >= array.size() || v >= array.size()){
            cout << "Vertex is out of range";
            return;
        }

        int u_index = array[u].edges.getIndex(v);
        int v_index = array[v].edges.getIndex(u);

        if(v_index == -1 || u_index == 1) {
            cout << "No edge in graph";
            return;
        };

        array[u].edges.removeAt(u_index);
        array[v].edges.removeAt(v_index);
    }

    void print() {
        for(int i = 0; i < array.size(); i++){
            cout<<"Vertex #" << i<< endl;
            array[i].print();
        }
        cout<<endl;
    }
    bool isConnected() {
        int n = array.size();
        for (int i = 0; i < n; i++)
        {
            vector<bool> visited(n);

            DFS(i, visited);

            if (find(visited.begin(), visited.end(), false) != visited.end())
                return false;

        }
        return true;
    }
    int minEdgeBFS(int u,int v)
    {
        int n = array.size();
        vector<bool> visited(n, 0);
        vector<int> distance(n, 0);

        queue <int> Q;
        distance[u] = 0;

        Q.push(u);
        visited[u] = true;
        while (!Q.empty())
        {
            int x = Q.front();
            Q.pop();

            for (int i=0; i < array[x].edges.GetSize(); i++)
            {
                int t = array[x].edges.getVertex(i);

                if (visited[t]) continue;

                distance[t] = distance[x] + 1;
                Q.push(t );
                visited[t] = 1;
            }
        }
        return distance[v];
    }

    void DFS(int v, vector<bool> &visited) {
        visited[v] = true;

        for (int i = 0; i < array[v].edges.GetSize(); i++ )
        {
            int u = array[v].edges.getVertex(i);
            if (!visited[u]) DFS(u, visited);
        }

    }

};

