//============================================================================
// Name        : CPPTests.cpp
// Author      : Abhilash Nanda
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <pthread.h>
#include <stdlib.h>

#define THREAD_NUMS 3

using namespace std;

int changeNum = 10;

pthread_mutex_t mutex1 = PTHREAD_MUTEX_INITIALIZER;


void *func(void*);



/*int main(int argc, char** argv) {
	pthread_t threads[THREAD_NUMS];
	pthread_attr_t attr;

//	pthread_attr_init(&attr);
//	pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_JOINABLE);

	for(int i = 0; i < THREAD_NUMS; i++){
		int res = pthread_create(&threads[i], NULL, func, (void *)i);
		if(res) cout << "Error creating thread number " << i << endl;
	}
//	pthread_attr_destroy(&attr);
//	for(int i = 0; i < THREAD_NUMS; i++){
//		int res = pthread_join(threads[i], NULL);
//		if(res){
//			cout << "Unable to join" << endl;
//			exit(-1);
//		}
//	}

	pthread_exit(NULL);

	return 0;
}
*/
void *func(void *threadID){
	pthread_mutex_lock(&mutex1);
	cout << "before: " << changeNum << endl;
	changeNum++;
	changeNum *= changeNum;
	changeNum /= 2;
	changeNum--;
	cout << "after: " << changeNum << endl;
	pthread_mutex_unlock(&mutex1);
	pthread_exit(NULL);
}

