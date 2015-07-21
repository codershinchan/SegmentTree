package segment.tree;
import java.util.*;
import java.io.*;
public class SegmentTree {
    static Scanner sc=new Scanner(System.in);
    static int nextpowerof2(int a)
    {
        a--;
        a|=a>>1;
        a|=a>>2;
        a|=a>>4;
        a|=a>>8;
        a|=a>>16;
        a++;
        return a;
    }
    static void constructminsegmentTree(int segmenttree[],int input[],int low,int high,int pos)
    {
        if(low==high)
        {
            segmenttree[pos]=input[low];
            return;
        }
        int mid=(low+high)/2;
        constructminsegmentTree(segmenttree,input,low,mid,2*pos+1);
        constructminsegmentTree(segmenttree,input,mid+1,high,2*pos+2);
        segmenttree[pos]=Math.min(segmenttree[2*pos+1], segmenttree[2*pos+2]);
    }
    static int[] createSegmentTree(int arr[])
    {
        int nextpowerof2=nextpowerof2(arr.length);
        int segmenttree[]=new int[nextpowerof2*2-1];
        for(int i=0;i<segmenttree.length;i++)
            segmenttree[i]=Integer.MAX_VALUE;
        constructminsegmentTree(segmenttree,arr,0,arr.length-1,0);
        return segmenttree;
    }
    static void updateminimumquery(int input[],int segmenttree[],int index,int value)
    {
        input[index]=value;
        updateminimumquery(segmenttree,index,value,0,input.length-1,0);
    }
    static void updateminimumquery(int segmenttree[],int index,int value,int low,int high,int pos)
    {
        if(index<low||index>high)
            return;
        if(low==high){
            segmenttree[pos]=value;
            return;
        }
        int mid=(low+high)/2;
        updateminimumquery(segmenttree,index,value,low,mid,2*pos+1);
        updateminimumquery(segmenttree,index,value,mid+1,high,2*pos+2);
        segmenttree[pos]=Math.min(segmenttree[2*pos+1], segmenttree[2*pos+2]);
    }
    static int rangeminimumquery(int segmenttree[],int qlow,int qhigh,int len)
    {
        return rangeminimumquery(segmenttree,0,len-1,qlow,qhigh,0);
    }
    static int rangeminimumquery(int []segmenttree,int low,int high,int qlow,int qhigh,int pos)
    {
        if(qlow<=low&&qhigh>=high)
            return segmenttree[pos];
        if(qlow>high||qhigh<low)
        {
            return Integer.MAX_VALUE;
        }
        int mid=(low+high)/2;
        return(Math.min(rangeminimumquery(segmenttree,low,mid,qlow,qhigh,2*pos+1),rangeminimumquery(segmenttree,mid+1,high,qlow,qhigh,2*pos+2)));
    }
    public static void main(String[] args) {
        
        int leafs;
        System.out.println("please enter no of the leafs of segment tree");
        leafs=sc.nextInt();
        int input[]=new int[leafs];
        for(int i=0;i<leafs;i++)
        {
            System.out.println("enter the "+i+"leaf");
            input[i]=sc.nextInt();
        }
        int qlow,qhigh;
        System.out.println("Enter qlow and qhigh");
        qlow=sc.nextInt();
        qhigh=sc.nextInt();
        int segmenttree[]=createSegmentTree(input);
        System.out.println(rangeminimumquery(segmenttree,qlow,qhigh,input.length));
    }
    
}
