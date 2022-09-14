#include <iostream>
#include "Stack.h"

using namespace std;
void quicksort(int* arr, int n);


int main()
{
    int arr[5] = { 5,1,3,4,2 };
    quicksort(arr, 5);

    for (int i = 0; i < 5; i++) cout << arr[i] << ",";
}

void quicksort(int *arr, int n)
{
    int bs, low, high, i, j;
    i = j = 0;

    Stack stack;
    stack.push(n - 1);
    stack.push(0);
    
    while (stack.getHead()) {                  
        low = stack.pop();
        high = stack.pop();

        if ( ((high - low) == 1) && ( arr[low] > arr[high]) ) swap(arr[low], arr[high]);
        else {
            bs = arr[(low + high) / 2];
            i = low;
            j = high;

            while (i <= j) {
                while (arr[i] < bs) i++;
                while (arr[j] > bs) j--;
                
                if (i <= j) swap(arr[i++], arr[j--]);
            } 
        }

        if (low < j) {
            stack.push(j);
            stack.push(low);
        }
        if (i < high) {
            stack.push(high);
            stack.push(i);
        }
    } 
}