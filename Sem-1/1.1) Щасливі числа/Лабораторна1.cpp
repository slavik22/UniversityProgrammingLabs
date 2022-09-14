#include <iostream>
using namespace std;

int length_of_int(int N) {
	int number_of_digits = 0;
	
	do {
		++number_of_digits;
		N /= 10;
	} while (N);

	return number_of_digits;
}

int main()
{
	int N = 0;
	cout << "N:"; cin >> N;

	for (int i = 0; i < N; i++) {

		int length = length_of_int(i),
			n = i, sum1 = 0, sum2 = 0;

		if (length % 2 != 0) 
		{ 
			continue; 
		}

		for (int t = length; t > 0; t--)
		{
			if (t > length / 2) 
			{ 
				sum2 += n % 10; 
			}
			else 
			{ 
				sum1 += n % 10; 
			}

			n /= 10;
		}

		if (sum1 == sum2) { cout << i << endl; }
	}

	return 0;
}