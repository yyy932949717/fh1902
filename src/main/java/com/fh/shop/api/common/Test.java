package com.fh.shop.api.common;

public class Test {
    public static void main(String[] args) {
//        int i;
//        System.out.println("请输入需要阶乘的一个整数:");
//        Scanner scanner = new Scanner(System.in);
//        i = 7;
//        System.out.println(i + "的阶乘是：" + jc(i));




        int n = 10;//第几个斐波那契数列
        int a = 1; //斐波那契数列的第一项
        int b = 1;//第二项
        long i = fib(n);//普通的递归
        long j =newFib(a,b,n);//优化后的递归
        System.out.println("第n个fibon数是--"+i);
        System.out.println("次数--"+n1);
        System.out.println("第n个fibon数是--"+j);
        System.out.println("次数--"+n2);

    }

    public static int jc(int i) {
        if (i == 0 && i == 1) {
            return 1;
        } else if (i > 0) {
            int result = i * jc(i - 1);
            return result;
        } else {
            return 1;
        }
    }

    static  int n2;
    public static long newFib(long a,long b ,long n){
        n2++;//记录被调用的次数
        if(n>=2){
            return newFib(a+b,a,n-1);//将本次计算的结果和上次计算的结果作为参数传入下一次计算中，以减少重复计算。第四法则
        }
        return a;
    }


    static  int n1;
    public static long fib(long n){
        n1++;//记录被调用的次数
        if(n<2){//基准情形，法则1
            return n;
        }
        return fib(n-1)+fib(n-2);  //通过调用自身，不断推进直到达到基准情形，法则2,3
    }
}