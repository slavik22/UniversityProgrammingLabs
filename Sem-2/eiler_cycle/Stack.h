#pragma once
class Stack
{
	class node {
	public:
		int p;
		node* next;


		node(int p, node* next = nullptr) {
			this->p = p;
			this->next = next;
		}
	};

	node* head;

public:
	Stack() { head = nullptr;}
	
	node* getHead() { return head; }

	void push(int p) {
		if (!head) {
			head = new node(p);
		}
		else {
			node* t = new node(p, head);
			head = t;
		}
	}
	int pop() {
		if (!head) { return 0; }
		else{
			node* t = head;
			head = t->next;

			int r = t->p;
			delete t;

			return r;
		}


	}
};

