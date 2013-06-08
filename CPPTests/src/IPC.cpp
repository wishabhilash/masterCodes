/*
 * IPC.cpp
 *
 *  Created on: 06-Oct-2012
 *      Author: wish
 */
#include <iostream>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <string>

using namespace std;

int gvar = 10;

int main(int argc, char **argv){
	string str;
	pid_t pID;
	int lvar = 10;
	if((pID = vfork()) == 0){
		str = "Child process";
		gvar++;
		lvar++;
	}else if(pID < 0){
		cerr << "Error creating child process";
		exit(1);
	}else{
		str = "Parent";
	}
	cout << str << endl;
	cout << gvar << endl;
	cout << lvar << endl;
	return 0;
}



