/* 
 * File:   main.c
 * Author: Emre Çelik - 141044024
 *
 * Created on March 10, 2017, 10:37 AM
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
/*
 * 
 */
float f(float x);
float calcErr(char* stopCrt,float p,float pre);
void bisection(float a , float b,char* stopCrt,float epsilon);

int main(int argc, char** argv) {
    if(argc != 5){
        printf("Argumants must be 5!\n");
        return 0;
    }
    float a = atof(argv[1]);
    float b = atof(argv[2]);
    char* stopCriterion = argv[3];
    float epsilon = atof(argv[4]);
    bisection(a,b,stopCriterion,epsilon);
    return 0;
}

float calcErr(char* stopCrt,float p,float pre){
    if(strcmp(stopCrt,"DISTANCE_TO_ROOT") == 0)
        return fabs(f(p));
    if(strcmp(stopCrt,"ABSOLUTE_ERROR") == 0)
        return fabs(p - pre);
    if(strcmp(stopCrt,"RELATIVE_ERROR") == 0)
        return fabs((p - pre)/p);
}

float f(float x){
    return x + 1 - 2*sin((x * M_PI / 180) * 180);
}
void bisection(float a , float b,char* stopCrt,float eps){
    printf("\n  -----> BISECTION METHOD <-----\n\n");

    int itr = 0;
    float p = a;
    float pre = 0;
    int n = 100;
    float err = 0;
    int reqItr = ceil(log2((b-a)/eps));
    if(f(a) * f(b) > 0){
        printf("f(a) * f(b) > 0 , no solution exists!\n");
        return;
    }
    printf("| %3s\t |  %8s  |  %8s \n","Iter","AbsError","RelError");
    do{
        ++itr;
        pre = p;
        p = (a+b) / 2;
        err = calcErr(stopCrt,p,pre);
        printf("| %3d\t |  %8f  |  %8f\n",
                itr,calcErr("ABSOLUTE_ERROR",p,pre),calcErr("RELATIVE_ERROR",p,pre));
        
        if(f(p) * f(a) > 0)
            a = p;
        else if(f(p) * f(a) < 0)
            b = p;
        
        
        
        if(itr == n){
            printf("No solution exists for this number of iterations!\n");
            exit(0);
        }
    }while(!(err < eps));
    
    printf("\nApproximate root : %f\n"
            "The number of iterations that have been executed : %d\n"
            "Theoretically required  number of iterations : %d\n",p,itr,reqItr);
    
}
