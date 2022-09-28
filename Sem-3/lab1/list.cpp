#include <iostream>
using namespace std;

template<typename T>
class List {
public:
    List();
    ~List();

    void push_back(int other_vertex, T data);
    void pop_front();
    void clear();
    int GetSize(){return Size;}

    void print();

    int getIndex(int vertex);

    int getVertex(int index);
    void changeVertex(int index);

    void removeAt(int index);

    T operator[](const int index);

private:

    template<typename T1>
    class Node{
    public:
        Node* next;

        int vertex;
        T1 data;

        Node(int vertex, T1 data, Node* next = nullptr){
            this->vertex = vertex;
            this->data = data;
            this->next = next;
        }
    };

    int Size;
    Node<T> *head;
};

template<typename T>
List<T>::List() {
    Size = 0;
    head = nullptr;
}

template<typename T>
void List<T>::push_back(int other_vertex, T data) {
    if(head == nullptr)
        head = new Node<T>(other_vertex, data);
    else
    {
        Node<T> *current = this->head;

        while (current->next != nullptr)
            current = current->next;

        current->next = new Node<T>(other_vertex,data);
    }
    Size++;
}

template<typename T>
T List<T>::operator[](const int index) {
            int counter = 0;
            Node<T> *current = head;

            while (current != nullptr)
            {
                if(counter == index) return current->data;
                current = current->next;
                counter++;
            }

            return T();
}

template<typename T>
void List<T>::pop_front() {
    if(head == nullptr) return;

    Node<T> *temp = head;
    head = head->next;
    delete temp;
    Size--;
}

template<typename T>
int List<T>::getIndex(int vertex) {
    if(head == nullptr) return -1;

    Node<T> *current = head;
    int counter = 0;

    while (current != nullptr){
        if(current->vertex == vertex) return counter;
        counter++;
    }

    return -1;

}

template<typename T>
void List<T>::clear() {
    while (Size) pop_front();
}

template<typename T>
List<T>::~List() {
    clear();
}

template<typename T>
void List<T>::removeAt(int index) {
    if(index == 0) {
        pop_front();
        return;
    }

    Node<T> *previous = this->head;

    for (int i = 0; i < index - 1; ++i)
        previous = previous->next;

    Node<T> *toDelete = previous->next;
    previous->next = toDelete->next;

    delete toDelete;
    Size--;
}

template<typename T>
void List<T>::print() {
    if(head == nullptr) return;

    Node<T> *current = this->head;

    while (current != nullptr){
        cout << "Edge:" <<  current->vertex << ", ";
        cout << "Data:" <<  current->data << ", ";
        current = current->next;
    }
        cout << endl;

}

template<typename T>
int List<T>::getVertex(int index) {
    int counter = 0;
    Node<T> **current = &head;

    while (*current != nullptr)
    {
        if(counter == index) return (*current)->vertex;
        *current = (*current)->next;
        counter++;
    }

    return -1;
}

template<typename T>
void List<T>::changeVertex(int index) {
    int counter = 0;
    Node<T> *current = head;

    while (current != nullptr)
    {
        if(counter == index) {
            current->vertex--;
            return;
        }
        current = current->next;
        counter++;
    }
}
