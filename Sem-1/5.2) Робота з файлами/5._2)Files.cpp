#include <iostream>
#include <stdio.h>

using namespace std;

const char* textFileName = "text.txt";
const char* resultFileName = "result.txt";

const int n = 2;
const char * dictionary[n] = { "a", "b"};

bool isInDictionary(char* str);


int main()
{
	FILE* file1;
	FILE* file2;
	
	char str[50];


	file1 = fopen(textFileName, "r");
	file2 = fopen(resultFileName, "w");


	while (fscanf(file1, "%s", str) != -1) {
		if (isInDictionary(str)) {
			continue;
		}

		fprintf(file2, "%s ", str);
	}

	fclose(file1);
	fclose(file2);
}

bool isInDictionary(char * str) {

	for (int i = 0; i < n; i++)
	{
		if (!strcmp(str, dictionary[i]))
		{
			return true;
		}
	}

	return false;
}
