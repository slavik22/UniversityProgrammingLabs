#include <iostream>
using namespace std;

template<typename T>
class List {
public:
    List();
    ~List();

    void push_back(T data);
    void pop_front();
    void clear();
    int GetSize(){return Size;}

    void print();

    int getIndex(T data);
    void removeAt(int index);

    T operator[](const int index);
private:

    template<typename T1>
    class Node{
    public:
        Node* next;
        T1 data;

        Node(T1 data = T1(), Node* next = nullptr){
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
void List<T>::push_back(T data) {
    if(head == nullptr) {
        head = new Node<T>(data);
    }
    else
    {
        Node<T> *current = this->head;

        while (current->next != nullptr)
            current = current->next;

        current->next = new Node<T>(data);
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
            return -1;
}

template<typename T>
void List<T>::pop_front() {
    Node<T> *temp = head;
    head = head->next;

    delete temp;
    Size--;
}

template<typename T>
int List<T>::getIndex(T data) {
    if(head == nullptr) return -1;

    Node<T> *current = head;

    for (int counter = 0; counter < GetSize(); counter++, current = current->next) {
        if(current->data == data) return counter;
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
        cout << current->data << ", ";
        current = current->next;
    }
        cout << endl;

}
