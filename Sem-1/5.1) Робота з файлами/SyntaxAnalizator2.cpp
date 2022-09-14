#include <string>
#include <iostream>
#include <fstream>

using namespace std;

const string fileName = "text.txt";

const string initializationDeclaration = "var";

const string variableTypes[5] = {
	 "integer", "double", "float", "char", "string"
};

const string keyWords[12] = {
	"var", "char", "integer", "string", "double", "float", "if",
	"else", "switch", "for", "do","while"
};

enum class codeState
{
	SUCCESS,
	END,
	VAR_IS_MISSED,
	VARIABLE_SYNTAX_ERROR,
	VARIABLE_FIST_LETTER_ERROR,
	VARIABLE_IS_KEYWORD,
	VARIABLE_TYPE_ERROR,
};

codeState variable(string listOfVariables, int& pos);
codeState typeOfVariables();
codeState listOfVariables();
codeState initialization();
codeState listOfInitializations();
codeState variablesDescription();
void analys();
string readFile();

int main()
{
	analys();
}

codeState variable(string listOfVariables, int& pos) {
	string temp;

	if (listOfVariables[pos] == ',') pos += 1;

	if (!isalpha(listOfVariables[pos]))
		return codeState::VARIABLE_FIST_LETTER_ERROR;

	while (listOfVariables[pos] != ',' && listOfVariables[pos] != ':')
	{
		if (isalnum(listOfVariables[pos]))
		{
			temp += listOfVariables[pos];
			pos += 1;
		}
		else
			return codeState::VARIABLE_SYNTAX_ERROR;
	}

	for (int i = 0; i < 12; i++)
		if (temp == keyWords[i])
			return codeState::VARIABLE_IS_KEYWORD;

	return codeState::SUCCESS;
}

codeState typeOfVariables() {
	string temp = readFile();
	temp.pop_back();

	for (int i = 0; i < 5; i++)
		if (temp == variableTypes[i])
			return codeState::SUCCESS;

	return codeState::VARIABLE_TYPE_ERROR;
}

codeState listOfVariables() {
	codeState result = codeState::SUCCESS;

	string list = readFile();
	int pos = 0;

	if (list.length() == 0) return codeState::END;

	while ((result = variable(list, pos)) == codeState::SUCCESS && list[pos] != ':');

	return result;
}

codeState initialization() {
	codeState result = codeState::SUCCESS;

	if ((result = listOfVariables()) != codeState::SUCCESS)
		return result;

	if ((result = typeOfVariables()) != codeState::SUCCESS)
		return result;

	return result;
}

codeState listOfInitializations() {
	codeState result = codeState::SUCCESS;

	while ((result = initialization()) == codeState::SUCCESS);

	return result;
}

codeState variablesDescription() {
	if (readFile() != initializationDeclaration)
		return codeState::VAR_IS_MISSED;

	return listOfInitializations();

}

void analys() {

	codeState result = variablesDescription();
	string massage;

	switch (result)
	{
	case codeState::END:
		massage = "SUCCESS. Your code is ok";
		break;
	case codeState::VAR_IS_MISSED:
		massage = "Error of initialization. Probably you missed initialization keyword";
		break;
	case codeState::VARIABLE_SYNTAX_ERROR:
		massage = "VARIABLE_SYNTAX_ERROR. Variale is incorrect";
		break;
	case codeState::VARIABLE_FIST_LETTER_ERROR:
		massage = "Variable starts with incorrect symbol";
		break;
	case codeState::VARIABLE_IS_KEYWORD:
		massage = "Variable is a system keyword";
		break;
	case codeState::VARIABLE_TYPE_ERROR:
		massage = "Type of variable is incorrect";
		break;
	}

	cout << massage;
}

string readFile() {
	static ifstream file(fileName);

	if (!file.is_open()) return "File error";
	if (file.eof()) {
		file.close();
		return "";
	}else{
		string text;
		file >> text;
		return text;
	}
}

