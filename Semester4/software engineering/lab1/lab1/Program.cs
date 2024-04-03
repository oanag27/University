using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab1
{
    internal class Program
    {
        static int palindromeCheck(int number)
        {
            int reverseNumber = 0;
            int copyOfTheNumber = number;
            int digit = 0;
            while (copyOfTheNumber > 0)
            {
                digit = copyOfTheNumber % 10;
                reverseNumber = reverseNumber * 10 + digit;
                copyOfTheNumber = copyOfTheNumber / 10;
            }
            if (number == reverseNumber)
            {
                return 1;
            }
            return 0;
        }
        static void Main(string[] args)
        {
            //11.
            //A palindromic number reads the same both ways.
            //The largest palindrome made from
            //the product of two 2 - digit numbers is
            //9009 = 91 * 99, Find the largest palindrome made
            //from the product of two 3 - digit numbers
            int largestPalindrome = 0;
            int product = 0;
            for(int i=100;i<=999;i++)
            {
                for(int j=100;j<=999;j++)
                {
                    product = i * j;
                    if(palindromeCheck(product)==1 && product>largestPalindrome)
                    {
                         largestPalindrome = product;
                    }
                }
            }
            Console.WriteLine("The largest palindrome made from the product of two 3-digit numbers is:" + largestPalindrome);
        }
    }
}
