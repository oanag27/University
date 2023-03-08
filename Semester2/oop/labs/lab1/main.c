#include <string.h>
#include <stdio.h>
/*problem 10*/
void readVector(int numberOfElements,int vector[])
{
    /*read a vector from the console
    numberOfElements: the number of elements of the vector
    vector: the vector in which we store the values that are read from the console
    */
   printf("number of elements: ");
   scanf("%d", &numberOfElements);
   printf("\n elements: ");
   for(int i = 0; i <numberOfElements; i++)
        {
            scanf("%d", &vector[i]);
        }
}

void printVector(int numberOfElements,int vector[])
{
    /*print the values from the vector
    numberOfElements: the number of elements of the vector
    vector: the vector in which we store the values that are read from the console
    */
    for(int i = 0; i <numberOfElements; i++)
        {
            printf("%d",vector[i]);
            printf(" ");
        }
}

int checkPrime(int number)
{
    /*check if a number is prime or not*/
   
    if(number<2 || (number>2 && number%2==0))
        return 0;
    int d=2;
    while(d*d<=number)
    {
        if(number%d==0) return 0;
        d++;
    }
    return 1;
}


int decomposeNumberAsSumOfPrimeNumbers(int number)
{
    /*decompose a given even natural number, greater than 2, as a sum of two prime numbers*/
    int i, check = 0;
    if (number%2!=0 || number <2)
       {
            printf("Invalid number!!");
            return 0;
       } 

    for (i = 2; i <= number / 2; ++i) {
        // check if i is a prime number
        if (checkPrime(i) == 1) {
        // check if n-i is a prime number
        if (checkPrime(number - i) == 1) {
            return i;
        }
        }
    }
    return 0;
}

int atLeastTwoDigitsInCommon(int firstNumber,int secondNumber)
{
    /*checks if the 2 numbers have at least 2 digits in common
    firstNumber - integer
    secondNumber - integer
    return 1 - if they have the same digits, 0 otherwise
    */
    int frequenceDigitsFirst[10]={0},frequenceDigitsSecond[10]={0};
    do
    {
        frequenceDigitsFirst[firstNumber%10]++;
        firstNumber/=10;
    } while (firstNumber!=0);

    do
    {
        frequenceDigitsSecond[secondNumber%10]++;
        secondNumber/=10;
    } while (secondNumber!=0);

    int check = 0;
    for (int i=0;i<=9;i++)
    {
        if(frequenceDigitsFirst[i]==frequenceDigitsSecond[i] && frequenceDigitsFirst[i]>0)
            check++;
            
    }
    if (check>=2)
        return 1;
    return 0;
}

void longestContiguousSubsequenceConsecutiveElementsTwoDistinctDigitsInCommon(int vector[], int numberOfElements,int maximum_vector[],int *maximum_length)
{
    /*prints the longest contiguous subsequence such that any consecutive elements have at least two distinct digits in common
    numberOfElements - integer
    vector - vector of integers
    */
    
    int new_vector[101]={0},new_length=1;
    for(int i=0;i<numberOfElements-1;i++)
    {
        if(atLeastTwoDigitsInCommon(vector[i],vector[i+1])==1)
        {
            new_vector[new_length-1]=vector[i];
            new_vector[new_length]=vector[i+1];
            new_length++;
            if (new_length>=*maximum_length)
            {
                for(int j=0;j<new_length;j++)
                    maximum_vector[j]=new_vector[j];
                *maximum_length = new_length;
            }
        }
        else
        {
            for(int j=0;j<numberOfElements;j++)
                new_vector[j] = 0;
            new_length=1;
        }
    }
}
void menu()
{
    printf("Choose one command: ");
    printf("Type 1 for reading a vector of numbers from the console\n");
    printf("Type 2 for decomposing a given even natural number, greater than 2, as a sum of two prime numbers\n");
    printf("Type 3 for finding the longest contiguous subsequence such that any consecutive elements have at least 2 distinct digits in common\n");
    printf("Type 4 for exiting the program\n");
}


int main()
{
    int finished=0;
    while (finished!=1)
    {
        menu();
        char command[10];
        printf("command: ");
        scanf("%s", command);
        if (strlen(command)!=1)
        {
            printf("Invalid command!");
            finished=0;
        }else
        {
            if(strchr(command,'1')!=0)
                {
                int numberOfElements = 0,vector[101];
                readVector(numberOfElements, vector);
                finished=0;
                }
            else
                if(strchr(command,'2')!=0)
                {
                int number;
                printf("Enter an even number, greater than 2: ");
                scanf("%d", &number);
                int firstPrimeNumber;
                firstPrimeNumber = decomposeNumberAsSumOfPrimeNumbers(number);
                if (firstPrimeNumber!=0)
                    printf("%d = %d + %d\n", number, firstPrimeNumber, number - firstPrimeNumber);
                else
                    printf("There are not any prime numbers, whose sum to  be equal to the number given");
                finished=0;
                }
                else
                    if(strchr(command,'3')!=0)
                    {
                    int numberOfElements = 0,vector[101],maximum_length=1,maximum_vector[101]={0};
                    //readVector(numberOfElements, vector);
                    printf(" number of elements: ");
                    scanf("%d", &numberOfElements);
                    printf(" elements: ");
                    for(int i = 0; i <numberOfElements; i++)
                            {
                                scanf("%d ", &vector[i]);

                            }
                    //printf("%d ",maximum_length);
                    longestContiguousSubsequenceConsecutiveElementsTwoDistinctDigitsInCommon(vector,numberOfElements,maximum_vector,&maximum_length);
                    //printVector(maximum_length,maximum_vector);
                    printf("  %d \n",maximum_length);
                    for(int i=0;i<maximum_length;i++)
                        printf("%d ",maximum_vector[i]);
                    finished=1;
                    }
                    else
                        if(strchr(command,'4')!=0)
                            {
                            printf("Exit!");
                            finished = 1;
                            break;
                            }

        }

    }
    return 0;
}
        
    
