#include <iostream>
#include <Windows.h>
#include <vector>

using namespace std;

const char* fileName = "dataBase.bin";
const char* tempFileName = "tempFile.bin";


struct date {
	int day = 0;
	int month = 0;
	int year = 0;
};

struct user {
	char email[100] = "";
	char login[100] = "";
	char password[100] = "";
	date timeOfRegistration;
} User;



void printDatabase(bool fromBeginning = true);
void readDatabase();
void deleteUser();
void editUser();
void searchUsers();

int main()
{
	SetConsoleOutputCP(1251);

	int answer;

	while (1)
	{
		cout << "---------------------------" << endl;
		cout << "Виберіть відповідний пункт:" << endl;
		cout << "0) Вихід" << endl;
		cout << "1) Створити базу даних" << endl;
		cout << "2) Перегляд бази даних" << endl;
		cout << "3) Добавити користувачів" << endl;
		cout << "4) Видалити користувача" << endl;
		cout << "5) Редагувати користувача" << endl;
		cout << "6) Вибірка користувачів" << endl;
		cout << "---------------------------" << endl;

		cin >> answer;

		switch (answer)
		{
		case 1:
			printDatabase();
			break;
		case 2:
			readDatabase();
			break;
		case 3:
			printDatabase(false);
			break;
		case 4:
			deleteUser();
			break;
		case 5:
			editUser();
			break;
		case 6:
			searchUsers();
			break;
		case 0:
		default:
			return 0;
			break;
		}

	} 
}

void printDatabase(bool fromBeginning) {
	const char* mode = fromBeginning ? "wb" : "ab";

	FILE* file = fopen(fileName, mode);

	int if_continue = 1;

	do
	{
		cout << "Email: "; cin >> User.email;
		cout << "Login: "; cin >> User.login;
		cout << "Password: "; cin >> User.password;
		cout << "Date:" << endl << "day: "; cin >> User.timeOfRegistration.day;
		cout << "month: "; cin >> User.timeOfRegistration.month;
		cout << "year: "; cin >> User.timeOfRegistration.year;

		fwrite(&User, sizeof(user), 1, file);
		
		cout << "Continue(1/0): "; cin >> if_continue;

	} while (if_continue);

	fclose(file);
}

void readDatabase() {
	FILE * file = fopen(fileName, "rb");
	
	int count = 0;

	while (fread(&User, sizeof(user), 1, file))
	{
		cout << count << ") Email:" << User.email << ", ";
		cout << "Login: " << User.login << ", ";
		cout << "Password: " << User.password << ", ";
		cout << "Date: " << User.timeOfRegistration.day << "." << User.timeOfRegistration.month << "." << User.timeOfRegistration.year << endl;
		count++;
	}

	fclose(file);
}

void deleteUser() {
	readDatabase();

	int id;
	cout << "Видалити користувача під номером: "; cin >> id;

	FILE* file = fopen(fileName, "rb");
	FILE* tempFile = fopen(tempFileName, "wb");

	int count = 0;

	while (fread(&User, sizeof(struct user), 1, file))
	{
		if (count != id) fwrite(&User, sizeof(struct user), 1, tempFile);
		count++;
	};

	fclose(file); fclose(tempFile);

	remove(fileName);
	rename(tempFileName, fileName);
}

void editUser() {
	readDatabase();

	int id;
	cout << "Редагувати користувача під номером: "; cin >> id;

	cout << "Email: "; cin >> User.email;
	cout << "Login: "; cin >> User.login;
	cout << "Password: "; cin >> User.password;
	cout << "Date:" << endl << "day: "; cin >> User.timeOfRegistration.day;
	cout << "month: "; cin >> User.timeOfRegistration.month;
	cout << "year: "; cin >> User.timeOfRegistration.year;
	
	user TempUser;

	FILE* file = fopen(fileName, "rb");
	FILE* tempFile = fopen(tempFileName, "wb");

	int count = 0;

	while (fread(&TempUser, sizeof(struct user), 1, file))
	{
		count == id ? fwrite(&User, sizeof(struct user), 1, tempFile) : fwrite(&TempUser, sizeof(struct user), 1, tempFile);
		count++;
	};

	fclose(file); fclose(tempFile);

	remove(fileName);
	rename(tempFileName, fileName);
}

void searchUsers() {
	cout << "Пошук користувачів з полями: " << endl; 

	cout << "Email: "; cin >> User.email;
	cout << "Login: "; cin >> User.login;
	cout << "Password: "; cin >> User.password;
	cout << "Date:" << endl << "day: "; cin >> User.timeOfRegistration.day;
	cout << "month: "; cin >> User.timeOfRegistration.month;
	cout << "year: "; cin >> User.timeOfRegistration.year;

	user tempUser;
	vector <user> filteredUsers;

	FILE* file = fopen(fileName, "rb");
	
	while (fread(&tempUser, sizeof(user), 1, file))
	{
		if (strcmp(User.email,"-") && strcmp(User.email, tempUser.email) ) continue;
		else if (strcmp(User.login, "-") && strcmp(User.login, tempUser.login)) continue;
		else if (strcmp(User.password, "-") && strcmp(User.password, tempUser.password)) continue;
		else if (User.timeOfRegistration.day != 0 && User.timeOfRegistration.day != tempUser.timeOfRegistration.day) continue;
		else if (User.timeOfRegistration.month != 0 && User.timeOfRegistration.month != tempUser.timeOfRegistration.month) continue;
		else if (User.timeOfRegistration.year != 0 && User.timeOfRegistration.year != tempUser.timeOfRegistration.year) continue;

		filteredUsers.push_back(tempUser);
	}
	fclose(file);
		
	for (int i = 0; i < filteredUsers.size(); i++)
	{
		cout << i << ") Email:" << filteredUsers[i].email << ", ";
		cout << "Login: " << filteredUsers[i].login << ", ";
		cout << "Password: " << filteredUsers[i].password << ", ";
		cout << "Date: " << filteredUsers[i].timeOfRegistration.day << "." << filteredUsers[i].timeOfRegistration.month << "." << filteredUsers[i].timeOfRegistration.year << endl;
	}
}