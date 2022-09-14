#include <string>
#include <iostream>

using namespace std;

struct code {
	string text;
	int pos = 0;

	char getChar() {
		return text[pos];
	}
};

enum class codeState
{
	SUCCESS,
	VAR_IS_MISSED,
	VARIABLE_SYNTAX_ERROR,
	VARIABLE_FIST_LETTER_ERROR,
	VARIABLE_IS_KEYWORD,
	VARIABLE_TYPE_ERROR,
	ERROR_OF_SEMILICON
};

const string initializationDeclaration = "var";

const string variableTypes[5] = {
	 "integer", "double", "float", "char", "string"
};

const string keyWords[12] = {
	"var", "char", "integer", "string", "double", "float", "if",
	"else", "switch", "for", "do","while"
};


codeState variable(code &str) {
	string temp;

	if (!isalpha(str.getChar()))
		return codeState::VARIABLE_FIST_LETTER_ERROR;

	while (str.getChar() != ',' && str.getChar() != ':')
	{
		if (isalnum(str.getChar()))
		{
			temp += str.getChar();
			str.pos++;
		}
		else
			return codeState::VARIABLE_SYNTAX_ERROR;
	}

	for (int i = 0; i < 12; i++)
		if (temp == keyWords[i])
			return codeState::VARIABLE_IS_KEYWORD;
	
	return codeState::SUCCESS;
}

codeState typeOfVariables(code &str) {
	string temp;

	while (str.getChar() != ';')
	{
			temp += str.getChar();
			str.pos++;
	}
	for (int i = 0; i < 5; i++)
		if (temp == variableTypes[i])
			return codeState::SUCCESS;

	return codeState::VARIABLE_TYPE_ERROR;
}

codeState listOfVariables(code &str) {
	codeState result = codeState::SUCCESS;

	while ((result = variable(str)) == codeState::SUCCESS && str.getChar() != ':')
		str.pos++;

	return result;
}

codeState initialization(code& str) {
	codeState result = codeState::SUCCESS;

	if ((result = listOfVariables(str)) != codeState::SUCCESS)
		return result;
	
	str.pos++;

	if ((result = typeOfVariables(str)) != codeState::SUCCESS)
		return result;

	return result;
}

codeState listOfInitializations(code& str) {
	codeState result = codeState::SUCCESS;
	
	do {
		result = initialization(str);
		if (str.text[str.pos + 1] == ';')
			break;
		else 
			str.pos += 2;
	} while (result == codeState::SUCCESS);

	return result;
}

codeState variablesDescription(code &str) {
	int len = initializationDeclaration.length();
	string temp = str.text.substr(0,len);

	if (temp != initializationDeclaration)
		return codeState::VAR_IS_MISSED;
	
	str.pos += len + 1;

	return listOfInitializations(str);
	
}

void analys(code& str) {
	
	codeState result = variablesDescription(str);
	string massage;

	switch (result)
	{
	case codeState::SUCCESS:
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
		massage = "Type of variables are incorrect";
		break;
	}

	cout << massage << ". Position: " << str.pos;
}

int main()
{
	code str;

	//cin >> str.text;
	str.text = "var a,b,c:string; a,b,for:integer;;";

	analys(str);
}

